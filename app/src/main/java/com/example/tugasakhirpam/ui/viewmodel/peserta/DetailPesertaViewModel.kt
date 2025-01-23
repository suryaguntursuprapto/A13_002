package com.example.tugasakhirpam.ui.viewmodel.peserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.tugasakhirpam.model.Peserta
import com.example.tugasakhirpam.repository.PesertaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailPesertaUiState {
    data class Success(val peserta: Peserta) : DetailPesertaUiState()
    object Error : DetailPesertaUiState()
    object Loading : DetailPesertaUiState()
}

class DetailPesertaViewModel(private val pesertaRepository: PesertaRepository) : ViewModel() {
    var pesertaDetailUiState: DetailPesertaUiState by mutableStateOf(DetailPesertaUiState.Loading)
        private set

    fun getPesertaById(idPeserta: String) {
        viewModelScope.launch {
            pesertaDetailUiState = DetailPesertaUiState.Loading
            pesertaDetailUiState = try {
                val peserta = pesertaRepository.getPesertabyId(idPeserta)
                DetailPesertaUiState.Success(peserta)
            } catch (e: IOException) {
                DetailPesertaUiState.Error
            } catch (e: HttpException) {
                DetailPesertaUiState.Error
            }
        }
    }
}
