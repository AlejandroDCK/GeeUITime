package com.renhejia.robot.display.manager

import android.content.Context

/**
 * Launcher 偏好设置管理器
 */
class RobotClockConfigManager private constructor(private val mContext: Context?) :
    ClockConfigConst {
    private val mKidSharedPreference: RobotSharedPreference = RobotSharedPreference(
        mContext,
        RobotSharedPreference.SHARE_PREFERENCE_NAME,
        RobotSharedPreference.ACTION_INTENT_CONFIG_CHANGE
    )

    /**
     * Add preference initialisation logic
     * (not used for now, reserved for future use when the watch needs to initialise some state values)
     */
    private fun initKidSmartConfigState() {
    }

    fun commit(): Boolean {
        return mKidSharedPreference.commit()
    }

    var lTPClockSkinPath: String?
        /**
         * @return
         */
        get() {
            val skinPath: String? = mKidSharedPreference.getString(
                ClockConfigConst.KEY_LTP_CLOCK_SKIN_PATH,
                ""
            )
            return skinPath
        }
        /**
         *
         */
        set(skinPath) {
            mKidSharedPreference.putString(
                ClockConfigConst.KEY_LTP_CLOCK_SKIN_PATH,
                skinPath!!
            )
        }

    var watchHeight: Int
        /**
         * 获取手表高度
         *
         * @return
         */
        get() = mKidSharedPreference.getInt(
            ClockConfigConst.KEY_WATCH_HEIGHT,
            ClockConfigConst.VALUE_WATCH_HEIGHT_DEFAULT
        )
        /**
         * 设置手表高度
         *
         * @return
         */
        set(height) {
            mKidSharedPreference.putInt(ClockConfigConst.KEY_WATCH_HEIGHT, height)
        }

    var watchWidth: Int
        /**
         * 获取手表宽度
         *
         * @return
         */
        get() {
            return mKidSharedPreference.getInt(
                ClockConfigConst.KEY_WATCH_WIDTH,
                ClockConfigConst.VALUE_WATCH_WIDTH_DEFAULT
            )
        }
        /**
         * 设置手表宽度
         *
         * @return
         */
        set(width) {
            mKidSharedPreference.putInt(ClockConfigConst.KEY_WATCH_WIDTH, width)
        }

    /**
     * 是否显示日期
     *
     * @return
     */
    fun IsShowDate(): Boolean {
        return (mKidSharedPreference.getInt(
            ClockConfigConst.KEY_IS_SHOW_DATE,
            ClockConfigConst.VALUE_IS_SHOW_OPENED
        ) == 1)
    }

    /**
     * 设置是否显示日期
     *
     * @param isShowDate
     */
    fun setShowDate(isShowDate: Int) {
        mKidSharedPreference.putInt(ClockConfigConst.KEY_IS_SHOW_DATE, isShowDate)
    }

    /**
     * 是否显示天气
     *
     * @return
     */
    fun IsShowWeather(): Boolean {
        return (mKidSharedPreference.getInt(
            ClockConfigConst.KEY_IS_SHOW_WEATHER,
            ClockConfigConst.VALUE_IS_SHOW_OPENED
        ) == 1)
    }

    /**
     * 设置是否显示天气
     */
    fun setShowWeather(isShow: Int) {
        mKidSharedPreference.putInt(ClockConfigConst.KEY_IS_SHOW_WEATHER, isShow)
    }


    fun IsShowRandomBg(): Boolean {
        return (mKidSharedPreference.getInt(
            ClockConfigConst.KEY_IS_RANDOM_CHANGE,
            ClockConfigConst.VALUE_IS_SHOW_OPENED
        ) == 1)
    }

    fun setShowRandomBg(isShow: Int) {
        mKidSharedPreference.putInt(ClockConfigConst.KEY_IS_RANDOM_CHANGE, isShow)
    }

    fun IsShowCustomBg(): Boolean {
        return (mKidSharedPreference.getInt(
            ClockConfigConst.KEY_IS_SHOW_CUSTOM,
            ClockConfigConst.VALUE_IS_SHOW_OPENED
        ) == 1)
    }

    fun setShowCustomBg(isShow: Int) {
        mKidSharedPreference.putInt(ClockConfigConst.KEY_IS_SHOW_CUSTOM, isShow)
    }

    var customBgUrl: String?
        /**
         * @return
         */
        get() {
            val skinPath: String? =
                mKidSharedPreference.getString(ClockConfigConst.KEY_CUSTOM_SKIN_URL, "")
            return skinPath
        }
        /**
         * @param skinUrl
         */
        set(skinUrl) {
            mKidSharedPreference.putString(
                ClockConfigConst.KEY_CUSTOM_SKIN_URL,
                skinUrl!!
            )
        }


    var customSkinName: String?
        get() {
            val skinPath: String? =
                mKidSharedPreference.getString(ClockConfigConst.KEY_CUSTOM_SKIN_NAME, "")
            return skinPath
        }
        set(skinName) {
            mKidSharedPreference.putString(
                ClockConfigConst.KEY_CUSTOM_SKIN_NAME,
                skinName!!
            )
        }

    var timeZone: String?
        get() {
            val skinPath: String? =
                mKidSharedPreference.getString(ClockConfigConst.KEY_TIME_ZONE, "")
            return skinPath
        }
        set(timeZone) {
            mKidSharedPreference.putString(ClockConfigConst.KEY_TIME_ZONE, timeZone!!)
        }

    var tempMode: String?
        get() {
            val tempMode: String? = mKidSharedPreference.getString(
                ClockConfigConst.KEY_TEMP_MODE,
                ClockConfigConst.KEY_TEMP_MODE_CEL
            )
            return tempMode
        }
        set(tempMode) {
            mKidSharedPreference.putString(ClockConfigConst.KEY_TEMP_MODE, tempMode!!)
        }


    companion object {
        private var mLauncherConfigManager: RobotClockConfigManager? = null
        fun getInstance(context: Context?): RobotClockConfigManager? {
            if (mLauncherConfigManager == null) {
                mLauncherConfigManager = RobotClockConfigManager(context)
                mLauncherConfigManager!!.initKidSmartConfigState()
                mLauncherConfigManager!!.commit()
            }
            return mLauncherConfigManager
        }
    }
}
