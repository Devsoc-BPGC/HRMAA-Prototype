package com.devsoc.hrmaa.fitbit.dataclasses

data class CustomHeartRateZone(
    val caloriesOut: Double,
    val max: Int,
    val min: Int,
    val minutes: Int,
    val name: String
)