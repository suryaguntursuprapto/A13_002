package com.example.tugasakhirpam.repository

import com.example.tugasakhirpam.model.Tiket
import com.example.tugasakhirpam.service_api.EventService
import com.example.tugasakhirpam.service_api.PesertaService
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
    private val tiketApiService: TiketService,
    private val eventApiService: EventService, // Assuming you have a service for events
    private val userApiService: PesertaService
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

    override suspend fun getAllTikets(): List<Tiket> {
        val tiketList = tiketApiService.getAllTiket()

        // Fetch event and user names for each tiket
        return tiketList.map { tiket ->
            val eventName = eventApiService.getEventById(tiket.idEvent)?.namaEvent ?: "Unknown Event"
            val userName = userApiService.getPesertaById(tiket.idPengguna)?.namaPeserta ?: "Unknown User"

            // Return a new Tiket with added eventName and userName
            tiket.copy(idEvent = eventName, idPengguna = userName)
        }
    }

    override suspend fun getTiketById(idTiket: String): Tiket {
        val tiket = tiketApiService.getTiketById(idTiket)

        // Fetch event and user names for the specific tiket
        val eventName = eventApiService.getEventById(tiket.idEvent)?.namaEvent ?: "Unknown Event"
        val userName = userApiService.getPesertaById(tiket.idPengguna)?.namaPeserta ?: "Unknown User"

        // Return a Tiket with added eventName and userName
        return tiket.copy(idEvent = eventName, idPengguna = userName)
    }
} // Remove the extra brace here
