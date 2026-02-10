package com.navtej.doordashlite.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.navtej.doordashlite.model.DoorDashRepository
import com.navtej.doordashlite.model.RestaurantDetail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class RestaurantViewModel @Inject constructor(
    private val doorDashRepository: DoorDashRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _restaurantId = MutableStateFlow<String?>(null)
    private val _uiState = MutableStateFlow<RestaurantDetailUiState>(RestaurantDetailUiState.Loading)
    val uiState: StateFlow<RestaurantDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _restaurantId
                .flatMapLatest { id ->
                    if (id.isNullOrBlank()) {
                        flowOf(RestaurantDetailUiState.Loading)
                    } else {
                        flow {
                            emit(RestaurantDetailUiState.Loading)
                            doorDashRepository.getRestaurantDetails(id)
                                .collect { result ->
                                    val state = result.fold(
                                        onSuccess = { RestaurantDetailUiState.Success(it) },
                                        onFailure = { RestaurantDetailUiState.Error(it.message ?: "Unknown error") }
                                    )
                                    emit(state as RestaurantDetailUiState.Loading)
                                }
                        }
                    }
                }
                .collect { _uiState.value = it }
        }
    }

    fun setRestaurantId(restaurantId: String) {
        _restaurantId.value = restaurantId
    }

    sealed class RestaurantDetailUiState {
        data object Loading : RestaurantDetailUiState()
        data class Success(val restaurant: RestaurantDetail) : RestaurantDetailUiState()
        data class Error(val message: String) : RestaurantDetailUiState()
    }
}
