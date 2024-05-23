package com.letianpai.robot.time.callback;

import com.letianpai.robot.time.parser.general.GeneralInfo;

/**
 * @author liujunbin
 */
public class GeneralInfoCallback {

    private GeneralInfoUpdateListener mGeneralInfoUpdateListener;

    private static class GeneralInfoCallbackHolder {
        private static GeneralInfoCallback instance = new GeneralInfoCallback();
    }

    private GeneralInfoCallback() {

    }

    public static GeneralInfoCallback getInstance() {
        return GeneralInfoCallbackHolder.instance;
    }

    public interface GeneralInfoUpdateListener {
        void onAtCmdResultReturn(GeneralInfo generalInfo);

    }

    public void setGeneralInfoUpdateListener(GeneralInfoUpdateListener listener) {
        this.mGeneralInfoUpdateListener = listener;
    }

    public void setGeneralInfo(GeneralInfo generalInfo) {
        if (mGeneralInfoUpdateListener != null) {
            mGeneralInfoUpdateListener.onAtCmdResultReturn(generalInfo);
        }
    }


}
