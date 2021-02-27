package com.dvndr.weather.View.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.dvndr.weather.Data.DataFactory
import com.dvndr.weather.Model.HomeFragment.HomeData
import com.dvndr.weather.R
import retrofit2.Call
import retrofit2.Response

private const val mName = "name"
private const val mLatitude = "lat"
private const val mLongitude = "long"
private const val mValue = "value"

class HomeFragment : Fragment() {
     var getName: String? = null
     var getLatitude: Double? = null
     var getLongitude: Double? = null
     var getValue: Boolean? = null
     var name: TextView? = null
     var condition: TextView? = null
     var temp: TextView? = null
     var speed: TextView? = null
     var humidity: TextView? = null
     var location: TextView? = null
     lateinit var switch : SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            getName = it.getString(mName)
            getLatitude = it.getDouble(mLatitude)
            getLongitude = it.getDouble(mLongitude)
            getValue = it.getBoolean(mValue)
            Log.d("home" , getName.toString()+"  lat  = " + getLatitude.toString()
            + " long = "+getLongitude.toString() +" value : " + getValue.toString())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        name = view?.findViewById(R.id.name)
        condition = view?.findViewById(R.id.condition)
        temp = view?.findViewById(R.id.temp)
        speed = view?.findViewById(R.id.speed)
        humidity = view?.findViewById(R.id.humidity)
        location = view?.findViewById(R.id.location)
        switch = view.findViewById(R.id.settingSwitch)
        callApi()
        switch.setOnClickListener{
            if (switch.isChecked()) {
                switch.isChecked = true
                callApichecked()
            } else {
                callApi()
            }
        }

        name?.text = getName

        return view
    }

     fun callApichecked() {
         val key = "e3d620dc102aa9ddb00c872c54deb990"
         val data = DataFactory.dataInterface.getDataUsingLatLong(lat=  getLatitude.toString(),
             lon = getLongitude.toString(), apiid = key)

         data.enqueue(object : retrofit2.Callback<HomeData> {
             @SuppressLint("SetTextI18n")
             override fun onResponse(call: Call<HomeData>, response: Response<HomeData>) {
                 Log.d("fail" , response.toString())
                 var data = response.body()
                 if(response!= null)
                 {
                     val tempString : Float? = data?.main?.temp
                     val tempFloat : Float? = tempString
                     val a: Float? = (tempFloat?.minus(273.15))?.toFloat()
                     val b: Float? = (a?.times((9/5))?.plus(32))
                     temp?.text = b.toString()+"  °F"

                     condition?.text = data?.weather?.get(0)?.main.toString()
                     humidity?.text = data?.main?.humidity.toString() + "  air g.kg-1"
                     location?.text = data?.name.toString()
                     speed?.text = data?.wind?.speed.toString() + "  km/h"
                 }
             }

             override fun onFailure(call: Call<HomeData>, t: Throwable) {
                  Log.d("fail" , t.toString())
             }
         })
        }

     fun callApi() {
             val key = "e3d620dc102aa9ddb00c872c54deb990"
             val data = DataFactory.dataInterface.getDataUsingLatLong(lat=  getLatitude.toString(),
                lon = getLongitude.toString(), apiid = key)

             data.enqueue(object : retrofit2.Callback<HomeData> {
                 @SuppressLint("SetTextI18n")
                 override fun onResponse(call: Call<HomeData>, response: Response<HomeData>) {
                     Log.d("fail" , response.toString())
                     val data = response.body()
                     val tempString : Float? = data?.main?.temp
                     val tempFloat : Float? = tempString
                     val a: Float? = (tempFloat?.minus(273.15))?.toFloat()
                     temp?.text = a.toString() +" °C"

                     condition?.text = data?.weather?.get(0)?.main.toString()
                     humidity?.text = data?.main?.humidity.toString() + "  air g.kg-1"
                     location?.text = data?.name.toString()
                     speed?.text = data?.wind?.speed.toString() + "  km/h"
                 }

                 override fun onFailure(call: Call<HomeData>, t: Throwable) {
                      Log.d("fail" , t.toString())
                 }
             })
            }


    companion object {
        @JvmStatic
        fun newInstance(Name: String?, Latitude: Double? , Longitude : Double?, Value : Boolean?) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(mName, Name)
                    Latitude?.let { putDouble(mLatitude, it) }
                    Longitude?.let { putDouble(mLongitude, it) }
                    Value?.let { putBoolean(mValue, it) }
                }
            }
    }
}
