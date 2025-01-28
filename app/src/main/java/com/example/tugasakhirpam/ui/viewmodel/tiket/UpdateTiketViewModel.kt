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

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UpdateTiketViewModel(
    private val tiketRepository: TiketRepository,
    private val pesertaRepository: PesertaRepository,
    private val eventRepository: EventRepository
) : ViewModel() {
    var uiState by mutableStateOf(UpdateTiketUiState())
        private set

    // Use StateFlow to store data for daftarPeserta and daftarEvent
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


    fun updateUiState(updateTiketEvent: UpdateTiketEvent) {
        uiState = UpdateTiketUiState(updateTiketEvent = updateTiketEvent)
    }

    suspend fun updateTiket(idTiket: String) {
        viewModelScope.launch {
            try {
                tiketRepository.updateTiket(idTiket, uiState.updateTiketEvent.toTiket())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getTiketById(idTiket: String) {
        viewModelScope.launch {
            try {
                val result = tiketRepository.getTiketById(idTiket)
                uiState = result.toUpdateTiketUiState()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


data class UpdateTiketUiState(
    val updateTiketEvent: UpdateTiketEvent = UpdateTiketEvent()
)

data class UpdateTiketEvent(
    val idTiket: String = "",
    val idEvent: String = "",
    val idPengguna: String = "",
    val kapasitasTiket: String = "",
    val hargaTiket: String = ""
)

fun UpdateTiketEvent.toTiket(): Tiket = Tiket(
    idTiket = idTiket,
    idPengguna = idPengguna,
    idEvent = idEvent,
    kapasitasTiket = kapasitasTiket,
    hargaTiket = hargaTiket
)

fun Tiket.toUpdateTiketUiState(): UpdateTiketUiState = UpdateTiketUiState(
    updateTiketEvent = toUpdateTiketEvent()
)

fun Tiket.toUpdateTiketEvent(): UpdateTiketEvent = UpdateTiketEvent(
    idTiket = idTiket,
    idEvent = idEvent,
    idPengguna = idPengguna,
    kapasitasTiket = kapasitasTiket,
    hargaTiket = hargaTiket
)
