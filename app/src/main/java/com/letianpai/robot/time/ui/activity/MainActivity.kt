package com.letianpai.robot.time.ui.activity

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.letianpai.robot.components.network.nets.GeeUIStatusUploader
import com.letianpai.robot.components.network.nets.GeeUiNetManager.getAllConfig
import com.letianpai.robot.components.network.system.SystemUtil.isInChinese
import com.letianpai.robot.components.network.system.SystemUtil.setAppLanguage
import com.letianpai.robot.components.view.BottomStatusBar
import com.letianpai.robot.time.R
import com.letianpai.robot.time.callback.CloseAppCallback
import com.letianpai.robot.time.net.GeeUINetResponseManager
import com.letianpai.robot.time.service.TimeService
import com.letianpai.robot.time.ui.view.TimeView
import com.letianpai.robot.time.utils.SystemFunctionUtil
import com.renhejia.robot.commandlib.parser.config.RobotConfig
import com.renhejia.robot.display.manager.RobotClockConfigManager
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

/**
 *
 */
class MainActivity : AppCompatActivity() {
    private var timeView: TimeView? = null
    private var tvVersion: TextView? = null
    private var bottomStatusBar: BottomStatusBar? = null
    var isOnPaused: Boolean = false
        private set
    private var allConfigCallback: Callback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppLanguage(this@MainActivity)
        if (isInChinese) {
            setTimeZone(this@MainActivity)
        }

        val decorView = window.decorView
        val uiOptions =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
        setContentView(R.layout.activity_main)
        initView()
        initAllConfigCallback()
    }

    private fun initAllConfigCallback() {
        allConfigCallback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.body != null) {
                    var robotConfig: RobotConfig? = null
                    var info = ""
                    if (response.body != null) {
                        info = response.body!!.string()
                    }
                    try {
                        if (info != null) {
                            Log.e("letianpai_allconfig", "info: $info")
                            robotConfig = Gson().fromJson(info, RobotConfig::class.java)
                            if (robotConfig?.data != null && robotConfig.data!!.device_date_config != null) {
                                Log.e(
                                    "letianpai_allconfig",
                                    "robotConfig.getData().getDevice_date_config().getHour_24_switch(): " + robotConfig.data!!.device_date_config!!.hour_24_switch
                                )
                                if (robotConfig.data!!.device_date_config!!.hour_24_switch == 1) {
                                    SystemFunctionUtil.Companion.set24HourFormat(this@MainActivity)
                                } else {
                                    SystemFunctionUtil.Companion.set12HourFormat(this@MainActivity)
                                }
                                val timeZone = robotConfig.data!!.device_date_config!!.time_zone

                                if ((!TextUtils.isEmpty(timeZone)) && (timeZone != "auto")) {
                                    Log.e(
                                        "letianpai_allconfig",
                                        "robotConfig.getData().getDevice_date_config().getTime_zone(): " + robotConfig.data!!.device_date_config!!.time_zone
                                    )
                                    (this@MainActivity.getSystemService(ALARM_SERVICE) as AlarmManager).setTimeZone(
                                        robotConfig.data!!.device_date_config!!.time_zone
                                    )
                                } else if ((!TextUtils.isEmpty(timeZone)) && (timeZone == "auto") && isInChinese) {
                                    Log.e(
                                        "letianpai_allconfig",
                                        "robotConfig.getData().getDevice_date_config() Asia/Shanghai: " + robotConfig.data!!.device_date_config!!.time_zone
                                    )
                                    (this@MainActivity.getSystemService(ALARM_SERVICE) as AlarmManager).setTimeZone(
                                        "Asia/Shanghai"
                                    )
                                }
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun initView() {
        timeView = findViewById(R.id.tv_timeView)
        bottomStatusBar = findViewById(R.id.bottomStatusBar)
        tvVersion = findViewById(R.id.tv_version)

        startTimeService()
    }

    private fun startTimeService() {
        val intent = Intent(this@MainActivity, TimeService::class.java)
        startService(intent)
    }

    override fun onResume() {
        super.onResume()
        isOnPaused = false
        if (timeView != null) {
            timeView!!.setUpdateBackground(true)
        }
        cleanData()
        GeeUINetResponseManager.Companion.getInstance(this@MainActivity)!!
            .updateGeneralInfo()
        //        GeeUINetResponseManager.getInstance(MainActivity.this).getCustomWatchConfig();
        GeeUIStatusUploader.getInstance(this@MainActivity)!!.syncRobotStatus()
        robotStatus
    }

    private val robotStatus: Unit
        get() {
            getAllConfig(this@MainActivity, isInChinese, allConfigCallback)
        }

    override fun onPause() {
        super.onPause()
        isOnPaused = true
        if (timeView != null) {
            timeView!!.setUpdateBackground(false)
        }
        //closeApp();
    }

    private fun cleanData() {
        if (timeView != null) {
            timeView!!.cleanViewData()
        }
    }

    fun showBottomTextView() {
        bottomStatusBar!!.visibility = View.VISIBLE
        tvVersion!!.visibility = View.GONE
    }

    fun showSystemVersion() {
        bottomStatusBar!!.visibility = View.GONE
        tvVersion!!.text = "Version: " + Build.DISPLAY
        tvVersion!!.visibility = View.VISIBLE
    }

    private fun closeApp() {
        CloseAppCallback.instance.setCloseAppCmd()
        val intent = Intent(this@MainActivity, TimeService::class.java)
        stopService(intent)
        this@MainActivity.finish()
        System.exit(0)
    }


    companion object {
        /**
         * 设置东八区时区
         *
         * @param context
         */
        fun setTimeZone(context: Context) {
            if (!TextUtils.isEmpty(RobotClockConfigManager.getInstance(context).timeZone)) {
                (context.getSystemService(ALARM_SERVICE) as AlarmManager).setTimeZone(
                    RobotClockConfigManager.getInstance(context).timeZone
                )
            }
        }
    }
}