package com.isme.shen.slibrary.utils;

import android.app.Activity;
import android.util.Log;

import com.isme.shen.slibrary.R;
import com.isme.shen.slibrary.SBaseApplication;
import com.isme.shen.slibrary.view.LoadingDialog;

/**
 * Created by shen on 2016/9/6.
 */
public class DialogUtils {

    static DialogUtils dialogUtils;

    public static DialogUtils getInstance() {
        if (dialogUtils == null) {
            synchronized (DialogUtils.class) {
                if (dialogUtils == null) {
                    dialogUtils = new DialogUtils();
                }
            }
        }
        return dialogUtils;
    }

    LoadingDialog loadingDialog;

    //显示progressialog
    public void showProgressDialog(Activity activity) {
        if(activity == null) return;
        loadingDialog = new LoadingDialog(activity, R.style.loadingDialog);
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    //取消progressdialog
    public void dismissProgressDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

}
