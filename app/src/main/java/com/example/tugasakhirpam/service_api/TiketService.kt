package com.example.tugasakhirpam.service_api

import com.example.tugasakhirpam.model.Tiket
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TiketService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("inserttiket.php")
    suspend fun insertTiket(@Body tiket: Tiket)

    @GET("bacatiket.php")
    suspend fun getAllTiket(): List<Tiket>

    @GET("baca1tiket.php/{id_tiket}")
    suspend fun getTiketById(@Query("id_tiket") idTiket: String): Tiket

    @PUT("edittiket.php/{id_tiket}")
    suspend fun updateTiket(@Query("id_tiket") idTiket: String, @Body tiket: Tiket)

    @DELETE("deletetiket.php/{id_tiket}")
    suspend fun deleteTiket(@Query("id_tiket") idTiket: String): Response<Void>
}