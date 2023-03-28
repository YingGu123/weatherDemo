package com.example.weatherinfo.adapter

import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherinfo.R
import com.example.weatherinfo.BR
import com.example.weatherinfo.Const
import com.example.weatherinfo.WeatherDetailActivity
import com.example.weatherinfo.base.BaseListAdapter
import com.example.weatherinfo.model.WeatherLiveInfo

class WeatherBaseInfoAdapter :
    BaseListAdapter<WeatherLiveInfo>(diffUtil, R.layout.item_weather_base_info, BR.item) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<WeatherLiveInfo>() {

            override fun areItemsTheSame(
                oldItem: WeatherLiveInfo, newItem: WeatherLiveInfo
            ): Boolean = oldItem.adCode == newItem.adCode

            override fun areContentsTheSame(
                oldItem: WeatherLiveInfo, newItem: WeatherLiveInfo
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = super.onCreateViewHolder(parent, viewType)
        viewHolder.itemView.setOnClickListener {
            val context = parent.context
            val position = (parent as RecyclerView).getChildAdapterPosition(it)
            val adCode = getItem(position).adCode
            val intent = Intent(context, WeatherDetailActivity::class.java)
            intent.putExtra(Const.BUNDLE_AD_CODE, adCode)
            context.startActivity(intent)
        }
        return viewHolder
    }
}