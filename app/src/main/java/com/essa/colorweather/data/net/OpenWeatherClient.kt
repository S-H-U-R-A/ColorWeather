package com.essa.colorweather.data.net
import Json4Kotlin_Base
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory

object OpenWeatherClient {
    private val openWeatherapi: OpenWeatherApi
    private const val  API_KEY: String  = "c1cb69d2d06a27a5b65dc49c1e266a13"
    private const val  OPEN_WEATHER_API     = "https://api.openweathermap.org/"
    private val coordinates: Pair<String,String>     = Pair("7.11392", "-73.1198 7")

    init {
        val builder = okhttp3.OkHttpClient.Builder()
        val okHttpClient = builder.build()
        //Se construye el cliente de Retrofit
        val retrofit = Builder()
            .baseUrl(this.OPEN_WEATHER_API)
            //La sig Linea lo que hace es decirle a la llamada que
            // que el obj Json lo convierta a clases de KT usando GSON
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        openWeatherapi = retrofit.create(OpenWeatherApi::class.java)
    }

    public fun getWeather(lat: String = coordinates.first,
                          lon: String = coordinates.second): Call<Json4Kotlin_Base>{
        return openWeatherapi.getWeather( lat,lon, this.API_KEY );
    }

}