package com.letianpai.robot.time.ui.activity;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.letianpai.robot.components.network.nets.GeeUIStatusUploader;
import com.letianpai.robot.components.network.system.SystemUtil;
import com.letianpai.robot.components.view.BottomStatusBar;
import com.letianpai.robot.time.R;
import com.letianpai.robot.time.callback.CloseAppCallback;
import com.letianpai.robot.time.net.GeeUINetResponseManager;
import com.letianpai.robot.time.service.TimeService;
import com.letianpai.robot.time.ui.view.TimeView;
import com.letianpai.robot.time.utils.SystemFunctionUtil;
import com.renhejia.robot.commandlib.parser.config.RobotConfig;
import com.renhejia.robot.display.manager.RobotClockConfigManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 *
 */
public class MainActivity extends AppCompatActivity {

    private TimeView timeView;
    private TextView tvVersion;
    private BottomStatusBar bottomStatusBar;
    private boolean isOnPaused;
    private Callback allConfigCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUtil.setAppLanguage(MainActivity.this);
        if (SystemUtil.isInChinese()) {
            setTimeZone(MainActivity.this);
        }

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_main);
        initView();
        initAllConfigCallback();
    }

    private void initAllConfigCallback() {
        allConfigCallback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response != null && response.body() != null) {

                    RobotConfig robotConfig = null;
                    String info = "";
                    if (response != null && response.body() != null) {
                        info = response.body().string();
                    }
                    try {
                        if (info != null) {
                            Log.e("letianpai_allconfig", "info: " + info);
                            robotConfig = new Gson().fromJson(info, RobotConfig.class);
                            if (robotConfig != null && robotConfig.getData() != null && robotConfig.getData().getDevice_date_config() != null) {
                                Log.e("letianpai_allconfig", "robotConfig.getData().getDevice_date_config().getHour_24_switch(): " + robotConfig.getData().getDevice_date_config().getHour_24_switch());
                                if (robotConfig.getData().getDevice_date_config().getHour_24_switch() == 1) {
                                    SystemFunctionUtil.set24HourFormat(MainActivity.this);
                                } else {
                                    SystemFunctionUtil.set12HourFormat(MainActivity.this);
                                }
                                String timeZone = robotConfig.getData().getDevice_date_config().getTime_zone();

                                if ((!TextUtils.isEmpty(timeZone)) && (!timeZone.equals("auto"))) {
                                    Log.e("letianpai_allconfig", "robotConfig.getData().getDevice_date_config().getTime_zone(): " + robotConfig.getData().getDevice_date_config().getTime_zone());
                                    ((AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE)).setTimeZone(robotConfig.getData().getDevice_date_config().getTime_zone());
                                } else if ((!TextUtils.isEmpty(timeZone)) && (timeZone.equals("auto")) && SystemUtil.isInChinese()) {
                                    Log.e("letianpai_allconfig", "robotConfig.getData().getDevice_date_config() Asia/Shanghai: " + robotConfig.getData().getDevice_date_config().getTime_zone());
                                    ((AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE)).setTimeZone("Asia/Shanghai");
                                }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private void initView() {
        timeView = findViewById(R.id.tv_timeView);
        bottomStatusBar = findViewById(R.id.bottomStatusBar);
        tvVersion = findViewById(R.id.tv_version);

        startTimeService();
    }

    private void startTimeService() {
        Intent intent = new Intent(MainActivity.this, TimeService.class);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isOnPaused = false;
        if (timeView != null) {
            timeView.setUpdateBackground(true);
        }
        cleanData();
        GeeUINetResponseManager.getInstance(MainActivity.this).updateGeneralInfo();
//        GeeUINetResponseManager.getInstance(MainActivity.this).getCustomWatchConfig();
        GeeUIStatusUploader.getInstance(MainActivity.this).syncRobotStatus();
        getRobotStatus();
    }

    private void getRobotStatus() {
        com.letianpai.robot.components.network.nets.GeeUiNetManager.getAllConfig(MainActivity.this, SystemUtil.isInChinese(), allConfigCallback);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isOnPaused = true;
        if (timeView != null) {
            timeView.setUpdateBackground(false);
        }
        //closeApp();
    }

    public boolean isOnPaused() {
        return isOnPaused;
    }

    private void cleanData() {
        if (timeView != null) {
            timeView.cleanViewData();
        }
    }

    public void showBottomTextView() {
        bottomStatusBar.setVisibility(View.VISIBLE);
        tvVersion.setVisibility(View.GONE);

    }

    public void showSystemVersion() {
        bottomStatusBar.setVisibility(View.GONE);
        tvVersion.setText("Version: " + Build.DISPLAY);
        tvVersion.setVisibility(View.VISIBLE);
    }

    /**
     * 设置东八区时区
     *
     * @param context
     */
    public static void setTimeZone(Context context) {
        if (!TextUtils.isEmpty(RobotClockConfigManager.getInstance(context).getTimeZone())) {
            ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTimeZone(RobotClockConfigManager.getInstance(context).getTimeZone());
        }
    }

    private void closeApp() {
        CloseAppCallback.getInstance().setCloseAppCmd();
        Intent intent = new Intent(MainActivity.this, TimeService.class);
        stopService(intent);
        MainActivity.this.finish();
        System.exit(0);

    }




}