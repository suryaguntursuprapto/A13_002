package com.example.tugasakhirpam.repository

import com.example.tugasakhirpam.model.Tiket
import com.example.tugasakhirpam.service_api.TiketService
import okio.IOException

interface TiketRepository {

    suspend fun insertTiket(tiket: Tiket)

    suspend fun getAllTikets(): List<Tiket>

    suspend fun updateTiket(idTiket: String, tiket: Tiket)

    suspend fun deleteTiket(idTiket: String)

    suspend fun getTiketById(idTiket: String): Tiket
}

class NetworkTiketRepository(
    private val tiketApiService: TiketService
) : TiketRepository {

    override suspend fun insertTiket(tiket: Tiket) {
        tiketApiService.insertTiket(tiket)
    }

    override suspend fun updateTiket(idTiket: String, tiket: Tiket) {
        tiketApiService.updateTiket(idTiket, tiket)
    }

    override suspend fun deleteTiket(idTiket: String) {
        try {
            val response = tiketApiService.deleteTiket(idTiket)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete tiket. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAllTikets(): List<Tiket> =
        tiketApiService.getAllTiket()

    override suspend fun getTiketById(idTiket: String): Tiket {
        return tiketApiService.getTiketById(idTiket)
    }
}