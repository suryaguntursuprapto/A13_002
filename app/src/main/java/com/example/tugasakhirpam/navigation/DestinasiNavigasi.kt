package com.example.tugasakhirpam.navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}


object DestinasiHomePeserta : DestinasiNavigasi {
    override val route = "home_peserta"
    override val titleRes = "Home Peserta"
}

object DestinasiInsertPeserta : DestinasiNavigasi {
    override val route = "Insert_peserta"
    override val titleRes = "Insert Peserta"
}

object DestinasiDetailPeserta : DestinasiNavigasi {
    override val route = "detail_peserta/{id_peserta}"
    override val titleRes = "Detail Peserta"
    const val idArg = "id"
}
