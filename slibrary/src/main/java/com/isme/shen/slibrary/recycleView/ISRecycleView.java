package com.isme.shen.slibrary.recycleView;

/**
 * Created by shen on 2016/8/20.
 */
public interface ISRecycleView {

//    基本功能接口
    public interface OnSRecycleViewListener extends ISRecycleView{
        void refresh();
    }

    public interface OnSRecycleViewScrollListening extends ISRecycleView{
        void onScrolled(int dx, int dy);
    }

}
