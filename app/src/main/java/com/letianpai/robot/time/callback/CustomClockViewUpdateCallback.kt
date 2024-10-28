package com.letianpai.robot.time.callback

import com.letianpai.robot.time.parser.custom.CustomWatchConfig

/**
 * @author liujunbin
 */
class CustomClockViewUpdateCallback private constructor() {
    private var mCustomClockViewUpdateListener: CustomClockViewUpdateListener? = null

    private object GeneralInfoCallbackHolder {
        val instance = CustomClockViewUpdateCallback()
    }

    fun interface CustomClockViewUpdateListener {
        fun onCustomClockViewChanged(customWatchConfig: CustomWatchConfig)
    }

    fun setCustomClockViewUpdateListener(listener: CustomClockViewUpdateListener?) {
        this.mCustomClockViewUpdateListener = listener
    }

    fun setCustomClockInfo(customWatchConfig: CustomWatchConfig) {
        if (mCustomClockViewUpdateListener != null) {
            mCustomClockViewUpdateListener!!.onCustomClockViewChanged(customWatchConfig)
        }
    }


    companion object {
        val instance: CustomClockViewUpdateCallback
            get() = GeneralInfoCallbackHolder.instance
    }
}
