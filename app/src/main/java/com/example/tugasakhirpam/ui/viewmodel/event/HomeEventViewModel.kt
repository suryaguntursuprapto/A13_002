package com.example.tugasakhirpam.ui.viewmodel.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.tugasakhirpam.model.Event
import com.example.tugasakhirpam.repository.EventRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeEventUiState {
    data class Success(val events: List<Event>) : HomeEventUiState()
    object Error : HomeEventUiState()
    object Loading : HomeEventUiState()
}

class HomeEventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    var eventUiState: HomeEventUiState by mutableStateOf(HomeEventUiState.Loading)
        private set

    init {
        getEvent()
    }

    fun getEvent() {
        viewModelScope.launch {
            eventUiState = HomeEventUiState.Loading
            eventUiState = try {
                HomeEventUiState.Success(eventRepository.getAllEvents())
            } catch (e: IOException) {
                HomeEventUiState.Error
            } catch (e: HttpException) {
                HomeEventUiState.Error
            }
        }
    }

    fun deleteEvent(idEvent: String) {
        viewModelScope.launch {
            try {
                eventRepository.deleteEvent(idEvent)
            } catch (e: IOException) {
                eventUiState = HomeEventUiState.Error
            } catch (e: HttpException) {
                eventUiState = HomeEventUiState.Error
            }
        }
    }
}
