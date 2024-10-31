package com.renhejia.robot.display

import android.graphics.Rect
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class RobotSkinNumber {
    private var origRect: Rect? = null
    private var dispRect: Rect? = null

    private var origTouchRect: Rect? = null
    private var dispTouchRect: Rect? = null

    private var filePrefix: String? = null
    private var fileSpace: Int = 0
    private var dataFormat: String? = null

    private var align: Int = 0
    fun getOrigRect(): Rect? {
        return origRect
    }

    fun setOrigRect(origRect: Rect?) {
        this.origRect = origRect
    }

    fun getDispRect(): Rect? {
        return dispRect
    }

    fun setDispRect(dispRect: Rect?) {
        this.dispRect = dispRect
    }

    fun getFilePrefix(): String? {
        return filePrefix
    }

    fun setFilePrefix(filePrefix: String?) {
        this.filePrefix = filePrefix
    }

    fun getTimeString(date: Date, hourFormat: Int): String {
        try { //Todo format 'u'在jdk1.6不支持
            var strFormat: String? = dataFormat

            if (dataFormat == "u") {
                val now: Calendar = Calendar.getInstance()
                now.setTime(date)
                // boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
                val weekDay: Int = now.get(Calendar.DAY_OF_WEEK)
                var weekStr: String = ""
                when (weekDay) {
                    Calendar.SUNDAY ->                         //                        weekStr = "7";
                        weekStr = "g"

                    Calendar.MONDAY ->                         //                        weekStr = "1";
                        weekStr = "a"

                    Calendar.TUESDAY ->                         //                        weekStr = "2";
                        weekStr = "b"

                    Calendar.WEDNESDAY ->                         //                        weekStr = "3";
                        weekStr = "c"

                    Calendar.THURSDAY ->                         //                        weekStr = "4";
                        weekStr = "d"

                    Calendar.FRIDAY ->                         //                        weekStr = "5";
                        weekStr = "e"

                    Calendar.SATURDAY ->                         //                        weekStr = "6";
                        weekStr = "f"

                    else -> {}
                }

                return weekStr
            }

            if (hourFormat == RobotClockView.Companion.SPINE_CLOCK_HOURS_24) {
                strFormat = strFormat!!.replace("hh", "HH")
            } else if (hourFormat == RobotClockView.Companion.SPINE_CLOCK_HOURS_12) {
                strFormat = strFormat!!.replace("HH", "hh")
            }

            if (strFormat!!.length > 0) {
                val weekIndex: Int = getWeekFormatIndex(strFormat)
                val format: SimpleDateFormat = SimpleDateFormat(strFormat, Locale.getDefault())
                var strDate: String = format.format(date)
                if (weekIndex != INVALID_INDEX) {
                    strDate = convertToCorrectDateFormat(weekIndex, strDate, date)
                }

                return strDate
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    private fun convertToCorrectDateFormat(index: Int, strDate: String, date: Date): String {
        val now: Calendar = Calendar.getInstance()
        now.setTime(date)
        val weekDay: Int = now.get(Calendar.DAY_OF_WEEK)
        var weekStr: String = ""
        when (weekDay) {
            Calendar.SUNDAY -> weekStr = "g"
            Calendar.MONDAY -> weekStr = "a"
            Calendar.TUESDAY -> weekStr = "b"
            Calendar.WEDNESDAY -> weekStr = "c"
            Calendar.THURSDAY -> weekStr = "d"
            Calendar.FRIDAY -> weekStr = "e"
            Calendar.SATURDAY -> weekStr = "f"
            else -> {}
        }
        val newStrDate: String =
            strDate.substring(0, index) + weekStr + strDate.substring(index + 1, strDate.length)
        return newStrDate
    }

    private fun getWeekFormatIndex(strFormat: String): Int {
        val WEEK_INDICATOR: Char = 'u'
        for (i in 0 until strFormat.length) {
            if (WEEK_INDICATOR == strFormat.get(i)) {
                return i
            }
        }
        return INVALID_INDEX
    }


    fun getImgFilename(str: String?): String {
        return filePrefix + RobotSkinFileMap.getFilePostfix(str) + ".png"
    }

    fun getImgFilenameFlash(str: String?, millisecond: Int): String {
        return filePrefix + RobotSkinFileMap.getFilePostfixWith(str, millisecond) + ".png"
    }

    fun getFileSpace(): Int {
        return fileSpace
    }

    fun setFileSpace(fileSpace: Int) {
        this.fileSpace = fileSpace
    }

    fun getAlign(): Int {
        return align
    }

    fun setAlign(align: Int) {
        this.align = align
    }

    fun getDataFormat(): String? {
        return dataFormat
    }

    fun setDataFormat(dataFormat: String?) {
        this.dataFormat = dataFormat
    }

    fun getOrigTouchRect(): Rect? {
        return origTouchRect
    }

    fun setOrigTouchRect(origTouchRect: Rect?) {
        this.origTouchRect = origTouchRect
    }

    fun getDispTouchRect(): Rect? {
        return dispTouchRect
    }

    fun setDispTouchRect(dispTouchRect: Rect?) {
        this.dispTouchRect = dispTouchRect
    }

    companion object {
        private val INVALID_INDEX: Int = 99


        private fun getBestDateTimePattern(skeleton: String): String {
            val pattern: String = DateFormat.getBestDateTimePattern(Locale.getDefault(), skeleton)

            return pattern
        }
    }
}
