package com.isme.shen.slibrary.http;

/**
 * 自定义异常类
 * Created by shen on 2016/9/6.
 */
public class ApiException extends RuntimeException {
    public static final int PARSE_ERROR = 1;
    public static final int UNKNOWN = 2;

    public static final int CONNECTION_OUT = 3;
    public static final int NET_DISCONNECT = 4;
    public static final int DATA_STYLE_ERROR = 5;

    private String reason;//真实异常原因
    private String displayMessage;//显示给用户的异常原因
    private int code;

    public ApiException(int code,String reason,String displayMessage){
        super(reason);
        this.reason = reason;
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
