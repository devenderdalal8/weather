package com.dvndr.weather.Model.CalenderFragment

import com.google.gson.annotations.SerializedName



data class Sys (

	@SerializedName("country") val country : String,
	@SerializedName("sunrise") val sunrise : Int,
	@SerializedName("sunset") val sunset : Int
)