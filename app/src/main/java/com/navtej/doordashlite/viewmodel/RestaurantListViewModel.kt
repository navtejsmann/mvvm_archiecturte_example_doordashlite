package com.navtej.doordashlite.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.navtej.doordashlite.model.DoorDashRepository
import com.navtej.doordashlite.model.RestaurantDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class RestaurantListViewModel @Inject constructor(
    private val doorDashRepository: DoorDashRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<RestaurantListUiState>(RestaurantListUiState.Loading)
    val uiState: StateFlow<RestaurantListUiState> = _uiState.asStateFlow()

    init {
        loadRestaurants()
    }

    fun loadRestaurants() {
        viewModelScope.launch {
            val options = mapOf(
                "lat" to "37.422740",
                "lng" to "-122.139956",
                "offset" to "0",
                "limit" to "50"
            )
            doorDashRepository.getRestaurantList(options)
                .catch { e ->
                    _uiState.value = RestaurantListUiState.Error(e.message ?: "Unknown error")
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { list ->
                            _uiState.value = RestaurantListUiState.Success(list ?: emptyList())
                        },
                        onFailure = { e ->
                            _uiState.value = RestaurantListUiState.Error(e.message ?: "Unknown error")
                        }
                    )
                }
        }
    }

    sealed class RestaurantListUiState {
        data object Loading : RestaurantListUiState()
        data class Success(val restaurants: List<RestaurantDetail>) : RestaurantListUiState()
        data class Error(val message: String) : RestaurantListUiState()
    }
}
