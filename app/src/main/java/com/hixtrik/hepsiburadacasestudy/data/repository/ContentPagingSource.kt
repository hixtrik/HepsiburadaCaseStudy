package com.hixtrik.hepsiburadacasestudy.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hixtrik.hepsiburadacasestudy.data.api.ApiService
import com.hixtrik.hepsiburadacasestudy.data.mapper.toContent
import com.hixtrik.hepsiburadacasestudy.data.models.Content
import retrofit2.HttpException
import java.io.IOException

//┌──────────────────────────┐
//│ Created by Taha ARICAN   │
//│ aricantaha06@gmail.com   │
//│ 29.10.2021               │
//└──────────────────────────┘
class ContentPagingSource(
    private val apiService: ApiService,
    private val searchTerm: String, // Search Text
    private val mediaType: String // wrapperType
) : PagingSource<String, Content>() {
    private val limit = 20 // Item count

    override fun getRefreshKey(state: PagingState<String, Content>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Content> {

        return try {
            val items = apiService.getContentWithFilter(
                term = searchTerm,
                media = mediaType,
                limit = limit.toString(),
                offset = if (params.key != null) (params.key!!.toInt() + limit).toString()
                else (0).toString() // Load first page
            ).results.map {
                it.toContent()
            }

            LoadResult.Page(
                data = items,
                prevKey = null, // Only paging forward
                nextKey = if (items.count() == limit) { // Reach end of response
                    if (params.key != null) (params.key!!.toInt() + limit).toString()
                    else (0).toString() // Load first page
                } else null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}