package com.filip.newsappmvvm.ui.news

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.filip.newsappmvvm.R
import com.filip.newsappmvvm.common.isNetworkAvailable
import com.filip.newsappmvvm.databinding.ActivityMainBinding
import com.filip.newsappmvvm.extensions.goBack
import com.filip.newsappmvvm.ui.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        fun getIntent(from: Context) = Intent(from, MainActivity::class.java)
    }

    private val newsViewModel by viewModel<NewsViewModel>()

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    private fun initUI() {
        replaceFragment(R.id.container, NewListFragment(), true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount < 1) {
            finish()
        }
    }
}