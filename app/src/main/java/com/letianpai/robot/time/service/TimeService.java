package com.letianpai.robot.time.service;

import android.app.AlarmManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.letianpai.robot.letianpaiservice.LtpLongConnectCallback;
import com.letianpai.robot.time.callback.CloseAppCallback;
import com.letianpai.robot.time.callback.CustomClockViewUpdateCallback;
import com.letianpai.robot.time.net.GeeUINetResponseManager;
import com.letianpai.robot.time.parser.custom.CustomWatchConfig;
import com.letianpai.robot.time.parser.timezone.TimeZone;
import com.letianpai.robot.time.timer.TimerKeeperCallback;
import com.letianpai.robot.time.timer.TimerReceiver;
import com.letianpai.robot.time.ui.activity.MainActivity;
import com.renhejia.robot.display.manager.RobotClockConfigManager;
import com.renhejia.robot.letianpaiservice.ILetianpaiService;

import java.lang.ref.WeakReference;
import java.util.Calendar;


public class TimeService extends Service {
    private Context mContext;
    private TimerReceiver mTimeReceiver;
    private boolean isConnectService = false;
    private ILetianpaiService iLetianpaiService;
    public static final  String COMMAND_UPDATE_DEVICE_TIME_ZONE = "updateDeviceTimeZone";
    public static final  String COMMAND_UPDATE_CUSTOM_WATCH_CONFIG = "updateCustomWatchConfig";
    public final static String COMMAND_TYPE_UPDATE_WEATHER_TEMP = "updateWeatherTemp";
    private Gson gson;
    private final int UPDATE_ZONE = 1;
    private TimeZoneUpdateHandler timeZoneUpdateHandler;
    private static String SKIN_PATH = "skin/skin_";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = TimeService.this;
        timeZoneUpdateHandler = new TimeZoneUpdateHandler(mContext);
        initData();
        connectService();

    }

    private void initData() {
        initTimeReceiver();
        gson = new Gson();
        addCloseAppCmdListeners();
    }

    private void initTimeReceiver() {
        mTimeReceiver = new TimerReceiver();
        IntentFilter timeFilter = new IntentFilter();
        timeFilter.addAction(Intent.ACTION_TIME_TICK);
        mContext.registerReceiver(mTimeReceiver, timeFilter);
    }

    private void unInitTimeReceiver() {
        if (mTimeReceiver != null){
            mContext.unregisterReceiver(mTimeReceiver);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unInitTimeReceiver();
        if (serviceConnection != null) {
            unbindService(serviceConnection);
        }
    }


    //链接服务端
    private void connectService() {
//        Log.e("letianpai_CustomService", "CustomService ======================= 1 =================");
        Intent intent = new Intent();
        intent.setPackage("com.renhejia.robot.letianpaiservice");
        intent.setAction("android.intent.action.LETIANPAI");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("", "乐天派 时间 完成AIDLService服务");
            iLetianpaiService = ILetianpaiService.Stub.asInterface(service);
            try {
                iLetianpaiService.registerLCCallback(ltpLongConnectCallback);
//                iLetianpaiService.setAppCmd(COMMAND_SET_APP_MODE,COMMAND_HIDE_TEXT);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            isConnectService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("", "乐天派 Custom  无法绑定aidlserver的AIDLService服务");
            isConnectService = false;
        }
    };

    private final LtpLongConnectCallback.Stub ltpLongConnectCallback = new LtpLongConnectCallback.Stub() {
        @Override
        public void onLongConnectCommand(String command, String data) throws RemoteException {
            commandDistribute(command, data);
        }
    };

    public void commandDistribute(String command, String data) {
        Log.e("RemoteCmdResponser", "commandDistribute:command " + command + "  data:" + data);
        switch (command) {
//            // 5. 更新通用配置（原显示模式配置）

            case COMMAND_UPDATE_DEVICE_TIME_ZONE:
                Log.e("letianpai_test_control", "switchToNewAutoPlayMode === commandDistribute ======= 2  ==========");
//                installApk(data);
                updateDeviceTimeZone(data);
                break;

            case COMMAND_UPDATE_CUSTOM_WATCH_CONFIG:
//                installApk(data);
                updateCustomWatchConfig(data);
                break;

            case COMMAND_TYPE_UPDATE_WEATHER_TEMP:
                GeeUINetResponseManager.getInstance(mContext).updateGeneralInfo();
                break;

            default:
                break;
        }

    }

    private void updateCustomWatchConfig(String data) {
        CustomWatchConfig config = gson.fromJson(data, CustomWatchConfig.class);
        if (config != null){
            if (!TextUtils.isEmpty(config.getCustom_bg_url())){
                RobotClockConfigManager.getInstance(mContext).setCustomBgUrl(config.getCustom_bg_url());
            }

            RobotClockConfigManager.getInstance(mContext).setShowRandomBg(config.getIs_random());
            RobotClockConfigManager.getInstance(mContext).setShowDate(config.getIs_date());
            RobotClockConfigManager.getInstance(mContext).setShowWeather(config.getIs_weather());
            RobotClockConfigManager.getInstance(mContext).setShowCustomBg(config.getIs_custom());
            RobotClockConfigManager.getInstance(mContext).setCustomBgUrl(config.getCustom_bg_url());
            RobotClockConfigManager.getInstance(mContext).setCustomSkinName(SKIN_PATH + config.getBg_id());
            RobotClockConfigManager.getInstance(mContext).commit();
            updateClockView(config);

        }

        // TODO 1.保存更新状态
//        if (config != null && !TextUtils.isEmpty(config.getCustom_bg_url())){
//            ((AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE)).setTimeZone(timeZone.getZone());
//            updateTime();
//
//        }

    }

    private void updateClockView(CustomWatchConfig config ) {
        CustomClockViewUpdateCallback.getInstance().setCustomClockInfo(config);
    }

    private void updateDeviceTimeZone(String data) {
        TimeZone timeZone = gson.fromJson(data, TimeZone.class);
        if (timeZone != null && !TextUtils.isEmpty(timeZone.getZone())){
            ((AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE)).setTimeZone(timeZone.getZone());
            RobotClockConfigManager.getInstance(mContext).setTimeZone(timeZone.getZone());
            RobotClockConfigManager.getInstance(mContext).commit();
            updateTime();

        }
    }

    private void updateTime() {
        Message message = new Message();
        message.what = UPDATE_ZONE;
        timeZoneUpdateHandler.sendMessageDelayed(message,500);
    }

    private void updateTimeView() {
        int hour =  Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int min =  Calendar.getInstance().get(Calendar.MINUTE);
        TimerKeeperCallback.getInstance().setTimerKeeper(hour,min);
    }

    private class TimeZoneUpdateHandler extends Handler {

        private final WeakReference<Context> context;

        public TimeZoneUpdateHandler(Context context) {
            this.context = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_ZONE) {
                updateTimeView();

            }
        }

    }

    private void addCloseAppCmdListeners() {
        CloseAppCallback.getInstance().setCloseAppCmdReceivedListener(new CloseAppCallback.CloseAppCmdListener() {
            @Override
            public void onCloseAppCmdReceived() {
                if (serviceConnection != null){
                    unbindService(serviceConnection);
                }
            }
        });
    }


}
