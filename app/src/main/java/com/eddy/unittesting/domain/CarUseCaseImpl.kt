package com.eddy.unittesting.domain

import com.eddy.unittesting.data.model.enums.CarType
import com.eddy.unittesting.data.model.request.Person
import com.eddy.unittesting.data.model.response.CarResponse
import com.eddy.unittesting.repository.CarRepository
import com.eddy.unittesting.repository.util.Resource

class CarUseCaseImpl(
    private val repository: CarRepository
) : CarUseCase {
    override suspend fun getRedCars(person: Person): Resource<List<CarResponse>> {
        return when (val result = repository.getCars(person)) {
            is Resource.Success -> Resource.Success(filterRedCars(result.data))
            is Resource.Error -> result
            Resource.Loading -> Resource.Error(IllegalStateException("Should not reach this line"))
        }
    }

    private fun filterRedCars(cars: List<CarResponse>): List<CarResponse> =
        cars.filter { it.type == CarType.RED.value }
}