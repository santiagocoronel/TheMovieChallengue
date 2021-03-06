package com.example.themoviechallenge.presenter.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themoviechallenge.databinding.ItemTvShowBinding
import com.example.themoviechallenge.presenter.model.TvShowModel

class TvShowViewHolder(
    private val binding: ItemTvShowBinding,
    private val listener: ViewHolderListener<TvShowModel>
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        const val MAX_LENGHT_OVERVIEW = 50
    }

    fun bind(tvShowModel: TvShowModel, position: Int) {
        with(binding) {

            Glide.with(imageview)
                .load("https://image.tmdb.org/t/p/w500/${tvShowModel.posterPath}")
                .centerInside()
                .into(imageview)

            textviewTitle.text = tvShowModel.name

            if (tvShowModel.overview.length > MAX_LENGHT_OVERVIEW) {
                textviewOverview.text =
                    "${tvShowModel.overview.substring(0, MAX_LENGHT_OVERVIEW)} ..."
            } else {
                textviewOverview.text = tvShowModel.overview
            }

            textviewRate.text = tvShowModel.voteAverage.toString()

            binding.root.setOnClickListener {
                listener.onClick(item = tvShowModel, position = position)
            }
        }
    }

}