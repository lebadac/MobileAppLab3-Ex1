package com.dac.lab3_ex1.ui.screens

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dac.lab3_ex1.databinding.ActivityMainBinding
import com.dac.lab3_ex1.ui.adapter.NewsAdapter
import com.dac.lab3_ex1.ui.viewmodel.NewsViewModel
import com.dac.lab3_ex1.ui.viewmodel.NewsViewModelFactory
import com.dac.lab3_ex1.data.api.NewsApiService
import com.dac.lab3_ex1.sentiment.SentimentAnalyzer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// Main Activity that displays a list of news articles with sentiment analysis
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = NewsAdapter()
    // ViewModel with dependency injection for API service and sentiment analyzer
    private val viewModel: NewsViewModel by viewModels {
        NewsViewModelFactory(
            NewsApiService.create(),
            SentimentAnalyzer(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.articles.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}
