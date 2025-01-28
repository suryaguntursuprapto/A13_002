package com.example.tugasakhirpam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tugasakhirpam.KonserApplications
import com.example.tugasakhirpam.repository.EventRepository
import com.example.tugasakhirpam.ui.viewmodel.*
import com.example.tugasakhirpam.ui.viewmodel.event.*
import com.example.tugasakhirpam.ui.viewmodel.peserta.*
import com.example.tugasakhirpam.ui.viewmodel.tiket.*
import com.example.tugasakhirpam.ui.viewmodel.transaksi.*

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // Home ViewModels
        initializer { HomePesertaViewModel(aplikasiKonser().container.pesertaRepository) }
        initializer { HomeEventViewModel(aplikasiKonser().container.eventRepository) }
        initializer { HomeTiketViewModel(aplikasiKonser().container.tiketRepository) }
        initializer { HomeTransaksiViewModel(aplikasiKonser().container.transaksiRepository) }

        // Insert ViewModels
        initializer { InsertPesertaViewModel(aplikasiKonser().container.pesertaRepository) }
        initializer { InsertEventViewModel(aplikasiKonser().container.eventRepository) }
        initializer {
            InsertTiketViewModel(
                aplikasiKonser().container.tiketRepository,
                aplikasiKonser().container.pesertaRepository,
                aplikasiKonser().container.eventRepository
            )
        }
        initializer { InsertTransaksiViewModel(aplikasiKonser().container.transaksiRepository) }

        // Detail ViewModels
        initializer { DetailPesertaViewModel(aplikasiKonser().container.pesertaRepository) }
        initializer { DetailEventViewModel(aplikasiKonser().container.eventRepository) }
        initializer { DetailTiketViewModel(aplikasiKonser().container.tiketRepository) }
        initializer { DetailTransaksiViewModel(aplikasiKonser().container.transaksiRepository) }

        // Update ViewModels
        initializer { UpdatePesertaViewModel(aplikasiKonser().container.pesertaRepository) }
        initializer { UpdateEventViewModel(aplikasiKonser().container.eventRepository) }
        initializer { UpdateTiketViewModel(aplikasiKonser().container.tiketRepository) }
    }
}
fun CreationExtras.aplikasiKonser(): KonserApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KonserApplications)
