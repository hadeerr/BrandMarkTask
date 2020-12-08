package com.example.brandmarktask.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
data class MovieDetailModel(
    @SerializedName("description")
    var description: String,
    @SerializedName("favorite_count")
    var favorite_count: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("iso_639_1")
    var iso_639_1: String,
    @SerializedName("item_count")
    var item_count: Int,
    @SerializedName("list_type")
    var list_type: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("poster_path")
    var poster_path: Any
)