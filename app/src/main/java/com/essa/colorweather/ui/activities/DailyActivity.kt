package com.essa.colorweather.ui.activities

import Daily
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.essa.colorweather.R
import com.essa.colorweather.ui.adapters.DailyAdapter
import kotlinx.android.synthetic.main.activity_daily.*

class DailyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily)
        val dailydata = intent.getParcelableArrayListExtra<Daily>(MainActivity.DAILY_DATA)

        val dailyAdapter = DailyAdapter(dailydata)

        //Aca se le dice al recyclerView que queremos que la info de despliegue en forma de lista
        //o linearLayout que fue el tipo que creamos el item_list.xml
        dailyRecyclerView.layoutManager  = LinearLayoutManager(this)
        //Asignamos el adaptador
        dailyRecyclerView.adapter = dailyAdapter

    }
}
