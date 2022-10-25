package com.example.forecastmvvm.data

import com.example.forecastmvvm.data.network.currentweather.response.CurrentWeatherResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "cb7d947dcacaad81c6bffcbc59f13d0c"
const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

// https://api.openweathermap.org/data/2.5/weather?q=cerritos,ca,usa&appid=cb7d947dcacaad81c6bffcbc59f13d0c

interface OpenWeatherMapApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") location: String
    ): CurrentWeatherResponse

    companion object {
        operator fun invoke(): OpenWeatherMapApiService {
            // invoke allows creation of service without create function
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("appid", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherMapApiService::class.java)
        }
    }
}