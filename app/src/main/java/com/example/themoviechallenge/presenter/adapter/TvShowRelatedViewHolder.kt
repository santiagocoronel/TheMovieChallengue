package com.example.themoviechallenge.presenter.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themoviechallenge.databinding.ItemTvShowBinding
import com.example.themoviechallenge.databinding.ItemTvShowRelatedBinding
import com.example.themoviechallenge.presenter.model.TvShowModel

class TvShowRelatedViewHolder(
    private val binding: ItemTvShowRelatedBinding,
    private val listener: ViewHolderListener<TvShowModel>
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(tvShowModel: TvShowModel, position: Int) {
        with(binding) {

            Glide.with(imageview)
                .load("https://image.tmdb.org/t/p/w500/${tvShowModel.posterPath}")
                .centerInside()
                .into(imageview)

            binding.root.setOnClickListener {
                listener.onClick(item = tvShowModel, position = position)
            }
        }
    }

}