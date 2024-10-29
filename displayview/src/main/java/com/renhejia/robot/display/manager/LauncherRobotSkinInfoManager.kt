package com.renhejia.robot.display.manager

import android.content.Context
import android.provider.Settings
import android.text.TextUtils
import com.renhejia.robot.display.R
import com.renhejia.robot.display.RobotPlatformState
import com.renhejia.robot.display.RobotSkinInfoItem
import com.renhejia.robot.display.callback.RobotPlatformInfoUpdateCallback

/**
 * 表盘信息更新管理器
 */
class LauncherRobotSkinInfoManager //        getStepCount();
//        getWeatherStatus();
private constructor(private val mContext: Context) {
    private var batteryLevel = 0 //电池电量
    private var batteryLevelTemp = 0 //电池电量Temp
    private val usbStatus = 0 //USB状态
    private val usbStatusTemp = 0 //USB状态Temp
    private var volume = 0 //音量
    private var volumeTemp = 0 //音量Temp
    private var stepCount = 0 //记步
    private var stepCountTemp = -1 //记步Temp
    private var wifiStates = false //wifi状态
    private var wifiStatesTemp = false //wifi状态Temp
    private var chargingStatus = false //充电状态
    private var chargeinStatesTemp = false //充电状态Temp
    private var isBluetoothOn = false
    private var isBluetoothStatusTemp = false
    private var weatherState = -1
    private var weatherStateTemp = 0
    private var currentTemp = 0
    private var currentTempTemp = 0
    private var airQuality = 0
    private var airQualityTemp = 0


    /**
     * 更新电量等级
     *
     * @param batteryLevel
     */
    fun setBatteryLevel(batteryLevel: Int) {
        this.batteryLevel = batteryLevel
        if (batteryLevel != batteryLevelTemp) {
            batteryLevelTemp = batteryLevel
            RobotPlatformInfoUpdateCallback.instance.updateBatteryLevel(batteryLevel)
        }
    }


    fun getBatteryLevel(): Int {
//        batteryLevel = LauncherInfoManager.getInstance(mContext).getBatteryLevel();
        return batteryLevel
    }

    /**
     * 更新音量
     *
     * @param volume
     */
    fun setVolume(volume: Int) {
        this.volume = volume
        if (volume != volumeTemp) {
            volumeTemp = volume
            RobotPlatformInfoUpdateCallback.instance.updateMediaVolume(volume)
        }
    }

    fun getVolume(): Int {
//        volume = QSettings.getVolumeLevelRing(mContext);
        //TODO 增加获取系统音量的方法
        return 1
        //        return volume;
    }

    //TODO 需要确认连接状态

    var bluetoothStatus: Boolean
        /**
         * 获取蓝牙状态
         *
         * @param
         */
        get() = isBluetoothOn
        /**
         * 更新蓝牙状态
         *
         * @param bluetoothConnected
         */
        set(bluetoothConnected) {
            isBluetoothOn = bluetoothConnected
            if (isBluetoothOn != isBluetoothStatusTemp) {
                isBluetoothStatusTemp = bluetoothConnected
                RobotPlatformInfoUpdateCallback.instance
                    .updateBluetoothEnabled(bluetoothConnected)
            }
        }

    /**
     * 更新wifi状态
     *
     * @param wifiStates
     */
    fun setWifiStates(wifiStates: Boolean) {
        this.wifiStates = wifiStates
        if (wifiStates != wifiStatesTemp) {
            wifiStatesTemp = wifiStates
            RobotPlatformInfoUpdateCallback.instance.updateWifiEnabled(wifiStates)
        }
    }

    /**
     * 获取wifi状态
     *
     * @return
     */
    fun getWifiStates(): Boolean {
//        wifiStates = LauncherInfoManager.getInstance(mContext).getWifiStates();
        return wifiStates
    }

    fun setWeatherState(weatherState: Int) {
        this.weatherState = weatherState
    }

    //TODO 记步变化时，不会发通知
    /**
     * @param stepCount
     */
    fun setStepCount(stepCount: Int) {
        this.stepCount = stepCount
        if (stepCount != stepCountTemp) {
            stepCountTemp = stepCount
            RobotPlatformInfoUpdateCallback.instance.updateStepNumber(stepCount)
        }
    }

    /**
     * 获取步数
     *
     * @return
     */
    fun getStepCount(): Int {
        stepCount = stepsData
        return stepCount
    }

    private val stepsData: Int
        /**
         * 获取计步信息
         * @return
         */
        get() {
            val KID_TODAY_STEPS = "kid_today_steps"
            val mCurrentSteps = Settings.Global.getInt(
                mContext.contentResolver,
                KID_TODAY_STEPS,
                0
            )
            return mCurrentSteps
        }


    /**
     * 设置充电状态变化
     *
     * @param chargingStates 充电状态
     */
    fun setChargingStates(chargingStates: Boolean) {
        this.chargingStatus = chargingStates
        if (this.chargingStatus != chargeinStatesTemp) {
            chargeinStatesTemp = chargingStates
            RobotPlatformInfoUpdateCallback.instance
                .updateBatteryCharging(chargingStates)
        }
    }

    /**
     * 更新天气信息
     *
     * @param weatherState 天气状态码
     * @param currentTemp  当前天气
     * @param airQuality   当前天气质量
     */
    fun updateWeather(weatherState: Int, currentTemp: Int, airQuality: Int) {
        this.weatherState = weatherState
        this.currentTemp = currentTemp
        this.airQuality = airQuality
        if ((weatherState != weatherStateTemp) || (currentTemp != currentTempTemp) || (airQuality != airQualityTemp)) {
            weatherStateTemp = weatherState
            currentTempTemp = currentTemp
            airQualityTemp = airQuality

            RobotPlatformInfoUpdateCallback.instance
                .updateWeather(weatherState, currentTemp, airQuality)
        }
    }

    /**
     * 更新天气信息
     *
     * @param weatherState 天气状态码
     * @param currentTemp  当前天气
     * @param airQuality   当前天气质量
     */
    fun updateWeather(weatherState: Int, currentTemp: String, airQuality: Int) {
        val temp =
            currentTemp.replace(mContext.resources.getString(R.string.temperature_degrees), "")
        if (!TextUtils.isEmpty(temp)) {
            val temprature = temp.toInt()
            updateWeather(weatherState, temprature, airQuality)
        }
    }

    private val weatherStatus: Unit
        get() {
//        weatherState = LauncherWeatherInfoManager.getInstance(mContext).getRealtimeWeather();
//        String temp = LauncherWeatherInfoManager.getInstance(mContext).getCurrentTemperatureInt();
            val temp: String? = null
            currentTemp = if (!TextUtils.isEmpty(temp)) {
                temp!!.toInt()
            } else {
                -99
            }


            //        airQuality = LauncherWeatherInfoManager.getInstance(mContext).getPm25Int();
        }
    val spineSkinInfoItem: RobotSkinInfoItem
        /**
         * 获取皮肤信息结构体
         *
         * @return
         */
        get() {
            updateData()
            val spineSkinInfoItem = RobotSkinInfoItem()
            val robotPlatformState: RobotPlatformState = RobotPlatformState.getInstance(mContext)
            //        WeatherInfoItem weatherInfoItem = new WeatherInfoItem();
//        stepCount = (int) WolfPlatformManager.getInstance(mContext).getStepsData(mContext, 0, 0);
            spineSkinInfoItem.setBatteryLevel(batteryLevel)
            //        LogUtils.logi("Mars111333","batteryLevel: " + batteryLevel);
            spineSkinInfoItem.setBluetoothStatus(isBluetoothOn)
            //        LogUtils.logi("Mars111333","isBluetoothOn: " + isBluetoothOn);
            spineSkinInfoItem.setChargingStates(chargingStatus)
            //        LogUtils.logi("Mars111333","chargingStates: " + chargingStates);
            spineSkinInfoItem.setVolume(volume)
            //        LogUtils.logi("Mars111333","volume: " + volume);
            spineSkinInfoItem.setWifiStates(wifiStates)
            //        LogUtils.logi("Mars111333","wifiStates: " + wifiStates);
            spineSkinInfoItem.setStepCount(stepCount)

            //        LogUtils.logi("Mars111333","stepCount: " + stepCount);
//        weatherInfoItem.setAirQuality(airQuality);
//        weatherInfoItem.setCurrentTemp(currentTemp);
//        weatherInfoItem.setWeatherState(weatherState);
//        spineSkinInfoItem.setWeatherInfoItem(weatherInfoItem);
            return spineSkinInfoItem
        }

    private fun updateData() {
        getBatteryLevel()
        bluetoothStatus
        getVolume()
        getWifiStates()
        chargingStatus
        getStepCount()
        weatherStatus
    }

    companion object {
        private var instance: LauncherRobotSkinInfoManager? = null

        fun getInstance(context: Context): LauncherRobotSkinInfoManager {
            if (instance == null) {
                instance = LauncherRobotSkinInfoManager(context.applicationContext)
            }
            return instance!!
        }
    }
}
