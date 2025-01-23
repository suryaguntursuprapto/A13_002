package com.example.tugasakhirpam.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.model.Tiket
import com.example.tugasakhirpam.repository.TiketRepository
import kotlinx.coroutines.launch

class InsertTiketViewModel(private val tiketRepository: TiketRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertTiketUiState())
        private set

    fun updateInsertTiketState(insertTiket: InsertTiket) {
        uiState = InsertTiketUiState(insertTiket = insertTiket)
    }

    suspend fun insertTiket() {
        viewModelScope.launch {
            try {
                tiketRepository.insertTiket(uiState.insertTiket.toTiket())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertTiketUiState(
    val insertTiket: InsertTiket = InsertTiket()
)

data class InsertTiket(
    val idTiket: String = "",
    val idEvent: String = "", // Foreign Key ke Event.idEvent
    val idPengguna: String = "", // Foreign Key ke Peserta.idPeserta
    val kapasitasTiket: String = "",
    val hargaTiket: String = ""
)

fun InsertTiket.toTiket(): Tiket = Tiket(
    idTiket = idTiket,
    idEvent = idEvent,
    idPengguna = idPengguna,
    kapasitasTiket = kapasitasTiket,
    hargaTiket = hargaTiket
)

fun Tiket.toUiStateTiket(): InsertTiketUiState = InsertTiketUiState(
    insertTiket = toInsertTiket()
)

fun Tiket.toInsertTiket(): InsertTiket = InsertTiket(
    idTiket = idTiket,
    idEvent = idEvent,
    idPengguna = idPengguna,
    kapasitasTiket = kapasitasTiket,
    hargaTiket = hargaTiket
)
