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
import java.io.IOException

sealed class DetailEventUiState {
    data class Success(val event: Event) : DetailEventUiState()
    object Error : DetailEventUiState()
    object Loading : DetailEventUiState()
}

class DetailEventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    var eventDetailUiState: DetailEventUiState by mutableStateOf(DetailEventUiState.Loading)
        private set

    fun getEventById(idEvent: String) {
        viewModelScope.launch {
            eventDetailUiState = DetailEventUiState.Loading
            eventDetailUiState = try {
                val event = eventRepository.getEventById(idEvent)
                DetailEventUiState.Success(event)
            } catch (e: IOException) {
                DetailEventUiState.Error
            } catch (e: HttpException) {
                DetailEventUiState.Error
            }
        }
    }
}
