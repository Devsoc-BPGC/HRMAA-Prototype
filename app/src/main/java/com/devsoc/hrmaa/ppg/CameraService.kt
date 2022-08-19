package com.devsoc.hrmaa.ppg

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.*
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log
import android.view.Surface
import androidx.core.app.ActivityCompat
import java.util.*


internal class CameraService(private val activity: Activity, private val handler: Handler) {
    private var cameraId: String? = null
    private var cameraDevice: CameraDevice? = null
    private var previewSession: CameraCaptureSession? = null
    private var previewCaptureRequestBuilder: CaptureRequest.Builder? = null
    fun start(previewSurface: Surface) {
        val cameraManager = activity.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraId = Objects.requireNonNull(cameraManager).cameraIdList[0]
        } catch (e: CameraAccessException) {
            Log.e("camera", "No access to camera", e)
            handler.sendMessage(
                Message.obtain(
                    handler,
                    PPGActivity.MESSAGE_CAMERA_NOT_AVAILABLE,
                    "No access to camera...."
                )
            )
        } catch (e: NullPointerException) {
            Log.e("camera", "No access to camera", e)
            handler.sendMessage(
                Message.obtain(
                    handler,
                    PPGActivity.MESSAGE_CAMERA_NOT_AVAILABLE,
                    "No access to camera...."
                )
            )
        } catch (e: ArrayIndexOutOfBoundsException) {
            Log.e("camera", "No access to camera", e)
            handler.sendMessage(
                Message.obtain(
                    handler,
                    PPGActivity.MESSAGE_CAMERA_NOT_AVAILABLE,
                    "No access to camera...."
                )
            )
        }
        try {
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.println(Log.ERROR, "camera", "No permission to take photos")
                handler.sendMessage(
                    Message.obtain(
                        handler,
                        PPGActivity.MESSAGE_CAMERA_NOT_AVAILABLE,
                        "No permission to take photos"
                    )
                )
                return
            }

            // message has been sent to MainActivity, this method can return.
            if (cameraId == null) {
                return
            }
            Objects.requireNonNull(cameraManager)
                .openCamera(cameraId!!, object : CameraDevice.StateCallback() {
                    override fun onOpened(camera: CameraDevice) {
                        cameraDevice = camera
                        val stateCallback: CameraCaptureSession.StateCallback =
                            object : CameraCaptureSession.StateCallback() {
                                override fun onConfigured(session: CameraCaptureSession) {
                                    previewSession = session
                                    try {
                                        previewCaptureRequestBuilder =
                                            cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                                        previewCaptureRequestBuilder!!.addTarget(previewSurface) // this is previewSurface
                                        previewCaptureRequestBuilder!!.set(
                                            CaptureRequest.FLASH_MODE,
                                            CaptureRequest.FLASH_MODE_TORCH
                                        )
                                        val thread = HandlerThread("CameraPreview")
                                        thread.start()
                                        previewSession!!.setRepeatingRequest(
                                            previewCaptureRequestBuilder!!.build(),
                                            null,
                                            null
                                        )
                                    } catch (e: CameraAccessException) {
                                        if (e.message != null) {
                                            Log.println(
                                                Log.ERROR, "camera",
                                                e.message!!
                                            )
                                        }
                                    }
                                }

                                override fun onConfigureFailed(session: CameraCaptureSession) {
                                    Log.println(Log.ERROR, "camera", "Session configuration failed")
                                }
                            }
                        try {
                            // deprecated in API 30, but changing it would bump minSdkVersion to 28.
                            camera.createCaptureSession(
                                listOf(previewSurface),
                                stateCallback,
                                null
                            ) //1
                        } catch (e: CameraAccessException) {
                            if (e.message != null) {
                                Log.println(Log.ERROR, "camera", e.message!!)
                            }
                        }
                    }

                    override fun onDisconnected(camera: CameraDevice) {}
                    override fun onError(camera: CameraDevice, error: Int) {}
                }, null)
        } catch (e: CameraAccessException) {
            if (e.message != null) {
                Log.println(Log.ERROR, "camera", e.message!!)
                handler.sendMessage(
                    Message.obtain(
                        handler,
                        PPGActivity.MESSAGE_CAMERA_NOT_AVAILABLE,
                        e.message
                    )
                )
            }
        } catch (e: SecurityException) {
            if (e.message != null) {
                Log.println(Log.ERROR, "camera", e.message!!)
                handler.sendMessage(
                    Message.obtain(
                        handler,
                        PPGActivity.MESSAGE_CAMERA_NOT_AVAILABLE,
                        e.message
                    )
                )
            }
        }
    }

    fun stop() {
        try {
            cameraDevice!!.close()
        } catch (e: Exception) {
            Log.println(Log.ERROR, "camera", "cannot close camera device" + e.message)
        }
    }
}
