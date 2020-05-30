package com.essa.colorweather.ui.activities

import Current
import Daily
import Json4Kotlin_Base
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.essa.colorweather.data.net.OpenWeatherClient
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt
import com.essa.colorweather.R
import com.essa.colorweather.ui.convertTime
import com.google.android.gms.location.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    companion object{
        const val HOURLY_DESCRIPTION:String = "HOURLY_DESCRIPTION"
        const val DAILY_DATA:String = "DAILY_DATA"
    }

    var hourlyDescription: List<String>? = null
    var dailyData: List<Daily>? = null

    //Verificación de permisos
    val PERMISSION_ID = 42

    //Latitud y longitud
    var latitude: Double? = null
    var longitude: Double? = null

    lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation();

    }

    private fun getWeather(){
        //Se muestra el progress bar
        displayProgressBar(true)
        //Se ocultan los widgets
        displayUi(false)
        //Se hace la petición
        OpenWeatherClient.getWeather(latitude.toString(), longitude.toString()).enqueue(object: retrofit2.Callback<Json4Kotlin_Base>{
            override fun onFailure(
                call: Call<Json4Kotlin_Base>, t: Throwable
            ) {
                //Se oculta la barra de cargando
                displayProgressBar(false)
                //Llamar una funcion que despliegue una funcion de error
                displayErrorMessaage()

            }

            override fun onResponse(
                call: Call<Json4Kotlin_Base>,
                response: Response<Json4Kotlin_Base>
            ) {
                //Se oculta la barra de cargando
                displayProgressBar(false)
                //se muestran los widgets
                displayUi(true)
                //Se valida que la respuesta sea exitosa
                if(response.isSuccessful){

                    dailyData = response.body()?.daily

                    hourlyDescription = response.body()?.hourly?.map{
                        convertTime(it.dt, "MMMM dd, hh:mm a") + "  " + it.weather[0].description
                    }

                    //Se llama el metodo de modificar los widgets
                    setUpWidgets(response.body()?.current)
                }else{
                    displayErrorMessaage()
                }

            }

        })
    }

    private fun displayUi(visible: Boolean){
        dateTextView.visibility = if(visible) View.VISIBLE else View.GONE
        iconImageView.visibility = if(visible) View.VISIBLE else View.GONE
        descriptionTextView.visibility = if(visible) View.VISIBLE else View.GONE
        precipProbTextView.visibility = if(visible) View.VISIBLE else View.GONE
        minTextView.visibility = if(visible) View.VISIBLE else View.GONE
        dailyButton.visibility = if(visible) View.VISIBLE else View.GONE
        hourlyButton.visibility = if(visible) View.VISIBLE else View.GONE
    }

    private fun displayProgressBar(visible: Boolean){
        progressBar.visibility = if(visible){
            View.VISIBLE
        }else{
            View.GONE
        }
    }

    private fun displayErrorMessaage(){
        Snackbar.make(
                mainLayout,
                getString(R.string.network_error),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(this.getString(R.string.ok)) {
                    getWeather()
                }.show()
    }

    private fun setImage(icon: String){
        Picasso.get()
        .load("https://openweathermap.org/img/w/${icon}.png")
        .resize(50,50)
        .centerCrop()
        .into(iconImageView)
    }

    private fun setUpWidgets(currently: Current?){
        descriptionTextView.text = currently?.weather?.get(0)?.description?.capitalize()
            ?: getString(R.string.no_data)
        minTextView.text = getString(R.string.temp, currently?.temp?.roundToInt())
        precipProbTextView.text = getString(R.string.precipProb, currently?.humidity)
        setImage( currently?.weather?.get(0)?.icon.toString() )
        dateTextView.text = getDateTime()?.capitalize() ?: getString(R.string.no_data)
    }

    private fun getDateTime(): String?{
        return try{
            val format = "MMMM d";
            val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault());
            val date  = Calendar.getInstance().time;
            simpleDateFormat.format(date);
        }catch (e: Exception){
            e.toString();
        }
    }

    public fun startDailDailyActivity(view: View){
        //CREANDOO UN INTENT, el poner ::class.java despues del activity lo que le indica a android
        //es que le aseguramos que dailyActivity es una clase compatible con Java
        val intent = Intent(this, DailyActivity::class.java)
        val array = dailyData as? ArrayList<Parcelable>
        intent.putParcelableArrayListExtra(DAILY_DATA, array)
        startActivity(intent)
    }

    public fun startHourlyActivity(view: View){
        val intent = Intent(this, HourlyActivity::class.java)
        val array = hourlyDescription?.toTypedArray()
        intent.putExtra(HOURLY_DESCRIPTION, array)
        startActivity(intent)
    }

    //Verificar Permisos Android 6
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true

        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        latitude = location.latitude
                        longitude = location.longitude
                        getWeather();
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            latitude = mLastLocation.latitude
            longitude = mLastLocation.longitude
            getWeather();
        }
    }

}
