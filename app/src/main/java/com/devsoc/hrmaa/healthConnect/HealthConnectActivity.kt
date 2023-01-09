package com.devsoc.hrmaa.healthConnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.Permission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.devsoc.hrmaa.R
import com.devsoc.hrmaa.databinding.ActivityHealthConnectBinding
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZonedDateTime

class HealthConnectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHealthConnectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthConnectBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.readCvHca.setOnClickListener {
            val intent = Intent(this@HealthConnectActivity, ReadDataActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}