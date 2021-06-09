package com.example.myapplication.view.pages

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.myapplication.data.CountryDto
import com.example.myapplication.view.vewmodel.CountryState
import com.example.myapplication.view.widget.FlagImage
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun ListScreen(controller : NavController,countryLiveData: LiveData<CountryState>,applicationContext : Context){
    Log.d("testing","list screen")
    Scaffold(
        topBar = {
            TopAppBar(content = {
                Text("JetPack Compose Country",
            modifier = Modifier.fillMaxWidth(),textAlign = TextAlign.Center)
            })
        },
        content = { CountryList( countryLiveData,applicationContext,controller) }
    )

}
@Composable
fun CountryList(countryState : LiveData<CountryState>, context : Context,controller: NavController){
    Log.d("testing","observe")
    val state : CountryState by countryState.observeAsState(CountryState.Loading)
    when(state){

        is CountryState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        is CountryState.Success -> {
            LazyColumn() {
                itemsIndexed((state as CountryState.Success).countryList){
                        _, item ->
                    Item(item, controller)
                }
            }
        }
        is CountryState.Failure -> Text((state as CountryState.Failure).error)
    }


}

@Composable()
fun Item(countryDto: CountryDto, controller: NavController){
    Log.d("testing","itemizing")
    val painter = rememberCoilPainter("https://www.countryflags.io/${countryDto.alpha2Code}/shiny/64.png")
    Card(
        modifier = Modifier.padding(4.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(10.dp)


    ) {
        Box( modifier = Modifier.clickable {
            Log.d("testing","$countryDto",)
            controller.currentBackStackEntry?.arguments =
                Bundle().apply {
                    putParcelable("country", countryDto)
                }
            controller.navigate("detail_screen")


        }){
            Row(
                verticalAlignment = Alignment.CenterVertically,

                ){
                FlagImage(painter)
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Name - ${countryDto.name}")
                    Text("Region - ${countryDto.region}")
                    Text("Population - ${countryDto.population}")
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }


}




@Composable
fun Divider(){
    Divider(modifier = Modifier.padding(10.dp))
}