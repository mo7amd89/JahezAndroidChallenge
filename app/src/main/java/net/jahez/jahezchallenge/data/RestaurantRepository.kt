package net.jahez.jahezchallenge.data

import androidx.room.withTransaction
import kotlinx.coroutines.delay
import net.jahez.jahezchallenge.api.RestaurantApi
import net.jahez.jahezchallenge.util.networkBoundResource
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
    private val api: RestaurantApi,
    private val db: RestaurantDatabase
) {
    private val dao = db.restaurantDao()

    fun getRestaurants() = networkBoundResource(
        query = {
            dao.getAllRestaurants()
        },
        fetch = {
            delay(2000)
            api.getRestaurants()
        },
        saveFetchResult = { restaurants ->
            db.withTransaction {
                dao.deleteAllRestaurants()
                dao.insertRestaurants(restaurants)
            }
        }
    )

}