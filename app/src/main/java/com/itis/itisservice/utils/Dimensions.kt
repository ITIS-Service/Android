package com.itis.itisservice.utils

import android.content.Context

fun toDp(px: Int, context: Context): Int {
    val scale = context.resources.displayMetrics.density
    return (px * scale + 0.5f).toInt()
}