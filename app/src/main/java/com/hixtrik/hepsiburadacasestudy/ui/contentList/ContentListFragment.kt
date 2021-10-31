package com.hixtrik.hepsiburadacasestudy.ui.contentList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.recyclerview.widget.GridLayoutManager
import com.hixtrik.hepsiburadacasestudy.R
import com.hixtrik.hepsiburadacasestudy.databinding.FragmentContentListBinding
import com.hixtrik.hepsiburadacasestudy.ui.contentList.adapter.ContentListGridAdapter
import com.hixtrik.hepsiburadacasestudy.utils.networkState.NetworkStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter


@AndroidEntryPoint
class ContentListFragment : Fragment() {
    private val binding: FragmentContentListBinding by lazy {
        FragmentContentListBinding.inflate(
            layoutInflater
        )
    }
    private val model: ContentListViewModel by viewModels()
    private lateinit var adapter: ContentListGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
        initSwipeToRefresh()
        initSearch()
        initFilter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun initFilter() {
        binding.toggleButtons.addOnButtonCheckedListener { _, _, _ ->
            when (binding.toggleButtons.checkedButtonId) {
                R.id.buttonMovies -> {
                    model.mediaType("movie")
                }
                R.id.buttonMusic -> {
                    model.mediaType("music")
                }
                R.id.buttonApps -> {
                    model.mediaType("software")
                }
                R.id.buttonBooks -> {
                    model.mediaType("ebook")
                }
                else -> {
                    model.mediaType("all")
                }
            }
        }
    }

    private fun initAdapter() {
        adapter = ContentListGridAdapter()
        val recyclerView = binding.recyclerViewContentList
        val headerAdapter = NetworkStateAdapter(adapter)
        val footerAdapter = NetworkStateAdapter(adapter)
        val concatAdapter = adapter.withLoadStateHeaderAndFooter(
            header = headerAdapter,
            footer = footerAdapter
        )

        recyclerView.adapter = concatAdapter
        val layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = layoutManager
        // Centered network state item
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0 && headerAdapter.itemCount > 0) {
                    // if it is the first position and we have a header,
                    2
                } else if (position == concatAdapter.itemCount - 1 && footerAdapter.itemCount > 0) {
                    // if it is the last position and we have a footer
                    2
                } else {
                    1
                }
            }
        }
        recyclerView.run {
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collect { loadStates ->
                binding.swipeRefreshLayout.isRefreshing =
                    loadStates.mediator?.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            model.contents.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                // Only emit when REFRESH changes
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                // Scroll to top is synchronous with UI updates, even if remote load was triggered.
                .collect {
                    recyclerView.scrollToPosition(0)
                    binding.imageNoResults.isVisible = noResult(it.source)
                    binding.textNoResults.isVisible = noResult(it.source)
                }
        }
    }

    private fun noResult(it: LoadStates) = it.refresh is LoadState.NotLoading
            && it.append.endOfPaginationReached
            && adapter.itemCount < 1

    private fun initSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun initSearch() {
        binding.editTextSearch.addTextChangedListener {
            if (it != null) {
                if (it.length > 2) {
                    updateContentFromInput()
                }
            }
        }
    }

    private fun updateContentFromInput() {
        binding.editTextSearch.text.trim().toString().let {
            if (it.isNotBlank()) {
                model.searchTerm(it)
            }
        }
    }
}