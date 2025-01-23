package com.example.tugasakhirpam

import android.app.Application
import com.example.tugasakhirpam.dependeciesinjection.AppContainer
import com.example.tugasakhirpam.dependeciesinjection.KonserContainer

class KonserApplications : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = KonserContainer()  // Menggunakan AppContainerImpl untuk dependensi
    }
}
