package com.eddy.unittesting.data.datasource

import com.eddy.unittesting.data.model.request.Person
import com.eddy.unittesting.data.network.ApiClient

class CarRemoteDataSourceImpl(
    private val apiClient: ApiClient
) : CarRemoteDataSource {
    override suspend fun getCars(person: Person) =
        apiClient.getCars(person)
}