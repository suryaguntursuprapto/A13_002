package com.example.tugasakhirpam.ui.viewmodel.transaksi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.model.Event
import com.example.tugasakhirpam.model.Tiket
import com.example.tugasakhirpam.model.Transaksi
import com.example.tugasakhirpam.repository.TiketRepository
import com.example.tugasakhirpam.repository.TransaksiRepository
import kotlinx.coroutines.launch

class InsertTransaksiViewModel(
    private val transaksiRepository: TransaksiRepository,
    private val tiketRepository: TiketRepository
) : ViewModel() {
    var uiState by mutableStateOf(InsertTransaksiUiState())
        private set
    var daftarTiket by mutableStateOf(emptyList<Tiket>())
        private set

    init {
        fetchTiket()
    }

    private fun fetchTiket() {
        viewModelScope.launch {
            try {
                daftarTiket = tiketRepository.getAllTikets()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertTransaksiState(insertTransaksi: InsertTransaksi) {
        uiState = InsertTransaksiUiState(insertTransaksi = insertTransaksi)
    }

    suspend fun insertTransaksi() {
        viewModelScope.launch {
            try {
                transaksiRepository.insertTransaksi(uiState.insertTransaksi.toTransaksi())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertTransaksiUiState(
    val insertTransaksi: InsertTransaksi = InsertTransaksi()
)

data class InsertTransaksi(
    val idTransaksi: String = "",
    val idTiket: String = "", // Foreign Key ke Tiket.idTiket
    val jumlahTiket: String = "",
    val jumlahPembayaran: String = "",
    val tanggalTransaksi: String = ""
)

fun InsertTransaksi.toTransaksi(): Transaksi = Transaksi(
    idTransaksi = idTransaksi,
    idTiket = idTiket,
    jumlahTiket = jumlahTiket,
    jumlahPembayaran = jumlahPembayaran,
    tanggalTransaksi = tanggalTransaksi
)

fun Transaksi.toUiStateTransaksi(): InsertTransaksiUiState = InsertTransaksiUiState(
    insertTransaksi = toInsertTransaksi()
)

fun Transaksi.toInsertTransaksi(): InsertTransaksi = InsertTransaksi(
    idTransaksi = idTransaksi,
    idTiket = idTiket,
    jumlahTiket = jumlahTiket,
    jumlahPembayaran = jumlahPembayaran,
    tanggalTransaksi = tanggalTransaksi
)
