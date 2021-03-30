package com.filip.newsappmvvm.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.filip.newsappmvvm.R
import com.filip.newsappmvvm.common.isNetworkAvailable
import com.filip.newsappmvvm.data.model.ArticleModel
import com.filip.newsappmvvm.data.response.NewsResponse
import com.filip.newsappmvvm.databinding.FragmentNewsListBinding
import com.filip.newsappmvvm.extensions.gone
import com.filip.newsappmvvm.extensions.showFragment
import com.filip.newsappmvvm.extensions.subscribe
import com.filip.newsappmvvm.extensions.visible
import com.filip.newsappmvvm.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel

class NewListFragment : BaseFragment<FragmentNewsListBinding>() {

    private val sharedNewsViewModel by sharedViewModel<NewsViewModel>()
    private val adapter by lazy { NewsAdapter { newsClicked(it) } }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNewsListBinding
        get() = FragmentNewsListBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedNewsViewModel.getArticles(activity.isNetworkAvailable())
        initUi()
        subscribeData()

    }

    private fun initUi() {
        binding.run {
            newsList.layoutManager = LinearLayoutManager(activity)
            newsList.adapter = adapter
        }
    }

    private fun subscribeData() {
        sharedNewsViewModel.newsLisState.subscribe(this, ::handleNewsListState)
        sharedNewsViewModel.newsList.subscribe(this, ::handleData)
    }

    private fun handleData(response: NewsResponse) {
       adapter.setData(response.articles)
    }

    private fun handleNewsListState(state: NewsListState) {
        when (state) {
            NewsListState.NoInternet -> showNoInternet()
            NewsListState.ShowData -> showData()
            NewsListState.NoData -> noData()
            NewsListState.Loading -> loading()
        }
    }



    private fun loading() {
        binding.progressBar.root.visible()
    }

    private fun noData() {
        binding.run {
            progressBar.root.gone()
        }
    }

    private fun showData() {
        binding.progressBar.root.gone()
    }

    private fun showNoInternet() {
        binding.progressBar.root.gone()
        binding.noInternet.root.visible()

    }

    private fun newsClicked(articleModel: ArticleModel) {
        sharedNewsViewModel.setArticleData(articleModel)
        activity?.showFragment(R.id.container,DetailsNewsFragment(),true)
    }
}