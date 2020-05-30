package com.essa.colorweather.data.net
import Json4Kotlin_Base
import android.util.Log
import com.essa.colorweather.ui.activities.MainActivity
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory

object OpenWeatherClient {

    private val openWeatherapi: OpenWeatherApi
    private  val  UNITS = "metric"
    private  val  LANGUAGE = "es"
    private  val  API_KEY: String  = "c1cb69d2d06a27a5b65dc49c1e266a13"
    private  val  OPEN_WEATHER_API     = "https://api.openweathermap.org/data/2.5/"
    //private val coordinates: Pair<String,String>     = Pair( "7.8939099", "-72.5078201")

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

    public fun getWeather(lat: String , lon: String ): Call<Json4Kotlin_Base>{
        Log.i("Cliente", lat)
        return openWeatherapi.getWeather( lat,  lon, this.API_KEY, this.UNITS, this.LANGUAGE);
    }

}