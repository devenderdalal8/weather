package com.dvndr.weather.ViewHolder

import android.view.View
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.dvndr.weather.R


class ReportViewHolder(itemView :View) : RecyclerView.ViewHolder(itemView) {
    // name , temp , condition , wind speed , humid , location

    var temp = itemView.findViewById<TextView>(R.id.temp)
    var condition = itemView.findViewById<TextView>(R.id.condition)
    var humid = itemView.findViewById<TextView>(R.id.humidity)
    var wind = itemView.findViewById<TextView>(R.id.speed)
    var location = itemView.findViewById<TextView>(R.id.location)



}