package com.devsoc.hrmaa.healthConnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.Permission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.lifecycle.lifecycleScope
import com.devsoc.hrmaa.R
import com.devsoc.hrmaa.databinding.ActivityHealthConnectBinding
import kotlinx.coroutines.launch

class HealthConnectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHealthConnectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthConnectBinding.inflate(layoutInflater)
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

                    binding.readCvHca.setOnTouchListener(object : View.OnTouchListener {
                        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                            when (event?.action) {
                                MotionEvent.ACTION_DOWN -> binding.readCvHca.setCardBackgroundColor(ContextCompat.getColorStateList(applicationContext, com.google.android.material.R.color.material_grey_300)!!)
                                MotionEvent.ACTION_UP -> binding.readCvHca.setCardBackgroundColor(ContextCompat.getColorStateList(applicationContext, com.google.android.material.R.color.m3_ref_palette_white)!!)
                            }

                            return v?.onTouchEvent(event) ?: true
                        }
                    })
                    binding.readCvHca.setOnClickListener {
                        val intent = Intent(this@HealthConnectActivity, DateActivity::class.java)
                        startActivity(intent)
                    }

                    binding.manageCvHca.setOnTouchListener(object : View.OnTouchListener {
                        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                            when (event?.action) {
                                MotionEvent.ACTION_DOWN -> binding.manageCvHca.setCardBackgroundColor(ContextCompat.getColorStateList(applicationContext, com.google.android.material.R.color.material_grey_300)!!)
                                MotionEvent.ACTION_UP -> binding.manageCvHca.setCardBackgroundColor(ContextCompat.getColorStateList(applicationContext, com.google.android.material.R.color.m3_ref_palette_white)!!)
                            }

                            return v?.onTouchEvent(event) ?: true
                        }
                    })
                    binding.manageCvHca.setOnClickListener {
                        val launch = packageManager.getLaunchIntentForPackage("com.google.android.apps.healthdata")
                        startActivity(launch)
                    }

                } else {
                    requestPermissions.launch(PERMISSIONS)
                }
            }
        }

        checkPermissionsAndRun(healthConnectClient)
    }

    private val PERMISSIONS =
        setOf(
            Permission.createReadPermission(HeartRateRecord::class),
            Permission.createWritePermission(HeartRateRecord::class),
        )
}