package com.hixtrik.hepsiburadacasestudy.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

//┌──────────────────────────┐
//│ Created by Taha ARICAN   │
//│ aricantaha06@gmail.com   │            
//│ 25.10.2021               │
//└──────────────────────────┘
fun ImageView.load(imgUrl: String) {
    Glide.with(context)
        .load(imgUrl)
        .into(this)
}