package com.example.tugasakhirpam.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.model.Event
import com.example.tugasakhirpam.model.Peserta
import com.example.tugasakhirpam.model.Tiket
import com.example.tugasakhirpam.repository.EventRepository
import com.example.tugasakhirpam.repository.PesertaRepository
import com.example.tugasakhirpam.repository.TiketRepository
import kotlinx.coroutines.launch

class InsertTiketViewModel(
    private val tiketRepository: TiketRepository,
    private val pesertaRepository: PesertaRepository,
    private val eventRepository: EventRepository
) : ViewModel() {
    var uiState by mutableStateOf(InsertTiketUiState())
        private set

    var daftarPeserta by mutableStateOf(emptyList<Peserta>())
        private set

    var daftarEvent by mutableStateOf(emptyList<Event>())
        private set

    init {
        fetchPesertaAndEvent()
    }

    private fun fetchPesertaAndEvent() {
        viewModelScope.launch {
            try {
                daftarPeserta = pesertaRepository.getPeserta()
                daftarEvent = eventRepository.getAllEvents()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

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
    val idEvent: String = "",
    val idPengguna: String = "",
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


