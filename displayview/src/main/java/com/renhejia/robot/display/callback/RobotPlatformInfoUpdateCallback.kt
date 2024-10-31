package com.renhejia.robot.display.callback

import android.util.Log


/**
 * 桌面信息更新回调
 * Created by liujunbin on 28/11/2017.
 */
class RobotPlatformInfoUpdateCallback private constructor() {
    private var mSpinePlatformListener: SpinePlatformListener? = null

    private object SpinePlatformInfoUpdateCallbackHolder {
        val instance: RobotPlatformInfoUpdateCallback = RobotPlatformInfoUpdateCallback()
    }

    interface SpinePlatformListener {
        fun updateBluetoothEnabled(isBluetoothEnabled: Boolean)
        fun updateBatteryLevel(batteryLevel: Int)
        fun updateBatteryCharging(isBatteryCharging: Boolean)
        fun updateWifiEnabled(isBatteryCharging: Boolean)
        fun updateMediaVolume(batteryLevel: Int)
        fun updateStepNumber(batteryLevel: Int)
        fun updateWeather(weatherState: Int, currentTemp: Int, airQuality: Int)
        fun updateWeatherDes(weatherState: Int, weatherStateStr: String?, currentTemp: Int)
    }

    fun setSpinePlatformListener(listener: SpinePlatformListener?) {
        this.mSpinePlatformListener = listener
    }

    /**
     * 更新蓝牙状态
     * @param isBluetoothEnabled
     */
    fun updateBluetoothEnabled(isBluetoothEnabled: Boolean) {
        if (mSpinePlatformListener != null) {
            mSpinePlatformListener!!.updateBluetoothEnabled(isBluetoothEnabled)
        }
    }

    /**
     * 更新电池等级
     * @param batteryLevel
     */
    fun updateBatteryLevel(batteryLevel: Int) {
        if (mSpinePlatformListener != null) {
            mSpinePlatformListener!!.updateBatteryLevel(batteryLevel)
        }
    }

    /**
     * 更新电池充电状态
     * @param isBatteryCharging
     */
    fun updateBatteryCharging(isBatteryCharging: Boolean) {
        if (mSpinePlatformListener != null) {
            mSpinePlatformListener!!.updateBatteryCharging(isBatteryCharging)
        }
    }

    /**
     * 更新Wifi 状态
     * @param wifiEnabled
     */
    fun updateWifiEnabled(wifiEnabled: Boolean) {
        if (mSpinePlatformListener != null) {
            mSpinePlatformListener!!.updateWifiEnabled(wifiEnabled)
        }
    }

    /**
     * 更新媒体音量
     * @param batteryLevel
     */
    fun updateMediaVolume(batteryLevel: Int) {
        if (mSpinePlatformListener != null) {
            mSpinePlatformListener!!.updateMediaVolume(batteryLevel)
        }
    }

    /**
     * 更新步数信息
     * @param stepNumber
     */
    fun updateStepNumber(stepNumber: Int) {
        if (mSpinePlatformListener != null) {
            mSpinePlatformListener!!.updateStepNumber(stepNumber)
        }
    }

    /**
     * 更新天气信息
     * @param weatherState  天气状态码
     * @param currentTemp   当前天气
     * @param airQuality    当前天气质量
     */
    fun updateWeather(weatherState: Int, currentTemp: Int, airQuality: Int) {
        if (mSpinePlatformListener != null) {
            mSpinePlatformListener!!.updateWeather(weatherState, currentTemp, airQuality)
        }
    }

    /**
     * 更新天气信息
     * @param weatherState
     * @param currentTemp   当前天气
     * @param airQuality
     */
    /**
     *
     * @param weatherState  天气状态码
     * @param weatherStateStr 当前天气
     * @param currentTemp 当前温度
     */
    fun updateWeatherDes(weatherState: Int, weatherStateStr: String?, currentTemp: Int) {
        Log.e("letianpai", "========= =======updateViews ============ 5 ===========:")
        if (mSpinePlatformListener != null) {
            Log.e("letianpai", "========= =======updateViews ============ 6 ===========:")
            mSpinePlatformListener!!.updateWeatherDes(weatherState, weatherStateStr, currentTemp)
        }
    }


    companion object {
        val instance: RobotPlatformInfoUpdateCallback
            get() = SpinePlatformInfoUpdateCallbackHolder.instance
    }
}
