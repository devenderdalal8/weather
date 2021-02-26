package com.dvndr.weather.Data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataFactory {

    companion object DetailsService {

        val dataInterface: DataService

        init {

            val BASE_URL = "http://api.openweathermap.org/data/2.5/"
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            dataInterface = retrofit.create(DataService::class.java)
        }
    }
}
