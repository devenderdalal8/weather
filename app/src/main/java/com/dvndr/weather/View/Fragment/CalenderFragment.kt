package com.dvndr.weather.View.Fragment

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteCursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.dvndr.weather.Data.DataFactory
import com.dvndr.weather.Model.CalenderFragment.CalenderData
import com.dvndr.weather.R
import retrofit2.Call
import retrofit2.Response
import java.util.*

private const val mValue = "value"

class CalenderFragment : Fragment()  {
    var Date: String? = null

    var getValue: Boolean? = null
    lateinit var spinner: Spinner
    var name: TextView? = null
    var condition: TextView? = null
    var temp: TextView? = null
    var speed: TextView? = null
    lateinit var switch : SwitchCompat
    var humidity: TextView? = null
    var location: TextView? = null

    lateinit var city: TextView
    var cityName:String = "Noida"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            getValue = it.getBoolean(mValue)

        }
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calender, container, false)
        val calendar = view.findViewById<CalendarView>(R.id.calender)

        city = view.findViewById(R.id.city)
        name = view?.findViewById(R.id.name)
        condition = view?.findViewById(R.id.condition)
        temp = view?.findViewById(R.id.temp)
        speed = view?.findViewById(R.id.speed)
        humidity = view?.findViewById(R.id.humidity)
        location = view?.findViewById(R.id.location)


        val date = view.findViewById<TextView>(R.id.date)
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Date = dayOfMonth.toString() + "-" + (month + 1) + "-" + year
            date.text = Date
        }
        spinner = view.findViewById<Spinner>(R.id.spinner)
        switch = view.findViewById(R.id.settingSwitch)
        getCalaenderDataChecked()
        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                city.text = spinner.selectedItem.toString()
                cityName = city.text.toString()
                getCalaenderData()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                city.text = "Noida"
                getCalaenderData()
            }
        })
        switch.setOnClickListener{
            if (switch.isChecked()) {

                switch.isChecked = true
                getCalaenderDataChecked()

            } else {

                getCalaenderData()
            }
        }
        return view
    }

    private fun getCalaenderData() {
        val key = "e3d620dc102aa9ddb00c872c54deb990"
        val data = DataFactory.dataInterface.getCalender(q = cityName, apiid = key)
        data.enqueue(object : retrofit2.Callback<CalenderData> {
            override fun onResponse(call: Call<CalenderData>, response: Response<CalenderData>) {
                Log.d("fail", response.toString())
                var data = response.body()
                val tempString: Float? = data?.main?.temp
                val tempFloat: Float? = tempString
                if (getValue == false) {
                    val a: Float? = (tempFloat?.plus(273.15))?.toFloat()
                    temp?.text = a.toString() + "  °F"
                } else {
                    val a: Float? = (tempFloat?.minus(273.15))?.toFloat()
                    temp?.text = a.toString() + " °C"
                }

                condition?.text = data?.weather?.get(0)?.main.toString()
                humidity?.text = data?.main?.humidity.toString() + "  air g.kg-1"
                location?.text = data?.name.toString()
                speed?.text = data?.wind?.speed.toString() + "  km/h"

            }

            override fun onFailure(call: Call<CalenderData>, t: Throwable) {
                Log.d("fail", t.toString())
            }
        })
    }

     private fun getCalaenderDataChecked() {
            val key = "e3d620dc102aa9ddb00c872c54deb990"
            val data = DataFactory.dataInterface.getCalender(q = cityName, apiid = key)
            data.enqueue(object : retrofit2.Callback<CalenderData> {
                override fun onResponse(call: Call<CalenderData>, response: Response<CalenderData>) {
                    Log.d("fail", response.toString())
                    var data = response.body()
                    val tempString: Float? = data?.main?.temp
                    val tempFloat: Float? = tempString
                    if (getValue == true) {
                        val a: Float? = (tempFloat?.plus(273.15))?.toFloat()
                        temp?.text = a.toString() + "  °F"
                    } else {
                        val a: Float? = (tempFloat?.minus(273.15))?.toFloat()
                        temp?.text = a.toString() + " °C"
                    }

                    condition?.text = data?.weather?.get(0)?.main.toString()
                    humidity?.text = data?.main?.humidity.toString() + "  air g.kg-1"
                    location?.text = data?.name.toString()
                    speed?.text = data?.wind?.speed.toString() + "  km/h"

                }

                override fun onFailure(call: Call<CalenderData>, t: Throwable) {
                    Log.d("fail", t.toString())
                }
            })
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    companion object {
        @JvmStatic
        fun newInstance(Value: Boolean) =
            CalenderFragment().apply {
                arguments = Bundle().apply {
                    Value?.let { putBoolean(mValue, it) }
                }
            }
    }

}