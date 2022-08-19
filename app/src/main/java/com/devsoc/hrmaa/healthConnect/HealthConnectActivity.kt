package com.devsoc.hrmaa.healthConnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.registerForActivityResult
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.Permission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.lifecycle.lifecycleScope
import com.devsoc.hrmaa.R
import kotlinx.coroutines.launch

class HealthConnectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_connect)

        if (HealthConnectClient.isAvailable(this)) {
            // Health Connect is available and installed.
            val healthConnectClient = HealthConnectClient.getOrCreate(this)

            val requestPermissionActivityContract = healthConnectClient.permissionController.createRequestPermissionActivityContract()

            val requestPermissions =
                registerForActivityResult(requestPermissionActivityContract) { granted ->
                    if (granted.containsAll(PERMISSIONS)) {
                        // Permissions successfully granted
                    } else {
                        // Lack of required permissions
                    }
                }
            // Create the permissions launcher.

            fun checkPermissionsAndRun(client: HealthConnectClient) {
                lifecycleScope.launch {
                    val granted = client.permissionController.getGrantedPermissions(PERMISSIONS)
                    if (granted.containsAll(PERMISSIONS)) {
                        // Permissions already granted
                    } else {
                        requestPermissions.launch(PERMISSIONS)
                    }
                }
            }
        } else {
            // ...
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