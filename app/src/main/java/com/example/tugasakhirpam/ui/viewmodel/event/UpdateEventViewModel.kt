package com.example.tugasakhirpam.ui.viewmodel.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpam.model.Event
import com.example.tugasakhirpam.repository.EventRepository
import kotlinx.coroutines.launch

class UpdateEventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    var uiState by mutableStateOf(UpdateEventUiState())
        private set

    fun updateUiState(updateEventEvent: UpdateEventEvent) {
        uiState = UpdateEventUiState(updateEventEvent = updateEventEvent)
    }

    suspend fun updateEvent(idEvent: String) {
        viewModelScope.launch {
            try {
                eventRepository.updateEvent(idEvent, uiState.updateEventEvent.toEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getEventById(idEvent: String) {
        viewModelScope.launch {
            try {
                val result = eventRepository.getEventById(idEvent)
                uiState = result.toUpdateEventUiState()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class UpdateEventUiState(
    val updateEventEvent: UpdateEventEvent = UpdateEventEvent()
)

data class UpdateEventEvent(
    val idEvent: String = "",
    val namaEvent: String = "",
    val tanggalEvent: String = "",
    val lokasiEvent: String = "",
    val deskripsiEvent: String = "",
)

fun UpdateEventEvent.toEvent(): Event = Event(
    idEvent = idEvent,
    namaEvent = namaEvent,
    tanggalEvent = tanggalEvent,
    deskripsiEvent = deskripsiEvent,
    lokasiEvent = lokasiEvent
)

fun Event.toUpdateEventUiState(): UpdateEventUiState = UpdateEventUiState(
    updateEventEvent = toUpdateEventEvent()
)

fun Event.toUpdateEventEvent(): UpdateEventEvent = UpdateEventEvent(
    idEvent = idEvent,
    namaEvent = namaEvent,
    tanggalEvent = tanggalEvent,
    deskripsiEvent = deskripsiEvent,
    lokasiEvent = lokasiEvent
)
