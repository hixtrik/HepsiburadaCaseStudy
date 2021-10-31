package com.hixtrik.hepsiburadacasestudy.data.mapper

import com.hixtrik.hepsiburadacasestudy.data.models.ContentDetail
import com.hixtrik.hepsiburadacasestudy.data.models.ContentDetailResponse

fun ContentDetailResponse.toContentDetail(): ContentDetail {
    return ContentDetail(
        collectionPrice = collectionPrice,
        longDescription = longDescription,
        releaseDate = releaseDate,
        trackName = trackName,
        description = description,
        collectionName = collectionName,
        formattedPrice = formattedPrice,
    )
}