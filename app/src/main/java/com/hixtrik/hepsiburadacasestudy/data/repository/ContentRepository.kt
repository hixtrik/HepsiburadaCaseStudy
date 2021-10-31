package com.hixtrik.hepsiburadacasestudy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.hixtrik.hepsiburadacasestudy.data.api.ApiService
import com.hixtrik.hepsiburadacasestudy.data.mapper.toContentDetail
import com.hixtrik.hepsiburadacasestudy.data.models.ContentDetail
import com.hixtrik.hepsiburadacasestudy.data.models.ContentFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

//┌──────────────────────────┐
//│ Created by Taha ARICAN   │
//│ aricantaha06@gmail.com   │            
//│ 29.10.2021               │
//└──────────────────────────┘
class ContentRepository @Inject constructor(private val apiService: ApiService) {

    fun contentListWithFilter(contentFilter: ContentFilter) = Pager(
        PagingConfig(pageSize = 20, prefetchDistance = 2)
    ) {
        ContentPagingSource(
            apiService = apiService,
            searchTerm = contentFilter.term ?: "",
            mediaType = contentFilter.media ?: ""
        )
    }.flow

    suspend fun contentDetail(contentId: String): Flow<ContentDetail?> {
        return flow {
            try {
                val contentDetail = apiService.getContentDetail(contentId).results.firstOrNull()
                emit(contentDetail?.toContentDetail())
            } catch (e: Exception) {
            }
        }.flowOn(Dispatchers.IO)
    }
}