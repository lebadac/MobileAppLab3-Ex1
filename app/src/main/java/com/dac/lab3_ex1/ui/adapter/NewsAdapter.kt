package com.dac.lab3_ex1.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dac.lab3_ex1.data.model.ArticleWithSentiment
import com.dac.lab3_ex1.databinding.ItemArticleBinding

// Adapter class for displaying paginated news articles with sentiment-based coloring
class NewsAdapter : PagingDataAdapter<ArticleWithSentiment, NewsAdapter.NewsViewHolder>(ARTICLE_COMPARATOR) {

    // Creates a new ViewHolder instance
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    // Binds data to the ViewHolder
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    // ViewHolder class for binding article data to the item layout
    class NewsViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(articleWithSentiment: ArticleWithSentiment) {
            binding.textViewTitle.text = articleWithSentiment.article.title
            binding.textViewPublishedAt.text = articleWithSentiment.article.publishedAt
            binding.imageViewArticle.load(articleWithSentiment.article.urlToImage) {
                crossfade(true)
                placeholder(android.R.drawable.ic_menu_gallery)
                error(android.R.drawable.ic_menu_report_image)
            }

            binding.textViewDescription.text = articleWithSentiment.article.content ?: "No description available"

            val color = when (articleWithSentiment.sentiment.sentiment) {
                "positive" -> Color.argb(200, 76, 175, 80)
                "negative" -> Color.argb(200, 244, 67, 54)
                else -> Color.argb(200, 255, 235, 59)
            }

            // Đặt màu sắc cho CardView
            binding.root.setCardBackgroundColor(color)
        }
    }


    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<ArticleWithSentiment>() {
            override fun areItemsTheSame(oldItem: ArticleWithSentiment, newItem: ArticleWithSentiment): Boolean {
                return oldItem.article.title == newItem.article.title &&
                        oldItem.article.publishedAt == newItem.article.publishedAt
            }

            override fun areContentsTheSame(oldItem: ArticleWithSentiment, newItem: ArticleWithSentiment): Boolean {
                return oldItem == newItem
            }
        }
    }
}
