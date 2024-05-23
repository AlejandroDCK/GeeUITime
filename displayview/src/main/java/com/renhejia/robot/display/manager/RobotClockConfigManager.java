package com.renhejia.robot.display.manager;

import android.content.Context;


/**
 * Launcher 偏好设置管理器
 */
public class RobotClockConfigManager implements ClockConfigConst {

    private static RobotClockConfigManager mLauncherConfigManager;
    private RobotSharedPreference mKidSharedPreference;
    private Context mContext;


    private RobotClockConfigManager(Context context) {
        this.mContext = context;
        this.mKidSharedPreference = new RobotSharedPreference(context,
                RobotSharedPreference.SHARE_PREFERENCE_NAME, RobotSharedPreference.ACTION_INTENT_CONFIG_CHANGE);
    }

    /**
     * 增加偏好设置初始化逻辑
     * (暂时没有用，预留给将来手表需要初始化一些状态值时使用)
     */
    private void initKidSmartConfigState() {

    }

    public static RobotClockConfigManager getInstance(Context context) {
        if (mLauncherConfigManager == null) {
            mLauncherConfigManager = new RobotClockConfigManager(context);
            mLauncherConfigManager.initKidSmartConfigState();
            mLauncherConfigManager.commit();
        }
        return mLauncherConfigManager;

    }

    public boolean commit() {
        return mKidSharedPreference.commit();
    }

    /**
     * @return
     */
    public String getLTPClockSkinPath() {
        String skinPath = mKidSharedPreference.getString(KEY_LTP_CLOCK_SKIN_PATH, "");
        return skinPath;
    }

    /**
     *
     */
    public void setLTPClockSkinPath(String skinPath) {
        mKidSharedPreference.putString(KEY_LTP_CLOCK_SKIN_PATH, skinPath);
    }

    /**
     * 获取手表高度
     *
     * @return
     */
    public int getWatchHeight() {
        return mKidSharedPreference.getInt(KEY_WATCH_HEIGHT, VALUE_WATCH_HEIGHT_DEFAULT);
    }

    /**
     * 设置手表高度
     *
     * @return
     */
    public void setWatchHeight(int height) {
        mKidSharedPreference.putInt(KEY_WATCH_HEIGHT, height);
    }

    /**
     * 获取手表宽度
     *
     * @return
     */
    public int getWatchWidth() {
        return mKidSharedPreference.getInt(KEY_WATCH_WIDTH, VALUE_WATCH_WIDTH_DEFAULT);
    }

    /**
     * 设置手表宽度
     *
     * @return
     */
    public void setWatchWidth(int width) {
        mKidSharedPreference.putInt(KEY_WATCH_WIDTH, width);
    }

    /**
     * 是否显示日期
     *
     * @return
     */
    public boolean IsShowDate() {
        return (mKidSharedPreference.getInt(KEY_IS_SHOW_DATE, VALUE_IS_SHOW_OPENED) == 1);
    }

    /**
     * 设置是否显示日期
     *
     * @param isShowDate
     */
    public void setShowDate(int isShowDate) {
        mKidSharedPreference.putInt(KEY_IS_SHOW_DATE, isShowDate);
    }

    /**
     * 是否显示天气
     *
     * @return
     */
    public boolean IsShowWeather() {
        return (mKidSharedPreference.getInt(KEY_IS_SHOW_WEATHER, VALUE_IS_SHOW_OPENED) == 1);
    }

    /**
     * 设置是否显示天气
     */
    public void setShowWeather(int isShow) {
        mKidSharedPreference.putInt(KEY_IS_SHOW_WEATHER, isShow);
    }


    public boolean IsShowRandomBg() {
        return (mKidSharedPreference.getInt(KEY_IS_RANDOM_CHANGE, VALUE_IS_SHOW_OPENED) == 1);
    }

    public void setShowRandomBg(int isShow) {
        mKidSharedPreference.putInt(KEY_IS_RANDOM_CHANGE, isShow);
    }

    public boolean IsShowCustomBg() {
        return (mKidSharedPreference.getInt(KEY_IS_SHOW_CUSTOM, VALUE_IS_SHOW_OPENED) == 1);
    }

    public void setShowCustomBg(int isShow) {
        mKidSharedPreference.putInt(KEY_IS_SHOW_CUSTOM, isShow);
    }

    /**
     * @return
     */
    public String getCustomBgUrl() {
        String skinPath = mKidSharedPreference.getString(KEY_CUSTOM_SKIN_URL, "");
        return skinPath;
    }

    /**
     * @param skinUrl
     */
    public void setCustomBgUrl(String skinUrl) {
        mKidSharedPreference.putString(KEY_CUSTOM_SKIN_URL, skinUrl);
    }


    public String getCustomSkinName() {
        String skinPath = mKidSharedPreference.getString(KEY_CUSTOM_SKIN_NAME, "");
        return skinPath;
    }

    public void setCustomSkinName(String skinName) {
        mKidSharedPreference.putString(KEY_CUSTOM_SKIN_NAME, skinName);
    }

    public String getTimeZone() {
        String skinPath = mKidSharedPreference.getString(KEY_TIME_ZONE, "");
        return skinPath;
    }

    public void setTimeZone(String timeZone) {
        mKidSharedPreference.putString(KEY_TIME_ZONE, timeZone);
    }

    public String getTempMode() {
        String tempMode = mKidSharedPreference.getString(KEY_TEMP_MODE, KEY_TEMP_MODE_CEL);
        return tempMode;
    }

    public void setTempMode(String tempMode) {
        mKidSharedPreference.putString(KEY_TEMP_MODE, tempMode);
    }


}
