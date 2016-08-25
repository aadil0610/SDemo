package com.isme.shen.slibrary.utils;

import android.graphics.Rect;
import android.view.View;

/**
 * Created by shen on 2016/8/25.
 */
public class WindowUtils {

    /**
     * 获取状态栏高度
     *
     * @param v
     * @return
     */
    public static int getStatusBarHeight(View v) {
        if (v == null) {
            return 0;
        }
        Rect frame = new Rect();
        v.getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

}
