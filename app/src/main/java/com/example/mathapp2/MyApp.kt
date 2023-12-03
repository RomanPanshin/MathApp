package com.example.mathapp2

import android.app.Application
import com.example.mathapp2.data.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR) // Use Level.NONE for no logging
            androidContext(this@MyApp)
            modules(appModule)
        }
    }
}
