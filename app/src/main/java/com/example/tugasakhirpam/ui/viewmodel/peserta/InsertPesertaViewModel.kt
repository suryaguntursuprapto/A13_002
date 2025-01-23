package com.example.tugasakhirpam.ui.viewmodel.peserta


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.model.Peserta
import com.example.tugasakhirpam.repository.PesertaRepository
import kotlinx.coroutines.launch

class InsertPesertaViewModel(private val pesertaRepository: PesertaRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertPesertaUiState())
        private set

    fun updateInsertPesertaState(insertPesertaEvent: InsertPesertaEvent) {
        uiState = InsertPesertaUiState(insertPesertaEvent = insertPesertaEvent)
    }

    suspend fun insertPeserta() {
        viewModelScope.launch {
            try {
                pesertaRepository.insertPeserta(uiState.insertPesertaEvent.toPeserta())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPesertaUiState(
    val insertPesertaEvent: InsertPesertaEvent = InsertPesertaEvent()
)

data class InsertPesertaEvent(
    val idPeserta: String = "",
    val namaPeserta: String = "",
    val email: String = "",
    val nomorTelepon: String = ""
)

fun InsertPesertaEvent.toPeserta(): Peserta = Peserta(
    idPeserta = idPeserta,
    namaPeserta = namaPeserta,
    email = email,
    nomorTelepon = nomorTelepon
)

fun Peserta.toUiStatePeserta(): InsertPesertaUiState = InsertPesertaUiState(
    insertPesertaEvent = toInsertPesertaEvent()
)

fun Peserta.toInsertPesertaEvent(): InsertPesertaEvent = InsertPesertaEvent(
    idPeserta = idPeserta,
    namaPeserta = namaPeserta,
    email = email,
    nomorTelepon = nomorTelepon
)
