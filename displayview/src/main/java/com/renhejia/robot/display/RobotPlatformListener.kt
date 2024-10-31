package com.renhejia.robot.display

interface RobotPlatformListener {
    fun updateBatteryLevel(batteryLevel: Int)
    fun updateBatteryCharging(batteryCharging: Boolean)
    fun updateBluetoothEnabled(bluetoothEnabled: Boolean)
    fun updateWifiEnabled(wifiEnabled: Boolean)
    fun updateMediaVolume(mediaVolume: Int)
    fun updateStepNumber(stepNumber: Int)
    fun updateWeather(weatherState: Int, currentTemp: Int, airQuality: Int)
    fun updateWeatherDes(weatherState: Int, weatherStateStr: String?, currentTemp: Int)

    fun updateAll(state: RobotPlatformState?)
}
