package com.letianpai.robot.time.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.database.ContentObserver
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.CountDownTimer
import android.os.Handler
import android.os.Message
import android.provider.Settings
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.gson.Gson
import com.letianpai.robot.components.network.nets.GeeUIStatusUploader
import com.letianpai.robot.components.network.system.SystemUtil.isInChinese
import com.letianpai.robot.time.R
import com.letianpai.robot.time.callback.GeneralInfoCallback
import com.letianpai.robot.time.callback.GeneralInfoCallback.GeneralInfoUpdateListener
import com.letianpai.robot.time.net.GeeUINetResponseManager
import com.letianpai.robot.time.parser.general.GeneralInfo
import com.letianpai.robot.time.timer.TimerKeeperCallback
import com.letianpai.robot.time.timer.TimerKeeperCallback.TimerKeeperUpdateListener
import com.letianpai.robot.time.ui.activity.MainActivity
import com.letianpai.robot.time.utils.GeeUINetConsts
import com.letianpai.robot.time.utils.LetianpaiFunctionUtil
import com.renhejia.robot.display.SpineSkinView
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random

class TimeViewBackup : LinearLayout {
    private lateinit var mContext: Context
    private val title: TextView? = null
    private lateinit var date: TextView
    private lateinit var tvHour: TextView
    private val time: Long = 0
    private var weather: TextView? = null
    private var ivWeather: ImageView? = null
    private val notice: TextView? = null
    private var gson: Gson? = null
    private lateinit var tvMin: TextView
    private var location: TextView? = null
    private var ivLocation: ImageView? = null
    private val tvVersion: TextView? = null
    private var countDownTimer: CountDownTimer? = null
    private var random = 0
    private var llRoot: RelativeLayout? = null
    private var bgIndex = 0
    private val bgList = ArrayList<Drawable>()
    private var isUpdateBackground = false
    private var spineSkinView: SpineSkinView? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        this.mContext = context
        gson = Gson()
        mHandler = UpdateViewHandler(context)
        random = Random().nextInt(60)
        Log.e("letianpai_uploader", "random: $random")
        inflate(context, R.layout.robot_display_time_backup, this)
        initData()
        initView()
        generalInfo
        fillData()
        addListeners()
        addTimerUpdateCallback()
        initCountDownTimer()
        GeeUIStatusUploader.getInstance(mContext)
        updateBackground()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initData() {
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_0))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_1))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_2))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_3))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_4))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_5))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_6))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_7))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_8))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_9))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_10))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_11))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_12))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_13))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_14))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_15))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_16))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_17))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_18))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_19))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_21))
        bgList.add(mContext!!.resources.getDrawable(R.drawable.bg_22))
    }

    private fun initCountDownTimer() {
        countDownTimer = object : CountDownTimer((55 * 1000).toLong(), (10 * 1000).toLong()) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                showVersion()
            }
        }
    }

    private fun showSystemVersion() {
        (mContext as MainActivity?)!!.showSystemVersion()
    }

    private val generalInfo: Unit
        get() {
            GeeUINetResponseManager.getInstance(mContext)?.generalInfo
        }

    private fun fillData() {
        val generalInfo: GeneralInfo? =
            GeeUINetResponseManager.getInstance(mContext)?.generalInfo
        if (generalInfo != null) {
            updateViews(generalInfo)
        }
    }

    private fun updateViews(generalInfo: GeneralInfo?) {
        if (generalInfo == null || generalInfo.data == null || generalInfo.data!!.tem == null || generalInfo.data!!.wea == null || generalInfo.data!!.tem == null) {
            return
        }
        //GeneralData{wea='阴', wea_img='', tem='18', calender_total=3}
        val weatherInfo = generalInfo.data!!.wea + " " + generalInfo.data!!.tem
        val notices = generalInfo.data!!.calender_total
        tvHour!!.text = hourTime

        tvMin!!.text = minTime
        if (!TextUtils.isEmpty(generalInfo.data!!.wea)) {
            weather!!.text = weatherInfo + DU
            ivWeather!!.visibility = VISIBLE
            fillWeatherIcon(generalInfo)
        } else {
            weather!!.text = ""
            ivWeather!!.visibility = INVISIBLE
        }

        val city = generalInfo.data!!.city

        //        if (!TextUtils.isEmpty(city)){
//            location.setText(city);
//            ivLocation.setVisibility(View.VISIBLE);
//        }else{
//            location.setText("");
//            ivLocation.setVisibility(View.INVISIBLE);
//        }
        location!!.text = ""
        ivLocation!!.visibility = GONE
    }

    private fun fillWeatherIcon(generalInfo: GeneralInfo) {
        if (generalInfo.data!!.wea_img == GeeUINetConsts.WEATHER_CONSTS_WEATHER_1_FOG) {
            ivWeather!!.setImageResource(R.drawable.wea1)
        } else if (generalInfo.data!!.wea_img == GeeUINetConsts.WEATHER_CONSTS_WEATHER_2_CLOUDY) {
            ivWeather!!.setImageResource(R.drawable.wea2)
        } else if (generalInfo.data!!.wea_img == GeeUINetConsts.WEATHER_CONSTS_WEATHER_3_WIND) {
            ivWeather!!.setImageResource(R.drawable.wea3)
        } else if (generalInfo.data!!.wea_img == GeeUINetConsts.WEATHER_CONSTS_WEATHER_4_SUNNY) {
            ivWeather!!.setImageResource(R.drawable.wea4)
        } else if (generalInfo.data!!.wea_img == GeeUINetConsts.WEATHER_CONSTS_WEATHER_5_DUST) {
            ivWeather!!.setImageResource(R.drawable.wea5)
        } else if (generalInfo.data!!.wea_img == GeeUINetConsts.WEATHER_CONSTS_WEATHER_6_SMOG) {
            ivWeather!!.setImageResource(R.drawable.wea6)
        } else if (generalInfo.data!!.wea_img == GeeUINetConsts.WEATHER_CONSTS_WEATHER_7_SNOW) {
            ivWeather!!.setImageResource(R.drawable.wea7)
        } else if (generalInfo.data!!.wea_img == GeeUINetConsts.WEATHER_CONSTS_WEATHER_8_RAIN) {
            ivWeather!!.setImageResource(R.drawable.wea8)
        } else {
            ivWeather!!.setImageResource(R.drawable.wea4)
        }
    }

    private var mGeneralInfo: GeneralInfo? = null

    private fun updateViewData(generalInfo: GeneralInfo) {
        this.mGeneralInfo = generalInfo
    }

    private fun addTimerUpdateCallback() {
        TimerKeeperCallback.instance.registerTimerKeeperUpdateListener { hour, minute ->
            updateTime()
            showBottomTextView()
            countDownTimer!!.start()
            uploadStatus()
        }
    }

    private fun uploadData() {
        GeeUIStatusUploader.getInstance(mContext)!!.uploadRobotStatus()
    }

    private fun showBottomTextView() {
        (mContext as MainActivity?)!!.showBottomTextView()
    }

    private fun initView() {
        spineSkinView = findViewById(R.id.robotClockView)
        llRoot = findViewById(R.id.root_view)
        date = findViewById(R.id.tv_date)
        tvHour = findViewById(R.id.tv_time_hour)
        tvMin = findViewById(R.id.tv_time_min)
        weather = findViewById(R.id.tv_weather)
        ivWeather = findViewById(R.id.iv_weather)
        location = findViewById(R.id.tv_location)
        //        notice = findViewById(R.id.tv_notice);
        ivLocation = findViewById(R.id.iv_location)
        date.text = fullTime
        tvHour.setText(hourTime)
        tvMin.setText(minTime)
        val hour = hourTime
        val minute = minTime
    }


    private val fullTime: String
        get() =//        if (RobotDifferenceUtil.isChinese()){
//            return convertTimeFormat("yyyy年MM月dd日   E");
//        }else{
            convertTimeFormat("yyyy/MM/dd   E")

    //        }

    private val clockTime: String
        get() = convertTimeFormat("HH:mm")

    private fun get12HourTime(): String {
        return convertTimeFormat("hh")
    }

    private val hourTime: String
        get() = if (LetianpaiFunctionUtil.is24HourFormat(mContext)) {
            get24HourTime()
        } else {
            get12HourTime()
        }

    private fun get24HourTime(): String {
        return convertTimeFormat("HH")
    }

    private val minTime: String
        get() = convertTimeFormat("mm")


    private fun convertTimeFormat(strFormat: String): String {
        val date = Date(System.currentTimeMillis())
        val format = if (isInChinese) {
            SimpleDateFormat(strFormat, Locale.SIMPLIFIED_CHINESE)
        } else {
            SimpleDateFormat(strFormat, (Locale.ENGLISH))
        }
        return format.format(date)
    }

    //
    //    private String convertTimeFormat(String strFormat) {
    //        Date date = new Date(System.currentTimeMillis());
    ////        SimpleDateFormat format = new SimpleDateFormat(strFormat, Locale.getDefault());
    //        SimpleDateFormat format = new SimpleDateFormat(strFormat, (Locale.ENGLISH));
    //        return format.format(date);
    //    }
    private fun addListeners() {
        GeneralInfoCallback.instance.setGeneralInfoUpdateListener { generalInfo ->
            updateViewData(generalInfo)
            update()
        }
    }

    private fun update() {
        val message = Message()
        message.what = UPDATE_STATUS
        mHandler!!.sendMessage(message)
    }

    private fun uploadStatus() {
        val message = Message()
        message.what = UPLOAD_DATA
        mHandler!!.sendMessageDelayed(message, (random * 1000).toLong())
    }

    private fun showShowBottomText() {
        val message = Message()
        message.what = SHOW_BOTTOM_TEXT
        mHandler!!.sendMessage(message)
    }

    private fun showVersion() {
        val message = Message()
        message.what = SHOW_VERSION
        mHandler!!.sendMessage(message)
    }

    fun cleanViewData() {
        val message = Message()
        message.what = HIDE_VIEWS
        mHandler!!.sendMessage(message)
    }

    private fun updateTime() {
        val message = Message()
        message.what = UPDATE_TIME
        mHandler!!.sendMessage(message)
        addTimeFormatChangeListeners()
    }

    private fun updateBackground() {
        val message = Message()
        message.what = UPDATE_BACKGROUND
        mHandler!!.sendMessage(message)
    }

    fun delayUpdateBackground() {
        val message = Message()
        message.what = UPDATE_BACKGROUND
        mHandler!!.sendMessageDelayed(message, DELAY_TIME.toLong())
    }

    private fun addTimeFormatChangeListeners() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mContext.contentResolver.registerContentObserver(
                Settings.System.CONTENT_URI,
                true,
                object : ContentObserver(Handler()) {
                    override fun onChange(selfChange: Boolean) {
                        super.onChange(selfChange)
                        if (Settings.System.getString(
                                mContext.contentResolver,
                                Settings.System.TIME_12_24
                            ) == "24"
                        ) {
                            // 时间格式为24小时制
                            tvHour.setText(hourTime)
                        } else {
                            // 时间格式为12小时制
                            tvHour.setText(hourTime)
                        }
                    }
                }
            )
        }
    }

    private var mHandler: UpdateViewHandler? = null

    private inner class UpdateViewHandler(context: Context) : Handler() {
        private val context = WeakReference(context)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                UPDATE_STATUS -> updateViews(mGeneralInfo)
                UPDATE_TIME -> {
                    tvHour.setText(hourTime)
                    tvMin.setText(minTime)
                    date.setText(fullTime)
                }

                HIDE_VIEWS -> {
                    weather!!.text = ""
                    ivWeather!!.visibility = INVISIBLE
                }

                SHOW_VERSION -> showSystemVersion()
                UPLOAD_DATA -> uploadData()
                UPDATE_BACKGROUND -> updateTimeBackGround()
            }
        }
    }

    private fun updateTimeBackGround() {
        bgIndex += 1
        if (bgIndex >= bgList.size) {
            bgIndex = 0
        }

        if (isUpdateBackground) {
            llRoot!!.background = bgList[bgIndex]
        }

        delayUpdateBackground()
    }

    fun setUpdateBackground(updateBackground: Boolean) {
        isUpdateBackground = updateBackground
    }

    companion object {
        //    private static String DU = "℃";
        private const val DU = "°"
        private const val UPDATE_STATUS = 110
        private const val UPDATE_TIME = 111
        private const val HIDE_VIEWS = 112
        private const val SHOW_BOTTOM_TEXT = 113
        private const val SHOW_VERSION = 115
        private const val UPLOAD_DATA = 116
        private const val UPDATE_BACKGROUND = 118
        private const val DELAY_TIME = 30 * 1000
    }
}
