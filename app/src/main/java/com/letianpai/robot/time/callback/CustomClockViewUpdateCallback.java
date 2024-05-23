package com.letianpai.robot.time.callback;

import com.letianpai.robot.time.parser.custom.CustomWatchConfig;

/**
 * @author liujunbin
 */
public class CustomClockViewUpdateCallback {

    private CustomClockViewUpdateListener mCustomClockViewUpdateListener;

    private static class GeneralInfoCallbackHolder {
        private static CustomClockViewUpdateCallback instance = new CustomClockViewUpdateCallback();
    }

    private CustomClockViewUpdateCallback() {

    }

    public static CustomClockViewUpdateCallback getInstance() {
        return GeneralInfoCallbackHolder.instance;
    }

    public interface CustomClockViewUpdateListener {
        void onCustomClockViewChanged(CustomWatchConfig customWatchConfig);

    }

    public void setCustomClockViewUpdateListener(CustomClockViewUpdateListener listener) {
        this.mCustomClockViewUpdateListener = listener;
    }

    public void setCustomClockInfo(CustomWatchConfig customWatchConfig) {
        if (mCustomClockViewUpdateListener != null) {
            mCustomClockViewUpdateListener.onCustomClockViewChanged(customWatchConfig);
        }
    }


}
