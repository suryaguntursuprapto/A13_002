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

    // To store validation error messages
    var validationErrors by mutableStateOf(InsertPesertaFormErrors())
        private set

    // Function to update the state of the form (on value change)
    fun updateInsertPesertaState(insertPesertaEvent: InsertPesertaEvent) {
        uiState = InsertPesertaUiState(insertPesertaEvent = insertPesertaEvent)
    }

    // Function to validate the form and return a boolean
    fun validateInsertPesertaForm(): Boolean {
        var isValid = true

        // Reset previous errors
        validationErrors = InsertPesertaFormErrors()

        // Validate each field and set errors if any are found
        if (uiState.insertPesertaEvent.idPeserta.isBlank()) {
            validationErrors = validationErrors.copy(idPeserta = "ID Peserta cannot be empty")
            isValid = false
        }

        if (uiState.insertPesertaEvent.namaPeserta.isBlank()) {
            validationErrors = validationErrors.copy(namaPeserta = "Nama Peserta cannot be empty")
            isValid = false
        }

        if (uiState.insertPesertaEvent.email.isBlank()) {
            validationErrors = validationErrors.copy(email = "Email cannot be empty")
            isValid = false
        }

        if (uiState.insertPesertaEvent.nomorTelepon.isBlank()) {
            validationErrors = validationErrors.copy(nomorTelepon = "Nomor Telepon cannot be empty")
            isValid = false
        }

        return isValid
    }

    // Function to insert Peserta into the database
    fun insertPeserta() {
        // Validate before trying to insert
        if (validateInsertPesertaForm()) {
            viewModelScope.launch {
                try {
                    // Proceed with the insert only if the form is valid
                    pesertaRepository.insertPeserta(uiState.insertPesertaEvent.toPeserta())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}

// UI State to hold the form event data
data class InsertPesertaUiState(
    val insertPesertaEvent: InsertPesertaEvent = InsertPesertaEvent()
)

// Form error state for each field
data class InsertPesertaFormErrors(
    val idPeserta: String? = null,
    val namaPeserta: String? = null,
    val email: String? = null,
    val nomorTelepon: String? = null
)

// Event data class representing the form fields
data class InsertPesertaEvent(
    val idPeserta: String = "",
    val namaPeserta: String = "",
    val email: String = "",
    val nomorTelepon: String = ""
)

// Convert InsertPesertaEvent to Peserta model
fun InsertPesertaEvent.toPeserta(): Peserta = Peserta(
    idPeserta = idPeserta,
    namaPeserta = namaPeserta,
    email = email,
    nomorTelepon = nomorTelepon
)

// Convert Peserta model to UI state
fun Peserta.toUiStatePeserta(): InsertPesertaUiState = InsertPesertaUiState(
    insertPesertaEvent = toInsertPesertaEvent()
)

// Convert Peserta model to InsertPesertaEvent for UI binding
fun Peserta.toInsertPesertaEvent(): InsertPesertaEvent = InsertPesertaEvent(
    idPeserta = idPeserta,
    namaPeserta = namaPeserta,
    email = email,
    nomorTelepon = nomorTelepon
)
