package com.navtej.doordashlite.di

import androidx.lifecycle.ViewModelProvider
import com.navtej.doordashlite.BuildConfig
import com.navtej.doordashlite.model.DoorDashService
import com.navtej.doordashlite.viewmodel.RestaurantViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(subcomponents = [ViewModelSubComponent::class])
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindViewModelFactory(factory: RestaurantViewModelFactory): ViewModelProvider.Factory

    @Module
    companion object {
        @Singleton
        @Provides
        @JvmStatic
        fun provideDoorDashService(): DoorDashService {
            val builder = Retrofit.Builder()
                .baseUrl(DoorDashService.HTTPS_API_DOORDASH_URL)
                .addConverterFactory(GsonConverterFactory.create())

            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
                val okHttp = OkHttpClient.Builder().addInterceptor(logging).build()
                builder.client(okHttp)
            }

            return builder.build().create(DoorDashService::class.java)
        }
    }
}
