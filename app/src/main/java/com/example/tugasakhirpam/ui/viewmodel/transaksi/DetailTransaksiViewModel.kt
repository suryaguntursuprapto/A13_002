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
import java.io.IOException

sealed class DetailTransaksiUiState {
    data class Success(val transaksi: Transaksi) : DetailTransaksiUiState()
    object Error : DetailTransaksiUiState()
    object Loading : DetailTransaksiUiState()
}

class DetailTransaksiViewModel(private val transaksiRepository: TransaksiRepository) : ViewModel() {
    var transaksiDetailUiState: DetailTransaksiUiState by mutableStateOf(DetailTransaksiUiState.Loading)
        private set

    fun getTransaksiById(idTransaksi: String) {
        viewModelScope.launch {
            transaksiDetailUiState = DetailTransaksiUiState.Loading
            transaksiDetailUiState = try {
                val transaksi = transaksiRepository.getTransaksiById(idTransaksi)
                DetailTransaksiUiState.Success(transaksi)
            } catch (e: IOException) {
                DetailTransaksiUiState.Error
            } catch (e: HttpException) {
                DetailTransaksiUiState.Error
            }
        }
    }
}
