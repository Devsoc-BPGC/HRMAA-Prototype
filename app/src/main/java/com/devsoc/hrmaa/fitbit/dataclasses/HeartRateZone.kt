package com.devsoc.hrmaa.fitbit.dataclasses

data class HeartRateZone(
    val caloriesOut: Double,
    val max: Int,
    val min: Int,
    val minutes: Int,
    val name: String
)