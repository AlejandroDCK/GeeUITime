package com.renhejia.robot.display.manager

import android.content.Context
import android.text.TextUtils
import com.renhejia.robot.display.R
import com.renhejia.robot.display.RobotSkinInfoItem
import com.renhejia.robot.display.callback.RobotPlatformInfoUpdateCallback
import com.renhejia.robot.display.parser.CalendarInfo
import com.renhejia.robot.display.parser.CountdownInfo
import com.renhejia.robot.display.parser.FanInfo
import com.renhejia.robot.display.parser.WeatherInfoItem

/**
 * 表盘信息更新管理器
 */
class RobotDisplayInfoManager //        getStepCount();
//        getWeatherStatus();
private constructor(private val mContext: Context) {
    private var batteryLevel: Int = 0 //电池电量
    private var batteryLevelTemp: Int = 0 //电池电量Temp
    private val usbStatus: Int = 0 //USB状态
    private val usbStatusTemp: Int = 0 //USB状态Temp
    private var volume: Int = 0 //音量
    private var volumeTemp: Int = 0 //音量Temp
    private var wifiStates: Boolean = false //wifi状态
    private var wifiStatesTemp: Boolean = false //wifi状态Temp
    private var chargingStatus: Boolean = false //充电状态
    private var chargeinStatesTemp: Boolean = false //充电状态Temp
    private var isBluetoothOn: Boolean = false
    private var isBluetoothStatusTemp: Boolean = false
    private var weatherState: Int = -1
    private var weatherStateTemp: Int = 0
    private var currentTemp: Int = 0
    private var currentTempTemp: Int = 0
    private var airQuality: Int = 0
    private var airQualityTemp: Int = 0

    private var weatherInfoItem: WeatherInfoItem? = null //天气信息
    private var weatherInfoItemTemp: WeatherInfoItem? = null //天气信息
    private val countdownInfo: ArrayList<CountdownInfo>? = null //事件倒计时
    private val countdownInfoTemp: ArrayList<CountdownInfo>? = null //事件倒计时
    private val calendarInfos: ArrayList<CalendarInfo>? = null //日历事件
    private val calendarInfoTemp: ArrayList<CalendarInfo>? = null //日历事件
    private val fanInfo: FanInfo? = null //粉丝信息
    private val fanInfoTemp: FanInfo? = null //粉丝信息


    fun setWeatherInfoItem(weatherInfoItem: WeatherInfoItem) {
        this.weatherInfoItem = weatherInfoItem
        if (weatherInfoItem !== weatherInfoItemTemp) {
            weatherInfoItemTemp = weatherInfoItem
            //TODO 增加天气刷新逻辑
            //RobotPlatformInfoUpdateCallback.getInstance().updateBatteryLevel(batteryLevel);
        }
    }

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
        get() {
            return isBluetoothOn
        }
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

    /**TODO No notification is sent when a remembered step changes
    //// * Get pedometer information.
    //     * @return
    //     */
    //    private int getStepsData() {
    //        String KID_TODAY_STEPS = "kid_today_steps";
    //        int mCurrentSteps = Settings.Global.getInt(mContext.getContentResolver(), KID_TODAY_STEPS, 0);
    //        return mCurrentSteps;
    //    }
    //
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
        val temp: String =
            currentTemp.replace(mContext.getResources().getString(R.string.temperature_degrees), "")
        if (!TextUtils.isEmpty(temp)) {
            val temprature: Int = temp.toInt()
            updateWeather(weatherState, temprature, airQuality)
        }
    }

    private val weatherStatus: Unit
        get() {
//        weatherState = LauncherWeatherInfoManager.getInstance(mContext).getRealtimeWeather();
//        String temp = LauncherWeatherInfoManager.getInstance(mContext).getCurrentTemperatureInt();
            val temp: String? = null
            if (!TextUtils.isEmpty(temp)) {
                currentTemp = temp!!.toInt()
            } else {
                currentTemp = -99
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
            val spineSkinInfoItem: RobotSkinInfoItem = RobotSkinInfoItem()
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
        weatherStatus
    }

    companion object {
        private var instance: RobotDisplayInfoManager? = null

        fun getInstance(context: Context): RobotDisplayInfoManager {
            if (instance == null) {
                instance = RobotDisplayInfoManager(context.getApplicationContext())
            }
            return instance!!
        }
    }
}
