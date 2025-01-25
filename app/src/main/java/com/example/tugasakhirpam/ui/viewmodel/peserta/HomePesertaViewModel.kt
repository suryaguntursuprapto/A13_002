package com.example.tugasakhirpam.ui.viewmodel.peserta


import android.util.Log
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
            try {
                pesertaUiState = HomePesertaUiState.Loading
                Log.d("HomePesertaViewModel", "Loading data...")
                val pesertaList = pesertaRepository.getPeserta()
                pesertaUiState = HomePesertaUiState.Success(pesertaList)
                Log.d("HomePesertaViewModel", "Data loaded successfully.")
            } catch (e: IOException) {
                Log.e("HomePesertaViewModel", "IOException: ${e.message}")
                pesertaUiState = HomePesertaUiState.Error
            } catch (e: HttpException) {
                Log.e("HomePesertaViewModel", "HttpException: ${e.message}")
                pesertaUiState = HomePesertaUiState.Error
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
