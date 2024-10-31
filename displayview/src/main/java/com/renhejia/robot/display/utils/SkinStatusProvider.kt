package com.renhejia.robot.display.utils

import android.content.Context
import android.text.format.DateFormat

/**
 * @author liujunbin
 */
object SkinStatusProvider {
    /**
     *
     * @param context
     * @return
     */
    fun is12HourFormat(context: Context?): Boolean {
        return !is24HourFormat(context)
    }

    fun is24HourFormat(context: Context?): Boolean {
        return DateFormat.is24HourFormat(context)
    }
}
