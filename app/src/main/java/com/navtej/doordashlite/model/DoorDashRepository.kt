package com.navtej.doordashlite.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DoorDashRepository @Inject constructor(
    private val doorDashService: DoorDashService
) {
    fun getRestaurantList(options: Map<String, String>): Flow<Result<List<RestaurantDetail>>> = flow {
        try {
            val list = doorDashService.getRestaurantList(options)
            emit(Result.success(list))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    fun getRestaurantDetails(restaurantId: String): Flow<Result<RestaurantDetail>> = flow {
        try {
            val detail = doorDashService.getRestaurantDetails(restaurantId)
            emit(Result.success(detail))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}
