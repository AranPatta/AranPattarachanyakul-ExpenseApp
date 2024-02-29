package com.example.a4p3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ExpenseRepository.initialize(this)
        setContentView(R.layout.activity_main)
        Log.d("Debug", "In MainActivity")
    }
}