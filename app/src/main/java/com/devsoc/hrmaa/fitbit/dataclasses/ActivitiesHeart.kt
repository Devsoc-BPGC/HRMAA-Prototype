package com.devsoc.hrmaa.fitbit.dataclasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActivitiesHeart(
    val dateTime: String?,
    val value: Value
): Parcelable