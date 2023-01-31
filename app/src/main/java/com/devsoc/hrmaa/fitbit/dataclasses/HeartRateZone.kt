package com.devsoc.hrmaa.fitbit.dataclasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HeartRateZone(
    val caloriesOut: Double,
    val max: Int,
    val min: Int,
    val minutes: Int,
    val name: String
): Parcelable