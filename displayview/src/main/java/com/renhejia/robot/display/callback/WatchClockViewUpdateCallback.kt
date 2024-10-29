package com.renhejia.robot.display.callback

/**
 * 更新手表表盘状态回调
 */
class WatchClockViewUpdateCallback private constructor() {
    private var mWatchClockViewUpdateListener: WatchClockViewUpdateListener? = null

    private object MessageListUpdateCallbackHolder {
        val instance: WatchClockViewUpdateCallback = WatchClockViewUpdateCallback()
    }

    interface WatchClockViewUpdateListener {
        fun onWatchClockViewUpdate(updateModel: Int, status: Int)
    }

    fun setMessageListUpdateListener(listener: WatchClockViewUpdateListener?) {
        this.mWatchClockViewUpdateListener = listener
    }

    fun setWatchClockViewUpdate(updateModel: Int, status: Int) {
        if (mWatchClockViewUpdateListener != null) {
            mWatchClockViewUpdateListener!!.onWatchClockViewUpdate(updateModel, status)
        }
    }


    companion object {
        val instance: WatchClockViewUpdateCallback
            get() = MessageListUpdateCallbackHolder.instance
    }
}
