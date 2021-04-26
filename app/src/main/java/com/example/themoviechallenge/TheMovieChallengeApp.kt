package com.example.themoviechallenge

import android.app.Application
import android.util.Log
import com.example.themoviechallenge.module.networkModule
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
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

        printFirebaseToken()
    }

    private fun printFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            task.result?.let { token ->
                Log.d("FCM", token)
            }
        })
    }
}