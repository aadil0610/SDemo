package com.isme.shen.slibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.isme.shen.slibrary.R;

/**
 * 加载框
 * Created by Mr-Zhang on 2016/5/11.
 */
public class LoadingDialog extends Dialog {
    private ImageView imgLoading;
    private Context context;
    private TextView tvProgressHint;
    private String msgHint;

    public LoadingDialog(Context context) {
        super(context);
        this.context = context;
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public LoadingDialog(Context context, int themeResId, String msg) {
        super(context, themeResId);
        this.context = context;
        this.msgHint = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_loading_dialog);
        imgLoading = (ImageView) findViewById(R.id.img_loading_dialog);
        tvProgressHint = (TextView) findViewById(R.id.tv_progress_hint);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.circle);
        LinearInterpolator lin = new LinearInterpolator();
        animation.setInterpolator(lin);
        imgLoading.setAnimation(animation);
        getWindow().setWindowAnimations(R.style.dialogWindowAnim);

        if(!TextUtils.isEmpty(msgHint)){
            tvProgressHint.setText(msgHint);
        }

    }

    @Override
    public void dismiss() {
        if (this == null) {
            return;
        }
        if (imgLoading != null) {
            imgLoading.clearAnimation();
        }
        if (context != null) {
            super.dismiss();
        }
    }
}
