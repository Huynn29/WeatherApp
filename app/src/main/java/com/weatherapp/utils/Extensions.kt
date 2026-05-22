package com.weatherapp.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.weatherapp.BuildConfig
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun ImageView.loadWeatherIcon(iconCode: String) {

    val url = "${BuildConfig.ICON_BASE_URL}${iconCode}@2x.png"

    Glide.with(context)
        .load(url)
        .into(this)
}

fun Long.toFormattedDate(
    pattern: String = "HH:mm, dd/MM"
): String {

    val sdf = SimpleDateFormat(
        pattern,
        Locale("vi")
    )

    return sdf.format(Date(this * 1000))
}

fun Long.toDayOfWeek(): String {

    val sdf = SimpleDateFormat(
        "EEEE",
        Locale("vi")
    )

    return sdf.format(Date(this * 1000))
        .replaceFirstChar { it.uppercase() }
}

fun Int.toWindDirection(): String {

    val dirs = arrayOf(
        "B", "ĐB", "Đ", "ĐN",
        "N", "TN", "T", "TB"
    )

    return dirs[
        ((this + 22.5) / 45).toInt() % 8
    ]
}

fun Double.toTempString(): String {
    return "${this.toInt()}°C"
}
