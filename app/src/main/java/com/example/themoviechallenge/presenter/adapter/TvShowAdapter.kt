package com.example.themoviechallenge.presenter.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviechallenge.R
import com.example.themoviechallenge.databinding.ItemLoadingBinding
import com.example.themoviechallenge.databinding.ItemTvShowBinding
import com.example.themoviechallenge.presenter.model.TvShowModel

class TvShowAdapter(
    private val list: MutableList<Any> = mutableListOf(),
    private val listener: ViewHolderListener<TvShowModel>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

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

                //first position is not visible
                if (linearLayoutManager.findFirstVisibleItemPosition() > 0) {
                    if (isFirstPositionVisible) {
                        listener.firstPositionIsVisible(false)
                        isFirstPositionVisible = false
                    }
                } else {
                    if (!isFirstPositionVisible) {
                        listener.firstPositionIsVisible(true)
                        isFirstPositionVisible = true
                    }
                }

                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (totalItemCount <= lastVisibleItem + visibleThreshold) {
                    if (!isLoading) {
                        if (list.size > 0) {
                            Log.d("TvShowAdapter", "emit on load more")
                            listener.onLoadMore()
                        }
                        isLoading = true
                    }
                }
            }
        })
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                TvShowViewHolder(
                    binding = ItemTvShowBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), listener = listener
                )
            }
            VIEW_TYPE_LOADING -> {
                LoadingViewHolder(
                    binding = ItemLoadingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw Exception("Unsupport view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position] is TvShowModel) {
            (holder as TvShowViewHolder).bind(list[position] as TvShowModel, position)
        }
        val item = list[position]
        when (holder) {
            is TvShowViewHolder -> {
                val animation: Animation =
                    AnimationUtils.loadAnimation(recyclerView.context, R.anim.animation_one)
                holder.bind(item as TvShowModel, position)
                holder.itemView.startAnimation(animation)
            }
            is LoadingViewHolder -> {
                holder.binding.itemProgressbar.isIndeterminate = true
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] is Loading) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int = list.size

    fun clear() {
        this.list.clear()
    }

    fun addAll(list: List<TvShowModel>) {
        this.list.addAll(list)
    }

    fun addLoading() {
        this.list.add(Loading())
    }

    fun removeLoading() {
        if (list.isEmpty()) return
        val item = list[list.size - 1]
        if (item is Loading) {
            this.list.removeAt(list.size - 1)
        }
    }

    fun remove(id: Int) {
        list.forEachIndexed { index, item ->
            if (item is TvShowModel) {
                if (item.id == id) {
                    list.removeAt(index)
                    return
                }
            }
        }
    }
}