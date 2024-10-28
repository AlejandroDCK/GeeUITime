package com.letianpai.robot.time.callback

/**
 *
 * @author liujunbin
 */
class CloseAppCallback private constructor() {
    private var mCloseAppCmdListener: CloseAppCmdListener? = null

    private object CloseAppCmdCallbackHolder {
        val instance: CloseAppCallback = CloseAppCallback()
    }

    interface CloseAppCmdListener {
        fun onCloseAppCmdReceived()
    }

    fun setCloseAppCmdReceivedListener(listener: CloseAppCmdListener?) {
        this.mCloseAppCmdListener = listener
    }

    fun setCloseAppCmd() {
        if (mCloseAppCmdListener != null) {
            mCloseAppCmdListener!!.onCloseAppCmdReceived()
        }
    } //    public void setCloseAppCmd(String commandType, Object commandData) {
    //        if (mCloseAppCmdListener != null) {
    //            mCloseAppCmdListener.onCloseAppCmdReceived(commandType, commandData);
    //        }
    //    }
    //    public interface CloseAppCmdListener {
    //        void onCloseAppCmdReceived(String commandType, Object commandData);
    //    }


    companion object {
        val instance: CloseAppCallback
            get() = CloseAppCmdCallbackHolder.instance
    }
}
