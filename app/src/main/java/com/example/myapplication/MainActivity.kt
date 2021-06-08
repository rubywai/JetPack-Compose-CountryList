package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.api.ApiService
import com.example.myapplication.api.CountryApi
import com.example.myapplication.data.CountryDto
import com.example.myapplication.state.CountryState
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import java.lang.Exception


class MainActivity : ComponentActivity() {
    private val countryApi = ApiService.getCountry()
    private var countryState = MutableLiveData<CountryState>(CountryState.Loading)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                  Scaffold(
                      topBar = {TopAppBar(content = {Text("JetPack Compose Country",
                      modifier = Modifier.fillMaxWidth(),textAlign = TextAlign.Center)})},
                      content = {CountryList( countryState)}
                  )
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            try {
                val countryList = countryApi.getCountries()
                countryState.value = CountryState.Success(countryList)
            }
            catch (e : Exception){
                countryState.value = CountryState.Failure("Error")
            }
        }

    }
}
@Composable
fun CountryList(countryState : LiveData<CountryState>){
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
                    Item(item)
                }
            }
        }
        is CountryState.Failure -> Text((state as CountryState.Failure).error)
    }


}

@Composable()
fun Item(countryDto : CountryDto){
    var painter = rememberCoilPainter("https://www.countryflags.io/${countryDto.alpha2Code}/shiny/64.png")
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Name - ${countryDto.name}")
            Text("Region - ${countryDto.region}")
            Text("Population - ${countryDto.population}")
            Box{
                Image(
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp),
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
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}




@Composable
fun Divider(){
    Divider(modifier = Modifier.padding(10.dp))
}