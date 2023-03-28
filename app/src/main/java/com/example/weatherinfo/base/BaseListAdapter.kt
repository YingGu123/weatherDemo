package com.example.weatherinfo.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

open class BaseListAdapter<T> constructor(
    callBack: DiffUtil.ItemCallback<T>,
    @LayoutRes private val layoutId: Int,
    private val varId: Int
) : ListAdapter<T, RecyclerView.ViewHolder>(callBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater, viewType, parent, false
        )
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BindingViewHolder) {
            holder.binding.setVariable(varId, getItem(position))
            holder.binding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return layoutId
    }

    class BindingViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)
}