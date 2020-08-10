package com.dviecas.mymovielist.data.api.model

import com.dviecas.mymovielist.ui.model.Movie
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ApiMovie(
    val name: String,
    val desc: String,
    val rating: Double,
    val thumb_url: String,
    val imdb_url: String
)

fun ApiMovie.toMovie() = Movie(
    id = this.imdb_url,
    name = this.name,
    description = this.desc,
    rating = this.rating,
    posterUrl = thumb_url
)
