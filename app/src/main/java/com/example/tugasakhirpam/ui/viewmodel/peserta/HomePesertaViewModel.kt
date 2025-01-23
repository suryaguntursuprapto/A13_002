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
import okio.IOException

sealed class HomePesertaUiState {
    data class Success(val peserta: List<Peserta>) : HomePesertaUiState()
    object Error : HomePesertaUiState()
    object Loading : HomePesertaUiState()
}

class HomePesertaViewModel(private val pesertaRepository: PesertaRepository) : ViewModel() {
    var pesertaUiState: HomePesertaUiState by mutableStateOf(HomePesertaUiState.Loading)
        private set

    init {
        getPeserta()
    }

    fun getPeserta() {
        viewModelScope.launch {
            pesertaUiState = HomePesertaUiState.Loading
            pesertaUiState = try {
                HomePesertaUiState.Success(pesertaRepository.getPeserta())
            } catch (e: IOException) {
                HomePesertaUiState.Error
            } catch (e: HttpException) {
                HomePesertaUiState.Error
            }
        }
    }

    fun deletePeserta(idPeserta: String) {
        viewModelScope.launch {
            try {
                pesertaRepository.deletePeserta(idPeserta)
            } catch (e: IOException) {
                pesertaUiState = HomePesertaUiState.Error
            } catch (e: HttpException) {
                pesertaUiState = HomePesertaUiState.Error
            }
        }
    }
}
