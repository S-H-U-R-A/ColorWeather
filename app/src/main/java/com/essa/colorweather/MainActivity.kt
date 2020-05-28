package com.essa.colorweather

import Current
import Json4Kotlin_Base
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.essa.colorweather.data.net.OpenWeatherClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        OpenWeatherClient.getWeather().enqueue(object: retrofit2.Callback<Json4Kotlin_Base>{
            override fun onFailure(
                call: Call<Json4Kotlin_Base>, t: Throwable
            ) {
                //Llamar una funcion que despliegue una funcion de error
            }

            override fun onResponse(
                call: Call<Json4Kotlin_Base>,
                response: Response<Json4Kotlin_Base>
            ) {
                setUpWidgets(response.body()?.current)
            }

        })
    }

    public fun setImage(icon: String){
        Picasso.get()
        .load("https://openweathermap.org/img/w/${icon}.png")
        .resize(50,50)
        .centerCrop()
        .into(iconImageView)
    }

    private fun setUpWidgets(currently: Current?){
        descriptionTextView.text = currently?.weather?.get(0)?.description?.capitalize()
        minTextView.text = "${currently?.temp?.roundToInt()} C°"
        precipProbTextView.text = "${currently?.humidity} %"
        setImage( currently?.weather?.get(0)?.icon.toString() )
        dateTextView.text = getDateTime()?.capitalize() ?: "No date"
    }

    private fun getDateTime(): String?{
        return try{
            val simpleDateFormat = SimpleDateFormat("MMMM d", Locale.getDefault())
            val date  = Calendar.getInstance().time
            simpleDateFormat.format(date)
        }catch (e: Exception){
            e.toString()
        }
    }

}
