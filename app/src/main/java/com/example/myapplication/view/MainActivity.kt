package com.example.myapplication.view

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData

import com.example.myapplication.data.CountryDto
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.google.accompanist.imageloading.LoadPainter


class MainActivity : ComponentActivity() {
    private val countryStateViewModel  by viewModels<CountryViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                  Scaffold(
                      topBar = {TopAppBar(content = {Text("JetPack Compose Country",
                      modifier = Modifier.fillMaxWidth(),textAlign = TextAlign.Center)})},
                      content = {CountryList( countryStateViewModel.countryLiveData,applicationContext)}
                  )
                }
            }
        }

    }
}
@Composable
fun CountryList(countryState : LiveData<CountryState>,context : Context){
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
                    Item(item,context)
                }
            }
        }
        is CountryState.Failure -> Text((state as CountryState.Failure).error)
    }


}

@Composable()
fun Item(countryDto : CountryDto,context : Context){
    val painter = rememberCoilPainter("https://www.countryflags.io/${countryDto.alpha2Code}/shiny/64.png")
    Card(
        modifier = Modifier.padding(4.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(10.dp)


    ) {
        Box( modifier = Modifier.clickable {
            Toast.makeText(context,countryDto.name,Toast.LENGTH_SHORT).show()
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
fun FlagImage(painter : LoadPainter<Any>){
    Box{
        Image(
            modifier = Modifier
                .width(100.dp)
                .height(100.dp),
            contentScale = ContentScale.Fit,
            painter = painter,
            contentDescription = "images",
        )
        when(painter.loadState){
            is ImageLoadState.Loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            is ImageLoadState.Error -> {
                Text("Image Fail")
            }
        }
    }
}



@Composable
fun Divider(){
    Divider(modifier = Modifier.padding(10.dp))
}