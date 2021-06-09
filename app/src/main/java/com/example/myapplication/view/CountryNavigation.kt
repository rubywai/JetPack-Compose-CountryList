package com.example.myapplication.view

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.view.pages.DetailScreen
import com.example.myapplication.view.pages.ListScreen
import com.example.myapplication.view.vewmodel.CountryState

@Composable
fun CountryNavigation(context : Context,countryState : LiveData<CountryState>){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "list_screen"
    ){
        composable("list_screen"){
            ListScreen(controller = navController,countryLiveData = countryState, context)
        }
        composable("detail_screen"
      ){
            DetailScreen(controller = navController)
        }
    }

}