package com.my.note_app.DI

import androidx.transition.Visibility.Mode
import com.my.note_app.Api.AuthInterceptor
import com.my.note_app.Api.UserApi
import com.my.note_app.Utils.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModel {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun provideUserApi(retrofitBuilder: Retrofit.Builder): UserApi {
        return retrofitBuilder.build().create(UserApi::class.java)
    }


    @Singleton
    @Provides
    fun provideOkHttpClint(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }


    //with header
    @Singleton
    @Provides
    fun provideAuthUserApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): UserApi {
        return retrofitBuilder.client(okHttpClient).build().create(UserApi::class.java)
    }

}