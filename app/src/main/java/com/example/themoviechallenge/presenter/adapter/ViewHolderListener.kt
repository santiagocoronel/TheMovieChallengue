package com.example.themoviechallenge.presenter.adapter

interface ViewHolderListener<T> {
    fun onClick(item: T, position: Int)
    fun onLoadMore()
    fun firstPositionIsVisible(value: Boolean)
}