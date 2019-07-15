package com.example.starwarsappwithmvvm

import android.app.Application
import android.arch.persistence.room.Room
import com.example.starwarsappwithmvvm.model.database.AppDatabase


class App : Application() {
    lateinit var database: AppDatabase

    companion object {
        private lateinit var instance: App
        fun getInstance(): App {
            return instance
        }

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .build()
    }


}