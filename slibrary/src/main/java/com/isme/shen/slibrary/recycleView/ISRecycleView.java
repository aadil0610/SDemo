package com.isme.shen.slibrary.recycleView;

/**
 * Created by shen on 2016/8/20.
 */
public interface ISRecycleView {
    public interface OnSRecycleViewScrollListening extends ISRecycleView{
        void onScrolled(int dx, int dy);
    }

}
