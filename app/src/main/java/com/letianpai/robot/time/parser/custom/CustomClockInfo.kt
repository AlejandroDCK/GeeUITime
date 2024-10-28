package com.letianpai.robot.time.parser.custom

class CustomClockInfo {
    var code: Int = 0
    var data: CustomWatchConfig? = null
    var msg: String? = null

    override fun toString(): String {
        return "{" +
                "code:" + code +
                ", data:" + data +
                ", msg:'" + msg + '\'' +
                '}'
    }
}
