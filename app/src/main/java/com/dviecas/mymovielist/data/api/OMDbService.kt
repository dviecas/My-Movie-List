package com.dviecas.mymovielist.data.api

import com.dviecas.mymovielist.data.api.model.ApiMovie
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET


interface OMDbService {

    @GET("top250_min.json")
    fun getMovies(): Single<List<ApiMovie>>
}