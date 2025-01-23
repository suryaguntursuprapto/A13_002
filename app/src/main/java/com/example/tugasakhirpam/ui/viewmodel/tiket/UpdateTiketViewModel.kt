package com.example.tugasakhirpam.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.model.Tiket
import com.example.tugasakhirpam.repository.TiketRepository
import kotlinx.coroutines.launch

class UpdateTiketViewModel(private val tiketRepository: TiketRepository) : ViewModel() {
    var uiState by mutableStateOf(UpdateTiketUiState())
        private set

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
