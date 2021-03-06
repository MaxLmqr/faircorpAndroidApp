package com.faircorp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RoomApiService {
    @GET("rooms")
    fun findAll(): Call<List<RoomDto>>

    @GET("rooms/{id}")
    fun findById(@Path("id") id: Long): Call<RoomDto>

    @GET("rooms/{id}/windows")
    fun findRoomWindows(@Path("id") id: Long): Call<List<WindowDto>>

    @GET("rooms/{id}/heaters")
    fun findRoomHeaters(@Path("id") id: Long): Call<List<HeaterDto>>

    @GET("rooms/{id}/switchWindows")
    fun switchRoomWindows(@Path("id") id: Long): Call<Void>

    @GET("rooms/{id}/switchHeaters")
    fun switchRoomHeaters(@Path("id") id: Long): Call<Void>
}