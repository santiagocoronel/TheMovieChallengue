package com.example.themoviechallenge

import android.app.Application
import com.example.themoviechallenge.module.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.java.KoinJavaComponent.getKoin

class TheMovieChallengeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@TheMovieChallengeApp)
            koin.loadModules(
                listOf(
                    networkModule
                )
            )
            koin.createRootScope()
        }

        getKoin().setProperty("BASE_URL", BuildConfig.BASE_URL)

    }
}