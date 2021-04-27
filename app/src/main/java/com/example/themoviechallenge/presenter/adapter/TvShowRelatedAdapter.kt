package com.example.themoviechallenge.presenter.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviechallenge.R
import com.example.themoviechallenge.databinding.ItemTvShowRelatedBinding
import com.example.themoviechallenge.presenter.model.TvShowModel

class TvShowRelatedAdapter(
    private val list: MutableList<TvShowModel> = mutableListOf(),
    private val listener: ViewHolderListener<TvShowModel>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<TvShowRelatedViewHolder>() {

    var isLoading: Boolean = false
    private val visibleThreshold = 5
    private var lastVisibleItem: Int = 0
    var totalItemCount: Int = 0
    private var isFirstPositionVisible: Boolean = true

    init {
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (totalItemCount <= lastVisibleItem + visibleThreshold) {
                    if (!isLoading) {
                        if (list.size > 0) {
                            listener.onLoadMore()
                        }
                        isLoading = true
                    }
                }
            }
        })
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowRelatedViewHolder {
        return TvShowRelatedViewHolder(
            binding = ItemTvShowRelatedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listener = listener
        )
    }

    override fun onBindViewHolder(holder: TvShowRelatedViewHolder, position: Int) {
        holder.bind(list[position], position)
        val item = list[position]
        val animation: Animation =
            AnimationUtils.loadAnimation(recyclerView.context, R.anim.animation_one)
        holder.bind(item, position)
        holder.itemView.startAnimation(animation)
    }

    override fun getItemCount(): Int = list.size

    fun clear() {
        this.list.clear()
    }

    fun addAll(list: List<TvShowModel>) {
        this.list.addAll(list)
    }

    fun remove(id: Int) {
        list.forEachIndexed { index, item ->
            if (item.id == id) {
                list.removeAt(index)
                return
            }
        }
    }
}