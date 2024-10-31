package com.renhejia.robot.display.manager

import android.content.Context

/**
 * @author liujunbin
 */
class RobotSkinInfoManager private constructor(private val mContext: Context) {
    companion object {
        private var instance: RobotSkinInfoManager? = null

        fun getInstance(context: Context): RobotSkinInfoManager {
            if (instance == null) {
                instance = RobotSkinInfoManager(context.getApplicationContext())
            }
            return instance!!
        }
    }
}
