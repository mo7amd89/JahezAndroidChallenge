package net.jahez.jahezchallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import net.jahez.jahezchallenge.data.RestaurantRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: RestaurantRepository
) : ViewModel() {

    val restaurants = repo.getRestaurants().asLiveData()
}