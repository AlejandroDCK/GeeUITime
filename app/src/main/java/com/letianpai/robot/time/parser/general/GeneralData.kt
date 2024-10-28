package com.letianpai.robot.time.parser.general

class GeneralData {
    var wea: String? = null
    var wea_img: String? = null
    var tem: String? = null
    var calender_total: Int = 0
    var city: String? = null
    var temTag: String? = null


    //    @Override
    //    public String toString() {
    //        return "{" +
    //                "wea='" + wea + '\'' +
    //                ", wea_img='" + wea_img + '\'' +
    //                ", tem='" + tem + '\'' +
    //                ", calender_total=" + calender_total +
    //                '}';
    //    }
    override fun toString(): String {
        return "GeneralData{" +
                "wea='" + wea + '\'' +
                ", wea_img='" + wea_img + '\'' +
                ", tem='" + tem + '\'' +
                ", calender_total=" + calender_total +
                ", city='" + city + '\'' +
                ", temTag='" + temTag + '\'' +
                '}'
    }
}
