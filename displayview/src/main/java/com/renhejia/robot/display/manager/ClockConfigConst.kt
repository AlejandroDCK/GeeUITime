/**
 *
 */
package com.renhejia.robot.display.manager

/**
 *
 * @author liujunbin
 */
interface ClockConfigConst {
    companion object {
        /**
         * 初始化状态判断
         */
        const val IS_ROBOT_CONFIG_INIT: String = "is_watch_config_init"
        const val IS_WATCH_CONFIG_INIT_DONE: Boolean = true
        const val IS_WATCH_CONFIG_INIT_UNDONE: Boolean = false


        const val KEY_LTP_CLOCK_SKIN: String = "ltp_clock_skin"
        const val KEY_LTP_CLOCK_SKIN_PATH: String = "ltp_clock_skin_path"

        const val KEY_IS_LONG_PRESS_IN_EDIT: String = "is_long_press_in_edit"
        const val VALUE_IS_LONG_PRESS_IN_EDIT_TRUE: Boolean = true
        const val VALUE_IS_LONG_PRESS_IN_EDIT_FALSE: Boolean = false


        const val KEY_WATCH_HEIGHT: String = "watch_height"
        const val VALUE_WATCH_HEIGHT_DEFAULT: Int = 240

        const val KEY_WATCH_WIDTH: String = "watch_width"
        const val VALUE_WATCH_WIDTH_DEFAULT: Int = 240


        const val KEY_CLOCK_HEIGHT: String = "clock_height"
        const val VALUE_CLOCK_HEIGHT_DEFAULT: Int = 0

        const val KEY_CLOCK_WIDTH: String = "clock_width"
        const val VALUE_CLOCK_WIDTH_DEFAULT: Int = 0

        const val KEY_WAETHER_UPDATE_TIME: String = "weather_update_time"

        const val KEY_IS_SHOW_DATE: String = "is_show_date"
        const val KEY_IS_SHOW_WEATHER: String = "is_show_weather"
        const val KEY_IS_RANDOM_CHANGE: String = "is_random_change"
        const val KEY_IS_SHOW_CUSTOM: String = "is_show_custom"
        const val VALUE_IS_SHOW_OPENED: Int = 1
        const val VALUE_IS_SHOW_CLOSED: Int = 0
        const val KEY_CUSTOM_SKIN_URL: String = "custom_skin_url"
        const val KEY_CUSTOM_SKIN_NAME: String = "custom_skin_name"
        const val KEY_TIME_ZONE: String = "time_zone"
        const val KEY_TEMP_MODE: String = "time_mode"
        const val KEY_TEMP_MODE_CEL: String = "cel"
    }
}


