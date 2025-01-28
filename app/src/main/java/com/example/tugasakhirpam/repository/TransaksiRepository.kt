package com.example.tugasakhirpam.repository

import com.example.tugasakhirpam.model.Transaksi
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
    private val transaksiApiService: TransaksiService
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

    override suspend fun getAllTransaksi(): List<Transaksi> =
        transaksiApiService.getAllTransaksi()

    override suspend fun getTransaksiById(idTransaksi: String): Transaksi {
        return transaksiApiService.getTransaksiById(idTransaksi)
    }
}
