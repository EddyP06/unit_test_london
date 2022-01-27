package com.eddy.unittesting.data.network

import com.eddy.unittesting.data.model.request.Person
import com.eddy.unittesting.data.model.response.CarResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface ApiClient {

    @GET("getCars")
    suspend fun getCars(@Body person: Person): Response<List<CarResponse>>
}