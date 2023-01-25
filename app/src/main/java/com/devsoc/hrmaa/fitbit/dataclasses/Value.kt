package com.devsoc.hrmaa.fitbit.dataclasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Value(
    val customHeartRateZones: List<CustomHeartRateZone>,
    val heartRateZones: List<HeartRateZone>,
    val restingHeartRate: Int
): Parcelable