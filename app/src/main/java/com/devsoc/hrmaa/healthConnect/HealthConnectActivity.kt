package com.devsoc.hrmaa.healthConnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.Permission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.lifecycle.lifecycleScope
import com.devsoc.hrmaa.databinding.ActivityHealthConnectBinding
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZonedDateTime

class HealthConnectActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHealthConnectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthConnectBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (HealthConnectClient.isAvailable(this)) {
            // Health Connect is available and installed.
            val healthConnectClient = HealthConnectClient.getOrCreate(this)

            val requestPermissionActivityContract = healthConnectClient.permissionController.createRequestPermissionActivityContract()

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
                        val start = ZonedDateTime.now().minusYears(10).toInstant()
                        readHeartRateByTimeRange(healthConnectClient, start, end)
                    } else {
                        requestPermissions.launch(PERMISSIONS)
                    }
                }
            }

            checkPermissionsAndRun(healthConnectClient)
        } else {
            // ...
        }

    }

    fun readHeartRateByTimeRange(
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

            if(response.records.isNotEmpty()) {
                for (rec in response.records) {
                    //TODO : process each heart rate record
                    for(hr in rec.samples){

                    }
                }
            }
            else {
                binding.dataTvHca.text = "No records found!"
            }

        }
    }

    val PERMISSIONS =
        setOf(
            Permission.createReadPermission(HeartRateRecord::class),
            Permission.createWritePermission(HeartRateRecord::class),
            Permission.createReadPermission(StepsRecord::class),
            Permission.createWritePermission(StepsRecord::class)
        )
}