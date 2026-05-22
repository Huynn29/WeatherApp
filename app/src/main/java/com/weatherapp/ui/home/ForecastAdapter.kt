package com.weatherapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weatherapp.data.model.ForecastItem
import com.weatherapp.databinding.ItemForecastBinding
import com.weatherapp.utils.loadWeatherIcon
import com.weatherapp.utils.toDayOfWeek
import com.weatherapp.utils.toTempString

class ForecastAdapter :
    ListAdapter<ForecastItem, ForecastAdapter.VH>(DIFF) {

    inner class VH(
        private val binding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ForecastItem) =
            with(binding) {

                tvDay.text =
                    item.dt.toDayOfWeek()

                tvTime.text =
                    item.dtTxt.substring(11, 16)

                tvTemp.text =
                    item.main.temp.toTempString()

                tvDesc.text =
                    item.weather.firstOrNull()?.description ?: ""

                tvRain.text =
                    "${(item.pop * 100).toInt()}%"

                item.weather.firstOrNull()
                    ?.icon
                    ?.let {
                        ivIcon.loadWeatherIcon(it)
                    }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = VH(
        ItemForecastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: VH,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    companion object {

        val DIFF =
            object : DiffUtil.ItemCallback<ForecastItem>() {

                override fun areItemsTheSame(
                    a: ForecastItem,
                    b: ForecastItem
                ) = a.dt == b.dt

                override fun areContentsTheSame(
                    a: ForecastItem,
                    b: ForecastItem
                ) = a == b
            }
    }
}
