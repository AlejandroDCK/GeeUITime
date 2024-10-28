package com.letianpai.robot.time.utils

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log

/**
 * @author liujunbin
 */
object LetianpaiFunctionUtil {
    const val LAUNCHER_CLASS_NAME: String =
        "com.renhejia.robot.launcher.main.activity.LeTianPaiMainActivity"

    /**
     * @param context
     * @return
     */
    fun isLauncherOnTheTop(context: Context): Boolean {
        val activityName = getTopActivityName(context)
        Log.e("letianpai_", "")
        return if (activityName != null && activityName == LAUNCHER_CLASS_NAME) {
            true
        } else {
            false
        }
    }

    /**
     * 获取顶部 Activity
     *
     * @param context
     * @return
     */
    fun getTopActivityName(context: Context): String? {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningTasks = am.getRunningTasks(1)
        if (runningTasks != null && runningTasks.size > 0) {
            val taskInfo = runningTasks[0]
            val componentName = taskInfo.topActivity
            if (componentName != null && componentName.className != null) {
                return componentName.className
            }
        }
        return null
    }


    fun is24HourFormat(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val timeFormat =
                Settings.System.getString(context.contentResolver, Settings.System.TIME_12_24)

            if (timeFormat == null) {
                Log.e("letianpai_time", "timeFormat is null")
            }
            return if (timeFormat != null && timeFormat == "24") {
                true
                //                android.provider.Settings.System.putString(context.getContentResolver(), android.provider.Settings.System.TIME_12_24, "12");
//                DateFormat.is24HourFormat(context); // 更新时间格式
            } else {
                false
                //                android.provide
//                r.Settings.System.putString(context.getContentResolver(), android.provider.Settings.System.TIME_12_24, "24");
//                DateFormat.is24HourFormat(context); // 更新时间格式
            }
        } else {
            return true
        }
    }

    val isFactoryRom: Boolean
        /**
         * @return
         */
        get() {
            val displayVersion = Build.DISPLAY
            Log.e("letianpai_test", "displayVersion: $displayVersion")
            return if (displayVersion.endsWith("f")) {
                true
            } else {
                false
            }
        }
}
