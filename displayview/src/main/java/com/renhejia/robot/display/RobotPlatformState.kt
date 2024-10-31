package com.renhejia.robot.display

import android.content.Context
import com.renhejia.robot.display.manager.ClockConfigConst
import com.renhejia.robot.display.manager.RobotClockConfigManager

class RobotPlatformState //    public RobotPlatformState() {
//        batteryLevel = 80;
//        stepNumber = 12345;
//        mediaVolume = 3;
//        bluetoothEnabled = false;
//        wifiEnabled = true;
//        batteryCharging = true;
//        weatherState = 1;
//        weatherStateStr ="晴";
//        tempRange ="32° /10°";
////        currentTemp = NO_TEMP;
//        currentTemp = 26;
//        airQuality = 256;
////        tempDes = "晴 26"+ "°";
//
//    }
private constructor(private val mContext: Context) {
    var batteryLevel: Int = 0 // 电池电量

    @JvmName("getBatteryLevel1")
    fun getBatteryLevel(): Int {
        return batteryLevel
    }

    @JvmName("setBatteryLevel1")
    fun setBatteryLevel(batteryLevel: Int) {
        this.batteryLevel = batteryLevel
    }

    fun getStepNumber(): Int {
        return stepNumber
    }

    fun setStepNumber(stepNumber: Int) {
        this.stepNumber = stepNumber
    }

    fun getMediaVolume(): Int {
        return mediaVolume
    }

    fun setMediaVolume(mediaVolume: Int) {
        this.mediaVolume = mediaVolume
    }

    fun isBluetoothEnabled(): Boolean {
        return bluetoothEnabled
    }

    fun setBluetoothEnabled(bluetoothEnabled: Boolean) {
        this.bluetoothEnabled = bluetoothEnabled
    }

    fun isWifiEnabled(): Boolean {
        return wifiEnabled
    }

    fun setWifiEnabled(wifiEnabled: Boolean) {
        this.wifiEnabled = wifiEnabled
    }

    fun isBatteryCharging(): Boolean {
        return batteryCharging
    }

    fun setBatteryCharging(batteryCharging: Boolean) {
        this.batteryCharging = batteryCharging
    }

    fun getWeatherState(): Int {
        return weatherState
    }

    fun setWeatherState(weatherState: Int) {
        this.weatherState = weatherState
    }

    fun getWeatherStateStr(): String? {
        return weatherStateStr
    }

    fun setWeatherStateStr(weatherStateStr: String?) {
        this.weatherStateStr = weatherStateStr
    }

    fun getCurrentTemp(): Int {
        return currentTemp
    }

    fun getCurrentTempString(): String {
        return if (RobotClockConfigManager.getInstance(mContext)!!
                .tempMode == ClockConfigConst.KEY_TEMP_MODE_CEL
        ) {
            "$currentTemp°C"
        } else {
            "$currentTemp°F"
        }
    }

    fun setCurrentTemp(currentTemp: Int) {
        this.currentTemp = currentTemp
    }

    fun getTempRange(): String? {
        return tempRange
    }

    fun setTempRange(tempRange: String?) {
        this.tempRange = tempRange
    }

    fun getTempDes(): String {
        return if (RobotClockConfigManager.getInstance(mContext)!!
                .tempMode == ClockConfigConst.KEY_TEMP_MODE_CEL
        ) {
            ("$weatherStateStr  $currentTemp°C").also { tempDes = it }
        } else {
            ("$weatherStateStr  $currentTemp°F").also { tempDes = it }
        }
    }

    fun setTempDes(tempDes: String?) {
        this.tempDes = tempDes
    }

    fun getAirQuality(): Int {
        return airQuality
    }

    fun setAirQuality(airQuality: Int) {
        this.airQuality = airQuality
    }

    private var stepNumber: Int = 0 // 记步
    private var mediaVolume: Int = 0 // 媒体音量
    private var bluetoothEnabled: Boolean = false // 蓝牙开关
    private var wifiEnabled: Boolean = false // wifi开关
    private var batteryCharging: Boolean = false // 充电状态
    private var weatherState: Int = 0 // 天气情况
    private var weatherStateStr: String? = null // 天气情况
    private var currentTemp: Int = 0 // 实时温度
    private var tempRange: String? = null // 区间
    private var tempDes: String? = null
    private var airQuality: Int = 0 // 空气质量

    companion object {
        var NO_TEMP: Int = -99 // 无温度信息
        var NO_WEATHER: Int = -99 // 无温度信息

        private var instance: RobotPlatformState? = null


        fun getInstance(context: Context): RobotPlatformState {
            if (instance == null) {
                instance = RobotPlatformState(context.getApplicationContext())
            }
            return instance!!
        }
    }
}
