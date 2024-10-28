package com.letianpai.robot.time.service

import android.app.AlarmManager
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.RemoteException
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.letianpai.robot.letianpaiservice.LtpLongConnectCallback
import com.letianpai.robot.time.callback.CloseAppCallback
import com.letianpai.robot.time.callback.CustomClockViewUpdateCallback
import com.letianpai.robot.time.net.GeeUINetResponseManager
import com.letianpai.robot.time.parser.custom.CustomWatchConfig
import com.letianpai.robot.time.parser.timezone.TimeZone
import com.letianpai.robot.time.timer.TimerKeeperCallback
import com.letianpai.robot.time.timer.TimerReceiver
import com.renhejia.robot.display.manager.RobotClockConfigManager
import com.renhejia.robot.letianpaiservice.ILetianpaiService
import java.lang.ref.WeakReference
import java.util.Calendar

class TimeService : Service() {
    private lateinit var mContext: Context
    private var mTimeReceiver: TimerReceiver? = null
    private var isConnectService = false
    private lateinit var iLetianpaiService: ILetianpaiService
    private var gson: Gson? = null
    private val UPDATE_ZONE = 1
    private var timeZoneUpdateHandler: TimeZoneUpdateHandler? = null
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this@TimeService
        timeZoneUpdateHandler = TimeZoneUpdateHandler(mContext)
        initData()
        connectService()
    }

    private fun initData() {
        initTimeReceiver()
        gson = Gson()
        addCloseAppCmdListeners()
    }

    private fun initTimeReceiver() {
        mTimeReceiver = TimerReceiver()
        val timeFilter = IntentFilter()
        timeFilter.addAction(Intent.ACTION_TIME_TICK)
        mContext.registerReceiver(mTimeReceiver, timeFilter)
    }

    private fun unInitTimeReceiver() {
        if (mTimeReceiver != null) {
            mContext.unregisterReceiver(mTimeReceiver)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unInitTimeReceiver()
        unbindService(serviceConnection)
    }


    //链接服务端
    private fun connectService() {
//        Log.e("letianpai_CustomService", "CustomService ======================= 1 =================");
        val intent = Intent()
        intent.setPackage("com.renhejia.robot.letianpaiservice")
        intent.setAction("android.intent.action.LETIANPAI")
        bindService(intent, serviceConnection, BIND_AUTO_CREATE)
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.d("", "乐天派 时间 完成AIDLService服务")
            iLetianpaiService = ILetianpaiService.Stub.asInterface(service)
            try {
                iLetianpaiService.registerLCCallback(ltpLongConnectCallback)
                //                iLetianpaiService.setAppCmd(COMMAND_SET_APP_MODE,COMMAND_HIDE_TEXT);
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            isConnectService = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d("", "乐天派 Custom  无法绑定aidlserver的AIDLService服务")
            isConnectService = false
        }
    }

    private val ltpLongConnectCallback = object : LtpLongConnectCallback.Stub() {
        @Throws(RemoteException::class)
        override fun onLongConnectCommand(command: String, data: String) {
            commandDistribute(command, data)
        }
    }

    fun commandDistribute(command: String, data: String) {
        Log.e("RemoteCmdResponser", "commandDistribute:command $command  data:$data")
        when (command) {
            COMMAND_UPDATE_DEVICE_TIME_ZONE -> {
                Log.e(
                    "letianpai_test_control",
                    "switchToNewAutoPlayMode === commandDistribute ======= 2  =========="
                )
                //                installApk(data);
                updateDeviceTimeZone(data)
            }

            COMMAND_UPDATE_CUSTOM_WATCH_CONFIG -> //                installApk(data);
                updateCustomWatchConfig(data)

            COMMAND_TYPE_UPDATE_WEATHER_TEMP -> GeeUINetResponseManager.getInstance(
                mContext
            )!!
                .updateGeneralInfo()

            else -> {}
        }
    }

    private fun updateCustomWatchConfig(data: String) {
        val config: CustomWatchConfig =
            gson!!.fromJson<CustomWatchConfig>(data, CustomWatchConfig::class.java)
        if (!TextUtils.isEmpty(config.custom_bg_url)) {
            RobotClockConfigManager.getInstance(mContext)
                .setCustomBgUrl(config.custom_bg_url)
        }

        RobotClockConfigManager.getInstance(mContext).setShowRandomBg(config.is_random)
        RobotClockConfigManager.getInstance(mContext).setShowDate(config.is_date)
        RobotClockConfigManager.getInstance(mContext).setShowWeather(config.is_weather)
        RobotClockConfigManager.getInstance(mContext).setShowCustomBg(config.is_custom)
        RobotClockConfigManager.getInstance(mContext).setCustomBgUrl(config.custom_bg_url)
        RobotClockConfigManager.getInstance(mContext)
            .setCustomSkinName(SKIN_PATH + config.bg_id)
        RobotClockConfigManager.getInstance(mContext).commit()
        updateClockView(config)

        // TODO 1.保存更新状态
//        if (config != null && !TextUtils.isEmpty(config.getCustom_bg_url())){
//            ((AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE)).setTimeZone(timeZone.getZone());
//            updateTime();
//
//        }
    }

    private fun updateClockView(config: CustomWatchConfig) {
        CustomClockViewUpdateCallback.instance.setCustomClockInfo(config)
    }

    private fun updateDeviceTimeZone(data: String) {
        val timeZone = gson!!.fromJson(data, TimeZone::class.java)
        if (timeZone != null && !TextUtils.isEmpty(timeZone.zone)) {
            (mContext.getSystemService(ALARM_SERVICE) as AlarmManager).setTimeZone(timeZone.zone)
            RobotClockConfigManager.getInstance(mContext).timeZone = timeZone.zone
            RobotClockConfigManager.getInstance(mContext).commit()
            updateTime()
        }
    }

    private fun updateTime() {
        val message = Message()
        message.what = UPDATE_ZONE
        timeZoneUpdateHandler!!.sendMessageDelayed(message, 500)
    }

    private fun updateTimeView() {
        val hour = Calendar.getInstance()[Calendar.HOUR_OF_DAY]
        val min = Calendar.getInstance()[Calendar.MINUTE]
        TimerKeeperCallback.instance.setTimerKeeper(hour, min)
    }

    private inner class TimeZoneUpdateHandler(context: Context) : Handler() {
        private val context = WeakReference(context)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == UPDATE_ZONE) {
                updateTimeView()
            }
        }
    }

    private fun addCloseAppCmdListeners() {
        CloseAppCallback.instance
            .setCloseAppCmdReceivedListener(object : CloseAppCallback.CloseAppCmdListener {
                override fun onCloseAppCmdReceived() {
                    unbindService(serviceConnection)
                }
            })
    }


    companion object {
        const val COMMAND_UPDATE_DEVICE_TIME_ZONE: String = "updateDeviceTimeZone"
        const val COMMAND_UPDATE_CUSTOM_WATCH_CONFIG: String = "updateCustomWatchConfig"
        const val COMMAND_TYPE_UPDATE_WEATHER_TEMP: String = "updateWeatherTemp"
        private const val SKIN_PATH = "skin/skin_"
    }
}
