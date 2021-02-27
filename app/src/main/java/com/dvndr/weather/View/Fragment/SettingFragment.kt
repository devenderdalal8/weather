package com.dvndr.weather.View.Fragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.dvndr.weather.R

private const val mName = "name"
private const val mValue = "value"

class SettingFragment : Fragment() {
    var getName: String? = null
    var getValue: Boolean  = true
    var sharePreference: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    var check : Boolean = false
    lateinit var switch:SwitchCompat
    val DEFAULT : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            getName = it.getString(mName)
            getValue = it.getBoolean(mValue)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_setting, container, false)

        var name = view.findViewById<TextView>(R.id.settingName)
        switch = view.findViewById(R.id.settingSwitch)
        name.text= getName.toString()

        sharePreference = context?.getSharedPreferences("switch" , MODE_PRIVATE)
        editor=sharePreference?.edit()

        if(savedInstanceState ==null)
        {
            switch.setOnClickListener{
                if (switch.isChecked()) {
                    editor?.putBoolean("switch" , true)
                    editor?.apply()
                    editor?.commit()
                    switch.isChecked = true
                    check = switch.isChecked
                } else {
                    editor?.putBoolean("switch" , false)
                    editor?.apply()
                    editor?.commit()
                    switch.isChecked = false
                    check=switch.isChecked
                }
            }
        }
        else
        {
            val value = savedInstanceState.getBoolean("switch" )
            Log.d("value" , value.toString())
            switch.isChecked = value
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("switch" , switch.isChecked)
        super.onSaveInstanceState(outState)
    }

    companion object {
        @JvmStatic
        fun newInstance(Name: String? , value : Boolean) =
            SettingFragment().apply {
                arguments = Bundle().apply {
                    putString(mName, Name)

                }
            }
    }
}