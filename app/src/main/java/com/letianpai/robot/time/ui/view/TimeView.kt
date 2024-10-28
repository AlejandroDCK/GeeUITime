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
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.letianpai.robot.components.network.nets.GeeUIStatusUploader
import com.letianpai.robot.components.view.ImageBgView
import com.letianpai.robot.time.R
import com.letianpai.robot.time.callback.CustomClockViewUpdateCallback
import com.letianpai.robot.time.callback.CustomClockViewUpdateCallback.CustomClockViewUpdateListener
import com.letianpai.robot.time.callback.GeneralInfoCallback
import com.letianpai.robot.time.callback.GeneralInfoCallback.GeneralInfoUpdateListener
import com.letianpai.robot.time.net.GeeUINetResponseManager
import com.letianpai.robot.time.parser.custom.CustomWatchConfig
import com.letianpai.robot.time.parser.general.GeneralInfo
import com.letianpai.robot.time.timer.TimerKeeperCallback
import com.letianpai.robot.time.timer.TimerKeeperCallback.TimerKeeperUpdateListener
import com.letianpai.robot.time.ui.activity.MainActivity
import com.renhejia.robot.display.RobotPlatformState
import com.renhejia.robot.display.SpineSkinView
import com.renhejia.robot.display.manager.RobotClockConfigManager
import com.renhejia.robot.display.utils.SpineSkinUtils
import java.lang.ref.WeakReference
import java.util.Random

class TimeView : LinearLayout {
    private lateinit var mContext: Context
    private var countDownTimer: CountDownTimer? = null
    private var random = 0
    private var bgIndex = 0
    private val bgList = ArrayList<Drawable>()
    private var isUpdateBackground = false
    private var spineSkinView: SpineSkinView? = null
    private lateinit var skinList: Array<String>
    private var llRoot: RelativeLayout? = null
    private var imageBgView: ImageBgView? = null
    private val customWatchConfig: CustomWatchConfig? = null
    private var skinName: String? = null

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
        mHandler = UpdateViewHandler(context)
        random = Random().nextInt(60)
        skinList = SpineSkinUtils.getSkinList(mContext)
        inflate(context, R.layout.robot_display_time, this)
        initData()
        initView()
        generalInfo
        fillData()
        addListeners()
        addTimeFormatChangeListeners()
        addTimerUpdateCallback()
        initCountDownTimer()
        GeeUIStatusUploader.getInstance(mContext!!)
        updateBackground()
        addClockViewUpdateListeners()
    }

    private fun addClockViewUpdateListeners() {
        CustomClockViewUpdateCallback.instance.setCustomClockViewUpdateListener { customWatchConfig ->
            responseViewUpdate(
                customWatchConfig
            )
        }
    }

    private fun responseViewUpdate(customWatchConfig: CustomWatchConfig) {
        val message = Message()
        message.obj = customWatchConfig
        message.what = UPDATE_ALL_VIEWS
        mHandler!!.sendMessage(message)
    }

    private fun updateCustomImageBackground() {
        val message = Message()
        message.what = UPDATE_CUSTOM_BACKGROUND
        mHandler!!.sendMessage(message)
    }

    private fun loadSkin() {
        val message = Message()
        message.what = LOAD_SKIN
        mHandler!!.sendMessage(message)
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
        //        bgList.add(mContext.getResources().getDrawable(R.drawable.black_screen));
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
            GeeUINetResponseManager.Companion.getInstance(mContext)?.generalInfo
            GeeUINetResponseManager.Companion.getInstance(mContext)!!.customWatchConfig
        }

    private fun fillData() {
        val generalInfo: GeneralInfo? =
            GeeUINetResponseManager.getInstance(mContext)?.generalInfo
        if (generalInfo != null) {
            updateViews(generalInfo)
        }
    }

    private fun updateViews(generalInfo: GeneralInfo?) {
        if (generalInfo == null || generalInfo.data == null || TextUtils.isEmpty(generalInfo.data!!.tem)
            || TextUtils.isEmpty(generalInfo.data!!.wea)
            || TextUtils.isEmpty(generalInfo.data!!.wea_img)
        ) {
            return
        }
        RobotPlatformState.getInstance(mContext).weatherState = generalInfo.data!!.wea_img!!.toInt()
        RobotPlatformState.getInstance(mContext).weatherStateStr = generalInfo.data!!.wea
        RobotPlatformState.getInstance(mContext).currentTemp = generalInfo.data!!.tem!!.toInt()
        loadSkin()
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
        imageBgView = findViewById(R.id.imageBgView)
        loadSkin()
        updateCustomImageBackground()
    }

    private fun loadCustomSkin() {
//        Log.e("letianpai_skin","skinName: "+skinName );
        skinName = RobotClockConfigManager.getInstance(mContext).customSkinName
        if (TextUtils.isEmpty(skinName)) {
            spineSkinView!!.loadSkin(skinList[0])
        } else {
            spineSkinView!!.loadSkin(skinName)
        }
    }

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
            mContext!!.contentResolver.registerContentObserver(
                Settings.System.CONTENT_URI,
                true,
                object : ContentObserver(Handler()) {
                    override fun onChange(selfChange: Boolean) {
                        super.onChange(selfChange)
                        if (Settings.System.getString(
                                mContext!!.contentResolver,
                                Settings.System.TIME_12_24
                            ) == "24"
                        ) {
                            // 时间格式为24小时制
                            spineSkinView!!.is12HourFormat = false
                        } else {
                            // 时间格式为12小时制
                            spineSkinView!!.is12HourFormat = true
                        }
                        loadSkin()
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
                UPDATE_TIME, LOAD_SKIN -> loadCustomSkin()
                HIDE_VIEWS -> {}
                SHOW_VERSION -> showSystemVersion()
                UPLOAD_DATA -> uploadData()
                UPDATE_BACKGROUND -> updateTimeBackGround()
                UPDATE_ALL_VIEWS -> updateAllViews(msg)
                UPDATE_CUSTOM_BACKGROUND -> updateCustomBackground()
            }
        }
    }

    private fun updateCustomBackground() {
        if (RobotClockConfigManager.getInstance(mContext).IsShowCustomBg() && !TextUtils.isEmpty(
                RobotClockConfigManager.getInstance(mContext).customBgUrl
            )
        ) {
            imageBgView!!.visibility = VISIBLE
            imageBgView!!.updateBackground(RobotClockConfigManager.getInstance(mContext).customBgUrl)
        } else {
            imageBgView!!.visibility = GONE
        }
    }

    private fun updateAllViews(message: Message) {
        //TODO 更新自定义背景
        //TODO 更新轮播图状态
        //TODO

        val config = message.obj as CustomWatchConfig
        if (config != null) {
            if (!TextUtils.isEmpty(config.custom_bg_url)) {
                RobotClockConfigManager.getInstance(mContext).customBgUrl = config.custom_bg_url
            }
            RobotClockConfigManager.getInstance(mContext).setShowRandomBg(config.is_random)
            RobotClockConfigManager.getInstance(mContext).setShowDate(config.is_date)
            RobotClockConfigManager.getInstance(mContext).setShowWeather(config.is_weather)
            RobotClockConfigManager.getInstance(mContext).setShowCustomBg(config.is_custom)
            RobotClockConfigManager.getInstance(mContext).customBgUrl = config.custom_bg_url
            RobotClockConfigManager.getInstance(mContext).customSkinName =
                SKIN_PATH + config.bg_id

            //            Log.e("letianpai","updateAllViews_2.1:_config.getCustom_bg_url(): "+ config.getCustom_bg_url());
//            Log.e("letianpai","updateAllViews_3:_config.getIs_random(): "+ config.getIs_random());
//            Log.e("letianpai","updateAllViews_4:_config.getIs_date(): "+ config.getIs_date());
//            Log.e("letianpai","updateAllViews_5:_config.getIs_weather(): "+ config.getIs_weather());
//            Log.e("letianpai","updateAllViews_6:_config.getIs_custom(): "+ config.getIs_custom());
//            Log.e("letianpai","updateAllViews_7:_config.getCustom_bg_url(): "+ config.getCustom_bg_url());
//            Log.e("letianpai","updateAllViews_8:_config.SKIN_PATH + config.getBg_id(): "+ SKIN_PATH + config.getBg_id());
            RobotClockConfigManager.getInstance(mContext).commit()
        }

        loadSkin()
        updateCustomImageBackground()
        //        imageBgView.updateBackground("https://cdn.file.letianpai.com/shop-WUWj34G2gXdzMXnAVtUlHe/20230711-223048-WXFX.png");
    }

    private fun updateTimeBackGround() {
        bgIndex += 1
        if (bgIndex >= bgList.size) {
            bgIndex = 0
        }

        if (isUpdateBackground && RobotClockConfigManager.getInstance(mContext).IsShowRandomBg()) {
            llRoot!!.background = bgList[bgIndex]
        }
        delayUpdateBackground()
    }

    fun setUpdateBackground(updateBackground: Boolean) {
        isUpdateBackground = updateBackground
    }

    companion object {
        private const val SKIN_PATH = "skin/skin_"


        private const val UPDATE_STATUS = 110
        private const val UPDATE_TIME = 111
        private const val HIDE_VIEWS = 112
        private const val SHOW_BOTTOM_TEXT = 113
        private const val SHOW_VERSION = 115
        private const val UPLOAD_DATA = 116
        private const val UPDATE_BACKGROUND = 118
        private const val UPDATE_ALL_VIEWS = 119
        private const val UPDATE_CUSTOM_BACKGROUND = 120
        private const val LOAD_SKIN = 121
        private const val DELAY_TIME = 30 * 1000
    }
}
