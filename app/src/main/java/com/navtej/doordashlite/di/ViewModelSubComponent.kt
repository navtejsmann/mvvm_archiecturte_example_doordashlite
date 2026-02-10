package com.navtej.doordashlite.di

import com.navtej.doordashlite.viewmodel.RestaurantListViewModel
import com.navtej.doordashlite.viewmodel.RestaurantViewModel
import dagger.Subcomponent

/**
 * A sub component to create ViewModels. It is called by [RestaurantViewModelFactory].
 */
@Subcomponent
interface ViewModelSubComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubComponent
    }

    fun restaurantListViewModel(): RestaurantListViewModel
    fun restaurantViewModel(): RestaurantViewModel
}
