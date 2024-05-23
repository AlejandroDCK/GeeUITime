package com.letianpai.robot.time.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.letianpai.robot.components.network.nets.GeeUiNetManager;
import com.letianpai.robot.components.network.system.SystemUtil;
import com.letianpai.robot.time.callback.CustomClockViewUpdateCallback;
import com.letianpai.robot.time.callback.GeneralInfoCallback;
import com.letianpai.robot.time.parser.custom.CustomClockInfo;
import com.letianpai.robot.time.parser.general.GeneralInfo;
import com.letianpai.robot.time.storage.manager.LauncherConfigManager;
import com.renhejia.robot.commandlib.consts.RobotRemoteConsts;
import com.renhejia.robot.commandlib.log.LogUtils;
import com.renhejia.robot.display.manager.RobotClockConfigManager;
//import com.renhejia.robot.guidelib.wifi.WIFIConnectionManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author liujunbin
 */
public class GeeUINetResponseManager {

    private static GeeUINetResponseManager instance;
    private Context mContext;
    private Gson gson;
    private GeneralInfo generalInfo;
    private GeneralInfo generalInfoEn;

    private GeeUINetResponseManager(Context context) {
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        gson = new Gson();
    }

    public static GeeUINetResponseManager getInstance(Context context) {
        synchronized (GeeUINetResponseManager.class) {
            if (instance == null) {
                instance = new GeeUINetResponseManager(context.getApplicationContext());
            }
            return instance;
        }

    }

    public void dispatchTask(String cmd, Object data) {
        Log.e("letianpai123456789", "commandData: ======= 2 ");
        if (cmd == null) {
            return;
        }
        Log.e("letianpai123456789", "commandData: ======= 3 ");
        if (cmd.equals(RobotRemoteConsts.COMMAND_TYPE_UPDATE_GENERAL_CONFIG)) {
            updateGeneralInfo();

        }
    }

    /**
     *
     */
    public void getCustomWatchConfig() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (SystemUtil.isInChinese()) {
                    getCustomWatchConfig(true);

                } else {
                    getCustomWatchConfig(false);
                }
            }
        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (SystemUtil.isInChinese()) {
//                    getCloudFileToken(true);
//
//                } else {
//                    getCloudFileToken(false);
//                }
//            }
//        }).start();

    }

    /**
     *
     */
    public void updateGeneralInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (SystemUtil.isInChinese()) {
                    getGeneralInfoList(true);

                } else {
                    getGeneralInfoList(false);
                }
            }
        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (SystemUtil.isInChinese()) {
//                    getCloudFileToken(true);
//
//                } else {
//                    getCloudFileToken(false);
//                }
//            }
//        }).start();

    }


    private void getGeneralInfoList(boolean isChinese) {
        GeeUiNetManager.getGeneralInfoList(mContext, isChinese, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response != null && response.body() != null) {

                    GeneralInfo generalInfo = null;
                    String info = response.body().string();

                    if (info != null) {
                        LogUtils.logi("letianpai_1234567", "generalInfo: " + info);
                        try{
                            generalInfo = new Gson().fromJson(info, GeneralInfo.class);
                            if (generalInfo != null) {
//                            LogUtils.logi("letianpai_1234567", "generalInfo: " + generalInfo.toString());
                                if (generalInfo.getData() != null && generalInfo.getData().getTemTag() != null){
                                    RobotClockConfigManager.getInstance(mContext).setTempMode(generalInfo.getData().getTemTag());
                                    RobotClockConfigManager.getInstance(mContext).commit();
                                }
                                GeneralInfoCallback.getInstance().setGeneralInfo(generalInfo);
                                setGeneralInfo(generalInfo);
                                LauncherConfigManager.getInstance(mContext).setRobotGeneralInfo(info);
                                LauncherConfigManager.getInstance(mContext).commit();

                            } else {
                                Log.e("letianpai_1234", "generalInfo is null: ");
                            }

                        }catch (Exception e){

                        }

                    }
                }
            }
        });
    }

    private void getCustomWatchConfig(boolean isChinese) {
        GeeUiNetManager.getCustomWatchConfig(mContext, isChinese, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response != null && response.body() != null) {

                    String info = response.body().string();
                    if (info != null) {

                        CustomClockInfo customClockInfo = gson.fromJson(info,CustomClockInfo.class);
                        if (customClockInfo != null && customClockInfo.getData() != null){
                            if (!TextUtils.isEmpty(customClockInfo.getData().getCustom_bg_url())){
                                RobotClockConfigManager.getInstance(mContext).setCustomBgUrl(customClockInfo.getData().getCustom_bg_url());
                                RobotClockConfigManager.getInstance(mContext).commit();
                                CustomClockViewUpdateCallback.getInstance().setCustomClockInfo(customClockInfo.getData());
                            }
                            CustomClockViewUpdateCallback.getInstance().setCustomClockInfo(customClockInfo.getData());

                        }
//                        generalInfo = new Gson().fromJson(info, GeneralInfo.class);
//                        if (generalInfo != null) {
//                            LogUtils.logi("letianpai_1234567", "generalInfo: " + generalInfo.toString());
//                            GeneralInfoCallback.getInstance().setGeneralInfo(generalInfo);
//                            setGeneralInfo(generalInfo);
//                            LauncherConfigManager.getInstance(mContext).setRobotGeneralInfo(generalInfo.toString());
//                            LauncherConfigManager.getInstance(mContext).commit();
//                        } else {
//                            Log.e("letianpai_1234", "generalInfo is null: ");
//                        }
                    }
                }
            }
        });
    }

    private void getCloudFileToken(boolean isChinese) {
        GeeUiNetManager.getCloudFileToken(mContext, isChinese, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response != null && response.body() != null) {

//                    Log.e("letianpai_1234", "response: " + response.toString());
//                    Log.e("letianpai_1234", "response11: " + response.body().toString());
//                    GeneralInfo generalInfo = null;
                    String info = response.body().string();

                    if (info != null) {
                        Log.e("letianpai_1234", "info: " + info.toString());
//                        generalInfo = new Gson().fromJson(info, GeneralInfo.class);
//                        if (generalInfo != null) {
//                            LogUtils.logi("letianpai_1234567", "generalInfo: " + generalInfo.toString());
//                            GeneralInfoCallback.getInstance().setGeneralInfo(generalInfo);
//                            setGeneralInfo(generalInfo);
//                            LauncherConfigManager.getInstance(mContext).setRobotGeneralInfo(generalInfo.toString());
//                            LauncherConfigManager.getInstance(mContext).commit();
//                        } else {
//                            Log.e("letianpai_1234", "generalInfo is null: ");
//                        }
                    }
                }
            }
        });
    }


    public void setGeneralInfo(GeneralInfo generalInfo) {
        if (SystemUtil.isInChinese()){
            this.generalInfo = generalInfo;
        }else{
            this.generalInfoEn = generalInfo;
        }

    }


//    public GeneralInfo getGeneralInfo() {
//        if (generalInfo == null) {
//            if (SystemUtil.isInChinese()) {
//                getGeneralInfoList(true);
//            } else {
//                getGeneralInfoList(false);
//            }
//
//        }
//        return generalInfo;
//    }
    public GeneralInfo getGeneralInfo() {
        if (SystemUtil.isInChinese()){
            if (generalInfo == null){
                getGeneralInfoList(true);
            }else{
                return generalInfo;
            }
        }else{
            if (generalInfoEn == null){
                getGeneralInfoList(false);
            }else{
                return generalInfoEn;
            }
        }
        if (SystemUtil.isInChinese()){
            return generalInfo;
        }else{
            return generalInfoEn;
        }

    }


}
