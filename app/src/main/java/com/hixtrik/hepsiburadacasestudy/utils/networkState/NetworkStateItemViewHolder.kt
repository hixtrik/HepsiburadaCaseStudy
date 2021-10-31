package com.hixtrik.hepsiburadacasestudy.utils.networkState

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import androidx.recyclerview.widget.RecyclerView
import com.hixtrik.hepsiburadacasestudy.R
import com.hixtrik.hepsiburadacasestudy.databinding.NetworkStateItemBinding

/**
 * A View Holder that can display a loading or have click action.
 * It is used to show the network state of paging.
 */
class NetworkStateItemViewHolder(
    parent: ViewGroup,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.network_state_item, parent, false)
) {
    private val binding = NetworkStateItemBinding.bind(itemView)

    fun bindTo(loadState: LoadState) {
        binding.progressBar.isVisible = loadState is Loading
        binding.buttonRetry.isVisible = loadState is Error
        binding.buttonRetry.setOnClickListener {
            retryCallback()
        }
        binding.textErrorMessage.isVisible = !(loadState as? Error)?.error?.message.isNullOrBlank()
        binding.textErrorMessage.text = (loadState as? Error)?.error?.message
    }
}