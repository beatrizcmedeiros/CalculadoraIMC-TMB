package com.cafezin.calculadoraimc

import android.app.Application
import com.cafezin.calculadoraimc.model.AppDatabase

class App: Application() {
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
    }
}