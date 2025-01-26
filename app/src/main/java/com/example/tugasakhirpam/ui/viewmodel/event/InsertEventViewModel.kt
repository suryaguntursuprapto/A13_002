package com.example.tugasakhirpam.ui.viewmodel.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.model.Event
import com.example.tugasakhirpam.repository.EventRepository
import kotlinx.coroutines.launch

class InsertEventViewModel(private val eventRepository: EventRepository) : ViewModel() {

    // UI state to store form data
    var uiState by mutableStateOf(InsertEventUiState())
        private set

    // Validation errors state
    var validationErrors by mutableStateOf(InsertEventFormErrors())
        private set

    // Update the form data when fields change
    fun updateInsertEventState(insertEvent: InsertEvent) {
        uiState = InsertEventUiState(insertEvent = insertEvent)
    }

    // Validate form data
    fun validateInsertEventForm(): Boolean {
        var isValid = true

        // Reset previous errors
        validationErrors = InsertEventFormErrors()

        // Validate each field and set errors if any are found
        if (uiState.insertEvent.idEvent.isBlank()) {
            validationErrors = validationErrors.copy(idEvent = "ID Event cannot be empty")
            isValid = false
        }

        if (uiState.insertEvent.namaEvent.isBlank()) {
            validationErrors = validationErrors.copy(namaEvent = "Nama Event cannot be empty")
            isValid = false
        }

        if (uiState.insertEvent.deskripsiEvent.isBlank()) {
            validationErrors = validationErrors.copy(deskripsiEvent = "Deskripsi Event cannot be empty")
            isValid = false
        }

        if (uiState.insertEvent.tanggalEvent.isBlank()) {
            validationErrors = validationErrors.copy(tanggalEvent = "Tanggal Event cannot be empty")
            isValid = false
        }

        return isValid
    }

    // Insert event into the database
    fun insertEvent() {
        // Validate before trying to insert
        if (validateInsertEventForm()) {
            viewModelScope.launch {
                try {
                    // Proceed with the insert only if the form is valid
                    eventRepository.insertEvent(uiState.insertEvent.toEvent())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}

// UI state to hold the form event data
data class InsertEventUiState(
    val insertEvent: InsertEvent = InsertEvent()
)

// Form error state for each field
data class InsertEventFormErrors(
    val idEvent: String? = null,
    val namaEvent: String? = null,
    val deskripsiEvent: String? = null,
    val tanggalEvent: String? = null
)

// Event data class representing the form fields
data class InsertEvent(
    val idEvent: String = "",
    val namaEvent: String = "",
    val deskripsiEvent: String = "",
    val tanggalEvent: String = ""
)

// Convert InsertEvent to Event model
fun InsertEvent.toEvent(): Event = Event(
    idEvent = idEvent,
    namaEvent = namaEvent,
    deskripsiEvent = deskripsiEvent,
    tanggalEvent = tanggalEvent
)

// Convert Event model to UI state
fun Event.toUiStateEvent(): InsertEventUiState = InsertEventUiState(
    insertEvent = toInsertEvent()
)

// Convert Event model to InsertEvent for UI binding
fun Event.toInsertEvent(): InsertEvent = InsertEvent(
    idEvent = idEvent,
    namaEvent = namaEvent,
    deskripsiEvent = deskripsiEvent,
    tanggalEvent = tanggalEvent
)
