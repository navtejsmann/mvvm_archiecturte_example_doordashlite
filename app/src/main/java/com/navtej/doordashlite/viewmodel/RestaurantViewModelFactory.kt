package com.navtej.doordashlite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.navtej.doordashlite.di.ViewModelSubComponent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantViewModelFactory @Inject constructor(
    private val viewModelSubComponentBuilder: ViewModelSubComponent.Builder
) : ViewModelProvider.Factory {

    private val viewModelSubComponent by lazy {
        viewModelSubComponentBuilder.build()
    }

    private val creators: Map<Class<*>, () -> ViewModel> = mapOf(
        RestaurantViewModel::class.java to { viewModelSubComponent.restaurantViewModel() },
        RestaurantListViewModel::class.java to { viewModelSubComponent.restaurantListViewModel() }
    )

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass]
            ?: creators.entries.firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: throw IllegalArgumentException("Unknown model class $modelClass")
        return creator() as T
    }
}
