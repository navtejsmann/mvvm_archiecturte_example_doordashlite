package com.navtej.doordashlite.di;

import com.navtej.doordashlite.BuildConfig;
import com.navtej.doordashlite.model.DoorDashService;
import com.navtej.doordashlite.viewmodel.RestaurantViewModelFactory;

import javax.inject.Singleton;

import androidx.lifecycle.ViewModelProvider;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(subcomponents = ViewModelSubComponent.class)
class AppModule {
    @Singleton @Provides
    DoorDashService provideDoorDashService() {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(DoorDashService.HTTPS_API_DOORDASH_URL)
                .addConverterFactory(GsonConverterFactory.create());

        if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor);
            builder.client(okHttpBuilder.build());
        }

        DoorDashService doorDashService = builder.build().create(DoorDashService.class);

        return doorDashService;
    }

    @Singleton
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(
            ViewModelSubComponent.Builder viewModelSubComponent) {

        return new RestaurantViewModelFactory(viewModelSubComponent.build());
    }
}
