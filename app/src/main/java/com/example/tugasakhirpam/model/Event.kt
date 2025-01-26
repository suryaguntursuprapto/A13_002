package com.example.tugasakhirpam.model


import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*


@Serializable
data class Event (
    @SerialName("id_event")
    val idEvent: String,

    @SerialName("nama_event")
    val namaEvent: String,

    @SerialName("deskripsi_event")
    val deskripsiEvent: String,

    @SerialName("tanggal_event")
    val tanggalEvent: String,

    @SerialName("lokasi_event")
    val lokasiEvent: String
)

