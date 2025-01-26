package com.example.tugasakhirpam.navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

object DestinasiHalamanAwalKonser : DestinasiNavigasi {
    override val route = "halaman_awal_konser"
    override val titleRes = "Halaman Awal Konser"
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
    const val idArg = "id_peserta"
}

object DestinasiEditPeserta : DestinasiNavigasi {
    override val route = "edit_peserta/{id_peserta}"
    override val titleRes = "Edit Peserta"
    const val idArg = "id_peserta"

    fun createRoute(idPeserta: String): String {
        return "edit_peserta/$idPeserta"
    }
}

//EVENT
object DestinasiHomeEvent : DestinasiNavigasi {
    override val route = "home_event"
    override val titleRes = "Home Event"
}

object DestinasiDetailEvent : DestinasiNavigasi {
    override val route = "detail_event/{id_event}"
    override val titleRes = "Detail Event"
    const val idArg = "id_event"
}
