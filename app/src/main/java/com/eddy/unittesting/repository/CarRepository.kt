package com.eddy.unittesting.repository

import com.eddy.unittesting.data.model.request.Person
import com.eddy.unittesting.data.model.response.CarResponse
import com.eddy.unittesting.repository.util.Resource

interface CarRepository {
    suspend fun getCars(person: Person): Resource<List<CarResponse>>
}