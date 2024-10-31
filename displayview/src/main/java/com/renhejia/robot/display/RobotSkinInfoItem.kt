package com.renhejia.robot.display

/**
 * 机器人 信息结构体
 * @author liujunbin
 */
class RobotSkinInfoItem {
    private var batteryLevel: Int = 0 //电池电量
    private var volume: Int = 0 //音量
    private var stepCount: Int = 0 //记步
    private var wifiStates: Boolean = false //wifi状态
    private var chargingStates: Boolean = false //充电状态
    private var bluetoothStatus: Boolean = false //蓝牙状态

    //    private WeatherInfoItem weatherInfoItem;    //天气信息
    fun getStepCount(): Int {
        return stepCount
    }

    fun setStepCount(stepCount: Int) {
        this.stepCount = stepCount
    }

    fun getVolume(): Int {
        return volume
    }

    fun setVolume(volume: Int) {
        this.volume = volume
    }

    fun getBatteryLevel(): Int {
        return batteryLevel
    }

    fun setBatteryLevel(batteryLevel: Int) {
        this.batteryLevel = batteryLevel
    }

    //    public WeatherInfoItem getWeatherInfoItem() {
    //        return weatherInfoItem;
    //    }
    //
    //    public void setWeatherInfoItem(WeatherInfoItem weatherInfoItem) {
    //        this.weatherInfoItem = weatherInfoItem;
    //    }
    fun getWifiStatus(): Boolean {
        return wifiStates
    }

    fun setWifiStates(wifiStates: Boolean) {
        this.wifiStates = wifiStates
    }

    fun setBluetoothStatus(bluetoothStatus: Boolean) {
        this.bluetoothStatus = bluetoothStatus
    }

    fun getBluetoothStatus(): Boolean {
        return bluetoothStatus
    }

    fun setChargingStates(chargingStates: Boolean) {
        this.chargingStates = chargingStates
    }

    fun getChargingStates(): Boolean {
        return chargingStates
    }
}
