package net.jahez.jahezchallenge.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.jahez.jahezchallenge.BuildConfig
import net.jahez.jahezchallenge.api.RestaurantApi
import net.jahez.jahezchallenge.data.RestaurantDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    const val BASE_URL = "https://jahez-other-oniiphi8.s3.eu-central-1.amazonaws.com/"

   /* @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()*/


    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder().readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder().readTimeout(2, TimeUnit.MINUTES)
        .writeTimeout(2, TimeUnit.MINUTES)
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()



    @Provides
    @Singleton
    fun provideRestaurantApi(retrofit: Retrofit): RestaurantApi =
        retrofit.create(RestaurantApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): RestaurantDatabase =
        Room.databaseBuilder(app, RestaurantDatabase::class.java, "jahez_db")
            .build()
}