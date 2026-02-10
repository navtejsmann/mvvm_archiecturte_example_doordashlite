package com.navtej.doordashlite.view.callback

import com.navtej.doordashlite.model.RestaurantDetail

fun interface RestaurantClickCallback {
    fun onClick(restaurantDetail: RestaurantDetail)
}
