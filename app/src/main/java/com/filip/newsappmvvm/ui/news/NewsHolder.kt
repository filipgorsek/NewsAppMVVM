package com.filip.newsappmvvm.ui.news

import androidx.recyclerview.widget.RecyclerView
import com.filip.newsappmvvm.R
import com.filip.newsappmvvm.data.model.ArticleModel
import com.filip.newsappmvvm.databinding.ItemNewsBinding
import com.filip.newsappmvvm.extensions.loadDrawable
import com.filip.newsappmvvm.extensions.loadImage
import com.filip.newsappmvvm.extensions.onClick

class NewsHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(article: ArticleModel, clickListener: (ArticleModel) -> Unit) {
        binding.run {
            with(article) {
                root.onClick { clickListener(article) }
                if (urlToImage.isNotEmpty()) {
                    newsImage.loadImage(urlToImage)
                } else {
                    newsImage.loadDrawable(R.drawable.ic_launcher_background)
                }
                newsTitle.text = title
            }
        }
    }
}

