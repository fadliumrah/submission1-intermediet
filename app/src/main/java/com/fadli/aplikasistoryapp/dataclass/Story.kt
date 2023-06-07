package com.fadli.aplikasistoryapp.dataclass

import com.google.gson.annotations.SerializedName

data class Story(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,


    )