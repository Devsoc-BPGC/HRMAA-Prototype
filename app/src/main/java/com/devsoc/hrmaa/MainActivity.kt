package com.devsoc.hrmaa
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
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


        binding.hcCvMa.setOnClickListener{
            startActivity(Intent(this,HealthConnectActivity::class.java))
        }
        binding.fitbitCvMa.setOnClickListener{
            startActivity(Intent(this,FitbitActivity::class.java))
        }
        binding.ppgCvMa.setOnClickListener{
            startActivity(Intent(this,PPGActivity::class.java))
        }
        binding.ecgCvMa.setOnClickListener{
            startActivity(Intent(this,ECGActivity::class.java))
        }


    }

}