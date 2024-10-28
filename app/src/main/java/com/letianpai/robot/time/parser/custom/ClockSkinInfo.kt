package com.letianpai.robot.time.parser.custom

class ClockSkinInfo {
    var id: Int = 0
    var url: String? = null

    override fun toString(): String {
        return "{" +
                "id:" + id +
                ", url:'" + url + '\'' +
                '}'
    }
}
