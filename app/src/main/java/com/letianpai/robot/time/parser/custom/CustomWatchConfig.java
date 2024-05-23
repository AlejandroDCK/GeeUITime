package com.letianpai.robot.time.parser.custom;

import java.util.Arrays;

public class CustomWatchConfig {

    private  int bg_id; //  表盘背景id
    private String bg_url;
    private ClockSkinInfo[] bg_url_list;
    private String custom_bg_url;
    private int is_custom;
    private int is_random;
    private int is_date;
    private int is_weather;
    private long update_time;

    public int getBg_id() {
        return bg_id;
    }

    public void setBg_id(int bg_id) {
        this.bg_id = bg_id;
    }

    public String getBg_url() {
        return bg_url;
    }

    public void setBg_url(String bg_url) {
        this.bg_url = bg_url;
    }

    public String getCustom_bg_url() {
        return custom_bg_url;
    }

    public void setCustom_bg_url(String custom_bg_url) {
        this.custom_bg_url = custom_bg_url;
    }

    public int getIs_custom() {
        return is_custom;
    }

    public void setIs_custom(int is_custom) {
        this.is_custom = is_custom;
    }

    public int getIs_date() {
        return is_date;
    }

    public void setIs_date(int is_date) {
        this.is_date = is_date;
    }

    public int getIs_weather() {
        return is_weather;
    }

    public void setIs_weather(int is_weather) {
        this.is_weather = is_weather;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public ClockSkinInfo[] getBg_url_list() {
        return bg_url_list;
    }

    public void setBg_url_list(ClockSkinInfo[] bg_url_list) {
        this.bg_url_list = bg_url_list;
    }

    public int getIs_random() {
        return is_random;
    }

    public void setIs_random(int is_random) {
        this.is_random = is_random;
    }

    @Override
    public String toString() {
        return "CustomWatchConfig{" +
                "bg_id=" + bg_id +
                ", bg_url:'" + bg_url + '\'' +
                ", bg_url_list:" + Arrays.toString(bg_url_list) +
                ", custom_bg_url:'" + custom_bg_url + '\'' +
                ", is_custom:" + is_custom +
                ", is_random:" + is_random +
                ", is_date:" + is_date +
                ", is_weather:" + is_weather +
                ", update_time:" + update_time +
                '}';
    }

    //    @Override
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
