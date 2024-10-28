package com.letianpai.robot.time.utils

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.PowerManager
import android.os.SystemClock
import android.provider.Settings
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import android.view.WindowManager
import com.letianpai.robot.components.network.system.SystemUtil
import com.letianpai.robot.components.utils.GeeUILogUtils
import java.io.IOException
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import kotlin.math.min

/**
 * @author liujunbin
 */
class SystemFunctionUtil {
    var wakeLock: PowerManager.WakeLock? = null

    companion object {
        /**
         * 判断是否安装应用程序
         * @param context
         * @param packageName
         * @return
         */
        fun isAppInstalled(context: Context, packageName: String): Boolean {
            val pm: PackageManager = context.packageManager
            try {
                val appInfo: ApplicationInfo = pm.getApplicationInfo(packageName, 0)
                return appInfo != null
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return false
            }
        }

        val isChinese: Boolean
            /**
             * 判断是海外还是国内
             * @return
             */
            get() {
                val isChinese = SystemUtil.get(SystemUtil.REGION_LANGUAGE, "zh")
                return isChinese != null && isChinese == "zh"
            }


        /**
         * 清理用户数据
         *
         * @param packageName
         * @return
         */
        fun clearAppUserData(packageName: String): Process? {
            val p = execRuntimeProcess("pm clear $packageName")
            if (p == null) {
                GeeUILogUtils.logi(
                    "Letianpai", "Clear app data packageName:" + packageName
                            + ", FAILED !"
                )
            } else {
                GeeUILogUtils.logi(
                    "Letianpai", "Clear app data packageName:" + packageName
                            + ", SUCCESS !"
                )
            }
            return p
        }

        /**
         * @param commond
         * @return
         */
        fun execRuntimeProcess(commond: String): Process? {
            var p: Process? = null
            try {
                p = Runtime.getRuntime().exec(commond)
                GeeUILogUtils.logi("Letianpai", "exec Runtime commond:$commond, Process:$p")
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return p
        }

        /**
         * 重启
         *
         * @param context
         */
        fun screenOff(context: Context) {
            val pm: PowerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            val clazz: Class<*> = pm.javaClass
            try {
                val shutdown = clazz.getMethod(
                    "reboot",
                    Boolean::class.javaPrimitiveType,
                    String::class.java,
                    Boolean::class.javaPrimitiveType
                )
                shutdown.invoke(pm, false, "reboot", false)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        /**
         * 重启
         *
         * @param context
         */
        fun screenOn(context: Context) {
            val pm: PowerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            val clazz: Class<*> = pm.javaClass
            try {
                val shutdown = clazz.getMethod(
                    "reboot",
                    Boolean::class.javaPrimitiveType,
                    String::class.java,
                    Boolean::class.javaPrimitiveType
                )
                shutdown.invoke(pm, false, "reboot", false)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        /**
         * 唤醒屏幕
         *
         * @param context
         */
        fun wakeUp(context: Context) {
            val powerManager: PowerManager =
                context.getSystemService(Context.POWER_SERVICE) as PowerManager
            Log.e(
                "letianpai_sleep_screen",
                "powerManager.isInteractive()2: " + powerManager.isInteractive()
            )
            if (powerManager.isInteractive()) {
                return
            }
            try {
                powerManager.javaClass.getMethod(
                    "wakeUp", *arrayOf<Class<*>?>(
                        Long::class.javaPrimitiveType
                    )
                ).invoke(powerManager, SystemClock.uptimeMillis())
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            }
        }

        //    /**
        //     * 重启
        //     * @param context
        //     */
        //    public static void goToSleep(Context context) {
        //        PowerManager pm = (PowerManager) context.getSystemService(POWER_SERVICE);
        //        Class clazz = pm.getClass();
        //        try {
        //            Method shutdown = clazz.getMethod("goToSleep", long.class, String.class);
        //            long current = System.currentTimeMillis()+ 5000;
        //            shutdown.invoke(pm, current, "goToSleep");
        //
        //        } catch (Exception ex) {
        //            ex.printStackTrace();
        //        }
        //
        //
        //    }
        /**
         * 关闭屏幕 ，其实是使系统休眠
         */
        //    public static void goToSleep(Context context) {
        //        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //        try {
        //            powerManager.getClass().getMethod("goToSleep", new Class[]{long.class}).invoke(powerManager, SystemClock.uptimeMillis());
        //        } catch (IllegalAccessException e) {
        //            e.printStackTrace();
        //        } catch (InvocationTargetException e) {
        //            e.printStackTrace();
        //        } catch (NoSuchMethodException e) {
        //            e.printStackTrace();
        //        }
        //    }
        fun goToSleep(context: Context) {
            Log.e("letianpai_sleep_test_repeat", "=========== 2 =======")
            val powerManager: PowerManager =
                context.getSystemService(Context.POWER_SERVICE) as PowerManager
            try {
                powerManager.javaClass
                    .getMethod(
                        "goToSleep",
                        *arrayOf<Class<*>?>(
                            Long::class.javaPrimitiveType,
                            Integer.TYPE,
                            Integer.TYPE
                        )
                    )
                    .invoke(powerManager, SystemClock.uptimeMillis(), 0, 0)
            } catch (e: InvocationTargetException) {
                throw RuntimeException(e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException(e)
            } catch (e: NoSuchMethodException) {
                throw RuntimeException(e)
            }
        }

        /**
         * 关闭屏幕 ，但是系统不休眠
         */
        fun goToSleep1(context: Context) {
            val powerManager: PowerManager =
                context.getSystemService(Context.POWER_SERVICE) as PowerManager
            val wakeLock: PowerManager.WakeLock =
                powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "MyApp::MyWakelockTag")
            wakeLock.acquire(10*60*1000L /*10 minutes*/)
        }


        //    public static void wakeUp(Context context) {
        //        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //        try {
        //            powerManager.getClass().getMethod("wakeUp", new Class[]{long.class}).invoke(powerManager, SystemClock.uptimeMillis());
        //        } catch (IllegalAccessException e) {
        //            e.printStackTrace();
        //        } catch (InvocationTargetException e) {
        //            e.printStackTrace();
        //        } catch (NoSuchMethodException e) {
        //            e.printStackTrace();
        //        }
        //    }
        /**
         * 设置背光亮度
         *
         * @param context
         * @param brightness The brightness value from 0 to 255.
         */
        fun setBacklightBrightness(context: Context, brightness: Int) {
            val powerManager: PowerManager =
                context.getSystemService(Context.POWER_SERVICE) as PowerManager
            try {
//            powerManager.getClass().getMethod("setBacklightBrightness", new Class[]{int.class}).invoke(powerManager, brightness);
                powerManager.javaClass.getMethod(
                    "setBacklightBrightness", *arrayOf<Class<*>>(
                        Int::class.java
                    )
                ).invoke(powerManager, brightness)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            }
        }

        /**
         * 设置东八区时区
         *
         * @param context
         */
        fun setTimeZone(context: Context?) {
//        ((AlarmManager)context.getSystemService(Context.ALARM_SERVICE)).setTimeZone("Asia/Tokyo");
//        ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTimeZone("Asia/Shanghai");
        }

        /**
         * 设置时区
         *
         * @param context
         */
        fun set24HourFormat(context: Context) {
            Settings.System.putString(
                context.contentResolver,
                Settings.System.TIME_12_24, "24"
            )
            val timeFormat =
                Settings.System.getString(context.contentResolver, Settings.System.TIME_12_24)
            Log.e("letianpai_time", "set24HourFormat: set24HourFormat: $timeFormat")
        }

        fun set12HourFormat(context: Context) {
            Settings.System.putString(
                context.contentResolver,
                Settings.System.TIME_12_24, "12"
            )
            val timeFormat =
                Settings.System.getString(context.contentResolver, Settings.System.TIME_12_24)
            Log.e("letianpai_time", "set24HourFormat: set12HourFormat: $timeFormat")
        }

        fun set1224HourFormat(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                val timeFormat =
                    Settings.System.getString(context.contentResolver, Settings.System.TIME_12_24)
                if (timeFormat == "24") {
                    Settings.System.putString(
                        context.contentResolver,
                        Settings.System.TIME_12_24,
                        "12"
                    )
                } else {
                    Settings.System.putString(
                        context.contentResolver,
                        Settings.System.TIME_12_24,
                        "24"
                    )
                }
                val timeFormat1 =
                    Settings.System.getString(context.contentResolver, Settings.System.TIME_12_24)
            }
        }

        private fun setScreenBrightOFF(context: Activity) {
            // 获取当前屏幕亮度值
            try {
                val currentBrightness = Settings.System.getInt(
                    context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS
                )
                Log.e("letianpai_", "currentBrightness: $currentBrightness")
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
            }

            // 设置屏幕亮度值
            val newBrightness = 100 // 0 到 255 之间的整数，代表亮度值
            Settings.System.putInt(
                context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS,
                newBrightness
            )

            // 应用设置
            val layoutParams: WindowManager.LayoutParams = context.window.attributes
            layoutParams.screenBrightness = newBrightness / 255.0f // 将亮度值转换为 0 到 1 之间的浮点数
            context.window.attributes = layoutParams
        }

        private fun setScreenBrightOn(context: Activity) {
            // 获取当前屏幕亮度值
            try {
                val currentBrightness = Settings.System.getInt(
                    context.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS
                )
                Log.e("letianpai_", "currentBrightness: $currentBrightness")
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
            }

            // 设置屏幕亮度值
            val newBrightness = 100 // 0 到 255 之间的整数，代表亮度值
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                newBrightness
            )

            // 应用设置
            val layoutParams: WindowManager.LayoutParams = context.window.attributes
            layoutParams.screenBrightness = newBrightness / 255.0f // 将亮度值转换为 0 到 1 之间的浮点数
            context.window.attributes = layoutParams
        }


        /**
         * @param version1
         * @param version2
         * @return
         * @a
         */
        fun compareVersion(version1: String, version2: String): Boolean {
            // 切割点 "."；
            val versionArray1 =
                version1.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val versionArray2 =
                version2.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var idx = 0
            // 取最小长度值
            val minLength = min(versionArray1.size.toDouble(), versionArray2.size.toDouble())
                .toInt()
            var diff = 0
            // 先比较长度 再比较字符
            while (idx < minLength && ((versionArray1[idx].length - versionArray2[idx].length).also {
                    diff = it
                }) == 0 && (versionArray1[idx].compareTo(versionArray2[idx])
                    .also { diff = it }) == 0
            ) {
                ++idx
            }
            // 如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
            diff = if ((diff != 0)) diff else versionArray1.size - versionArray2.size
            return diff > 0
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val cm: ConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm == null) {
            } else {
                //如果仅仅是用来判断网络连接
                //则可以使用 cm.getActiveNetworkInfo().isAvailable();
                val info: Array<NetworkInfo> = cm.getAllNetworkInfo()
                if (info != null) {
                    for (i in info.indices) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true
                        }
                    }
                }
            }
            return false
        }

        val btAddressByReflection: String?
            /**
             * 获取蓝牙地址
             *
             * @return
             */
            get() {
                val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                var field: Field? = null
                try {
                    field = BluetoothAdapter::class.java.getDeclaredField("mService")
                    field.isAccessible = true
                    val bluetoothManagerService = field[bluetoothAdapter] ?: return null
                    val method = bluetoothManagerService.javaClass.getMethod("getAddress")
                    if (method != null) {
                        val obj = method.invoke(bluetoothManagerService)
                        if (obj != null) {
                            return obj.toString()
                        }
                    }
                } catch (e: NoSuchFieldException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                }
                return null
            }

        /**
         * 获取当前wifi名字
         *
         * @param context
         * @return
         */
        fun getConnectWifiSsid(context: Context): String {
            val wifiManager: WifiManager =
                context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo: WifiInfo = wifiManager.getConnectionInfo()
            return wifiInfo.getSSID()
        }

        // 5分19帧   7秒19帧
        //bitmap = rsBlur(mContext,bitmap,10,1);
        /**
         * 高斯模糊
         *
         * @param context
         * @param source
         * @param radius
         * @param scale
         * @return
         */
        private fun rsBlur(
            context: Context,
            source: Bitmap?,
            radius: Float,
            scale: Float
        ): Bitmap? {
            if (source == null) {
                return null
            }
            val scaleWidth = (source.width * scale).toInt()
            val scaleHeight = (source.height * scale).toInt()
            val scaledBitmap = Bitmap.createScaledBitmap(
                source, scaleWidth,
                scaleHeight, false
            )

            val inputBitmap = scaledBitmap
            Log.i("RenderScriptActivity", "size:" + inputBitmap.width + "," + inputBitmap.height)

            //创建RenderScript
            val renderScript: RenderScript = RenderScript.create(context)

            //创建Allocation
            val input: Allocation = Allocation.createFromBitmap(
                renderScript,
                inputBitmap,
                Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT
            )
            val output: Allocation = Allocation.createTyped(renderScript, input.getType())

            //创建ScriptIntrinsic
            val intrinsicBlur: ScriptIntrinsicBlur =
                ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))

            intrinsicBlur.setInput(input)

            intrinsicBlur.setRadius(radius)

            intrinsicBlur.forEach(output)

            output.copyTo(inputBitmap)

            renderScript.destroy()

            return inputBitmap
        }

        private fun drawableToBitmap(drawable: Drawable): Bitmap {
            //声明将要创建的bitmap
            var bitmap: Bitmap? = null
            //获取图片宽度
            val width: Int = drawable.intrinsicWidth
            //获取图片高度
            val height: Int = drawable.intrinsicHeight
            //图片位深，PixelFormat.OPAQUE代表没有透明度，RGB_565就是没有透明度的位深，否则就用ARGB_8888。详细见下面图片编码知识。
            val config =
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
            //创建一个空的Bitmap
            bitmap = Bitmap.createBitmap(width, height, config)
            //在bitmap上创建一个画布
            val canvas = Canvas(bitmap)
            //设置画布的范围
            drawable.setBounds(0, 0, width, height)
            //将drawable绘制在canvas上
            drawable.draw(canvas)
            return bitmap
        }

        fun getBitmap(context: Context?): Bitmap? {
//        Bitmap bitmap = drawableToBitamp(context.getDrawable(R.drawable.test_background));
//        return rsBlur(context,bitmap,10,1);
            return null
        }
    }
}
