package com.hixtrik.hepsiburadacasestudy.data.api

import com.hixtrik.hepsiburadacasestudy.data.models.ContentDetailResponse
import com.hixtrik.hepsiburadacasestudy.data.models.ContentResponse
import retrofit2.http.GET
import retrofit2.http.Query

//┌──────────────────────────┐
//│ Created by Taha ARICAN   │
//│ aricantaha06@gmail.com   │            
//│ 25.10.2021               │
//└──────────────────────────┘

interface ApiService {

    companion object {
        const val BASE_URL = "https://itunes.apple.com/"
    }

    @GET("/search")
    suspend fun getContentWithFilter(
        @Query("term") term: String,
        @Query("media") media: String,
        @Query("limit") limit: String?,
        @Query("offset") offset: String?,
    ): ListingResponse

    @GET("/lookup")
    suspend fun getContentDetail(
        @Query("id") id: String,
        ): DetailResponse

    class ListingResponse(
        val results: List<ContentResponse>
    )

    class DetailResponse(
        val results: List<ContentDetailResponse>
    )
}