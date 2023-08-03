package com.my.note_app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplicayion :Application() {
    override fun onCreate() {
        super.onCreate()
    }
}