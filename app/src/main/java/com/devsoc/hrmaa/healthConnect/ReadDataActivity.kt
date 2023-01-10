package com.devsoc.hrmaa.healthConnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.Permission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.lifecycle.lifecycleScope
import com.devsoc.hrmaa.databinding.ActivityReadDataBinding
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZonedDateTime

class ReadDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadDataBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


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
                    val end = Instant.now()
                    val start = ZonedDateTime.now().minusYears(5).toInstant()
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

            if (response.records.isNotEmpty()) {
                for (rec in response.records) {
                    //TODO : process each heart rate record
                    for (hr in rec.samples) {

                    }
                }
            } else {
                binding.dataTvRdf.text = "No records found!"
            }

        }
    }

    private val PERMISSIONS =
        setOf(
            Permission.createReadPermission(HeartRateRecord::class),
            Permission.createWritePermission(HeartRateRecord::class),
        )

}