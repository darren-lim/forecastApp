package com.example.forecastmvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.forecastmvvm.data.network.currentweather.response.CurrentWeatherResponse

@Database(
    entities = [CurrentWeatherResponse::class],
    version = 1
)
abstract class ForecastDatabase: RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao

    // create singleton forecast db
    companion object {
        @Volatile private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext, ForecastDatabase::class.java, "forecast.db").build()
    }
}