package com.isme.shen.slibrary.http;

/**
 * 统一解析类
 * Created by shen on 2016/9/2.
 */
public class Response<T> {
    public int state;
    public String reason;
    public T result;

    public Response(int state, String reason, T result) {
        this.state = state;
        this.reason = reason;
        this.result = result;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Response{" +
                "state=" + state +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
