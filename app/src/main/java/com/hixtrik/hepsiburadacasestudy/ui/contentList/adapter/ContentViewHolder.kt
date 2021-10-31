package com.hixtrik.hepsiburadacasestudy.ui.contentList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.hixtrik.hepsiburadacasestudy.data.models.Content
import com.hixtrik.hepsiburadacasestudy.databinding.ContentListItemBinding
import com.hixtrik.hepsiburadacasestudy.ui.contentList.ContentListFragmentDirections
import com.hixtrik.hepsiburadacasestudy.utils.load
import java.text.DateFormat

class ContentViewHolder(
    private val binding: ContentListItemBinding,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: Content?,
    ) {
        binding.apply {
            item?.artworkUrl100?.let {
                imageViewArtWork.load(it)
                binding.imageViewArtWork.transitionName = item.artworkUrl100
            }

            textCollectionName.text = item?.collectionName ?: item?.trackName
            textCollectionPrice.text =
                getPrice(item)
            item?.releaseDate?.let {
                textReleaseDate.text = DateFormat.getDateInstance(DateFormat.SHORT).format(it)
            }

            binding.root.rootView.setOnClickListener {
                toDetail(item)
            }
        }
    }

    // Api return different named fields sometimes for name, price, id
    private fun getPrice(item: Content?) =
        (item?.formattedPrice
            ?: if (item?.collectionPrice != null && item.collectionPrice != 0.0)
                "\$${item.collectionPrice}" else "")

    private fun toDetail(item: Content?) {
        // Extras for transition animation
        val extraInfoForSharedElement = FragmentNavigatorExtras(
            binding.imageViewArtWork to item?.artworkUrl100!!
        )
        val action =
            ContentListFragmentDirections.actionMainFragmentToContentDetailFragment(
                // Api return different named fields sometimes for name, price, id
                item.collectionId ?: item.trackId ?: item.artistId ?: 0,
                item.artworkUrl100
            )

        binding.root.findNavController()
            .navigate(action, extraInfoForSharedElement)
    }

    companion object {
        fun create(parent: ViewGroup): ContentViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return ContentViewHolder(ContentListItemBinding.inflate(inflater, parent, false))
        }
    }
}