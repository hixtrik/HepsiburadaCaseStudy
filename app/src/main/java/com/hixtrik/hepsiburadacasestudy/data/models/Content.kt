package com.hixtrik.hepsiburadacasestudy.data.models

import java.util.*

data class Content(
    val artistId: Int?,
    val artworkUrl100: String?,
    val collectionId: Int?,
    val collectionName: String?,
    val collectionPrice: Double?,
    val releaseDate: Date?,
    val trackId: Int?,
    val trackName: String?,
    val formattedPrice: String?,
)