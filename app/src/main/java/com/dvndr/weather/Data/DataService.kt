package com.dvndr.weather.Data

import com.dvndr.weather.Model.CalenderFragment.CalenderData
import com.dvndr.weather.Model.HomeFragment.HomeData
import com.dvndr.weather.Model.ReportFragment.ReportData
import com.dvndr.weather.View.Fragment.CalenderFragment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DataService {

    @GET("weather")
    fun getDataUsingLatLong(@Query("lat") lat:String ,
                      @Query("lon") lon:String ,
                      @Query("appid") apiid :String): Call<HomeData>

    @GET("forecast")
    fun getReportData(@Query("lat") lat:String ,
                      @Query("lon") lon :String, @Query("appid") apiid :String):
            Call<ReportData>

    @GET("weather")
    fun getCalender(@Query("q") q :String,
                    @Query("appid") apiid :String) :
        Call<CalenderData>
}