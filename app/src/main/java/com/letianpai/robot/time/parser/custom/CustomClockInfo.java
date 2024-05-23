package com.letianpai.robot.time.parser.custom;

public class CustomClockInfo {

    private int code;
    private CustomWatchConfig data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CustomWatchConfig getData() {
        return data;
    }

    public void setData(CustomWatchConfig data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "{" +
                "code:" + code +
                ", data:" + data +
                ", msg:'" + msg + '\'' +
                '}';
    }
}
