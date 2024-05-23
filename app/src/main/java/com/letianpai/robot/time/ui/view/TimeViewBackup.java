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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.letianpai.robot.components.network.nets.GeeUIStatusUploader;
import com.letianpai.robot.components.network.system.SystemUtil;
import com.letianpai.robot.time.R;
import com.letianpai.robot.time.callback.GeneralInfoCallback;
import com.letianpai.robot.time.net.GeeUINetResponseManager;
import com.letianpai.robot.time.parser.general.GeneralInfo;
import com.letianpai.robot.time.timer.TimerKeeperCallback;
import com.letianpai.robot.time.ui.activity.MainActivity;
import com.letianpai.robot.time.utils.GeeUINetConsts;
import com.letianpai.robot.time.utils.LetianpaiFunctionUtil;
import com.renhejia.robot.display.SpineSkinView;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class TimeViewBackup extends LinearLayout {
    private Context mContext;
    private TextView title;
    private TextView date;
    private TextView tvHour;
    private long time;
    private TextView weather;
    private ImageView ivWeather;
    private TextView notice;
    private Gson gson;
    private TextView tvMin;
    //    private static String DU = "℃";
    private static String DU = "°";
    private TextView location;
    private ImageView ivLocation;
    private TextView tvVersion;
    private CountDownTimer countDownTimer;;
    private int random;
    private RelativeLayout llRoot;
    private int bgIndex;
    private ArrayList<Drawable> bgList = new ArrayList<>();
    private boolean isUpdateBackground;
    private SpineSkinView spineSkinView;

    public TimeViewBackup(Context context) {
        super(context);
        init(context);
    }

    public TimeViewBackup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TimeViewBackup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        gson = new Gson();
        mHandler = new UpdateViewHandler(context);
        random = new Random().nextInt(60);
        Log.e("letianpai_uploader","random: "+ random);
        inflate(context, R.layout.robot_display_time_backup, this);
        initData();
        initView();
        getGeneralInfo();
        fillData();
        addListeners();
        addTimerUpdateCallback();
        initCountDownTimer();
        GeeUIStatusUploader.getInstance(mContext);
        updateBackground();
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
    }

    private void fillData() {
        GeneralInfo generalInfo = GeeUINetResponseManager.getInstance(mContext).getGeneralInfo();
        if (generalInfo != null) {
            updateViews(generalInfo);
        }
    }

    private void updateViews(GeneralInfo generalInfo) {
        if (generalInfo == null || generalInfo.getData() == null || generalInfo.getData().getTem() == null || generalInfo.getData().getWea() == null || generalInfo.getData().getTem() == null) {
            return;
        }
        //GeneralData{wea='阴', wea_img='', tem='18', calender_total=3}
        String weatherInfo = generalInfo.getData().getWea() + " " + generalInfo.getData().getTem();
        int notices = generalInfo.getData().getCalender_total();
        tvHour.setText(getHourTime());

        tvMin.setText(getMinTime());
        if (!TextUtils.isEmpty(generalInfo.getData().getWea())) {
            weather.setText(weatherInfo + DU);
            ivWeather.setVisibility(View.VISIBLE);
            fillWeatherIcon(generalInfo);
        } else {
            weather.setText("");
            ivWeather.setVisibility(View.INVISIBLE);
        }

        String city = generalInfo.getData().getCity();
//        if (!TextUtils.isEmpty(city)){
//            location.setText(city);
//            ivLocation.setVisibility(View.VISIBLE);
//        }else{
//            location.setText("");
//            ivLocation.setVisibility(View.INVISIBLE);
//        }

        location.setText("");
        ivLocation.setVisibility(View.GONE);

    }

    private void fillWeatherIcon(GeneralInfo generalInfo) {
        if (generalInfo.getData().getWea_img().equals(GeeUINetConsts.WEATHER_CONSTS_WEATHER_1_FOG)) {
            ivWeather.setImageResource(R.drawable.wea1);
        } else if (generalInfo.getData().getWea_img().equals(GeeUINetConsts.WEATHER_CONSTS_WEATHER_2_CLOUDY)) {
            ivWeather.setImageResource(R.drawable.wea2);
        } else if (generalInfo.getData().getWea_img().equals(GeeUINetConsts.WEATHER_CONSTS_WEATHER_3_WIND)) {
            ivWeather.setImageResource(R.drawable.wea3);
        } else if (generalInfo.getData().getWea_img().equals(GeeUINetConsts.WEATHER_CONSTS_WEATHER_4_SUNNY)) {
            ivWeather.setImageResource(R.drawable.wea4);
        } else if (generalInfo.getData().getWea_img().equals(GeeUINetConsts.WEATHER_CONSTS_WEATHER_5_DUST)) {
            ivWeather.setImageResource(R.drawable.wea5);
        } else if (generalInfo.getData().getWea_img().equals(GeeUINetConsts.WEATHER_CONSTS_WEATHER_6_SMOG)) {
            ivWeather.setImageResource(R.drawable.wea6);
        } else if (generalInfo.getData().getWea_img().equals(GeeUINetConsts.WEATHER_CONSTS_WEATHER_7_SNOW)) {
            ivWeather.setImageResource(R.drawable.wea7);
        } else if (generalInfo.getData().getWea_img().equals(GeeUINetConsts.WEATHER_CONSTS_WEATHER_8_RAIN)) {
            ivWeather.setImageResource(R.drawable.wea8);
        } else {
            ivWeather.setImageResource(R.drawable.wea4);
        }
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
        date = findViewById(R.id.tv_date);
        tvHour = findViewById(R.id.tv_time_hour);
        tvMin = findViewById(R.id.tv_time_min);
        weather = findViewById(R.id.tv_weather);
        ivWeather = findViewById(R.id.iv_weather);
        location = findViewById(R.id.tv_location);
//        notice = findViewById(R.id.tv_notice);
        ivLocation = findViewById(R.id.iv_location);
        date.setText(getFullTime());
        tvHour.setText(getHourTime());
        tvMin.setText(getMinTime());
        String hour = getHourTime();
        String minute = getMinTime();
    }


    private String getFullTime() {
//        if (RobotDifferenceUtil.isChinese()){
//            return convertTimeFormat("yyyy年MM月dd日   E");
//        }else{
        return convertTimeFormat("yyyy/MM/dd   E");
//        }

    }

    private String getClockTime() {
        return convertTimeFormat("HH:mm");
    }

    private String get12HourTime() {
        return convertTimeFormat("hh");
    }

    private String getHourTime() {
        if (LetianpaiFunctionUtil.is24HourFormat(mContext)) {
            return get24HourTime();
        } else {
            return get12HourTime();
        }

    }

    private String get24HourTime() {
        return convertTimeFormat("HH");
    }

    private String getMinTime() {
        return convertTimeFormat("mm");
    }


    private String convertTimeFormat(String strFormat) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format;
        if (SystemUtil.isInChinese()) {
            format = new SimpleDateFormat(strFormat, Locale.SIMPLIFIED_CHINESE);
        } else {
            format = new SimpleDateFormat(strFormat, (Locale.ENGLISH));
        }
        return format.format(date);
    }
//
//    private String convertTimeFormat(String strFormat) {
//        Date date = new Date(System.currentTimeMillis());
////        SimpleDateFormat format = new SimpleDateFormat(strFormat, Locale.getDefault());
//        SimpleDateFormat format = new SimpleDateFormat(strFormat, (Locale.ENGLISH));
//        return format.format(date);
//    }

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
        addTimeFormatChangeListeners();
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
                                tvHour.setText(getHourTime());

                            } else {
                                // 时间格式为12小时制
                                tvHour.setText(getHourTime());
                            }
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
                    tvHour.setText(getHourTime());
                    tvMin.setText(getMinTime());
                    date.setText(getFullTime());
                    break;
                case HIDE_VIEWS:
                    weather.setText("");
                    ivWeather.setVisibility(View.INVISIBLE);
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
            }
        }
    }

    private void updateTimeBackGround() {
        bgIndex += 1;
        if (bgIndex >= bgList.size()){
            bgIndex = 0;
        }

        if (isUpdateBackground){
            llRoot.setBackground(bgList.get(bgIndex));
        }

        delayUpdateBackground();
    }

    public void setUpdateBackground(boolean updateBackground) {
        isUpdateBackground = updateBackground;
    }
}
