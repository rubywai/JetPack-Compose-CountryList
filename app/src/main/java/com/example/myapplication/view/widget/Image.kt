package com.example.myapplication.view.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.accompanist.imageloading.ImageLoadState
import com.google.accompanist.imageloading.LoadPainter

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