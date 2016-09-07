package com.isme.shen.slibrary.http;

/**
 * 接口返回数据解析异常类
 * Created by shen on 2016/9/6.
 */
public class ApiException extends RuntimeException {
    public static final int PARSE_ERROR = 1;
    public static final int UNKNOWN = 2;

    public static final int CONNECTION_OUT = 3;
    public static final int NET_DISCONNECT = 4;

    private String displayMessage;
    private Throwable throwable;
    private int code;

    public ApiException(String reason) {
        super(reason);
    }

    public ApiException(Throwable e, int code) {
        this.code = code;
        this.throwable = e;
    }

    public ApiException(int code,String message){
        this.code = code;
        this.displayMessage = message;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public String getDisplayMessage(){
        return displayMessage;
    }

    public int getCode() {
        return code;
    }
}
