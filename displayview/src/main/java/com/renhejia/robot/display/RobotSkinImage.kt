package com.renhejia.robot.display

import android.graphics.Rect


open class RobotSkinImage {
    private var origRect: Rect? = null
    private var dispRect: Rect? = null

    private var origTouchRect: Rect? = null
    private var dispTouchRect: Rect? = null

    private var filePrefix: String? = null
    private var align: Int = 0

    fun getOrigRect(): Rect? {
        return origRect
    }

    fun setOrigRect(origRect: Rect?) {
        this.origRect = origRect
    }

    fun getDispRect(): Rect? {
        return dispRect
    }

    fun setDispRect(dispRect: Rect?) {
        this.dispRect = dispRect
    }

    fun getFilePrefix(): String? {
        return filePrefix
    }

    fun setFilePrefix(filePrefix: String?) {
        this.filePrefix = filePrefix
    }


    fun getOnOffFilename(enabled: Boolean): String {
        if (enabled) {
            return filePrefix + "on.png"
        } else {
            return filePrefix + "off.png"
        }
    }

    fun getNoticeFileName(): String {
        return filePrefix + "notice.png"
    }

    fun getFansIconFileName(): String {
        return filePrefix + "icon.png"
    }

    fun getFansHeadFileName(): String {
        return filePrefix + "head.png"
    }

    fun getWeatherFilename(weatherId: Int): String {
//        Log.e("letianpai_clock_test","letianpai_clock_test: "+ weatherId);
//        String strWeather = "no_info";
        val strWeather: String = weatherId.toString() + ""

        //        Log.e("letianpai_clock_test","strWeather: "+ strWeather);
//
//
//        switch (weatherId) {
//            case WEATHER_TYPE_NO_INFO:
//                strWeather = "no_info";
//                break;
//            case WEATHER_TYPE_SUNNY:
//                strWeather = "sunny";
//                break;
//            case WEATHER_TYPE_CLOUDY:
//                strWeather = "cloudy";
//                break;
//            case WEATHER_TYPE_RAIN:
//                strWeather = "rain";
//                break;
//            case WEATHER_TYPE_SNOW:
//                strWeather = "snow";
//                break;
//            case WEATHER_TYPE_HAZE:
//                strWeather = "haze";
//                break;
//            case WEATHER_TYPE_SAND_DUST:
//                strWeather = "sand_dust";
//                break;
//            case WEATHER_TYPE_WIND:
//                strWeather = "wind";
//                break;
//            case WEATHER_TYPE_THUNDER:
//                strWeather = "thunder";
//                break;
//            case WEATHER_TYPE_HAIL:
//                strWeather = "hail";
//                break;
//            case WEATHER_TYPE_FOG:
//                strWeather = "smog";
//                break;
//            case WEATHER_TYPE_RAIN_HAIL:
//                strWeather = "rain_hail";
//                break;
//            case WEATHER_TYPE_RAIN_SNOW:
//                strWeather = "rain_snow";
//                break;
//            case WEATHER_TYPE_RAIN_THUNDER:
//                strWeather = "rain_thunder";
//                break;
//        }
        return filePrefix + strWeather + ".png"
    }

    fun getVolumeFilename(volume: Int): String {
        return filePrefix + volume + ".png"
    }


    fun getBackgroundFilename(): String {
        if (filePrefix == null) {
            return "background.png"
        } else {
            return filePrefix + "background.png"
        }
    }

    fun getCustomizedBackgroundFilename(): String {
        return "customized_background.png"
    }

    fun getBatteryFilename(batteryLevel: Int): String {
        val level: Int = batteryLevel / 10 * 10
        return filePrefix + level + ".png"
    }


    fun getForeground(): String {
        if (filePrefix == null) {
            return "foreground.png"
        } else {
            return filePrefix + "foreground.png"
        }
    }

    fun getMiddle(): String {
        if (filePrefix == null) {
            return "middle.png"
        } else {
            return filePrefix + "middle.png"
        }
    }

    fun getBatterySquaresFull(): String {
        return "battery_squares_full.png"
    }

    fun getBatterySquaresEmpty(): String {
        return "battery_squares_empty.png"
    }

    fun getStepSquaresFull(): String {
        return "step_squares_full.png"
    }

    fun getStepSquaresEmpty(): String {
        return "step_squares_empty.png"
    }

    fun getBatteryFanFull(): String {
        return "battery_fan_full.png"
    }

    fun getBatteryFanEmpty(): String {
        return "battery_fan_empty.png"
    }


    fun getAlign(): Int {
        return align
    }

    fun setAlign(align: Int) {
        this.align = align
    }

    fun getOrigTouchRect(): Rect? {
        return origTouchRect
    }

    fun setOrigTouchRect(origTouchRect: Rect?) {
        this.origTouchRect = origTouchRect
    }

    fun getDispTouchRect(): Rect? {
        return dispTouchRect
    }

    fun setDispTouchRect(dispTouchRect: Rect?) {
        this.dispTouchRect = dispTouchRect
    }
}
