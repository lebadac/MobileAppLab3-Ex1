package com.dac.lab3_ex1.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.paging.LoadState
import com.dac.lab3_ex1.databinding.ItemLoadStateBinding

class LoadStateViewHolder(
    private val binding: ItemLoadStateBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        // Set up the retry button click listener
        binding.buttonRetry.setOnClickListener {
            retry()
        }
    }

    fun bind(loadState: LoadState) {
        binding.apply {
            // Handle progress bar visibility based on loading state
            progressBar.visibility = if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
            // Handle retry button visibility if there is an error
            buttonRetry.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
            // Show error message if load state is error
            textViewErrorMessage.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
            // Set error message text
            textViewErrorMessage.text = (loadState as? LoadState.Error)?.error?.localizedMessage
        }
    }
}
