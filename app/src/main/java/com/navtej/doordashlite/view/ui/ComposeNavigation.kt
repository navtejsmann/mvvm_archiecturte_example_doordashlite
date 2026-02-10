package com.navtej.doordashlite.view.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

val LocalViewModelFactory = compositionLocalOf<ViewModelProvider.Factory> {
    error("No ViewModelFactory provided")
}

object Routes {
    const val RESTAURANT_LIST = "restaurant_list"
    const val RESTAURANT_DETAIL = "restaurant_detail/{restaurantId}"
    fun restaurantDetail(restaurantId: String) = "restaurant_detail/$restaurantId"
}

@Composable
fun DoorDashLiteNavGraph(
    viewModelFactory: ViewModelProvider.Factory
) {
    val navController = rememberNavController()
    androidx.compose.runtime.CompositionLocalProvider(
        LocalViewModelFactory provides viewModelFactory
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.RESTAURANT_LIST
        ) {
            composable(Routes.RESTAURANT_LIST) {
                RestaurantListScreen(
                    onRestaurantClick = { restaurant ->
                        restaurant.id?.let { id ->
                            navController.navigate(Routes.restaurantDetail(id))
                        }
                    }
                )
            }
            composable(
                route = Routes.RESTAURANT_DETAIL,
                arguments = listOf(navArgument("restaurantId") { type = NavType.StringType })
            ) { backStackEntry ->
                val restaurantId = backStackEntry.arguments?.getString("restaurantId") ?: return@composable
                RestaurantDetailScreen(
                    restaurantId = restaurantId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
