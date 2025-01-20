package com.example.tugasakhirpam.model

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*


@Serializable
data class Tiket (
    @SerialName("id_tiket")
    val idTiket: String,

    @SerialName("id_event")
    val idEvent: String,

    @SerialName("id_pengguna")
    val idPengguna: String,

    @SerialName("kapasitas_tiket")
    val kapasitasTiket: String,

    @SerialName("harga_tiket")
    val hargaTiket: String
)
