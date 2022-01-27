package com.eddy.unittesting.data.model.request

import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("name")
    val name: String
)