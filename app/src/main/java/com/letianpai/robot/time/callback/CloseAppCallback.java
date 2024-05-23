package com.letianpai.robot.time.callback;

/**
 *
 * @author liujunbin
 */
public class CloseAppCallback {

    private CloseAppCmdListener mCloseAppCmdListener;

    private static class CloseAppCmdCallbackHolder {
        private static CloseAppCallback instance = new CloseAppCallback();
    }

    private CloseAppCallback() {

    }

    public static CloseAppCallback getInstance() {
        return CloseAppCmdCallbackHolder.instance;
    }

    public interface CloseAppCmdListener {
        void onCloseAppCmdReceived();
    }

    public void setCloseAppCmdReceivedListener(CloseAppCmdListener listener) {
        this.mCloseAppCmdListener = listener;
    }

    public void setCloseAppCmd() {
        if (mCloseAppCmdListener != null) {
            mCloseAppCmdListener.onCloseAppCmdReceived();
        }
    }
//    public void setCloseAppCmd(String commandType, Object commandData) {
//        if (mCloseAppCmdListener != null) {
//            mCloseAppCmdListener.onCloseAppCmdReceived(commandType, commandData);
//        }
//    }
//    public interface CloseAppCmdListener {
//        void onCloseAppCmdReceived(String commandType, Object commandData);
//    }


}
