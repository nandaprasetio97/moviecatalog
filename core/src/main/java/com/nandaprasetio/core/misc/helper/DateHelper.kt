package com.nandaprasetio.core.misc.helper

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    @SuppressLint("SimpleDateFormat")
    private val dateSimpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    fun parseDate(dateString: String?): Date? {
        return dateString?.let { dateSimpleDateFormat.parse(dateString) }
    }

    fun formatDate(date: Date?): String? {
        return date?.let { dateSimpleDateFormat.format(date) }
    }
}