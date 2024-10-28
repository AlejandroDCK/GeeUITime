package com.letianpai.robot.time.storage.manager

import android.content.Context
import android.util.Log
import com.letianpai.robot.components.network.system.SystemUtil.isInChinese
import com.letianpai.robot.time.storage.Consts.RobotConfigConsts

/**
 * Launcher 偏好设置管理器
 * @author liujunbin
 */
class LauncherConfigManager private constructor(private val mContext: Context) : RobotConfigConsts {
    private val mRobotSharedPreference = RobotSharedPreference(
        mContext,
        RobotSharedPreference.Companion.SHARE_PREFERENCE_NAME,
        RobotSharedPreference.Companion.ACTION_INTENT_CONFIG_CHANGE
    )

    /**
     * 增加偏好设置初始化逻辑
     * (暂时没有用，预留给将来机器人需要初始化一些状态值时使用)
     */
    private fun initRobotConfigState() {
    }

    fun commit(): Boolean {
        return mRobotSharedPreference.commit()
    }

    var robotTheme: Int
        /**
         * 获取机器人主题
         *
         * @return
         */
        get() = mRobotSharedPreference.getInt(
            RobotConfigConsts.Companion.KEY_ROBOT_THEME,
            RobotConfigConsts.Companion.VALUE_WATCH_THEME_DEFAULT
        )
        /**
         * 设置机器人主题
         *
         * @return
         */
        set(watchTheme) {
            mRobotSharedPreference.putInt(RobotConfigConsts.Companion.KEY_ROBOT_THEME, watchTheme)
        }

    var watchThemeName: String?
        /**
         * 获取手表主题
         *
         * @return
         */
        get() = mRobotSharedPreference.getString(
            RobotConfigConsts.Companion.KEY_ROBOT_THEME_NAME,
            "default"
        )
        /**
         * 获取手表主题
         *
         * @return
         */
        set(watchThemeName) {
            Log.d(TAG, "setWatchThemeName ===>> watchThemeName: $watchThemeName")
            mRobotSharedPreference.putString(
                RobotConfigConsts.Companion.KEY_ROBOT_THEME_NAME,
                watchThemeName!!
            )
            if (!hadSetTheme) {
                hadSetTheme = true
            }
        }

    var weatherUpdateTime: Long
        /**
         * 获取天气更新时间
         *
         * @return
         */
        get() = mRobotSharedPreference.getLong(
            RobotConfigConsts.Companion.KEY_WATCH_WEATHER_UPDATE_TIME,
            0L
        )
        /**
         * 设置天气更新时间
         *
         * @param updateTime
         */
        set(updateTime) {
            mRobotSharedPreference.putLong(
                RobotConfigConsts.Companion.KEY_WATCH_WEATHER_UPDATE_TIME,
                updateTime
            )
        }

    var startTime: Long
        /**
         * 获取手表启动时间
         *
         * @return
         */
        get() = mRobotSharedPreference.getLong(RobotConfigConsts.Companion.KEY_ROBOT_START_TIME, 0L)
        /**
         * 设置手表启动时间
         *
         * @param updateTime
         */
        set(updateTime) {
            mRobotSharedPreference.putLong(
                RobotConfigConsts.Companion.KEY_ROBOT_START_TIME,
                updateTime
            )
        }

    var robotHeight: Int
        /**
         * 获取手表高度
         *
         * @return
         */
        get() = mRobotSharedPreference.getInt(
            RobotConfigConsts.Companion.KEY_ROBOT_HEIGHT,
            RobotConfigConsts.Companion.VALUE_ROBOT_HEIGHT_DEFAULT
        )
        /**
         * 设置手表高度
         *
         * @return
         */
        set(height) {
            mRobotSharedPreference.putInt(RobotConfigConsts.Companion.KEY_ROBOT_HEIGHT, height)
        }

    var robotWidth: Int
        /**
         * 获取机器人View宽度
         *
         * @return
         */
        get() = mRobotSharedPreference.getInt(
            RobotConfigConsts.Companion.KEY_ROBOT_WIDTH,
            RobotConfigConsts.Companion.VALUE_ROBOT_WIDTH_DEFAULT
        )
        /**
         * 设置手表宽度
         *
         * @return
         */
        set(width) {
            mRobotSharedPreference.putInt(RobotConfigConsts.Companion.KEY_ROBOT_WIDTH, width)
        }

    var weather: String?
        /**
         * 获取 天气信息
         *
         * @return
         */
        get() = mRobotSharedPreference.getString(RobotConfigConsts.Companion.KEY_WEATHER, null)
        /**
         * 设置 天气信息
         *
         * @return
         */
        set(weather) {
            mRobotSharedPreference.putString(RobotConfigConsts.Companion.KEY_WEATHER, weather!!)
        }

    /**
     * 是否显示
     * @return
     */
    fun hadShowAutoShutdownView(): Boolean {
        return mRobotSharedPreference.getBoolean(
            RobotConfigConsts.Companion.KEY_SHOW_AUTO_SHUTDOWN,
            false
        )
    }

    /**
     * 设置展示自动关机
     * @return
     */
    fun setShowAutoShutdownView(showNoSimView: Boolean) {
        mRobotSharedPreference.putBoolean(
            RobotConfigConsts.Companion.KEY_SHOW_AUTO_SHUTDOWN,
            showNoSimView
        )
    }

    var hadSetTheme: Boolean
        /**
         * 是否显示
         * @return
         */
        get() = mRobotSharedPreference.getBoolean(
            RobotConfigConsts.Companion.KEY_HAD_SET_THEME,
            false
        )
        /**
         * 设置展示自动关机
         * @return
         */
        private set(hadSetTheme) {
            mRobotSharedPreference.putBoolean(
                RobotConfigConsts.Companion.KEY_HAD_SET_THEME,
                hadSetTheme
            )
        }

    /**
     * 是否已经显示引导页面
     * @return
     */
    fun hadShowGuideView(): Boolean {
        return mRobotSharedPreference.getBoolean(
            RobotConfigConsts.Companion.KEY_SHOW_GUIDE_VIEW,
            false
        )
    }

    /**
     * 设置显示引导页面状态
     * @return
     */
    fun setShowGuideView(showGuideView: Boolean) {
        mRobotSharedPreference.putBoolean(
            RobotConfigConsts.Companion.KEY_SHOW_GUIDE_VIEW,
            showGuideView
        )
    }

    /**
     * 是否已经显示视频通话
     * @return
     */
    fun getShowHideListStatus(key: String): Boolean {
        return mRobotSharedPreference.getBoolean(key, false)
    }

    /**
     * 设置显示引导页面状态
     * @return
     */
    fun setShowHideListStatus(key: String, showHideList: Boolean) {
        mRobotSharedPreference.putBoolean(key, showHideList)
    }

    var simIccidTimes: Int
        /**
         * 获取Iccid次数
         *
         * @return
         */
        get() = mRobotSharedPreference.getInt(RobotConfigConsts.Companion.SIMICCID_TIMES, 0)
        /**
         * 设置Iccid次数
         *
         * @return
         */
        set(times) {
            mRobotSharedPreference.putInt(RobotConfigConsts.Companion.SIMICCID_TIMES, times)
        }

    var sMSSendDayTimes: Int
        /**
         * 获取每天发送短信次数
         *
         * @return
         */
        get() = mRobotSharedPreference.getInt(RobotConfigConsts.Companion.SMSSEND_DAY_TIMES, 0)
        /**
         * 设置每天发送短信次数
         *
         * @return
         */
        set(times) {
            mRobotSharedPreference.putInt(RobotConfigConsts.Companion.SMSSEND_DAY_TIMES, times)
        }

    var simIccid: String?
        /**
         * 获取Iccid
         *
         * @return
         */
        get() = mRobotSharedPreference.getString(RobotConfigConsts.Companion.SIMICCID, null)
        /**
         * 设置Iccid
         *
         * @return
         */
        set(iccid) {
            mRobotSharedPreference.putString(RobotConfigConsts.Companion.SIMICCID, iccid!!)
        }

    var sMSSendTimes: Int
        /**
         * 获取短信发送次数
         *
         * @return
         */
        get() = mRobotSharedPreference.getInt(RobotConfigConsts.Companion.SMSSEND_TIMES, 0)
        /**
         * 设置短信发送次数
         *
         * @return
         */
        set(times) {
            mRobotSharedPreference.putInt(RobotConfigConsts.Companion.SMSSEND_TIMES, times)
        }

    var recordDate: String?
        /**
         * 获取 记录时间
         *
         * @return
         */
        get() = mRobotSharedPreference.getString(RobotConfigConsts.Companion.RECORD_DATE, "")
        /**
         * 设置 记录时间
         *
         * @return
         */
        set(date) {
            mRobotSharedPreference.putString(RobotConfigConsts.Companion.SERVERTIME, date!!)
        }

    var serverTime: String?
        /**
         * 获取 记录时间
         *
         * @return
         */
        get() = mRobotSharedPreference.getString(RobotConfigConsts.Companion.SERVERTIME, null)
        /**
         * 设置 severT
         *
         * @return
         */
        set(serverTime) {
            mRobotSharedPreference.putString(RobotConfigConsts.Companion.SERVERTIME, serverTime!!)
        }

    var selfNumberUpdateTime: Long
        /**
         * 获取 本地号码更新时间
         *
         * @return
         */
        get() = mRobotSharedPreference.getLong(RobotConfigConsts.Companion.SELF_NUMBER_TIME, 0)
        /**
         * 设置 本地号码更新时间
         *
         * @return
         */
        set(updateNumberTime) {
            mRobotSharedPreference.putLong(
                RobotConfigConsts.Companion.SELF_NUMBER_TIME,
                updateNumberTime
            )
        }

    var watchAppListStyle: Int
        /**
         * 获取手表主题
         *
         * @return
         */
        get() = mRobotSharedPreference.getInt(
            RobotConfigConsts.Companion.KEY_WATCH_APP_LIST_STYLE,
            RobotConfigConsts.Companion.VALUE_WATCH_APP_LIST_STYLE_NO_SET
        )
        /**
         * 获取手表主题
         *
         * @return
         */
        set(style) {
            mRobotSharedPreference.putInt(
                RobotConfigConsts.Companion.KEY_WATCH_APP_LIST_STYLE,
                style
            )
        }

    var appList: String?
        /**
         * 获取 应用列表
         *
         * @return
         */
        get() = mRobotSharedPreference.getString(RobotConfigConsts.Companion.APP_LIST, null)
        /**
         * 设置 应用列表
         *
         * @return
         */
        set(appList) {
            mRobotSharedPreference.putString(RobotConfigConsts.Companion.APP_LIST, appList!!)
        }

    var recentAppList: String?
        /**
         * 获取 最近使用应用列表
         *
         * @return
         */
        get() = mRobotSharedPreference.getString(RobotConfigConsts.Companion.RECENT_APP_LIST, null)
        /**
         * 设置 最近使用应用列表
         *
         * @return
         */
        set(appList) {
            mRobotSharedPreference.putString(RobotConfigConsts.Companion.RECENT_APP_LIST, appList!!)
        }

    var robotWeather: String?
        get() = mRobotSharedPreference.getString(RobotConfigConsts.Companion.RECENT_WEATHER, null)
        set(weather) {
            mRobotSharedPreference.putString(RobotConfigConsts.Companion.RECENT_WEATHER, weather!!)
        }

    var robotCalendar: String?
        get() = mRobotSharedPreference.getString(RobotConfigConsts.Companion.RECENT_CALENDAR, null)
        set(weather) {
            mRobotSharedPreference.putString(RobotConfigConsts.Companion.RECENT_CALENDAR, weather!!)
        }

    var robotFansInfo: String?
        get() = mRobotSharedPreference.getString(RobotConfigConsts.Companion.RECENT_FANS_INFO, null)
        set(fansInfo) {
            mRobotSharedPreference.putString(
                RobotConfigConsts.Companion.RECENT_FANS_INFO,
                fansInfo!!
            )
        }

    var robotGeneralInfo: String?
        get() = if (isInChinese) {
            mRobotSharedPreference.getString(RobotConfigConsts.Companion.RECENT_GENERAL_INFO, null)
        } else {
            mRobotSharedPreference.getString(
                RobotConfigConsts.Companion.RECENT_GENERAL_INFO_EN,
                null
            )
        }
        set(fansInfo) {
            if (isInChinese) {
                mRobotSharedPreference.putString(
                    RobotConfigConsts.Companion.RECENT_GENERAL_INFO,
                    fansInfo!!
                )
            } else {
                mRobotSharedPreference.putString(
                    RobotConfigConsts.Companion.RECENT_GENERAL_INFO_EN,
                    fansInfo!!
                )
            }
        }

    var robotCountDown: String?
        get() = mRobotSharedPreference.getString(
            RobotConfigConsts.Companion.RECENT_COUNT_DOWN,
            null
        )
        set(fansInfo) {
            mRobotSharedPreference.putString(
                RobotConfigConsts.Companion.RECENT_COUNT_DOWN,
                fansInfo!!
            )
        }

    var uploadInfoTime: Long
        get() = mRobotSharedPreference.getLong(RobotConfigConsts.Companion.UPLOAD_DATA_TIME, 0)
        set(uploadTime) {
            mRobotSharedPreference.putLong(RobotConfigConsts.Companion.UPLOAD_DATA_TIME, uploadTime)
        }


    companion object {
        private val TAG: String = LauncherConfigManager::class.java.simpleName
        private var mLauncherConfigManager: LauncherConfigManager? = null
        fun getInstance(context: Context): LauncherConfigManager? {
            if (mLauncherConfigManager == null) {
                mLauncherConfigManager = LauncherConfigManager(context)
                mLauncherConfigManager!!.initRobotConfigState()
                mLauncherConfigManager!!.commit()
            }
            return mLauncherConfigManager
        }
    }
}
