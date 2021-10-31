package com.hixtrik.hepsiburadacasestudy.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hixtrik.hepsiburadacasestudy.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}