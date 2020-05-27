package com.essa.colorweather

import Json4Kotlin_Base
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.essa.colorweather.data.net.OpenWeatherApi
import com.essa.colorweather.data.net.OpenWeatherClient
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        OpenWeatherClient.getWeather().enqueue(object: retrofit2.Callback<Json4Kotlin_Base>{
            override fun onFailure(
                call: Call<Json4Kotlin_Base>, t: Throwable
            ) {
                Log.d("MainActivity", "Error")
            }

            override fun onResponse(
                call: Call<Json4Kotlin_Base>,
                response: Response<Json4Kotlin_Base>
            ) {
                Log.d("MainActivity", "Ok, datos obtenidos")
            }

        })
    }
}
