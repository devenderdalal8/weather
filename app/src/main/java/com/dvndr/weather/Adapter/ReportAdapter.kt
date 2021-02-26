package com.dvndr.weather.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dvndr.weather.Model.ReportFragment.ReportData
import com.dvndr.weather.R
import com.dvndr.weather.ViewHolder.ReportViewHolder

class ReportAdapter(var context: Context, var data: ArrayList<ReportData?>)
    : RecyclerView.Adapter<ReportViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item , parent , false)
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val detail = data.get(0)?.list?.get(position)
        holder.condition.text = detail?.weather?.get(0)?.main.toString()

        val tempString: Float? = detail?.main?.temp
        val tempFloat: Float? = tempString
        val a: Float? = (tempFloat?.minus(273.15))?.toFloat()
        holder.temp.text = a.toString() + " °C"
        holder.humid.text = detail?.main?.humidity.toString()+ "  air g.kg-1"
        holder.wind.text = detail?.wind?.speed.toString()
        holder.location.text = data.get(0)?.city?.name.toString()

    }

     override fun getItemCount(): Int {
        return 7
    }
}