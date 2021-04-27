package com.example.themoviechallenge.presenter.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.themoviechallenge.databinding.ItemLoadingBinding

class LoadingViewHolder(val binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.itemProgressbar.isIndeterminate = true
    }
}