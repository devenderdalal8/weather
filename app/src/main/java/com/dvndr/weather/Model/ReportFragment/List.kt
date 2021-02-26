package com.dvndr.weather.Model.ReportFragment
import com.google.gson.annotations.SerializedName


data class List (

	@SerializedName("dt") val dt : Float,
	@SerializedName("main") val main : Main,
	@SerializedName("weather") val weather : ArrayList<Weather>,
	@SerializedName("clouds") val clouds : Clouds,
	@SerializedName("wind") val wind : Wind,
	@SerializedName("visibility") val visibility : Float,
	@SerializedName("pop") val pop : Double,
	@SerializedName("sys") val sys : Sys,
	@SerializedName("dt_txt") val dt_txt : String
)