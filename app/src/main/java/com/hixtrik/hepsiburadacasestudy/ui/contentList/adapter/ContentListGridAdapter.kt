package com.hixtrik.hepsiburadacasestudy.ui.contentList.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.hixtrik.hepsiburadacasestudy.data.models.Content

//┌──────────────────────────┐
//│ Created by Taha ARICAN   │
//│ aricantaha06@gmail.com   │            
//│ 25.10.2021               │
//└──────────────────────────┘
class ContentListGridAdapter
    : PagingDataAdapter<Content, ContentViewHolder>(COMPARATOR) {

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: ContentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        onBindViewHolder(holder, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder.create(parent)
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Content>() {
            override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean =
                oldItem.collectionId == newItem.collectionId
        }
    }
}