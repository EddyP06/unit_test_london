package com.eddy.unittesting

import com.eddy.unittesting.data.datasource.CarRemoteDataSource
import com.eddy.unittesting.data.datasource.CarRemoteDataSourceImpl
import com.eddy.unittesting.data.model.request.Person
import com.eddy.unittesting.data.network.ApiClient
import com.eddy.unittesting.repository.CarRepository
import com.eddy.unittesting.repository.CarRepositoryImpl
import com.eddy.unittesting.repository.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.MockitoKotlinException
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import retrofit2.Response

@ExperimentalCoroutinesApi
class CarRepositoryUnitTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val apiClient: ApiClient = Mockito.mock(ApiClient::class.java)
    private val remoteDataSource: CarRemoteDataSource = CarRemoteDataSourceImpl(apiClient)

    @Before
    fun before(){
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun after(){
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `given response is successful and contains response body when fetch the car list then return successful resource with given response body`() = testDispatcher.runBlockingTest {
        // given
        val data = getFakeCarList()
        whenever(apiClient.getCars(any())).thenReturn(Response.success(data))
        val repo: CarRepository = CarRepositoryImpl(remoteDataSource)

        // when
        val result = repo.getCars(Person(0L, "Johnny"))

        //then
        assert(result is Resource.Success)
        Assert.assertEquals( data, (result as Resource.Success).data )
    }

    @Test
    fun `given response is successful and response body is null when fetch the car list then return error resource with empty list`() = testDispatcher.runBlockingTest {
        // given
        whenever(apiClient.getCars(any())).thenReturn(Response.success(null))
        val repo: CarRepository = CarRepositoryImpl(remoteDataSource)

        // when
        val result = repo.getCars(Person(0L, "Johnny"))

        //then
        assert(result is Resource.Error)
        Assert.assertEquals( null, (result as Resource.Error).data )
    }

    @Test
    fun `given response is failed when fetch the car list then return error resource with null list`() = testDispatcher.runBlockingTest {
        // given
        whenever(apiClient.getCars(any())).thenReturn(Response.error(404, ResponseBody.create(null, "")))
        val repo: CarRepository = CarRepositoryImpl(remoteDataSource)

        // when
        val result = repo.getCars(Person(0L, "Johnny"))

        //then
        assert(result is Resource.Error)
        Assert.assertEquals( null, (result as Resource.Error).data )
    }

    @Test
    fun `given response is failed because of external error when fetch the car list then return error resource with exception message`() = testDispatcher.runBlockingTest {
        // given
        whenever(apiClient.getCars(any())).thenThrow(MockitoKotlinException(null, null))
        val repo: CarRepository = CarRepositoryImpl(remoteDataSource)

        // when
        val result = repo.getCars(Person(0L, "Johnny"))

        // then
        assert(result is Resource.Error)
        Assert.assertEquals( null, (result as Resource.Error).data )
    }


}