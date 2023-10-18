package com.example.asuransiastrabuana.util

sealed class ScreenRoute(
    val route:String
) {
    object SplashScreen : ScreenRoute("splashScreen")
    object HomePage : ScreenRoute("homePage")
    object DetailItem : ScreenRoute("detailItem")
}