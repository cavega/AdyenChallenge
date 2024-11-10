package com.adyen.android.assignment.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.adyen.android.assignment.BuildConfig

class MainActivity : ComponentActivity(){
    val test = BuildConfig.API_KEY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(MainActivity::class.simpleName, "API key is $test")
    }
}