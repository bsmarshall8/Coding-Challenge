package com.slack.exercise

import androidx.room.Room
import com.slack.exercise.dagger.DaggerAppComponent
import com.slack.exercise.db.AppDatabase
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        database = Room.databaseBuilder(this, AppDatabase::class.java, "app_database")
            .build()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    companion object {
        lateinit var database: AppDatabase
            private set
    }
}