package com.vicky.apps.rockpaperscissor.data.remote


import io.reactivex.Single
import retrofit2.http.GET


interface ApiService {
    @GET("")
    fun getDataFromService(): Single<List<Any>>
}
