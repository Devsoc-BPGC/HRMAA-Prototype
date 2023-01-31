package com.devsoc.hrmaa.bluetooth

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devsoc.hrmaa.R

class AvailableDevicesActivity : AppCompatActivity(),DeviceAdapter.OnDeviceInfoListener {

    lateinit var rvPairedDevices: RecyclerView
    lateinit var rvAvailableDevices: RecyclerView
    lateinit var blueAdapt :BluetoothAdapter
    lateinit var progressBar: ProgressBar
    lateinit var pairedDevices: MutableList<BluetoothDevice>
    var availDevList = mutableListOf<BluetoothDevice>()

    lateinit var btnDiscover: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_devices)

        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        blueAdapt = bluetoothManager.adapter

        //TODO: Permission code block has to be put in the homescreen
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_ADVERTISE,
                Manifest.permission.BLUETOOTH_SCAN,
                )
        )

        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE

        rvPairedDevices  =findViewById<RecyclerView>(R.id.rvPairedDevices)
        rvAvailableDevices = findViewById(R.id.rvAvailableDevices)
        btnDiscover = findViewById(R.id.btnDiscover)

        pairedDevices = blueAdapt.getBondedDevices().toMutableList()
        if(pairedDevices.size != 0 ){
            rvPairedDevices.adapter = DeviceAdapter(this,pairedDevices.toMutableList())
            rvPairedDevices.layoutManager = LinearLayoutManager(this)
        }

        //Now gonna search for new devices

        val intentFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(bluetoothDeviceListener, intentFilter)
        val intentFilter1 = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        registerReceiver(bluetoothDeviceListener, intentFilter1)

        btnDiscover.setOnClickListener {

            if (blueAdapt.isDiscovering()) {
                blueAdapt.cancelDiscovery()
            }
            blueAdapt.startDiscovery()                                                          //Herein, if we don't use intent filter thing in top
            progressBar.visibility = View.VISIBLE
        }


    }

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
        {

            if( (it[Manifest.permission.BLUETOOTH] == false
                        &&
                        (it[Manifest.permission.BLUETOOTH_CONNECT] == false
                                || it[Manifest.permission.BLUETOOTH_ADVERTISE]==false
                                ||it[Manifest.permission.BLUETOOTH_SCAN]==false))
                || (it[Manifest.permission.ACCESS_COARSE_LOCATION] ==false && it[Manifest.permission.ACCESS_FINE_LOCATION] ==false)){
                Toast.makeText(this,"Please provide required permissions", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"Permissions granted successfully", Toast.LENGTH_SHORT).show()
            }
    }

    private val bluetoothDeviceListener: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                device?.let{
                    availDevList.add(device)
                    rvAvailableDevices.adapter = DeviceAdapter(this@AvailableDevicesActivity,availDevList.toMutableList())
                    rvAvailableDevices.layoutManager = LinearLayoutManager(this@AvailableDevicesActivity)
                    Log.d("Dicovery",availDevList.toString())
                }
                Log.d("Dicovery",availDevList.toString())
            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {
                progressBar.setVisibility(View.GONE)
            }
        }
    }
    //TODO: THIS ACTIVITY MUST BE CALLED WITH THE startActivittyForResult using
//    val intent =Intent(this, DeviceListActivity::class.java)
//    startActivityForResult(intent, SELECT_DEVICE)

    override fun onDeviceInfoListener(intent: Intent) {
        Log.d("DeviceListActivity",intent.getStringExtra("devName").toString())
        setResult(RESULT_OK, intent)
        finish()
    }

}