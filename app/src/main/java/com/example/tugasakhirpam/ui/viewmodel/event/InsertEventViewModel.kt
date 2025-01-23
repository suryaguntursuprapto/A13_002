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
    var uiState by mutableStateOf(InsertEventUiState())
        private set

    fun updateInsertEventState(insertEvent: InsertEvent) {
        uiState = InsertEventUiState(insertEvent = insertEvent)
    }

    suspend fun insertEvent() {
        viewModelScope.launch {
            try {
                eventRepository.insertEvent(uiState.insertEvent.toEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertEventUiState(
    val insertEvent: InsertEvent = InsertEvent()
)

data class InsertEvent(
    val idEvent: String = "",
    val namaEvent: String = "",
    val deskripsiEvent: String = "",
    val tanggalEvent: String = ""
)

fun InsertEvent.toEvent(): Event = Event(
    idEvent = idEvent,
    namaEvent = namaEvent,
    deskripsiEvent = deskripsiEvent,
    tanggalEvent = tanggalEvent
)

fun Event.toUiStateEvent(): InsertEventUiState = InsertEventUiState(
    insertEvent = toInsertEvent()
)

fun Event.toInsertEvent(): InsertEvent = InsertEvent(
    idEvent = idEvent,
    namaEvent = namaEvent,
    deskripsiEvent = deskripsiEvent,
    tanggalEvent = tanggalEvent
)
