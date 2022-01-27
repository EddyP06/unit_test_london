package com.eddy.unittesting.data.datasource

import com.eddy.unittesting.data.model.request.Person
import com.eddy.unittesting.data.model.response.CarResponse
import retrofit2.Response

interface CarRemoteDataSource {
    suspend fun getCars(person: Person): Response<List<CarResponse>>
}