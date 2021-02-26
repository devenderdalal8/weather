package com.dvndr.weather.Model.ReportFragment
import com.google.gson.annotations.SerializedName



data class ReportData (
    @SerializedName("list") val list : ArrayList<List>,
    @SerializedName("city") val city : City
)