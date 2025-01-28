package com.example.tugasakhirpam.repository

import com.example.tugasakhirpam.model.Transaksi
import com.example.tugasakhirpam.service_api.EventService
import com.example.tugasakhirpam.service_api.PesertaService
import com.example.tugasakhirpam.service_api.TiketService
import com.example.tugasakhirpam.service_api.TransaksiService
import okio.IOException

interface TransaksiRepository {

    suspend fun insertTransaksi(transaksi: Transaksi)

    suspend fun getAllTransaksi(): List<Transaksi>

    suspend fun updateTransaksi(idTransaksi: String, transaksi: Transaksi)

    suspend fun deleteTransaksi(idTransaksi: String)

    suspend fun getTransaksiById(idTransaksi: String): Transaksi
}

class NetworkTransaksiRepository(
    private val transaksiApiService: TransaksiService,
    private val tiketApiService: TiketService
) : TransaksiRepository {

    override suspend fun insertTransaksi(transaksi: Transaksi) {
        transaksiApiService.insertTransaksi(transaksi)
    }

    override suspend fun updateTransaksi(idTransaksi: String, transaksi: Transaksi) {
        transaksiApiService.updateTransaksi(idTransaksi, transaksi)
    }

    override suspend fun deleteTransaksi(idTransaksi: String) {
        try {
            val response = transaksiApiService.deleteTransaksi(idTransaksi)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete transaksi. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAllTransaksi(): List<Transaksi> {
        val trxList = transaksiApiService.getAllTransaksi()

        // Fetch event and user names for each tiket
        return trxList.map { tiket ->
            val tiketName = tiketApiService.getTiketById(tiket.idTiket)?.idPengguna ?: "Unknown Event"

            // Return a new Tiket with added eventName and userName
            tiket.copy(idTiket = tiketName)
        }
    }

    override suspend fun getTransaksiById(idTransaksi: String): Transaksi {
        return transaksiApiService.getTransaksiById(idTransaksi)
    }
}
