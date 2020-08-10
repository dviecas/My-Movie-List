package com.dviecas.mymovielist.ui.model

import com.dviecas.mymovielist.data.db.model.MovieDto


data class Movie(
    val id: String,
    val name: String,
    val description: String,
    val rating: Double,
    val posterUrl: String,
    var expanded: Boolean = false,
    var favorite: Boolean = false
)

fun Movie.toDto() = MovieDto(
    id = id,
    name = name,
    description = description,
    rating = rating,
    posterUrl = posterUrl
)