package com.technologies.theone.api

import com.technologies.theone.utils.Constance
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit{
        return Retrofit.Builder().baseUrl(Constance.api).addConverterFactory(GsonConverterFactory.create()).build()
    }
}