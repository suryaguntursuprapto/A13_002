package com.example.tugasakhirpam.repository

import com.example.tugasakhirpam.model.Peserta
import com.example.tugasakhirpam.service_api.PesertaService
import okio.IOException

interface PesertaRepository {

    suspend fun insertPeserta(peserta: Peserta)

    suspend fun getPeserta(): List<Peserta>

    suspend fun updatePeserta(idPeserta: String, peserta: Peserta)

    suspend fun deletePeserta(idPeserta: String)

    suspend fun getPesertabyId(idPeserta: String): Peserta
}

class NetworkPesertaRepository(
    private val pesertaApiService: PesertaService
) : PesertaRepository {

    override suspend fun insertPeserta(peserta: Peserta) {
        pesertaApiService.insertPeserta(peserta)
    }

    override suspend fun updatePeserta(idPeserta: String, peserta: Peserta) {
        pesertaApiService.updatePeserta(idPeserta, peserta)
    }

    override suspend fun deletePeserta(idPeserta: String) {
        try {
            val response = pesertaApiService.deletePeserta(idPeserta)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete peserta. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPeserta(): List<Peserta> =
        pesertaApiService.getAllPeserta()

    override suspend fun getPesertabyId(idPeserta: String): Peserta {
        return pesertaApiService.getPesertaById(idPeserta)
    }
}
