package com.devsoc.hrmaa.fitbit.dataclasses

import com.google.gson.annotations.SerializedName

data class AuthInfo(
    @SerializedName("clientId") val clientId: String,
    @SerializedName("grant_type") val grant_type: String,
    @SerializedName("redirect_uri") val redirect_uri: String,
    @SerializedName("code") val code: String
)
