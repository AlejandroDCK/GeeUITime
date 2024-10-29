package com.renhejia.robot.display.utils

/**
 * 表盘配置相关常量
 *
 * @author liujunbin
 */
interface ClockViewConsts {
    companion object {
        const val ORIG_RECT: String = "origRect"

        //    public final static String DISP_RECT    = "dispRect";
        const val DATA_FORMAT: String = "dataFormat"
        const val FILE_PREFIX: String = "filePrefix"
        const val FILE_SPACE: String = "fileSpace"
        const val COLOR: String = "color"
        const val STYLE: String = "style"
        const val ORIG_SIZE: String = "origSize"
        const val LEFT: String = "left"
        const val TOP: String = "top"
        const val RIGHT: String = "right"
        const val BOTTOM: String = "bottom"
        const val IMG_FILE: String = "imgFile"
        const val X: String = "x"
        const val Y: String = "y"
        const val ORIG_ANCHOR: String = "origAnchor"
        const val LANGUAGE_FORMAT: String = "languageFormat"
        const val BG_COLOR: String = "bgColor"
        const val FG_COLOR: String = "fgColor"
        const val STROKE_WIDTH: String = "strokeWidth"
        const val START_ANGLE: String = "startAngle"
        const val SWEEP_ANGLE: String = "sweepAngle"

        const val ORIG_HOUR_ANCHOR: String = "origHourAnchor"
        const val ORIG_MINUTE_ANCHOR: String = "origMinuteAnchor"
        const val ORIG_SECOND_ANCHOR: String = "origSecondAnchor"


        const val INTERVAL_ANGLE: String = "intervalAngle"
        const val IMAGE_ANGLE: String = "imageAngle"
        const val DISPLAY_ANCHOR: String = "displayAnchor"

        const val DIGIT_TIMES: String = "digitTimes"
        const val LABEL_TIMES: String = "labelTimes"
        const val COUNTDOWN_EVENT: String = "countdownEvent"
        const val NOTICES: String = "notices"
        const val FANS_INFO: String = "fansInfo"
        const val FANS_ICON: String = "fansIcon"
        const val FANS_HEAD: String = "fansHead"
        const val ANALOG_TIMES: String = "analogTimes"
        const val BACKGROUNDS: String = "backgrounds"
        const val ALIGN: String = "align"
        const val ALIGNS: String = "aligns"
        const val TOTAL: String = "total"

        const val BATTERY: String = "battery"
        const val WIFI: String = "wifi"
        const val BLUETOOTH: String = "bluetooth"
        const val WEATHER: String = "weather"
        const val NOTICE: String = "notice"
        const val VOLUME: String = "volume"
        const val CHARGE: String = "charge"
        const val BACKGROUND: String = "background"
        const val FOREGROUND: String = "foreground"
        const val MIDDLE: String = "middle"

        const val STEP: String = "step"
        const val AIR_TEMP: String = "airTemp"


        const val BATTERY_SQUARES: String = "batterySquares"
        const val STEP_SQUARES: String = "stepSquares"

        const val BATTERY_ANCHOR: String = "batteryAnchor"
        const val WEEK_ANCHOR: String = "weekAnchor"

        const val STEP_NUMBER: String = "stepNumber"
        const val AQI_NUMBER: String = "aqiNumber"
        const val AQI_TEXT: String = "aqiText"
        const val NOTICE_TEXT: String = "noticeText"
        const val BATTERY_NUMBER: String = "batteryNumber"
        const val TEMPERATURE: String = "temperature"
        const val TEMPERATURE_DES: String = "temperatureDes"
        const val TEMPERATURE_INFO: String = "temperatureInfo"
        const val TEMPERATURE_RANGE: String = "temperatureRange"

        const val BATTERY_PROGRESS: String = "batteryProgress"

        const val STEP_SKIN_ARC: String = "stepSkinArc"
        const val BATTERY_ANGLE: String = "batteryAngle"
        const val CUSTOM_SKIN_JSON_FILE: String = "custom_skin.json"
        const val SKIN_JSON_FILE: String = "skin.json"

        const val CLOCK_SKIN_TYPE_BUILD_IN: Int = 0
        const val CLOCK_SKIN_TYPE_FROM_STORE: Int = 1
        const val CLOCK_SKIN_TYPE_CLOCK_SKIN_STORE_ENTRANCE: Int = 2
        val CLOCK_SKIN_BUILD_IN_ID: Int = -999
        const val CLOCK_SKIN_STORE_ID: Int = 23
        val CLOCK_SKIN_TYPE_NULL: Int = -999

        const val OPEN_CLOCK_SKIN: String = "open_skin_store"
    }
}
