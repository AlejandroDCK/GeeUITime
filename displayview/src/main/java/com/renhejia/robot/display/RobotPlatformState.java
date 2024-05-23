package com.renhejia.robot.display;

import android.content.Context;

import com.renhejia.robot.display.manager.ClockConfigConst;
import com.renhejia.robot.display.manager.RobotClockConfigManager;

public class RobotPlatformState {
    private Context mContext;
    public static int NO_TEMP = -99; // 无温度信息
    public static int NO_WEATHER = -99; // 无温度信息

    public int batteryLevel;  // 电池电量

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public int getMediaVolume() {
        return mediaVolume;
    }

    public void setMediaVolume(int mediaVolume) {
        this.mediaVolume = mediaVolume;
    }

    public boolean isBluetoothEnabled() {
        return bluetoothEnabled;
    }

    public void setBluetoothEnabled(boolean bluetoothEnabled) {
        this.bluetoothEnabled = bluetoothEnabled;
    }

    public boolean isWifiEnabled() {
        return wifiEnabled;
    }

    public void setWifiEnabled(boolean wifiEnabled) {
        this.wifiEnabled = wifiEnabled;
    }

    public boolean isBatteryCharging() {
        return batteryCharging;
    }

    public void setBatteryCharging(boolean batteryCharging) {
        this.batteryCharging = batteryCharging;
    }

    public int getWeatherState() {
        return weatherState;
    }

    public void setWeatherState(int weatherState) {
        this.weatherState = weatherState;
    }

    public String getWeatherStateStr() {
        return weatherStateStr;
    }

    public void setWeatherStateStr(String weatherStateStr) {
        this.weatherStateStr = weatherStateStr;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public String getCurrentTempString() {
        if (RobotClockConfigManager.getInstance(mContext).getTempMode().equals(ClockConfigConst.KEY_TEMP_MODE_CEL)){
            return currentTemp + "°C";

        }else{
            return currentTemp + "°F";
        }

    }

    public void setCurrentTemp(int currentTemp) {
        this.currentTemp = currentTemp;
    }

    public String getTempRange() {
        return tempRange;
    }

    public void setTempRange(String tempRange) {
        this.tempRange = tempRange;
    }

    public String getTempDes() {
        if (RobotClockConfigManager.getInstance(mContext).getTempMode().equals(ClockConfigConst.KEY_TEMP_MODE_CEL)){
            return tempDes = weatherStateStr + "  " + currentTemp + "°C";

        }else{
            return tempDes = weatherStateStr + "  " + currentTemp + "°F";
        }

    }

    public void setTempDes(String tempDes) {
        this.tempDes = tempDes;
    }

    public int getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(int airQuality) {
        this.airQuality = airQuality;
    }

    private int stepNumber;    // 记步
    private int mediaVolume;   // 媒体音量
    private boolean bluetoothEnabled; // 蓝牙开关
    private boolean wifiEnabled;      // wifi开关
    private boolean batteryCharging;  // 充电状态
    private int weatherState; // 天气情况
    private String weatherStateStr; // 天气情况
    private int currentTemp;  // 实时温度
    private String tempRange;  // 区间
    private String tempDes;
    private int airQuality;   // 空气质量
    private static RobotPlatformState instance;

//    public RobotPlatformState() {
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


    private RobotPlatformState(Context context) {
        this.mContext = context;

    }

    public static RobotPlatformState getInstance(Context context) {
        if (instance == null) {
            instance = new RobotPlatformState(context.getApplicationContext());
        }
        return instance;
    }

}
