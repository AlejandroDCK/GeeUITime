package com.renhejia.robot.display.callback

/**
 * 动画表盘更新回调
 */
class RobotClockViewUpdateCallback private constructor() {
    private var mWatchSpineClockViewUpdateListener: WatchSpineClockViewUpdateListener? = null

    private object WatchSpineClockViewUpdateCallbackkHolder {
        val instance: RobotClockViewUpdateCallback = RobotClockViewUpdateCallback()
    }

    interface WatchSpineClockViewUpdateListener {
        fun onWatchSpineClockViewUpdate(isSpine: Boolean, skinPath: String?)
    }

    fun setWatchSpineClockViewUpdateListener(listener: WatchSpineClockViewUpdateListener?) {
        this.mWatchSpineClockViewUpdateListener = listener
    }

    fun setWatchSpineClockViewUpdate(isSpine: Boolean, skinPath: String?) {
        if (mWatchSpineClockViewUpdateListener != null) {
            mWatchSpineClockViewUpdateListener!!.onWatchSpineClockViewUpdate(isSpine, skinPath)
        }
    }


    companion object {
        val instance: RobotClockViewUpdateCallback
            get() = WatchSpineClockViewUpdateCallbackkHolder.instance
    }
}
