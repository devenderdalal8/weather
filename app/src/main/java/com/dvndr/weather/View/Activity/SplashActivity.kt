package com.dvndr.weather.View.Activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dvndr.weather.R
import com.dvndr.weather.View.Fragment.HomeFragment

class SplashActivity : AppCompatActivity() , LocationListener {
      var locationManager: LocationManager?=null
     lateinit var mName: EditText
     lateinit var progressBar: ProgressBar
     private val locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mName = findViewById(R.id.name)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        progressBar = findViewById(R.id.progressBar)
        val button = findViewById<Button>(R.id.getLocation)

        button?.setOnClickListener {
                getLocation()
            }
    }

    fun getLocation() {
        progressBar.visibility = View.VISIBLE
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
            return
        } else {
            Toast.makeText(this,"Please Access Location" , Toast.LENGTH_SHORT).show()
        }
        locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    override fun onLocationChanged(location: Location) {
        val name = mName.text.toString()
        val latitude : Double = location.latitude
        val longitude : Double = location.longitude
        Log.d("lat" , latitude.toString())
        Log.d("long" , longitude.toString())
        Log.d("name" , name)

        if (!TextUtils.isEmpty(mName.toString())) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("long", longitude)
            intent.putExtra("lat", latitude)
            intent.putExtra("name", name)
            startActivity(intent)
            progressBar.visibility = View.GONE
        } else {
            Toast.makeText(this,"Please Fill Your Name",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun buildAlertMessageNoGps() {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    , 11)
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.cancel()
                finish()
            }
        val alert: AlertDialog  = builder.create()
        alert.show()


    }



}