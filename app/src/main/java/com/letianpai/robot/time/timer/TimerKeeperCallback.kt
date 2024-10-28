package com.letianpai.robot.time.timer

/**
 * Created by liujunbin
 */
class TimerKeeperCallback private constructor() {
    private val mTimerKeeperUpdateListener: MutableList<TimerKeeperUpdateListener?>? =
        ArrayList()

    private object TimerKeeperUpdateCallBackHolder {
        val instance: TimerKeeperCallback = TimerKeeperCallback()
    }

    fun interface TimerKeeperUpdateListener {
        fun onTimerKeeperUpdateReceived(hour: Int, minute: Int)
    }

    fun registerTimerKeeperUpdateListener(listener: TimerKeeperUpdateListener?) {
        mTimerKeeperUpdateListener?.add(listener)
    }

    fun unregisterTimerKeeperUpdateListener(listener: TimerKeeperUpdateListener?) {
        mTimerKeeperUpdateListener?.remove(listener)
    }


    fun setTimerKeeper(hour: Int, minute: Int) {
        for (i in mTimerKeeperUpdateListener!!.indices) {
            if (mTimerKeeperUpdateListener[i] != null) {
                mTimerKeeperUpdateListener[i]!!.onTimerKeeperUpdateReceived(hour, minute)
            }
        }
    }

    companion object {
        val instance: TimerKeeperCallback
            get() = TimerKeeperUpdateCallBackHolder.instance
    }
}
