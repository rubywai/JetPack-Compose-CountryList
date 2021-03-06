package com.example.myapplication.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class CountryDto(
    val name : String?,
    val region : String?,
    val population : Int?,
    val alpha2Code : String?
) : Parcelable
