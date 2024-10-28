package com.letianpai.robot.time.parser.custom

class CustomWatchConfig {
    var bg_id: Int = 0 //  表盘背景id
    var bg_url: String? = null
    lateinit var bg_url_list: Array<ClockSkinInfo>
    var custom_bg_url: String? = null
    var is_custom: Int = 0
    var is_random: Int = 0
    var is_date: Int = 0
    var is_weather: Int = 0
    var update_time: Long = 0

    override fun toString(): String {
        return "CustomWatchConfig{" +
                "bg_id=" + bg_id +
                ", bg_url:'" + bg_url + '\'' +
                ", bg_url_list:" + bg_url_list.contentToString() +
                ", custom_bg_url:'" + custom_bg_url + '\'' +
                ", is_custom:" + is_custom +
                ", is_random:" + is_random +
                ", is_date:" + is_date +
                ", is_weather:" + is_weather +
                ", update_time:" + update_time +
                '}'
    } //    @Override
    //    public String toString() {
    //        return "{" +
    //                "bg_id:" + bg_id +
    //                ", bg_url:'" + bg_url + '\'' +
    //                ", bg_url_list:" + Arrays.toString(bg_url_list) +
    //                ", custom_bg_url:'" + custom_bg_url + '\'' +
    //                ", is_custom:" + is_custom +
    //                ", is_date:" + is_date +
    //                ", is_weather:" + is_weather +
    //                ", update_time:" + update_time +
    //                '}';
    //    }
    //    {
    //        "bg_id":1, //  表盘背景id
    //            "bg_url":"https://cdn.file.letianpai.com/shop-WUWj34G2gXdzMXnAVtUlHe/20230711-223048-WXFX.png", //  表盘背景地址
    //            "custom_bg_url":"", // 自定义图片地址
    //            "is_custom":0,// 是否自定义(0:否，1:是)
    //            "is_date":1, // 是否打开日期(0:否，1:是)
    //            "is_weather":1,// 是否打开天气(0:否，1:是)
    //            "update_time":1699375660 // 更新时间,单位s
    //    }
}
