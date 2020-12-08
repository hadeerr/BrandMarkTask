package com.example.brandmarktask.model

import androidx.room.Entity

@Entity(tableName = "favorites")
data class Favorite(
    var description: String,
    var favorite_count: Int,
    var id: Int,
    var iso_639_1: String,
    var item_count: Int,
    var list_type: String,
    var name: String,
    var poster_path: Any
)