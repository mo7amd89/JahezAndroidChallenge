package net.jahez.jahezchallenge.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Restaurant(
    val description: String?,
    val distance: Double?,
    val hasOffer: Boolean?,
    val hours: String?,
    @PrimaryKey val id: Int?,
    val image: String?,
    val name: String?,
    val offer: String?,
    val rating: Double?
)