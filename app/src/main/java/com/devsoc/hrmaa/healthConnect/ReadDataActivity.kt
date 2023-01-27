package com.devsoc.hrmaa.healthConnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.Permission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.devsoc.hrmaa.databinding.ActivityReadDataBinding
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZonedDateTime
import java.util.*

class ReadDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadDataBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val startDate = intent.getStringExtra("start_date").toString()
        val endDate = intent.getStringExtra("end_date").toString()
        val sd = Date(startDate.substring(6,10).toInt(), startDate.substring(3,5).toInt(), startDate.substring(0,2).toInt())
        val ed = Date(endDate.substring(6,10).toInt(), endDate.substring(3,5).toInt(), endDate.substring(0,2).toInt())
        val healthConnectClient = HealthConnectClient.getOrCreate(this)

        val requestPermissionActivityContract =
            healthConnectClient.permissionController.createRequestPermissionActivityContract()

        val requestPermissions =
            registerForActivityResult(requestPermissionActivityContract) {

            }
        // Create the permissions launcher.

        fun checkPermissionsAndRun(client: HealthConnectClient) {
            lifecycleScope.launch {
                val granted = client.permissionController.getGrantedPermissions(PERMISSIONS)
                if (granted.containsAll(PERMISSIONS)) {
                    // Permissions already granted
                    val end = ed.toInstant()
                    val start = sd.toInstant()
                    readHeartRateByTimeRange(healthConnectClient, start, end)

                } else {
                    requestPermissions.launch(PERMISSIONS)
                }
            }
        }

        checkPermissionsAndRun(healthConnectClient)

    }

    private fun readHeartRateByTimeRange(
        healthConnectClient: HealthConnectClient,
        startTime: Instant,
        endTime: Instant
    ) {
        lifecycleScope.launch {
            val response =
                healthConnectClient.readRecords(
                    ReadRecordsRequest(
                        HeartRateRecord::class,
                        timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
                    )
                )

            val series = mutableListOf(
                HeartRateRecord.Sample(Instant.now().minusSeconds(259200), 101),
                HeartRateRecord.Sample(Instant.now().minusSeconds(172800), 89),
                HeartRateRecord.Sample(Instant.now().minusSeconds(86400), 69),
                HeartRateRecord.Sample(Instant.now(), 75)
            )
            val adapter = HeartRateSeriesAdapter(series)
            binding.heartRateSeriesRvRda.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(context)
            }

            if (response.records.isNotEmpty()) {
                binding.dataTvRdf.text = ""
                /*for (rec in response.records) {
                   val adapter = HeartRateSeriesAdapter(rec.samples)
                    binding.heartRateSeriesRvRda.apply {
                        this.adapter = adapter
                        layoutManager = LinearLayoutManager(context)
                    }
                }*/
            } else {
                //binding.dataTvRdf.text = "No records found!"
            }

        }
    }

    private val PERMISSIONS =
        setOf(
            Permission.createReadPermission(HeartRateRecord::class),
            Permission.createWritePermission(HeartRateRecord::class),
        )

}