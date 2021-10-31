package com.hixtrik.hepsiburadacasestudy.ui.contentDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.hixtrik.hepsiburadacasestudy.data.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class ContentDetailViewModel @Inject constructor(
    private val repository: ContentRepository,
    private val savedStateHandle: SavedStateHandle,

    ) : ViewModel() {

    companion object {
        const val CONTENT_ID = ""
    }

    init {
        if (!savedStateHandle.contains(CONTENT_ID)) {
            savedStateHandle.set(CONTENT_ID, "")
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val content = savedStateHandle.getLiveData<String>(CONTENT_ID)
        .asFlow()
        .flatMapLatest {
            repository.contentDetail(it)
        }

    fun contentId(contentId: String) {
        if (!shouldChangeContentId(contentId)) return
        savedStateHandle.set(CONTENT_ID, contentId)
    }

    private fun shouldChangeContentId(contentId: String): Boolean {
        return savedStateHandle.get<String>(CONTENT_ID) != contentId
    }
}