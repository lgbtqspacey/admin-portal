package com.lgbtqspacey.king.helpers

import java.text.SimpleDateFormat
import java.util.*

class DateHelper {
    companion object {
        /**
         * Parses milliseconds to informed date format.
         * @param millis date to be parsed
         * @param format date format to be used. Defaults to `dd/MM/yyyy`.
         */
        fun convertMillisToDate(millis: Long, format: String = "dd/MM/yyyy"): String {
            val formatter = SimpleDateFormat(format, Locale.getDefault())
            return formatter.format(Date(millis))
        }
    }
}
