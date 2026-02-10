package com.navtej.doordashlite.model

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface DoorDashService {
    @GET("/v2/restaurant/")
    suspend fun getRestaurantList(@QueryMap options: Map<String, String>): List<RestaurantDetail>

    @GET("/v2/restaurant/{restaurant_id}/")
    suspend fun getRestaurantDetails(@Path("restaurant_id") restaurantId: String): RestaurantDetail

    companion object {
        const val HTTPS_API_DOORDASH_URL = "https://api.doordash.com"
    }
}
