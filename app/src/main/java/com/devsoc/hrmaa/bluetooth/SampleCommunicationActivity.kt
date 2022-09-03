package com.devsoc.hrmaa.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.devsoc.hrmaa.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*
import java.util.*

@SuppressLint("MissingPermission")
class SampleCommunicationActivity : AppCompatActivity() {

    var bluetoothAdapter: BluetoothAdapter? = null
    var mSocket: BluetoothSocket? =null
    var MY_UUID = UUID.fromString("4cb4cec4-2017-4e54-9ef9-9e4aadaf033e")
    var apnaSocket : BluetoothSocket? = null
    var btDevice: BluetoothDevice? = BluetoothAdapter.getDefaultAdapter().getRemoteDevice("FC:AA:B6:AA:AC:22")
    lateinit var serverBtn: Button
    lateinit var clientBtn: Button
    var apnaServerSocket : BluetoothServerSocket?= null
    lateinit var textView: TextView
    lateinit var btnSendData: Button
    lateinit var etMessage: EditText
    val SELECT_DEVICE = 0

    var TAG= "ME HOO GIAN, ME HU BADA TAKATVAR"

    var inStream: InputStream? =null
    var outStream: OutputStream? = null
    var buffer: ByteArray? = ByteArray(1024)
    var messageByteArray = "kem palty".toByteArray()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_communication)

        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.getAdapter()
        if (bluetoothAdapter == null) {
            Toast.makeText(
                this,
                "Abe bluetooth he nahi, chala bluetooth se chat karne",
                Toast.LENGTH_SHORT
            ).show()
        }

        serverBtn =findViewById(R.id.ServerBtn)
        clientBtn =findViewById(R.id.clientButton)
        textView = findViewById(R.id.textView)
        btnSendData = findViewById(R.id.btnSendData)
        etMessage = findViewById(R.id.etMessage)

        bluetoothAdapter?.enable()


        serverBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {

                if(apnaServerSocket != null){
                    apnaServerSocket!!.close()
                }
                apnaSocket?.close()

                apnaServerSocket =
                    bluetoothAdapter?.listenUsingInsecureRfcommWithServiceRecord(
                        "BT_SERVICE",
                        MY_UUID
                    )
                var shouldLoop = true
                while (shouldLoop) {
                    Log.d("Log", "Entered loop")
                    apnaSocket = try {
                        Log.d("Log", "inside the try")
                        apnaServerSocket?.accept()
                    } catch (e: IOException) {
                        Log.e("Log", "Socket's accept() method failed", e)
                        shouldLoop = false
                        null
                    }
                    apnaSocket?.also {
                        apnaServerSocket?.close()
                        shouldLoop = false
                    }
                    Log.d(TAG, apnaSocket.toString())
                }

                Log.d("Log", "exited loop")
                withContext(Dispatchers.Main){
                    Toast.makeText(
                        this@SampleCommunicationActivity,
                        "Connected to ${apnaSocket.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                inStream =apnaSocket?.inputStream
                outStream=apnaSocket?.outputStream
                while (true) {
                    // Read from the InputStream.
                    var numBytes = try {
                        inStream?.read(buffer)
                    } catch (e: IOException) {
                        Log.d(TAG, "Input stream was disconnected", e)
                        break
                    }
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    Log.d(TAG,"numBytes: $numBytes")
                    Log.d(TAG,"buffer to string is ${buffer?.toString()}")
                    Log.d(TAG,"buffer to string is ${buffer?.decodeToString()}")
                    val readMessage= String(buffer!!, 0, buffer!!.size)
                    Log.d(TAG,"readMessage string is $readMessage")

                }
            }
        }



        clientBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                //code for client
                if(apnaServerSocket != null){
                    apnaServerSocket!!.close()
                }
                apnaSocket?.close()
                bluetoothAdapter?.cancelDiscovery()
                btDevice?.let {
                    apnaSocket = it.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
                    try {
                        apnaSocket?.connect()
                        Log.d("Log", apnaSocket.toString())
                    }
                    catch(e: IOException) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@SampleCommunicationActivity, e.message, Toast.LENGTH_SHORT).show()
                        }
//                        this.cancel("Hag diya + $e")
                    }
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@SampleCommunicationActivity,"Connected to ${apnaSocket?.remoteDevice?.name} \n ${apnaSocket.toString()}",Toast.LENGTH_SHORT).show()
                }
                inStream =apnaSocket?.inputStream
                outStream=apnaSocket?.outputStream

                try{
                    outStream?.write(1)
                    if(outStream==null){
                        Log.d(TAG,"outStream is null")
                    }
                }
                catch (e: IOException) {
                    Log.e(TAG, "Error occurred when sending data", e)
                }
                val reader = BufferedReader(InputStreamReader(inStream))

                while (true) {
                    outStream?.write(1)
                    var currStr=""
                    var numBytes = try {
                        currStr = reader.readLine()
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@SampleCommunicationActivity,currStr,Toast.LENGTH_SHORT).show()
                        }
                        Log.d(TAG,currStr)
                        buffer = ByteArray(1024)                            //we have to clear byteArray
                    }
                    catch (e: IOException) {
                        Log.d(TAG, "Input stream was disconnected", e)
                        break
                    }
                }
            }
        }

        btnSendData.setOnClickListener {
            inStream =apnaSocket?.inputStream
            outStream=apnaSocket?.outputStream

            try{
                outStream?.write(etMessage.text.toString().toByteArray())
                if(outStream==null){
                    Log.d(TAG,"outStream is null")
                }
            }
            catch (e: IOException) {
                Log.e(TAG, "Error occurred when sending data", e)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SELECT_DEVICE && resultCode == RESULT_OK) {
            val name = data?.getStringExtra("devName")
            val address = data!!.getStringExtra("devAddress")
            btDevice= bluetoothAdapter?.getRemoteDevice(address)
            if(btDevice ==null){
                Toast.makeText(this,"btDevice is null",Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(this,name +"\n"+address,Toast.LENGTH_SHORT).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        apnaSocket?.close()
        bluetoothAdapter?.cancelDiscovery()
        bluetoothAdapter?.disable()
    }
}