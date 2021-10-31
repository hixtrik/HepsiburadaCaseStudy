package com.hixtrik.hepsiburadacasestudy.data.mapper

import com.hixtrik.hepsiburadacasestudy.data.models.Content
import com.hixtrik.hepsiburadacasestudy.data.models.ContentResponse

fun ContentResponse.toContent(): Content {
    return Content(
        artistId = artistId,
        artworkUrl100 = artworkUrl100,
        collectionId = collectionId,
        collectionName = collectionName,
        collectionPrice = collectionPrice,
        releaseDate = releaseDate,
        trackId = trackId,
        trackName = trackName,
        formattedPrice = formattedPrice
    )
}