package com.letianpai.robot.time.net

//import com.renhejia.robot.guidelib.wifi.WIFIConnectionManager;
import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.elvishew.xlog.LogUtils
import com.google.gson.Gson
import com.letianpai.robot.components.network.nets.GeeUiNetManager
import com.letianpai.robot.components.network.system.SystemUtil
import com.letianpai.robot.time.callback.CustomClockViewUpdateCallback
import com.letianpai.robot.time.callback.GeneralInfoCallback
import com.letianpai.robot.time.parser.custom.CustomClockInfo
import com.letianpai.robot.time.parser.general.GeneralInfo
import com.letianpai.robot.time.storage.manager.LauncherConfigManager
import com.renhejia.robot.commandlib.consts.RobotRemoteConsts
import com.renhejia.robot.display.manager.RobotClockConfigManager
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

/**
 * @author liujunbin
 */
class GeeUINetResponseManager private constructor(context: Context) {
    private lateinit var mContext: Context
    private lateinit var gson: Gson
    var generalInfo: GeneralInfo? = null
        //    public GeneralInfo getGeneralInfo() {
        get() {
            if (SystemUtil.isInChinese) {
                if (field == null) {
                    getGeneralInfoList(true)
                } else {
                    return field
                }
            } else {
                if (generalInfoEn == null) {
                    getGeneralInfoList(false)
                } else {
                    return generalInfoEn
                }
            }
            return if (SystemUtil.isInChinese) {
                field
            } else {
                generalInfoEn
            }
        }
        set(generalInfo) {
            if (SystemUtil.isInChinese) {
                field = generalInfo
            } else {
                this.generalInfoEn = generalInfo
            }
        }
    private var generalInfoEn: GeneralInfo? = null

    init {
        init(context)
    }

    private fun init(context: Context) {
        this.mContext = context
        gson = Gson()
    }

    fun dispatchTask(cmd: String?, data: Any?) {
        Log.e("letianpai123456789", "commandData: ======= 2 ")
        if (cmd == null) {
            return
        }
        Log.e("letianpai123456789", "commandData: ======= 3 ")
        if (cmd == RobotRemoteConsts.COMMAND_TYPE_UPDATE_GENERAL_CONFIG) {
            updateGeneralInfo()
        }
    }

    val customWatchConfig: Unit
        /**
         *
         */
        get() {
            Thread {
                if (SystemUtil.isInChinese) {
                    getCustomWatchConfig(true)
                } else {
                    getCustomWatchConfig(false)
                }
            }.start()

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
    fun updateGeneralInfo() {
        Thread {
            if (SystemUtil.isInChinese) {
                getGeneralInfoList(true)
            } else {
                getGeneralInfoList(false)
            }
        }.start()

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


    private fun getGeneralInfoList(isChinese: Boolean) {
        GeeUiNetManager.getGeneralInfoList(mContext, isChinese, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.body != null) {
                    lateinit var  generalInfo: GeneralInfo
                    val info = response.body!!.string()

                    if (info != null) {
                        try {
                            generalInfo = Gson().fromJson(info, GeneralInfo::class.java)
                            if (generalInfo != null) {
//                            LogUtils.logi("letianpai_1234567", "generalInfo: " + generalInfo.toString());
                                if (generalInfo.data != null && generalInfo.data!!.temTag != null) {
                                    RobotClockConfigManager.getInstance(mContext)!!.tempMode =
                                        generalInfo.data!!.temTag
                                    RobotClockConfigManager.getInstance(mContext)!!.commit()
                                }
                                GeneralInfoCallback.instance
                                    .setGeneralInfo(generalInfo)
                                LauncherConfigManager.getInstance(mContext)
                                    ?.robotGeneralInfo = info
                                LauncherConfigManager.getInstance(mContext)?.commit()
                            } else {
                                Log.e("letianpai_1234", "generalInfo is null: ")
                            }
                        } catch (_: Exception) {
                        }
                    }
                }
            }
        })
    }

    private fun getCustomWatchConfig(isChinese: Boolean) {
        GeeUiNetManager.getCustomWatchConfig(mContext, isChinese, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.body != null) {
                    val info = response.body!!.string()
                    if (info != null) {
                        val customClockInfo: CustomClockInfo =
                            gson.fromJson<CustomClockInfo>(info, CustomClockInfo::class.java)
                        if (customClockInfo != null && customClockInfo.data != null) {
                            if (!TextUtils.isEmpty(customClockInfo.data!!.custom_bg_url)) {
                                RobotClockConfigManager.getInstance(mContext)!!
                                    .customBgUrl = (customClockInfo.data!!.custom_bg_url)
                                RobotClockConfigManager.getInstance(mContext)!!.commit()
                                CustomClockViewUpdateCallback.instance
                                    .setCustomClockInfo(customClockInfo.data!!)
                            }
                            CustomClockViewUpdateCallback.instance
                                .setCustomClockInfo(customClockInfo.data!!)
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
        })
    }

    private fun getCloudFileToken(isChinese: Boolean) {
        GeeUiNetManager.getCloudFileToken(mContext, isChinese, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.body != null) {
                    //                    Log.e("letianpai_1234", "response: " + response.toString());
//                    Log.e("letianpai_1234", "response11: " + response.body().toString());
//                    GeneralInfo generalInfo = null;

                    val info = response.body!!.string()

                    if (info != null) {
                        Log.e("letianpai_1234", "info: $info")
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
        })
    }


    companion object {
        private var instance: GeeUINetResponseManager? = null
        fun getInstance(context: Context): GeeUINetResponseManager? {
            synchronized(GeeUINetResponseManager::class.java) {
                if (instance == null) {
                    instance = GeeUINetResponseManager(context.applicationContext)
                }
                return instance
            }
        }
    }
}
