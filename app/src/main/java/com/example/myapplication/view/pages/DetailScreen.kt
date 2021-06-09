package com.example.myapplication.view.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.data.CountryDto
import com.example.myapplication.view.widget.FlagImage
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun DetailScreen(controller: NavController) {
    val countryDto =
        controller.previousBackStackEntry?.arguments?.getParcelable<CountryDto>("country")
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("JetPack Compose Country") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            controller.popBackStack()
                        },
                    ) {
                        Icon(Icons.Filled.ArrowBack, "back")
                    }
                })
        },
        content = {
            Card(
                modifier = Modifier.padding(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        "Name - ${countryDto?.name}", fontSize = 25.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                    Text(
                        "Region - ${countryDto?.region}", fontSize = 25.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                    Text(
                        "Population - ${countryDto?.population}", fontSize = 25.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                    FlagImage(painter = rememberCoilPainter("https://www.countryflags.io/${countryDto?.alpha2Code}/shiny/64.png"))
                }
            }

        }
    )
}