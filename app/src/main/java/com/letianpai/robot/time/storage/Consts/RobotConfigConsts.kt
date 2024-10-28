package com.letianpai.robot.time.storage.Consts

/**
 * @author liujunbin
 */
interface RobotConfigConsts {
    companion object {
        const val KEY_ROBOT_THEME: String = "robot_theme"
        const val KEY_ROBOT_THEME_NAME: String = "robot_theme_name"
        const val VALUE_WATCH_THEME_DEFAULT: Int = 0
        const val VALUE_WATCH_THEME_MONSTER: Int = 1
        const val VALUE_WATCH_THEME_STAR: Int = 2
        const val THEME_DEFAULT: String = "default"
        const val THEME_MONSTER: String = "monster"
        const val THEME_STAR: String = "star"


        // 显示充电锁屏 的广播
        // 隐藏充电锁屏 的广播
        // 关闭闹铃Action
        const val KEY_WATCH_WEATHER_UPDATE_TIME: String = "weather_update_time"
        const val KEY_ROBOT_START_TIME: String = "start_time"
        const val KEY_UPDATE_DISABLE_APP_UPDATE_TIME: String = "disable_apps_update_time"
        const val KEY_DISABLE_APP_LIST: String = "disable_apps_list"
        const val KEY_WATCH_WEATHER_INFO: String = "weather_info"

        const val KEY_ROBOT_HEIGHT: String = "robot_height"
        const val VALUE_ROBOT_HEIGHT_DEFAULT: Int = 240

        const val KEY_ROBOT_WIDTH: String = "robot_width"
        const val VALUE_ROBOT_WIDTH_DEFAULT: Int = 240

        const val KEY_MESSAGE_CENTER_NUMBER: String = "key_message_center"
        const val VALUE_MESSAGE_CENTER_NUMBER: String = "1069800003602120"
        const val VALUE_MESSAGE_CENTER_NUMBER_NEW_TEST: String = "10690329017360610011"
        const val VALUE_MESSAGE_CENTER_NUMBER_NEW_ONLINE: String = "10690329017360610012"
        const val KEY_WEATHER: String = "key_weather"
        const val KEY_SHOW_AUTO_SHUTDOWN: String = "key_show_auto_shut_down"
        const val KEY_SHOW_GUIDE_VIEW: String = "key_show_guide_down"
        const val KEY_HAD_SET_THEME: String = "key_had_set_theme"

        const val SIMICCID_TIMES: String = "iccid_times"
        const val SMSSEND_TIMES: String = "sms_send_times"
        const val SIMICCID: String = "iccid"
        const val SMSSEND_DAY_TIMES: String = "sms_send_day_times"
        const val RECORD_DATE: String = "record_date"
        const val SERVERTIME: String = "servertime"
        const val SELF_NUMBER: String = "watch_self_number"
        const val SELF_NUMBER_TIME: String = "self_number_timestamp"

        const val KEY_WATCH_APP_LIST_STYLE: String = "app_list_style"
        const val VALUE_WATCH_APP_LIST_STYLE_NO_SET: Int = 99
        const val APP_LIST: String = "applist"
        const val RECENT_APP_LIST: String = "recent_app_list"
        const val RECENT_WEATHER: String = "weather"
        const val RECENT_CALENDAR: String = "calendar"
        const val RECENT_FANS_INFO: String = "fansInfo"
        const val RECENT_GENERAL_INFO: String = "general"
        const val RECENT_GENERAL_INFO_EN: String = "general_en"
        const val RECENT_COUNT_DOWN: String = "countdown"
        const val UPLOAD_DATA_TIME: String = "upload_data"
    }
}
