package com.zakshaker.dadjokes.domain.entities

import com.google.gson.annotations.SerializedName

data class Joke(
    @SerializedName("id") val id: String,
    @SerializedName("value") val value: String
)