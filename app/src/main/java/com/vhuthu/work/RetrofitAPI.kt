package com.vhuthu.work

import com.vhuthu.work.models.FarmerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface RetrofitAPI {

    @GET("farmers/portfolio")
    fun getLanguages(@Header("Authorization") token: String): Call<FarmerResponse>
}

//@GET("login/cellphone")
//fun login(
//    @Query("phone") phone: String?,
//    @Query("password") password: String?
//): Call<LoginResponse?>?