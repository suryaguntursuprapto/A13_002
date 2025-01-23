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
import java.io.IOException

sealed class DetailTiketUiState {
    data class Success(val tiket: Tiket) : DetailTiketUiState()
    object Error : DetailTiketUiState()
    object Loading : DetailTiketUiState()
}

class DetailTiketViewModel(private val tiketRepository: TiketRepository) : ViewModel() {
    var tiketDetailUiState: DetailTiketUiState by mutableStateOf(DetailTiketUiState.Loading)
        private set

    fun getTiketById(idTiket: String) {
        viewModelScope.launch {
            tiketDetailUiState = DetailTiketUiState.Loading
            tiketDetailUiState = try {
                val tiket = tiketRepository.getTiketById(idTiket)
                DetailTiketUiState.Success(tiket)
            } catch (e: IOException) {
                DetailTiketUiState.Error
            } catch (e: HttpException) {
                DetailTiketUiState.Error
            }
        }
    }
}
