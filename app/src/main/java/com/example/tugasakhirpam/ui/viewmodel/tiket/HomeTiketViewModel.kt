package com.example.tugasakhirpam.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.tugasakhirpam.model.Tiket
import com.example.tugasakhirpam.repository.TiketRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeTiketUiState {
    data class Success(val tiket: List<Tiket>) : HomeTiketUiState()
    object Error : HomeTiketUiState()
    object Loading : HomeTiketUiState()
}

class HomeTiketViewModel(private val tiketRepository: TiketRepository) : ViewModel() {
    var tiketUiState: HomeTiketUiState by mutableStateOf(HomeTiketUiState.Loading)
        private set

    init {
        getTiket()
    }

    fun getTiket() {
        viewModelScope.launch {
            tiketUiState = HomeTiketUiState.Loading
            tiketUiState = try {
                HomeTiketUiState.Success(tiketRepository.getAllTikets())
            } catch (e: IOException) {
                HomeTiketUiState.Error
            } catch (e: HttpException) {
                HomeTiketUiState.Error
            }
        }
    }

    fun deleteTiket(idTiket: String) {
        viewModelScope.launch {
            try {
                tiketRepository.deleteTiket(idTiket)
            } catch (e: IOException) {
                tiketUiState = HomeTiketUiState.Error
            } catch (e: HttpException) {
                tiketUiState = HomeTiketUiState.Error
            }
        }
    }
}
