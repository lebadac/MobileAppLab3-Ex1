package com.dac.lab3_ex1.sentiment

import android.content.Context
import com.dac.lab3_ex1.data.model.SentimentResult
import org.tensorflow.lite.task.text.nlclassifier.NLClassifier

// Class to perform sentiment analysis using a TensorFlow Lite model
class SentimentAnalyzer(context: Context) {

    private val classifier: NLClassifier = NLClassifier.createFromFile(context, "sentiment_model.tflite")

    // Analyzes the input text and returns the sentiment result
    fun analyze(text: String): SentimentResult {
        val results = classifier.classify(text)
        var positiveScore = 0f
        var negativeScore = 0f

        for (category in results) {
            when (category.label.lowercase()) {
                "positive" -> positiveScore = category.score
                "negative" -> negativeScore = category.score
            }
        }

        val sentiment = when {
            positiveScore > 0.55f -> "positive"
            negativeScore > 0.55f -> "negative"
            else -> "neutral"
        }

        return SentimentResult(positiveScore, negativeScore, sentiment)
    }

    // Closes the classifier and releases resources
    fun close() {
        classifier.close()
    }
}
