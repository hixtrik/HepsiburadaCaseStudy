package com.hixtrik.hepsiburadacasestudy.ui.contentList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hixtrik.hepsiburadacasestudy.data.models.ContentFilter
import com.hixtrik.hepsiburadacasestudy.data.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class ContentListViewModel @Inject constructor(
    private val repository: ContentRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private lateinit var contentFilter: ContentFilter

    companion object {
        const val CONTENT_FILTER = "CONTENT_FILTER"
    }

    init {
        if (!savedStateHandle.contains(CONTENT_FILTER)) {
            //I don't want empty list on start, so that's initial search
            contentFilter = ContentFilter("a", "all")
            savedStateHandle.set(CONTENT_FILTER, contentFilter)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val contents = savedStateHandle.getLiveData<ContentFilter>(CONTENT_FILTER)
        .asFlow()
        .flatMapLatest {
            repository.contentListWithFilter(it)
        }
        .cachedIn(viewModelScope)

    fun searchTerm(searchTerm: String) {
        if (!shouldSearch(searchTerm)) return
        contentFilter.term = searchTerm
        savedStateHandle.set(CONTENT_FILTER, contentFilter)
    }

    fun mediaType(mediaType: String) {
        if (!shouldFilter(mediaType)) return
        contentFilter.media = mediaType
        savedStateHandle.set(CONTENT_FILTER, contentFilter)
    }

    private fun shouldSearch(searchTerm: String): Boolean {
        return savedStateHandle.get<ContentFilter>(CONTENT_FILTER)?.term != searchTerm
    }

    private fun shouldFilter(mediaType: String): Boolean {
        return savedStateHandle.get<ContentFilter>(CONTENT_FILTER)?.media != mediaType
    }
}