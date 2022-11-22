package com.example.veloanantes.api;

import com.example.veloanantes.model.Parking;

import retrofit2.Response;
import retrofit2.http.GET;

 interface ParkingApi {
    @GET("api/parkings")
    suspend fun getparkings():Response<List<Parking>>
}
