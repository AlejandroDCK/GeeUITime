package com.letianpai.robot.time.parser.general;

public class GeneralData {
    private String wea;
    private String wea_img;
    private String tem;
    private int calender_total;
    private String city;
    private String temTag;


    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getWea_img() {
        return wea_img;
    }

    public void setWea_img(String wea_img) {
        this.wea_img = wea_img;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public int getCalender_total() {
        return calender_total;
    }

    public void setCalender_total(int calender_total) {
        this.calender_total = calender_total;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemTag() {
        return temTag;
    }

    public void setTemTag(String temTag) {
        this.temTag = temTag;
    }
    //    @Override
//    public String toString() {
//        return "{" +
//                "wea='" + wea + '\'' +
//                ", wea_img='" + wea_img + '\'' +
//                ", tem='" + tem + '\'' +
//                ", calender_total=" + calender_total +
//                '}';
//    }


    @Override
    public String toString() {
        return "GeneralData{" +
                "wea='" + wea + '\'' +
                ", wea_img='" + wea_img + '\'' +
                ", tem='" + tem + '\'' +
                ", calender_total=" + calender_total +
                ", city='" + city + '\'' +
                ", temTag='" + temTag + '\'' +
                '}';
    }
}
