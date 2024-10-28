package com.letianpai.robot.time.callback

import com.letianpai.robot.time.parser.general.GeneralInfo

/**
 * @author liujunbin
 */
class GeneralInfoCallback private constructor() {
    private var mGeneralInfoUpdateListener: GeneralInfoUpdateListener? = null

    private object GeneralInfoCallbackHolder {
        val instance = GeneralInfoCallback()
    }

    fun interface GeneralInfoUpdateListener {
        fun onAtCmdResultReturn(generalInfo: GeneralInfo)
    }

    fun setGeneralInfoUpdateListener(listener: GeneralInfoUpdateListener?) {
        this.mGeneralInfoUpdateListener = listener
    }

    fun setGeneralInfo(generalInfo: GeneralInfo) {
        if (mGeneralInfoUpdateListener != null) {
            mGeneralInfoUpdateListener!!.onAtCmdResultReturn(generalInfo)
        }
    }


    companion object {
        val instance: GeneralInfoCallback
            get() = GeneralInfoCallbackHolder.instance
    }
}
