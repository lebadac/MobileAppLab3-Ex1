package com.dac.lab3_ex1.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dac.lab3_ex1.data.api.NewsApiService
import com.dac.lab3_ex1.data.model.ArticleWithSentiment
import com.dac.lab3_ex1.sentiment.SentimentAnalyzer
// PagingSource class to load paginated news articles with sentiment analysis
class NewsPagingSource(
    private val service: NewsApiService,
    private val apiKey: String,
    private val analyzer: SentimentAnalyzer
) : PagingSource<Int, ArticleWithSentiment>() {
    // Loads a page of articles with sentiment analysis
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleWithSentiment> {
        return try {
            val page = params.key ?: 1
            val response = service.getArticles(
                query = "android",
                pageSize = params.loadSize,
                page = page,
                apiKey = apiKey
            )
            val articlesWithSentiment = response.articles.map { article ->
                val sentiment = analyzer.analyze(article.content ?: article.title ?: "No content")
                ArticleWithSentiment(article, sentiment)
            }
            LoadResult.Page(
                data = articlesWithSentiment,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page * params.loadSize < response.totalResults) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    // Determines the key for reloading data after refresh
    override fun getRefreshKey(state: PagingState<Int, ArticleWithSentiment>): Int? = null
}
