package com.example.tugasakhirpam.ui.viewmodel.transaksi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.tugasakhirpam.model.Transaksi
import com.example.tugasakhirpam.repository.TransaksiRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeTransaksiUiState {
    data class Success(val transaksi: List<Transaksi>) : HomeTransaksiUiState()
    object Error : HomeTransaksiUiState()
    object Loading : HomeTransaksiUiState()
}

class HomeTransaksiViewModel(private val transaksiRepository: TransaksiRepository) : ViewModel() {
    var transaksiUiState: HomeTransaksiUiState by mutableStateOf(HomeTransaksiUiState.Loading)
        private set

    init {
        getTransaksi()
    }

    fun getTransaksi() {
        viewModelScope.launch {
            transaksiUiState = HomeTransaksiUiState.Loading
            transaksiUiState = try {
                HomeTransaksiUiState.Success(transaksiRepository.getAllTransaksi())
            } catch (e: IOException) {
                HomeTransaksiUiState.Error
            } catch (e: HttpException) {
                HomeTransaksiUiState.Error
            }
        }
    }

    fun deleteTransaksi(idTransaksi: String) {
        viewModelScope.launch {
            try {
                transaksiRepository.deleteTransaksi(idTransaksi)
            } catch (e: IOException) {
                transaksiUiState = HomeTransaksiUiState.Error
            } catch (e: HttpException) {
                transaksiUiState = HomeTransaksiUiState.Error
            }
        }
    }
}
