package com.example.ecommerce_social_media

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"inside onCreate")
    }

    companion object{
        const val TAG = "NEW APPLICATION"
    }
}