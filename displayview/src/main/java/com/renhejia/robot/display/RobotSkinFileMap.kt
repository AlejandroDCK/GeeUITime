package com.renhejia.robot.display

import android.text.TextUtils

object RobotSkinFileMap {
    private val CHINESE_LANGUAGE: String = "c"
    val mAssnMap: Map<*, *> = object : HashMap<Any?, Any?>() {
        init {
            put(":", "colon")
            put("°", "degree")
            put("#", "month")
            put("&", "day")
            put("-", "link")
            put("!", "space")
            put("上午", "AM")
            put("下午", "PM")
        }
    }
    val mAssnMap0: Map<*, *> = object : HashMap<Any?, Any?>() {
        init {
            put(":", "colon_2")
            put("°", "degree")
            put("#", "month")
            put("&", "day")
            put("-", "link")
            put("!", "space")
            put("上午", "AM")
            put("下午", "PM")
        }
    }
    val mAssnMap1: Map<*, *> = object : HashMap<Any?, Any?>() {
        init {
            put(":", "colon_4")
            put("°", "degree")
            put("#", "month")
            put("&", "day")
            put("-", "link")
            put("!", "space")
            put("上午", "AM")
            put("下午", "PM")
        }
    }
    val mAssnMap2: Map<*, *> = object : HashMap<Any?, Any?>() {
        init {
            put(":", "colon_6")
            put("°", "degree")
            put("#", "month")
            put("&", "day")
            put("-", "link")
            put("!", "space")
            put("上午", "AM")
            put("下午", "PM")
        }
    }
    val mAssnMap3: Map<*, *> = object : HashMap<Any?, Any?>() {
        init {
            put(":", "colon_8")
            put("°", "degree")
            put("#", "month")
            put("&", "day")
            put("-", "link")
            put("!", "space")
            put("上午", "AM")
            put("下午", "PM")
        }
    }
    val mAssnMap4: Map<*, *> = object : HashMap<Any?, Any?>() {
        init {
            put(":", "colon_10")
            put("°", "degree")
            put("#", "month")
            put("&", "day")
            put("-", "link")
            put("!", "space")
            put("上午", "AM")
            put("下午", "PM")
        }
    }

    //    public static String getFilePostfix(String key) {
    //
    //        if (mAssnMap.containsKey(key)) {
    //            return (String) mAssnMap.get(key);
    //        }
    //
    //        return key;
    //    }
    fun getFilePostfix(key: String?): String? {
        if (mAssnMap.containsKey(key)) {
            return mAssnMap.get(key) as String?
        }

        return key
    }

    fun getFilePostfixWith(key: String?, time: Int): String? {
        if (time > 0 && time < 200) {
            if (mAssnMap0.containsKey(key)) {
                return mAssnMap0.get(key) as String?
            }
        } else if (time >= 200 && time < 400) {
            if (mAssnMap1.containsKey(key)) {
                return mAssnMap1.get(key) as String?
            }
        } else if (time >= 400 && time < 600) {
            if (mAssnMap2.containsKey(key)) {
                return mAssnMap2.get(key) as String?
            }
        } else if (time >= 600 && time < 800) {
            if (mAssnMap3.containsKey(key)) {
                return mAssnMap3.get(key) as String?
            }
        } else if (time >= 800 && time < 1000) {
            if (mAssnMap4.containsKey(key)) {
                return mAssnMap4.get(key) as String?
            }
        }

        return key
    }

    fun getFilePostfix(key: String?, language: String?): String? {
        if (mAssnMap.containsKey(key)) {
            if ((!TextUtils.isEmpty(language)) && (language == CHINESE_LANGUAGE)) {
                return key
            } else {
                return mAssnMap.get(key) as String?
            }
        }

        return key
    }
}

