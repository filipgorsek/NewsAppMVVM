package com.filip.newsappmvvm.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.filip.newsappmvvm.data.model.ArticleModel
import com.filip.newsappmvvm.databinding.FragmentNewsDetailsBinding
import com.filip.newsappmvvm.extensions.loadImage
import com.filip.newsappmvvm.extensions.subscribe
import com.filip.newsappmvvm.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel

class DetailsNewsFragment : BaseFragment<FragmentNewsDetailsBinding>() {

    private val sharedNewsViewModel by sharedViewModel<NewsViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNewsDetailsBinding
            get() = FragmentNewsDetailsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeData()
    }

    private fun subscribeData() {
        sharedNewsViewModel.detailsArticle.subscribe(this, ::showData)
    }

    private fun showData(articleModel: ArticleModel) {
        binding.run {
            with(articleModel){
                detailsNewsImage.loadImage(urlToImage)
                detailsDescription.text = description
                detailsTitle.text = title
            }
        }
    }
}