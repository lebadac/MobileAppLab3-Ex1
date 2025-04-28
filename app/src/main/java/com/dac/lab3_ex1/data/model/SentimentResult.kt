package com.dac.lab3_ex1.data.model
// Data class representing the sentiment analysis result of an article
data class SentimentResult(
    val positiveScore: Float,
    val negativeScore: Float,
    val sentiment: String
)

