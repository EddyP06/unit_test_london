package com.eddy.unittesting.data.model.response

import com.eddy.unittesting.data.model.enums.CarType
import com.google.gson.annotations.SerializedName

data class CarResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String = CarType.RED.value
)