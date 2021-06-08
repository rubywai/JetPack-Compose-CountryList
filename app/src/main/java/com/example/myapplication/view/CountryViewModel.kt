package com.example.myapplication.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.ApiService
import kotlinx.coroutines.launch

class CountryViewModel : ViewModel() {
    private val countryApi = ApiService.getCountry()
    private val _countryLiveData : MutableLiveData<CountryState> = MutableLiveData(CountryState.Loading)
    val countryLiveData : LiveData<CountryState> get() = _countryLiveData
    init {
        _countryLiveData.value = CountryState.Loading
        viewModelScope.launch {
            try {
                val countries = countryApi.getCountries()
                _countryLiveData.value = CountryState.Success(countries)
            }
            catch (e : Exception){
                _countryLiveData.value = CountryState.Failure("Error While Loading")
            }
        }

    }
}