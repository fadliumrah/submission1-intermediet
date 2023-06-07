package com.fadli.aplikasistoryapp.api

import com.fadli.aplikasistoryapp.dataclass.Login
import com.fadli.aplikasistoryapp.dataclass.Register
import com.fadli.aplikasistoryapp.dataclass.StoryList
import com.fadli.aplikasistoryapp.dataclass.StoryUpload
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @POST("register")
    @FormUrlEncoded
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<Register>

    @POST("login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<Login>


    @GET("stories")
    fun getStoryList(
        @Header("Authorization") token: String,
        @Query("size") size: Int
    ): Call<StoryList>

    @Multipart
    @POST("stories")
    fun uploadStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<StoryUpload>
}