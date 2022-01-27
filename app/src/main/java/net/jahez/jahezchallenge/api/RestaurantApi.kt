package net.jahez.jahezchallenge.api

import net.jahez.jahezchallenge.data.Restaurant
import retrofit2.http.GET

interface RestaurantApi {

    @GET("restaurants.json")
    suspend fun getRestaurants(): List<Restaurant>

}