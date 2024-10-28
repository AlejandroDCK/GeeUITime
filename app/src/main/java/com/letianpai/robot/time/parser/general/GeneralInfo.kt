package com.letianpai.robot.time.parser.general

class GeneralInfo {
    var code: Int = 0
    var data: GeneralData? = null
    var msg: String? = null


    override fun toString(): String {
        return "{" +
                "code:" + code +
                ", data:" + data +
                ", msg:'" + msg + '\'' +
                '}'
    } //    @Override
    //    public String toString() {
    //        return "{" +
    //                "code=" + code +
    //                ", data=" + data +
    //                ", msg='" + msg + '\'' +
    //                '}';
    //    }
}
