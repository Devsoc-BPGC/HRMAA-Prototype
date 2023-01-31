package com.devsoc.hrmaa.fitbit.dataclasses

data class EcgData(
    val ecgReadings: List<EcgReading>,
    val pagination : Pagination
)
