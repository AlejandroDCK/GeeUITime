package com.letianpai.robot.time.ui.view;

import android.content.Context;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.letianpai.robot.components.network.nets.GeeUIStatusUploader;
import com.letianpai.robot.components.view.ImageBgView;
import com.letianpai.robot.time.R;
import com.letianpai.robot.time.callback.CustomClockViewUpdateCallback;
import com.letianpai.robot.time.callback.GeneralInfoCallback;
import com.letianpai.robot.time.net.GeeUINetResponseManager;
import com.letianpai.robot.time.parser.custom.CustomWatchConfig;
import com.letianpai.robot.time.parser.general.GeneralInfo;
import com.letianpai.robot.time.timer.TimerKeeperCallback;
import com.letianpai.robot.time.ui.activity.MainActivity;
import com.renhejia.robot.display.RobotPlatformState;
import com.renhejia.robot.display.SpineSkinView;
import com.renhejia.robot.display.manager.RobotClockConfigManager;
import com.renhejia.robot.display.utils.SpineSkinUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;

public class TimeView extends LinearLayout {
    private Context mContext;
    private CountDownTimer countDownTimer;;
    private int random;
    private int bgIndex;
    private ArrayList<Drawable> bgList = new ArrayList<>();
    private boolean isUpdateBackground;
    private SpineSkinView spineSkinView;
    private String[] skinList;
    private RelativeLayout llRoot;
    private ImageBgView imageBgView;
    private CustomWatchConfig customWatchConfig;
    private String skinName;
    private static String SKIN_PATH = "skin/skin_";


    public TimeView(Context context) {
        super(context);
        init(context);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mHandler = new UpdateViewHandler(context);
        random = new Random().nextInt(60);
        skinList = SpineSkinUtils.getSkinList(mContext);
        inflate(context, R.layout.robot_display_time, this);
        initData();
        initView();
        getGeneralInfo();
        fillData();
        addListeners();
        addTimeFormatChangeListeners();
        addTimerUpdateCallback();
        initCountDownTimer();
        GeeUIStatusUploader.getInstance(mContext);
        updateBackground();
        addClockViewUpdateListeners();
    }

    private void addClockViewUpdateListeners() {
        CustomClockViewUpdateCallback.getInstance().setCustomClockViewUpdateListener(new CustomClockViewUpdateCallback.CustomClockViewUpdateListener() {
            @Override
            public void onCustomClockViewChanged(CustomWatchConfig customWatchConfig) {
                responseViewUpdate(customWatchConfig);
            }
        });

    }

    private void responseViewUpdate(CustomWatchConfig customWatchConfig) {
        Message message = new Message();
        message.obj = customWatchConfig;
        message.what = UPDATE_ALL_VIEWS;
        mHandler.sendMessage(message);
    }

    private void updateCustomImageBackground() {
        Message message = new Message();
        message.what = UPDATE_CUSTOM_BACKGROUND;
        mHandler.sendMessage(message);
    }

    private void loadSkin() {
        Message message = new Message();
        message.what = LOAD_SKIN;
        mHandler.sendMessage(message);
    }

    private void initData() {

        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_0));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_1));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_2));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_3));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_4));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_5));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_6));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_7));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_8));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_9));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_10));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_11));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_12));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_13));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_14));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_15));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_16));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_17));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_18));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_19));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_21));
        bgList.add(mContext.getResources().getDrawable(R.drawable.bg_22));
//        bgList.add(mContext.getResources().getDrawable(R.drawable.black_screen));
    }

    private void initCountDownTimer() {
        countDownTimer = new CountDownTimer(55 * 1000,10* 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                showVersion();
            }
        };

    }

    private void showSystemVersion() {
        ((MainActivity)mContext).showSystemVersion();
    }

    private void getGeneralInfo() {
        GeeUINetResponseManager.getInstance(mContext).getGeneralInfo();
        GeeUINetResponseManager.getInstance(mContext).getCustomWatchConfig();
    }

    private void fillData() {
        GeneralInfo generalInfo = GeeUINetResponseManager.getInstance(mContext).getGeneralInfo();
        if (generalInfo != null) {
            updateViews(generalInfo);
        }
    }

    private void updateViews(GeneralInfo generalInfo) {

        if (generalInfo == null
                ||  generalInfo.getData() == null
                ||  TextUtils.isEmpty(generalInfo.getData().getTem())
                ||  TextUtils.isEmpty(generalInfo.getData().getWea())
                ||  TextUtils.isEmpty(generalInfo.getData().getWea_img()) ) {
            return;
        }
        RobotPlatformState.getInstance(mContext).setWeatherState(Integer.valueOf(generalInfo.getData().getWea_img()));
        RobotPlatformState.getInstance(mContext).setWeatherStateStr(generalInfo.getData().getWea());
        RobotPlatformState.getInstance(mContext).setCurrentTemp(Integer.valueOf(generalInfo.getData().getTem()));
        loadSkin();


    }

    private GeneralInfo mGeneralInfo;

    private void updateViewData(GeneralInfo generalInfo) {
        this.mGeneralInfo = generalInfo;
    }

    private void addTimerUpdateCallback() {
        TimerKeeperCallback.getInstance().registerTimerKeeperUpdateListener(new TimerKeeperCallback.TimerKeeperUpdateListener() {
            @Override
            public void onTimerKeeperUpdateReceived(int hour, int minute) {
                updateTime();
                showBottomTextView();
                countDownTimer.start();
                uploadStatus();
            }
        });
    }

    private void uploadData() {
        GeeUIStatusUploader.getInstance(mContext).uploadRobotStatus();
    }

    private void showBottomTextView() {
        ((MainActivity)mContext).showBottomTextView();
    }

    private void initView() {
        spineSkinView = findViewById(R.id.robotClockView);
        llRoot = findViewById(R.id.root_view);
        imageBgView = findViewById(R.id.imageBgView);
        loadSkin();
        updateCustomImageBackground();

    }

    private void loadCustomSkin() {
//        Log.e("letianpai_skin","skinName: "+skinName );
        skinName = RobotClockConfigManager.getInstance(mContext).getCustomSkinName();
        if (TextUtils.isEmpty(skinName)){
            spineSkinView.loadSkin(skinList[0]);
        }else{
            spineSkinView.loadSkin(skinName);
        }
    }

    private void addListeners() {
        GeneralInfoCallback.getInstance().setGeneralInfoUpdateListener(new GeneralInfoCallback.GeneralInfoUpdateListener() {
            @Override
            public void onAtCmdResultReturn(GeneralInfo generalInfo) {
                updateViewData(generalInfo);
                update();
            }
        });
    }

    private static final int UPDATE_STATUS = 110;
    private static final int UPDATE_TIME = 111;
    private static final int HIDE_VIEWS = 112;
    private static final int SHOW_BOTTOM_TEXT = 113;
    private static final int SHOW_VERSION = 115;
    private static final int UPLOAD_DATA = 116;
    private static final int UPDATE_BACKGROUND = 118;
    private static final int UPDATE_ALL_VIEWS = 119;
    private static final int UPDATE_CUSTOM_BACKGROUND = 120;
    private static final int LOAD_SKIN = 121;
    private static final int DELAY_TIME = 30 * 1000;

    private void update() {
        Message message = new Message();
        message.what = UPDATE_STATUS;
        mHandler.sendMessage(message);
    }
    private void uploadStatus() {
        Message message = new Message();
        message.what = UPLOAD_DATA;
        mHandler.sendMessageDelayed(message,random * 1000);
    }

    private void showShowBottomText() {
        Message message = new Message();
        message.what = SHOW_BOTTOM_TEXT;
        mHandler.sendMessage(message);
    }

    private void showVersion() {
        Message message = new Message();
        message.what = SHOW_VERSION;
        mHandler.sendMessage(message);
    }

    public void cleanViewData() {
        Message message = new Message();
        message.what = HIDE_VIEWS;
        mHandler.sendMessage(message);
    }

    private void updateTime() {
        Message message = new Message();
        message.what = UPDATE_TIME;
        mHandler.sendMessage(message);
    }

    private void updateBackground() {
        Message message = new Message();
        message.what = UPDATE_BACKGROUND;
        mHandler.sendMessage(message);
    }

    public void delayUpdateBackground() {
        Message message = new Message();
        message.what = UPDATE_BACKGROUND;
        mHandler.sendMessageDelayed(message,DELAY_TIME);
    }

    private void addTimeFormatChangeListeners() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mContext.getContentResolver().registerContentObserver(
                    android.provider.Settings.System.CONTENT_URI,
                    true,
                    new ContentObserver(new Handler()) {
                        @Override
                        public void onChange(boolean selfChange) {
                            super.onChange(selfChange);
                            if (android.provider.Settings.System.getString(mContext.getContentResolver(), android.provider.Settings.System.TIME_12_24).equals("24")) {
                                // 时间格式为24小时制
                                spineSkinView.set12HourFormat(false);

                            } else {
                                // 时间格式为12小时制
                                spineSkinView.set12HourFormat(true);
                            }
                            loadSkin();
                        }
                    }
            );
        }
    }

    private UpdateViewHandler mHandler;

    private class UpdateViewHandler extends Handler {
        private final WeakReference<Context> context;

        public UpdateViewHandler(Context context) {
            this.context = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_STATUS:
                    updateViews(mGeneralInfo);
                    break;
                case UPDATE_TIME:
                case LOAD_SKIN:
                    loadCustomSkin();
                    break;
                case HIDE_VIEWS:
                    break;

                case SHOW_VERSION:
                    showSystemVersion();
                    break;

                case UPLOAD_DATA:
                    uploadData();
                    break;

                case UPDATE_BACKGROUND:
                    updateTimeBackGround();
                    break;

                case UPDATE_ALL_VIEWS:
                    updateAllViews(msg);
                    break;

                case UPDATE_CUSTOM_BACKGROUND:
                    updateCustomBackground();
                    break;

            }
        }
    }

    private void updateCustomBackground() {
        if (RobotClockConfigManager.getInstance(mContext).IsShowCustomBg() && !TextUtils.isEmpty(RobotClockConfigManager.getInstance(mContext).getCustomBgUrl())){
            imageBgView.setVisibility(View.VISIBLE);
            imageBgView.updateBackground(RobotClockConfigManager.getInstance(mContext).getCustomBgUrl());
        }else{
            imageBgView.setVisibility(View.GONE);
        }
    }

    private void updateAllViews(Message message) {
        //TODO 更新自定义背景
        //TODO 更新轮播图状态
        //TODO

        CustomWatchConfig config = (CustomWatchConfig)(message.obj);
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

//            Log.e("letianpai","updateAllViews_2.1:_config.getCustom_bg_url(): "+ config.getCustom_bg_url());
//            Log.e("letianpai","updateAllViews_3:_config.getIs_random(): "+ config.getIs_random());
//            Log.e("letianpai","updateAllViews_4:_config.getIs_date(): "+ config.getIs_date());
//            Log.e("letianpai","updateAllViews_5:_config.getIs_weather(): "+ config.getIs_weather());
//            Log.e("letianpai","updateAllViews_6:_config.getIs_custom(): "+ config.getIs_custom());
//            Log.e("letianpai","updateAllViews_7:_config.getCustom_bg_url(): "+ config.getCustom_bg_url());
//            Log.e("letianpai","updateAllViews_8:_config.SKIN_PATH + config.getBg_id(): "+ SKIN_PATH + config.getBg_id());
            RobotClockConfigManager.getInstance(mContext).commit();

        }

        loadSkin();
        updateCustomImageBackground();
//        imageBgView.updateBackground("https://cdn.file.letianpai.com/shop-WUWj34G2gXdzMXnAVtUlHe/20230711-223048-WXFX.png");
    }

    private void updateTimeBackGround() {
        bgIndex += 1;
        if (bgIndex >= bgList.size()){
            bgIndex = 0;
        }

        if (isUpdateBackground && RobotClockConfigManager.getInstance(mContext).IsShowRandomBg()){
            llRoot.setBackground(bgList.get(bgIndex));
        }
        delayUpdateBackground();
    }

    public void setUpdateBackground(boolean updateBackground) {
        isUpdateBackground = updateBackground;
    }
}
