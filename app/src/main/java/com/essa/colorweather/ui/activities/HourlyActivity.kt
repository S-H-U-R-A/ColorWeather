package com.essa.colorweather.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.essa.colorweather.R
import kotlinx.android.synthetic.main.activity_hourly.*

class HourlyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hourly)
        //OBTENEMOS LOS DATOS DE LA ACTIVIDAD ANTERIOR
        val description = intent.getStringArrayExtra(MainActivity.HOURLY_DESCRIPTION)

        val adaptador = ArrayAdapter<String>(this,
                                    android.R.layout.simple_list_item_1,
                                    description)
        hourlyWeatherList.adapter = adaptador
    }

}
