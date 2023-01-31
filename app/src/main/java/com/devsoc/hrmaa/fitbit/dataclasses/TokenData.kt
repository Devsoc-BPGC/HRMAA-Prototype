package com.devsoc.hrmaa.fitbit.dataclasses

import com.google.gson.annotations.SerializedName

data class TokenData(
    @SerializedName("access_token") val access_token: String,
    @SerializedName("expires_in") val expires_in: Int,
    @SerializedName("refresh_token") val refresh_token: String,
    @SerializedName("scope") val scope: String,
    @SerializedName("token_type") val token_type: String,
    @SerializedName("user_id") val user_id: String
)
