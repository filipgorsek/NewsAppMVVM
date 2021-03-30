package com.filip.newsappmvvm.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.filip.newsappmvvm.R
import com.filip.newsappmvvm.data.model.ArticleModel
import com.filip.newsappmvvm.databinding.ItemNewsBinding

class NewsAdapter(private val clickListener: (ArticleModel) -> Unit) : RecyclerView.Adapter<NewsHolder>() {

    private val newsList: MutableList<ArticleModel> = mutableListOf()

    fun setData(newsList: MutableList<ArticleModel>) {
        this.newsList.clear()
        this.newsList.addAll(newsList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = newsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        val binding = ItemNewsBinding.bind(view)
        return  NewsHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bindData(newsList[position], clickListener)
    }
}