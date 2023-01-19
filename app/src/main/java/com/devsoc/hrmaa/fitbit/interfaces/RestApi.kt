package com.devsoc.hrmaa.fitbit.interfaces

import com.devsoc.hrmaa.fitbit.dataclasses.EcgData
import com.devsoc.hrmaa.fitbit.dataclasses.HeartRateSeries
import com.devsoc.hrmaa.fitbit.dataclasses.TokenData
import retrofit2.Call
import retrofit2.http.*

interface RestApi {

    @Headers("Authorization: Basic MjM4UUNZOmVjNWUyZGUwYTg1YmEzNjdlYWM1NzFhNTg3MWU4MGE5")
    @POST("oauth2/token")
    @FormUrlEncoded
    fun getTokenInfo(
        @Field("clientId") clientId: String,
        @Field("grant_type") grant_type: String,
        @Field("redirect_uri") redirect_uri: String,
        @Field("code") code: String
    ): Call<TokenData>

    @Headers("Authorization: Basic MjM4UUNZOmVjNWUyZGUwYTg1YmEzNjdlYWM1NzFhNTg3MWU4MGE5")
    @POST("oauth2/token")
    @FormUrlEncoded
    fun refresh(
        @Field("clientId") clientId: String,
        @Field("grant_type") grant_type: String,
        @Field("redirect_uri") redirect_uri: String,
        @Field("refresh_token") refresh_token: String
    ): Call<TokenData>

    @Headers("accept: application/json")
    @GET("1/user/-/ecg/list.json?afterDate=2022-09-28&sort=asc&limit=1&offset=0")
    fun getEcgData(@HeaderMap headers: Map<String, String>): Call<EcgData>

    @Headers("accept: application/json")
    @GET("1/user/-/activities/heart/date/{startDate}/{endDate}.json")
    fun getHeartRateSeries(@HeaderMap headers: Map<String, String>,
        @Path(value = "startDate", encoded = true) startDate: String,
        @Path(value = "endDate", encoded = true) endDate: String
    ): Call<HeartRateSeries>

    @Headers("accept: application/json")
    @GET("{path}")
    fun nextPage(@HeaderMap headers: Map<String, String>,
                           @Path(value = "path", encoded = true) path: String
    ): Call<EcgData>

}