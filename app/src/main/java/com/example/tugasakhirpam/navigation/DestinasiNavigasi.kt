package com.example.tugasakhirpam.navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}


object DestinasiHomePeserta : DestinasiNavigasi {
    override val route = "home_peserta"
    override val titleRes = "Home Peserta"
}
