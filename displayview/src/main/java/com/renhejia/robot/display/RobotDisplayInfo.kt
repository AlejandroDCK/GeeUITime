package com.renhejia.robot.display

import com.renhejia.robot.display.parser.CalendarInfo
import com.renhejia.robot.display.parser.CountdownInfo
import com.renhejia.robot.display.parser.FanInfo
import com.renhejia.robot.display.parser.WeatherInfoItem

/**
 * 机器人 信息结构体
 * @author liujunbin
 */
class RobotDisplayInfo {
    var batteryLevel: Int = 0 //电池电量
    var volume: Int = 0 //音量

    //    public WeatherInfoItem getWeatherInfoItem() {
    //        return weatherInfoItem;
    //    }
    //
    //    public void setWeatherInfoItem(WeatherInfoItem weatherInfoItem) {
    //        this.weatherInfoItem = weatherInfoItem;
    //    }
    var isWifiStates: Boolean = false //wifi状态
    var isChargingStates: Boolean = false //充电状态
    var isBluetoothStatus: Boolean = false //蓝牙状态
    var weatherInfoItem: WeatherInfoItem? = null //天气信息
    var countdownInfos: ArrayList<CountdownInfo>? = null //事件倒计时
    var calendarInfos: ArrayList<CalendarInfo>? = null //日历事件
    var fanInfo: FanInfo? = null //粉丝信息


    fun getBluetoothStatus(): Boolean {
        return isBluetoothStatus
    }

    fun getChargingStates(): Boolean {
        return isChargingStates
    }
}
