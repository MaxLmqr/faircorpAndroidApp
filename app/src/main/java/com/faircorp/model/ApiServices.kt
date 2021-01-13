package com.faircorp.model

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// Define api.
// Did not need to create heaterApi yet because lodasing the list of heaters is done by the room API Service.
class ApiServices {
    val windowsApiService: WindowApiService by lazy {
        Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl("https://max-lemasquerier.cleverapps.io/api/")
                .build()
                .create(WindowApiService::class.java)
    }

    val roomApiService: RoomApiService by lazy {
        Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl("https://max-lemasquerier.cleverapps.io/api/")
                .build()
                .create(RoomApiService::class.java)
    }

    val buildingsApiService: BuildingApiService by lazy {
        Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl("https://max-lemasquerier.cleverapps.io/api/")
                .build()
                .create(BuildingApiService::class.java)
    }
}