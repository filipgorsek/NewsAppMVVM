package com.filip.newsappmvvm.data.model

import com.filip.newsappmvvm.common.EMPTY
import java.io.Serializable

data class ArticleModel(
    var id: Long = 0L,
    var author: String = EMPTY,
    var title: String = EMPTY,
    var description: String = EMPTY,
    var url: String = EMPTY,
    var urlToImage: String = EMPTY,

    ) : Serializable