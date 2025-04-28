package com.dac.lab3_ex1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dac.lab3_ex1.data.api.NewsApiService
import com.dac.lab3_ex1.data.paging.NewsPagingSource
import com.dac.lab3_ex1.sentiment.SentimentAnalyzer

// ViewModel to handle the news articles and their sentiment analysis
class NewsViewModel(
    private val service: NewsApiService,
    private val analyzer: SentimentAnalyzer
) : ViewModel() {

    val articles = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { NewsPagingSource(service, "baacc16952c74dc6a805428fcb75994a", analyzer) }
    ).flow.cachedIn(viewModelScope)
}


// Factory to provide dependencies to the NewsViewModel
@Suppress("UNCHECKED_CAST")
class NewsViewModelFactory(
    private val service: NewsApiService,
    private val analyzer: SentimentAnalyzer
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(service, analyzer) as T
    }
}
