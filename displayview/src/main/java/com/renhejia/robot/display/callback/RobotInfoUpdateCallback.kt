//package com.renhejia.robot.display.callback
//
//import com.renhejia.robot.commandlib.parser.displaymodes.calendar.CalenderInfo
//import com.renhejia.robot.commandlib.parser.displaymodes.countdown.CountDownListInfo
//import com.renhejia.robot.commandlib.parser.displaymodes.fans.FansInfo
//import com.renhejia.robot.commandlib.parser.displaymodes.general.GeneralInfo
//import com.renhejia.robot.commandlib.parser.displaymodes.weather.WeatherInfo
//
//
///**
// * Created by liujunbin
// */
//class RobotInfoUpdateCallback private constructor() {
//    private var mRobotInfoListener: RobotInfoListener? = null
//
//    private object RobotInfoUpdateCallbackHolder {
//        val instance: RobotInfoUpdateCallback = RobotInfoUpdateCallback()
//    }
//
//    interface RobotInfoListener {
//        fun updateGeneralInfo(generalInfo: GeneralInfo?)
//        fun updateWeather(weatherInfo: WeatherInfo?)
//        fun updateCountDown(countDownListInfo: CountDownListInfo?)
//        fun updateNotice(calenderInfo: CalenderInfo?)
//        fun updateFansInfo(fansInfo: FansInfo?)
//    }
//
//    fun setRobotInfoListener(listener: RobotInfoListener?) {
//        this.mRobotInfoListener = listener
//    }
//
//    fun updateGeneralInfo(generalInfo: GeneralInfo?) {
//        if (mRobotInfoListener != null) {
//            mRobotInfoListener!!.updateGeneralInfo(generalInfo)
//        }
//    }
//
//    fun updateWeather(weatherInfo: WeatherInfo?) {
//        if (mRobotInfoListener != null) {
//            mRobotInfoListener!!.updateWeather(weatherInfo)
//        }
//    }
//
//    fun updateCountDown(countDownListInfo: CountDownListInfo?) {
//        if (mRobotInfoListener != null) {
//            mRobotInfoListener!!.updateCountDown(countDownListInfo)
//        }
//    }
//
//    fun updateNotice(calenderInfo: CalenderInfo?) {
//        if (mRobotInfoListener != null) {
//            mRobotInfoListener!!.updateNotice(calenderInfo)
//        }
//    }
//
//    fun updateFansInfo(fansInfo: FansInfo?) {
//        if (mRobotInfoListener != null) {
//            mRobotInfoListener!!.updateFansInfo(fansInfo)
//        }
//    }
//
//
//    companion object {
//        val instance: RobotInfoUpdateCallback
//            get() = RobotInfoUpdateCallbackHolder.instance
//    }
//}
//