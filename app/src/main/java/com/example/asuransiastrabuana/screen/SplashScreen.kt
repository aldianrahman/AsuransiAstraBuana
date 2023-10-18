package com.example.asuransiastrabuana.screen

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.asuransiastrabuana.R
import com.example.asuransiastrabuana.util.ScreenRoute

@Composable
fun SplashScreen(navController: NavHostController, context: Context) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Card(
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
            ) {
                Text("Pokedex",
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 15.dp)
                    )
                Image(
                    painterResource(R.drawable.pokeball),
                    contentDescription = null,
                    modifier = Modifier
                        .background(Color.White)
                        .size(200.dp)
                        .padding(bottom = 15.dp)
                )
                Button(
                    onClick = {
                        navController.navigate(ScreenRoute.HomePage.route)
                    },
                    Modifier.padding(16.dp),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    border = BorderStroke(1.dp, Color.Black)
                ){
                    Text("Start",color = Color.Black)
                }
            }
        }
    }
}