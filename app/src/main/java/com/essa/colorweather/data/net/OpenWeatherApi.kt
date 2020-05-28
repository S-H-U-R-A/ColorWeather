package com.essa.colorweather.data.net

import Json4Kotlin_Base
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//Al enter
interface OpenWeatherApi {
    //Se toma la ultima referencia path en donde despues inician las variables
    //El parametro Query Se encarga de añadir los parametros en  el orden dado
    //Con RETROFIT HAY QUE TENER CUIDADO HASTA CON LOS / DE LAS CARPETAS
    @GET("onecall")
    fun getWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") api_key : String,
        @Query("units") units : String,
        @Query("lang") lang : String
    ): Call<Json4Kotlin_Base>
}