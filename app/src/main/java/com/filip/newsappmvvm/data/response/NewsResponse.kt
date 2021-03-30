package com.filip.newsappmvvm.data.response

import com.filip.newsappmvvm.data.model.ArticleModel
import java.io.Serializable

data class NewsResponse(var articles: MutableList<ArticleModel> = mutableListOf()) : Serializable
