package com.example.myapplication.view.vewmodel

import com.example.myapplication.data.CountryDto

sealed class CountryState{
    object Loading : CountryState()
    data class Success(val countryList : List<CountryDto>) : CountryState()
    class Failure(var error : String) : CountryState()
}


