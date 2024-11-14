package com.adyen.android.assignment.astronomy

sealed class ScreenDestination(val route: String) {

    data object AstronomyPhotosDestination : ScreenDestination("items")
}
