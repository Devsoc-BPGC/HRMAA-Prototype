package com.devsoc.hrmaa.fitbit.dataclasses

data class Value(
    val customHeartRateZones: List<CustomHeartRateZone>,
    val heartRateZones: List<HeartRateZone>,
    val restingHeartRate: Int
)