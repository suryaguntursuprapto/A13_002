package com.example.tugasakhirpam.ui.viewmodel.peserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.model.Peserta
import com.example.tugasakhirpam.repository.PesertaRepository
import kotlinx.coroutines.launch

class UpdatePesertaViewModel(private val pesertaRepository: PesertaRepository) : ViewModel() {
    var uiState by mutableStateOf(UpdatePesertaUiState())
        private set

    fun updateUiState(updatePesertaEvent: UpdatePesertaEvent) {
        uiState = UpdatePesertaUiState(updatePesertaEvent = updatePesertaEvent)
    }

    suspend fun updatePeserta(idPeserta: String) {
        viewModelScope.launch {
            try {
                pesertaRepository.updatePeserta(idPeserta, uiState.updatePesertaEvent.toPeserta())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getPesertaById(idPeserta: String) {
        viewModelScope.launch {
            try {
                val result = pesertaRepository.getPesertabyId(idPeserta)
                uiState = result.toUpdatePesertaUiState()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class UpdatePesertaUiState(
    val updatePesertaEvent: UpdatePesertaEvent = UpdatePesertaEvent()
)

data class UpdatePesertaEvent(
    val idPeserta: String = "",
    val nama: String = "",
    val noTelepon: String = "",
    val email: String = ""
)

fun UpdatePesertaEvent.toPeserta(): Peserta = Peserta(
    idPeserta = idPeserta,
    namaPeserta = nama,
    email = email,
    nomorTelepon = noTelepon
)

fun Peserta.toUpdatePesertaUiState(): UpdatePesertaUiState = UpdatePesertaUiState(
    updatePesertaEvent = toUpdatePesertaEvent()
)

fun Peserta.toUpdatePesertaEvent(): UpdatePesertaEvent = UpdatePesertaEvent(
    idPeserta = idPeserta,
    nama = namaPeserta,
    email = email,
    noTelepon = nomorTelepon
)
