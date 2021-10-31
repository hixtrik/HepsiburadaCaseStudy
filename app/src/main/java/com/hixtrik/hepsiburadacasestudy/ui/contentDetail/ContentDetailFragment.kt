package com.hixtrik.hepsiburadacasestudy.ui.contentDetail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.hixtrik.hepsiburadacasestudy.R
import com.hixtrik.hepsiburadacasestudy.data.models.ContentDetail
import com.hixtrik.hepsiburadacasestudy.databinding.FragmentContentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.text.DateFormat

@AndroidEntryPoint
class ContentDetailFragment : Fragment() {
    private val binding: FragmentContentDetailBinding by lazy {
        FragmentContentDetailBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: ContentDetailViewModel by viewModels()
    private val args: ContentDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentContentDetailBinding.bind(view)
        viewModel.contentId(args.contentId.toString())
        setSharedElementTransitionOnEnter()
        postponeEnterTransition()
        binding.apply {
            imageArtWork.apply {
                transitionName = args.artworkUrl100
                startEnterTransitionAfterLoadingImage(args.artworkUrl100, this)
            }
            lifecycleScope.launchWhenCreated {
                viewModel.content.collectLatest {
                    initContent(it)
                }
            }
            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun FragmentContentDetailBinding.initContent(
        it: ContentDetail?
    ) {
        // Sometimes api return null on /lookup endpoint
        if (it != null) {
            imageNoResults.visibility = GONE
            textNotFound.visibility = GONE
            textCollectionName.text = it.collectionName ?: it.trackName
            textDescription.text = it.longDescription ?: it.description
            textCollectionPrice.text = it.formattedPrice
                ?: if (it.collectionPrice != null && it.collectionPrice != 0.0) "\$${it.collectionPrice}" else ""
            it.releaseDate?.let { date ->
                textReleaseDate.text =
                    DateFormat.getDateInstance(DateFormat.SHORT).format(date)
            }
        } else {
            imageNoResults.visibility = VISIBLE
            textNotFound.visibility = VISIBLE
        }
    }

    // Image transition animation
    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
    }


    private fun startEnterTransitionAfterLoadingImage(
        imageAddress: String,
        imageView: ImageView
    ) {
        Glide.with(this)
            .load(imageAddress)
            .dontAnimate() // 1
            .listener(object : RequestListener<Drawable> { // 2
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: com.bumptech.glide.request.target.Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(imageView)
    }
}