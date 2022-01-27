package com.eddy.unittesting.domain

import com.eddy.unittesting.data.model.request.Person
import com.eddy.unittesting.data.model.response.CarResponse
import com.eddy.unittesting.repository.util.Resource

interface CarUseCase {
    suspend fun getRedCars(person: Person): Resource<List<CarResponse>>
}