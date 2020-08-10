package com.dviecas.mymovielist.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dviecas.mymovielist.ui.model.Movie


@Entity
data class MovieDto(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name= "description") val description: String,
    @ColumnInfo(name = "rating") val rating: Double,
    @ColumnInfo(name = "posterUrl") val posterUrl: String
)

fun MovieDto.toMovie() = Movie(
    id = id,
    name = name,
    description = description,
    rating = rating,
    posterUrl = posterUrl
)