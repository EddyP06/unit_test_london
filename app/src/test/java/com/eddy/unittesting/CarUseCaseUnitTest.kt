package com.eddy.unittesting

import com.eddy.unittesting.data.model.response.CarResponse
import com.eddy.unittesting.domain.CarUseCase
import com.eddy.unittesting.domain.CarUseCaseImpl
import com.eddy.unittesting.repository.CarRepository
import com.eddy.unittesting.repository.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.MockitoKotlinException
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CarUseCaseUnitTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val repository: CarRepository = Mockito.mock(CarRepository::class.java)

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
    fun `given success response with list of all cars when get filtered list of red cars then return list of only red cars`() = testDispatcher.runBlockingTest {
        // given
        val redCarsData = getRedCars()
        val data = (redCarsData + getGreenCars() + getBlueCars()).shuffled()
        whenever(repository.getCars(any())).thenReturn(Resource.Success(data))
        val useCase: CarUseCase = CarUseCaseImpl(repository)

        // when
        val result = useCase.getRedCars(getSimplePerson())

        // then
        Assert.assertTrue(result is Resource.Success)
        Assert.assertEquals(redCarsData.sortedBy { it.id }, (result as Resource.Success).data.sortedBy { it.id })
    }

    @Test
    fun `given success response with list of empty red cars when get filtered list of red cars then return empty cars list`() = testDispatcher.runBlockingTest {
        // given
        val data = ( getGreenCars() + getBlueCars()).shuffled()
        whenever(repository.getCars(any())).thenReturn(Resource.Success(data))
        val useCase: CarUseCase = CarUseCaseImpl(repository)

        // when
        val result = useCase.getRedCars(getSimplePerson())

        // then
        Assert.assertTrue(result is Resource.Success)
        Assert.assertEquals(emptyList<CarResponse>().sortedBy { it.id }, (result as Resource.Success).data.sortedBy { it.id })
    }

    @Test
    fun `given success response with empty list when get filtered list of red cars then return empty list`() = testDispatcher.runBlockingTest {
        // given
        val data = emptyList<CarResponse>()
        whenever(repository.getCars(any())).thenReturn(Resource.Success(data))
        val useCase: CarUseCase = CarUseCaseImpl(repository)

        // when
        val result = useCase.getRedCars(getSimplePerson())

        // then
        Assert.assertTrue(result is Resource.Success)
        Assert.assertEquals(data.sortedBy { it.id }, (result as Resource.Success).data.sortedBy { it.id })
    }

    @Test
    fun `given error response when get filtered list of red cars then return null`() = testDispatcher.runBlockingTest {
        // given
        whenever(repository.getCars(any())).thenReturn(Resource.Error(MockitoKotlinException(null, null)))
        val useCase: CarUseCase = CarUseCaseImpl(repository)

        // when
        val result = useCase.getRedCars(getSimplePerson())

        // then
        assert(result is Resource.Error)
        Assert.assertEquals(null, (result as Resource.Error).data)

    }
}