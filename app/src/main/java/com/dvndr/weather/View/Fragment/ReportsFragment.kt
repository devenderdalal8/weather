package com.dvndr.weather.View.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dvndr.weather.Adapter.ReportAdapter
import com.dvndr.weather.Data.DataFactory
import com.dvndr.weather.Model.ReportFragment.ReportData
import com.dvndr.weather.R
import retrofit2.Call
import retrofit2.Response

//
private  const val mName = "name"
private const val mLatitude = "lat"
private const val mLongitude = "long"
private const val mValue = "value"


class ReportFragment : Fragment() {

    var getName: String? = null
    var getLatitude: Double? = null
    var getLongitude: Double? = null
    var getValue: Boolean ?= null
    var name: TextView? = null
    var location: TextView? = null
    var recyclerView: RecyclerView? = null
    lateinit var adapter: ReportAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            getName = it.getString(mName)
            getLatitude = it.getDouble(mLatitude)
            getLongitude = it.getDouble(mLongitude)
            getValue = it.getBoolean(mValue)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view  = inflater.inflate(R.layout.fragment_report, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        name = view.findViewById(R.id.name)
        location = view.findViewById(R.id.location)

        name?.text = getName

        getReportData()
        return view
    }

    private fun getReportData() {
        val key = "e3d620dc102aa9ddb00c872c54deb990"
        val call = DataFactory.dataInterface.getReportData(lat = getLatitude.toString() ,
        lon = getLongitude.toString() , apiid = key)
        call.enqueue(object : retrofit2.Callback<ReportData>{
            override fun onResponse(call: Call<ReportData>, response:
            Response<ReportData>) {
                val data = response.body()
                val dataArray : ArrayList<ReportData?> = arrayListOf(data)
//                name?.text= dataArray.toString(

                adapter = ReportAdapter(context ?: return, dataArray)
                recyclerView?.adapter = adapter
                recyclerView?.layoutManager = LinearLayoutManager(context)

            }

            override fun onFailure(call: Call<ReportData>, t: Throwable) {
                Log.d("failed"," hello "+t.toString())

            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(Name: String?, Latitude: Double? , Longitude : Double? , Value : Boolean) =
            ReportFragment().apply {
                arguments = Bundle().apply {
                    putString(mName, Name)
                    Latitude?.let { putDouble(mLatitude, it) }
                    Longitude?.let { putDouble(mLongitude, it) }
                    Value?.let { putBoolean(mValue, it) }
                }
            }
    }
}