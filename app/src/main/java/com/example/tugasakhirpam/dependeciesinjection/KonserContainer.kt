package com.example.tugasakhirpam.dependeciesinjection

import com.example.tugasakhirpam.repository.EventRepository
import com.example.tugasakhirpam.repository.NetworkEventRepository
import com.example.tugasakhirpam.repository.PesertaRepository
import com.example.tugasakhirpam.repository.NetworkPesertaRepository
import com.example.tugasakhirpam.repository.TiketRepository
import com.example.tugasakhirpam.repository.NetworkTiketRepository
import com.example.tugasakhirpam.repository.TransaksiRepository
import com.example.tugasakhirpam.repository.NetworkTransaksiRepository
import com.example.tugasakhirpam.service_api.EventService
import com.example.tugasakhirpam.service_api.PesertaService
import com.example.tugasakhirpam.service_api.TiketService
import com.example.tugasakhirpam.service_api.TransaksiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val pesertaRepository: PesertaRepository
    val eventRepository: EventRepository
    val tiketRepository: TiketRepository
    val transaksiRepository: TransaksiRepository
}

class KonserContainer : AppContainer {

    private val baseUrl = "http://10.0.2.2:80/konser/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val pesertaService: PesertaService by lazy {
        retrofit.create(PesertaService::class.java)
    }

    private val eventService: EventService by lazy {
        retrofit.create(EventService::class.java)
    }

    private val tiketService: TiketService by lazy {
        retrofit.create(TiketService::class.java)
    }

    private val transaksiService: TransaksiService by lazy {
        retrofit.create(TransaksiService::class.java)
    }

    override val pesertaRepository: PesertaRepository by lazy {
        NetworkPesertaRepository(pesertaService)
    }

    override val eventRepository: EventRepository by lazy {
        NetworkEventRepository(eventService)
    }

    override val tiketRepository: TiketRepository by lazy {
        NetworkTiketRepository(tiketService, eventService, pesertaService)
    }

    override val transaksiRepository: TransaksiRepository by lazy {
        NetworkTransaksiRepository(transaksiService, tiketService)
    }
}
