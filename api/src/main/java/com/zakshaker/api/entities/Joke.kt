package com.zakshaker.api.entities

import com.google.gson.annotations.SerializedName

data class Joke(
    @SerializedName("id") val id: String,
    @SerializedName("joke") val text: String
)