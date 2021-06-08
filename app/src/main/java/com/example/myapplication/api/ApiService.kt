package com.example.myapplication.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    var retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(CountryApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun getCountry() : CountryApi = retrofit.create(CountryApi::class.java)

}