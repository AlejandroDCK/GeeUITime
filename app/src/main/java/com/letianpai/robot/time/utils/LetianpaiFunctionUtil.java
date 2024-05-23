package com.letianpai.robot.time.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.util.List;

/**
 * @author liujunbin
 */
public class LetianpaiFunctionUtil {

    public static final String LAUNCHER_CLASS_NAME = "com.renhejia.robot.launcher.main.activity.LeTianPaiMainActivity";

    /**
     * @param context
     * @return
     */
    public static boolean isLauncherOnTheTop(Context context) {
        String activityName = getTopActivityName(context);
        Log.e("letianpai_", "");
        if (activityName != null && activityName.equals(LAUNCHER_CLASS_NAME)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 获取顶部 Activity
     *
     * @param context
     * @return
     */
    public static String getTopActivityName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
        if (runningTasks != null && runningTasks.size() > 0) {
            ActivityManager.RunningTaskInfo taskInfo = runningTasks.get(0);
            ComponentName componentName = taskInfo.topActivity;
            if (componentName != null && componentName.getClassName() != null) {
                return componentName.getClassName();
            }
        }
        return null;
    }


    public static boolean is24HourFormat(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            String timeFormat = android.provider.Settings.System.getString(context.getContentResolver(), android.provider.Settings.System.TIME_12_24);

            if (timeFormat == null) {
                Log.e("letianpai_time", "timeFormat is null");
            }
            if (timeFormat != null && timeFormat.equals("24")) {
                return true;
//                android.provider.Settings.System.putString(context.getContentResolver(), android.provider.Settings.System.TIME_12_24, "12");
//                DateFormat.is24HourFormat(context); // 更新时间格式
            } else {
                return false;
//                android.provide
//                r.Settings.System.putString(context.getContentResolver(), android.provider.Settings.System.TIME_12_24, "24");
//                DateFormat.is24HourFormat(context); // 更新时间格式
            }
        } else {
            return true;
        }
    }

    /**
     * @return
     */
    public static boolean isFactoryRom() {
        String displayVersion = Build.DISPLAY;
        Log.e("letianpai_test", "displayVersion: " + displayVersion);
        if (displayVersion.endsWith("f")) {
            return true;
        } else {
            return false;
        }
    }

}
