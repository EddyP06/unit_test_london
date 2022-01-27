package com.eddy.unittesting

import com.eddy.unittesting.data.model.enums.CarType
import com.eddy.unittesting.data.model.request.Person
import com.eddy.unittesting.data.model.response.CarResponse

fun getFakeCarList(): List<CarResponse> {
    val result = mutableListOf<CarResponse>()
    (0..10L).forEach {
        result.add(
            CarResponse(id = it, name = "car_name_$it", type = when {
                it % 3 == 0L -> CarType.RED.value
                it % 3 == 1L -> CarType.BLUE.value
                else -> CarType.GREEN.value
            })
        )
    }
    return result
}

fun getRedCars(): List<CarResponse> {
    val result = mutableListOf<CarResponse>()
    (0..3L).forEach {
        result.add(
            CarResponse(id = it, name = "car_name_$it", type = CarType.RED.value)
        )
    }
    return result
}

fun getGreenCars(): List<CarResponse> {
    val result = mutableListOf<CarResponse>()
    (0..3L).forEach {
        result.add(
            CarResponse(id = it, name = "car_name_$it", type = CarType.GREEN.value)
        )
    }
    return result
}

fun getBlueCars(): List<CarResponse> {
    val result = mutableListOf<CarResponse>()
    (0..3L).forEach {
        result.add(
            CarResponse(id = it, name = "car_name_$it", type = CarType.BLUE.value)
        )
    }
    return result
}

fun getSimplePerson() = Person(0L, "name")