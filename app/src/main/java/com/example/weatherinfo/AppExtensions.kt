package com.example.weatherinfo

import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setDividerItemDecoration(
    @DrawableRes resId: Int = R.drawable.inset_item_divider,
    orientation: Int = DividerItemDecoration.VERTICAL
) {
    ContextCompat.getDrawable(context, resId)?.let {
        val divider = DividerItemDecoration(context, orientation)
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}