package com.zakshaker.dadjokes
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHostController

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        NavHostController(this)
    }
}