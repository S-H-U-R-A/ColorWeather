package com.essa.colorweather.data.net

import Json4Kotlin_Base
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

//Al enter
interface OpenWeatherApi {
    @GET("data/2.5/onecall?lat={latitude}&lon={longitude}&appid={api_key}")
    fun getWeather(
        @Path("latitude") latitude: String,
        @Path("longitude") longitude: String,
        @Path("api_key") api_key : String
    ): Call<Json4Kotlin_Base>
}