package com.essa.colorweather.ui.adapters

import Daily
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.essa.colorweather.R
import com.essa.colorweather.ui.convertTime
import kotlinx.android.synthetic.main.list_item.view.*

class DailyAdapter(private val daily: List<Daily>?): RecyclerView.Adapter<DailyAdapter.DailyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyHolder {
        val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
        return DailyHolder(view)
    }

    override fun getItemCount(): Int = daily?.size ?: 0

    //Este metodo se llama cada vez que se agrega un elelemento en el recilcreView
    override fun onBindViewHolder(holder: DailyHolder, position: Int) {
        daily?.let {
            //Se le pasa la configuración que se creo en la clase inner para la creación
            //de cada item del reciclerView
            holder.bind(it[position])
        }
    }

    inner class DailyHolder(val view: View): RecyclerView.ViewHolder(view){

        public fun bind(data: Daily) = with(view){
            dailyDateTextView.text = convertTime(data.dt, "MMMM dd")
            dailyDescriptiontextView.text = data.weather?.get(0)?.description
        }

    }

}