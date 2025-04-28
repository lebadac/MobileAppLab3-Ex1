package com.dac.lab3_ex1.data.model
// Data class representing the response from the news API
data class NewsResponse(
    val articles: List<Article>,
    val totalResults: Int
)
