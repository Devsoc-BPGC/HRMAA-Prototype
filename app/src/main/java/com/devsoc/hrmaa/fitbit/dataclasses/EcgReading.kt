package com.devsoc.hrmaa.fitbit.dataclasses

data class EcgReading(
    val averageHeartRate: Int,
    val deviceName: String,
    val featureVersion: String,
    val firmwareVersion: String,
    val leadNumber: Int,
    val numberOfWaveformSample: Int,
    val resultClassification: String,
    val samplingFrequencyHz: String,
    val scalingFactor: Int,
    val startTime: String,
    val waveformSamples: List<Int>
)
