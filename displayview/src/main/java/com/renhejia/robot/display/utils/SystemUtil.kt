package com.renhejia.robot.display.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.net.NetworkInterface
import java.net.SocketException
import java.util.Enumeration
import java.util.Locale

/**
 * @author liujunbin
 */
object SystemUtil {
    private var sysPropGet: Method? = null
    private var sysPropGetInt: Method? = null
    private var sysPropSet: Method? = null
    private const val SN: String = "ro.serialno"
    private const val MCU_VERSION: String = "persist.sys.mcu.version"
    const val HARD_CODE: String = "persist.sys.hardcode"
    const val DEVICE_SIGN: String = "persist.sys.device.sign"
    private const val ROBOT_STATUS: String = "persist.sys.robot.status"
    const val REGION_LANGUAGE: String = "persist.sys.region.language"

    private const val ROBOT_STATUS_TRUE: String = "true"
    const val REGION_LANGUAGE_ZH: String = "zh"
    const val REGION_LANGUAGE_EN: String = "en"


    init {
        try {
            val S: Class<*> = Class.forName("android.os.SystemProperties")
            val M: Array<Method> = S.getMethods()
            for (m: Method in M) {
                val n: String = m.getName()
                if (n == "get") {
                    sysPropGet = m
                } else if (n == "getInt") {
                    sysPropGetInt = m
                } else if (n == "set") {
                    sysPropSet = m
                }
            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    fun get(name: String?, default_value: String?): String? {
        try {
            return sysPropGet!!.invoke(null, name, default_value) as String?
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return default_value
    }

    fun set(name: String?, value: String?) {
        try {
            sysPropSet!!.invoke(null, name, value)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }

    fun setRobotActivated() {
        set(ROBOT_STATUS, ROBOT_STATUS_TRUE)
    }

    val robotActivateStatus: Boolean
        get() {
            val status: String? = get(
                ROBOT_STATUS,
                null
            )
            if (status == ROBOT_STATUS_TRUE) {
                return true
            }
            return false
        }

    @get:SuppressLint("NewApi")
    val ltpSn: String = Build.getSerial()

    val ltpLastSn: String?
        get() {
            val sn: String = ltpSn
            if (TextUtils.isEmpty(sn)) {
                return null
            } else {
                return sn.substring(sn.length - 4)
            }
        }

    var hardCode: String?
        get() {
            return get(
                HARD_CODE,
                null
            )
        }
        set(hardCode) {
            set(
                HARD_CODE,
                hardCode
            )
        }

    fun hasHardCode(): Boolean {
        if (TextUtils.isEmpty(hardCode)) {
            return false
        }
        return true
    }

    var deviceSign: String?
        get() {
            return get(
                DEVICE_SIGN,
                null
            )
        }
        set(deviceSign) {
            set(
                DEVICE_SIGN,
                deviceSign
            )
        }

    val mcu: String?
        get() {
            return get(
                MCU_VERSION,
                null
            )
        }

    fun hadDeviceSign(): Boolean {
        if (TextUtils.isEmpty(deviceSign)) {
            return false
        }
        return true
    }


    val wlanMacAddress: String?
        get() {
            try {
                val networkInterfaces: Enumeration<NetworkInterface> =
                    NetworkInterface.getNetworkInterfaces()
                while (networkInterfaces.hasMoreElements()) {
                    val networkInterface: NetworkInterface =
                        networkInterfaces.nextElement()
                    if (networkInterface.getName() == "wlan0") {
                        val mac: StringBuilder = StringBuilder()
                        val hardwareAddress: ByteArray =
                            networkInterface.getHardwareAddress()
                        var hex: String =
                            Integer.toHexString(hardwareAddress.get(0).toInt() and 0xff)
                        if (hex.length == 1) {
                            mac.append('0')
                        }
                        mac.append(hex)
                        for (i in 1 until hardwareAddress.size) {
                            mac.append(':')
                            hex = Integer.toHexString(
                                hardwareAddress.get(i).toInt() and 0xff
                            )
                            if (hex.length == 1) {
                                mac.append('0')
                            }
                            mac.append(hex)
                        }
                        return mac.toString()
                    }
                }
            } catch (ex: SocketException) {
                Log.i("", ex.message!!)
            }
            return null
        }

    fun getIp(context: Context): String? {
        val wifiManager: WifiManager? =
            context.getApplicationContext().getSystemService(Context.WIFI_SERVICE) as WifiManager?
        if (wifiManager != null) {
            val wifiInfo: WifiInfo = wifiManager.getConnectionInfo()
            val ip: Int = wifiInfo.getIpAddress()

            val ipAddress: String = String.format(
                "%d.%d.%d.%d",
                (ip and 0xff),
                (ip shr 8 and 0xff),
                (ip shr 16 and 0xff),
                (ip shr 24 and 0xff)
            )
            return ipAddress
        }
        return null
    }

    val robotStatus: Boolean
        get() {
            val status: String? = get(
                ROBOT_STATUS,
                null
            )
            if (status == ROBOT_STATUS_TRUE) {
                return true
            }
            return false
        }


    val robotInChineseStatus: String?
        get() {
            val pro: String? = get(
                REGION_LANGUAGE,
                "zh"
            )
            return pro
        }

    val isInChinese: Boolean
        get() {
            val pro: String? = get(
                REGION_LANGUAGE,
                "zh"
            )
            if ("zh" == pro) {
                return true
            } else {
                return false
            }
        }

    val language: String?
        get() {
            return get(
                REGION_LANGUAGE,
                REGION_LANGUAGE_ZH
            )
        }

    val isChineseLanguage: Boolean
        get() {
            if (language == REGION_LANGUAGE_ZH) {
                return true
            } else {
                return false
            }
        }


    /**
     * @param context
     * @param language
     */
    fun setDefaultLanguage(context: Context, language: String) {
        if (TextUtils.isEmpty(language)) {
            return
        }

        val locale: Locale = Locale(language)
        Locale.setDefault(locale)

        val configuration: Configuration = context.getResources().getConfiguration()
        val metrics: DisplayMetrics = context.getResources().getDisplayMetrics()

        var loc: Locale = Locale.CHINA
        if (language == REGION_LANGUAGE_EN) {
            loc = Locale.ENGLISH
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(loc)
        } else {
            configuration.locale = loc
        }

        context.getResources().updateConfiguration(configuration, metrics)
    }

    /**
     * @param context
     */
    fun setAppLanguage(context: Context) {
//        if (isInChinese()){
        if (false) {
            setDefaultLanguage(context, "zh")
        } else {
            setDefaultLanguage(context, REGION_LANGUAGE_EN)
        }
    }
}
