package com.dvndr.weather.View.Activity

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dvndr.weather.R
import com.dvndr.weather.View.Fragment.CalenderFragment
import com.dvndr.weather.View.Fragment.HomeFragment
import com.dvndr.weather.View.Fragment.ReportFragment
import com.dvndr.weather.View.Fragment.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    var mName: String? = null
    var mLatitude: Double? = null
    var mLongitude: Double? = null

    lateinit var sharePreference: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var DEFAULT:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mName = intent.getStringExtra("name")
        mLatitude = intent.getDoubleExtra("lat", 0.0)
        mLongitude = intent.getDoubleExtra("long", 0.0)

        sharePreference = this.getSharedPreferences("switch", MODE_PRIVATE)
        var value = sharePreference.getBoolean("switch", DEFAULT)

        val navigateView = findViewById<BottomNavigationView>(R.id.navigationView)
        loadFragment(HomeFragment.newInstance(mName, mLatitude, mLongitude, value))
        navigateView.setOnNavigationItemSelectedListener(
            object :BottomNavigationView.OnNavigationItemSelectedListener{
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.home -> {
                            loadFragment(HomeFragment.newInstance(mName, mLatitude, mLongitude, value))
                            return true
                        }
                        R.id.calender -> {
                            loadFragment(CalenderFragment.newInstance(value))
                            return true
                        }
                        R.id.report -> { loadFragment(ReportFragment.newInstance(mName, mLatitude, mLongitude, value))
                            return true
                        }
                        R.id.setting -> {
                            loadFragment(SettingFragment.newInstance(mName, value))
                            return true
                        }
                    }
                    return false
                }
            })

    }



    fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
