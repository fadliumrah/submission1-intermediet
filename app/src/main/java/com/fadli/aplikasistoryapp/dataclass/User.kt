package com.fadli.aplikasistoryapp.dataclass

import com.google.gson.annotations.SerializedName

data class User(

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String
)