package com.isme.shen.slibrary.http;

import android.app.Activity;
import android.net.ParseException;
import android.os.Looper;

import com.google.gson.JsonParseException;
import com.isme.shen.slibrary.R;
import com.isme.shen.slibrary.utils.DialogUtils;
import com.isme.shen.slibrary.utils.LogUtils;
import com.isme.shen.slibrary.utils.ToastUtils;

import org.json.JSONException;

import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by shen on 2016/9/1.
 */
public abstract class ServiceSubscribe<T> extends Subscriber<T>{

    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    private Activity activity;
    public ServiceSubscribe(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onStart() {
        LogUtils.d("retrofit","onStart；"+ Looper.myLooper().getThread().getName());
    }

    @Override
    public void onCompleted() {
        DialogUtils.getInstance().dismissProgressDialog(activity);
        LogUtils.d("retrofit","onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        DialogUtils.getInstance().dismissProgressDialog(activity);
        LogUtils.d("retrofit","onError:"+e.getMessage()+":"+Looper.myLooper().getThread().getName());
        handleException(e);
    }

    /**
     * 详细处理异常
     * */
    private void handleException(Throwable e) {
        Throwable throwable = e;
        //获取最根源的异常
        while(throwable.getCause() != null){
            e = throwable;
            throwable = throwable.getCause();
        }

        ApiException ex;
        if(e instanceof SocketTimeoutException){
            ex = new ApiException(e, ApiException.CONNECTION_OUT);
            ex.setDisplayMessage(activity.getString(R.string.net_conn_out));
            onNetError(ex);
        }else if (e instanceof HttpException){             //HTTP错误
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, httpException.code());
            switch(httpException.code()){
                case UNAUTHORIZED:
                case FORBIDDEN:
                    ex.setDisplayMessage(activity.getString(R.string.permission_denied));
                    onPermissionError(ex);          //权限错误，需要实现
                    break;
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.setDisplayMessage(activity.getString(R.string.net_error));  //均视为网络错误
                    onNetError(ex);
                    break;
            }
        }  else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException){
            ex = new ApiException(e, ApiException.PARSE_ERROR);
            ex.setDisplayMessage(activity.getString(R.string.data_parse_error));            //均视为解析错误
            onJsonError(ex);
        } else {
            ex = new ApiException(e, ApiException.UNKNOWN);
            ex.setDisplayMessage(activity.getString(R.string.unknow_error));          //未知错误
            onUnKnowError(ex);
        }
    }

    /*未知错误*/
    protected void onUnKnowError(ApiException ex) {
        LogUtils.d("retrofit","onUnKnowError："+ex.getDisplayMessage());
        ToastUtils.showShort(activity,ex.getDisplayMessage());
    }
    /*解析错误*/
    protected void onJsonError(ApiException ex) {
        LogUtils.d("retrofit","onJsonError："+ex.getMessage());
        ToastUtils.showShort(activity,ex.getDisplayMessage());
    }
    /*网络错误*/
    protected void onNetError(ApiException ex) {
        switch (ex.getCode()){
            case ApiException.CONNECTION_OUT:
                LogUtils.d("retrofit","onNetError："+ex.getMessage());
                ToastUtils.showShort(activity,ex.getDisplayMessage());
                break;
            case ApiException.NET_DISCONNECT:
                LogUtils.d("retrofit","onNetError："+ex.getMessage());
                ToastUtils.showShort(activity,ex.getDisplayMessage());
                break;
            default:
                break;
        }
    }

    /*权限拒绝*/
    protected void onPermissionError(ApiException ex) {
        LogUtils.d("retrofit","onPermissionError："+ex.getMessage());
    }

}
