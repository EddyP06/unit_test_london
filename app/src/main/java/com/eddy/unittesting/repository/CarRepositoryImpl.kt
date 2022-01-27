package com.eddy.unittesting.repository

import com.eddy.unittesting.data.datasource.CarRemoteDataSource
import com.eddy.unittesting.data.model.request.Person
import com.eddy.unittesting.data.model.response.CarResponse
import com.eddy.unittesting.repository.util.Resource

class CarRepositoryImpl(
    private val remoteDataSource: CarRemoteDataSource
) : CarRepository {
    // there is better to return flow and emmit different states but for simplicity just return Resource
    override suspend fun getCars(person: Person): Resource<List<CarResponse>> {
        return try {
            val response = remoteDataSource.getCars(person)
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(it)
                } ?:  Resource.Error(Exception("empty response"))
            } else {
                Resource.Error(Exception(response.message()))
            }
        } catch (ex: Exception) {
            Resource.Error(ex, null)
        }
    }
}