package com.example.tugasakhirpam.model

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
data class Peserta (
    @SerialName("id_peserta")
    val idPeserta: String,

    @SerialName("nama_peserta")
    val namaPeserta: String,

    val email: String,

    @SerialName("nomor_telepon")
    val nomorTelepon: String
)

