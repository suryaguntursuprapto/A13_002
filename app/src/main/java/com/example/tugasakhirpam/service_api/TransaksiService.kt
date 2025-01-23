package com.example.tugasakhirpam.service_api

import com.example.tugasakhirpam.model.Transaksi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TransaksiService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("inserttransaksi.php")
    suspend fun insertTransaksi(@Body transaksi: Transaksi)

    @GET("bacatransaksi.php")
    suspend fun getAllTransaksi(): List<Transaksi>

    @GET("baca1transaksi.php/{id_transaksi}")
    suspend fun getTransaksiById(@Query("id_transaksi") idTransaksi: String): Transaksi

    @PUT("edittransaksi.php/{id_transaksi}")
    suspend fun updateTransaksi(@Query("id_transaksi") idTransaksi: String, @Body transaksi: Transaksi)

    @DELETE("deletetransaksi.php/{id_transaksi}")
    suspend fun deleteTransaksi(@Query("id_transaksi") idTransaksi: String): Response<Void>
}