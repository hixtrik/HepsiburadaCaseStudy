package com.hixtrik.hepsiburadacasestudy.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContentFilter(
    var term: String?,
    var media: String?,
) : Parcelable