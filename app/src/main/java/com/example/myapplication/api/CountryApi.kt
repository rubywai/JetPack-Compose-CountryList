package com.example.myapplication.api

import com.example.myapplication.data.CountryDto
import retrofit2.http.GET

interface CountryApi {
    companion object{
        const val BASE_URL = "https://restcountries.eu/rest/v2/"
        const val ALL = "all"
    }
    @GET(ALL)
    suspend fun getCountries() : List<CountryDto>

}