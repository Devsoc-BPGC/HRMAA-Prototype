package com.devsoc.hrmaa


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.health.connect.client.HealthConnectClient
import com.devsoc.hrmaa.bluetooth.AvailableDevicesActivity
import com.devsoc.hrmaa.databinding.ActivityMainBinding
import com.devsoc.hrmaa.ecg.ECGActivity
import com.devsoc.hrmaa.fitbit.FitbitActivity
import com.devsoc.hrmaa.healthConnect.HealthConnectActivity
import com.devsoc.hrmaa.ppg.PPGActivity


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        binding.hcCvMa.setOnClickListener {
            //launch only if Health Connect is installed on device
            if (HealthConnectClient.isAvailable(this)) {
                startActivity(Intent(this, HealthConnectActivity::class.java))
            } else {
                Toast.makeText(
                    this,
                    "Please install the Google Health Connect App first.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.fitbitCvMa.setOnClickListener {
            startActivity(Intent(this, FitbitActivity::class.java))
        }
        binding.ppgCvMa.setOnClickListener {
            startActivity(Intent(this, PPGActivity::class.java))
        }
        binding.ecgCvMa.setOnClickListener {
            startActivity(Intent(this, AvailableDevicesActivity::class.java))
        }


    }

}