package com.example.weatherinfo.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherinfo.R
import com.example.weatherinfo.BR
import com.example.weatherinfo.base.BaseListAdapter
import com.example.weatherinfo.model.WeatherCastInfo

class WeatherCastInfoAdapter :
    BaseListAdapter<WeatherCastInfo>(diffUtil, R.layout.item_weather_cast_info, BR.item) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<WeatherCastInfo>() {

            override fun areItemsTheSame(
                oldItem: WeatherCastInfo, newItem: WeatherCastInfo
            ): Boolean = oldItem.date == newItem.date

            override fun areContentsTheSame(
                oldItem: WeatherCastInfo, newItem: WeatherCastInfo
            ): Boolean = oldItem == newItem
        }
    }

}