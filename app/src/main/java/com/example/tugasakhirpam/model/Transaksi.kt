package com.example.tugasakhirpam.model

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*


@Serializable
data class Transaksi (
    @SerialName("id_transaksi")
    val idTransaksi: String,

    @SerialName("id_tiket")
    val idTiket: String,

    @SerialName("jumlah_tiket")
    val jumlahTiket: String,

    @SerialName("jumlah_pembayaran")
    val jumlahPembayaran: String,

    @SerialName("tanggal_transaksi")
    val tanggalTransaksi: String
)
