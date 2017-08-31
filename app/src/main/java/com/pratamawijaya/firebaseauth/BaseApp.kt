package com.pratamawijaya.firebaseauth

import android.app.Application
import timber.log.Timber

/**
 * Created by pratama on 8/31/17.
 */
class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}