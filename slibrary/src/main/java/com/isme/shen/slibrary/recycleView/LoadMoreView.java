package com.isme.shen.slibrary.recycleView;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.isme.shen.slibrary.R;
import com.isme.shen.slibrary.utils.DensityUtils;


/**
 * Created by shen on 2016/8/19.
 */
public class LoadMoreView extends LoadMoreViewAbs {

    private TextView tv;
    private View view;

    public LoadMoreView(Context context) {
        super(context);
        init();

    }

    public void init(){
        tv = (TextView) loadMoreView.findViewById(R.id.tv);
    }

    @Override
    protected void loadMoring() {
        tv.setText("正在加载更多...");
    }

    @Override
    protected void loadMoreCompleteSub(boolean isLoadMore) {
        if(isLoadMore){
            tv.setText("点击加载更多");
        } else {
            tv.setText("已经加载全部内容");
        }

    }

    @Override
    protected int getEmptyHeight() {
        return ((int) (DensityUtils.px2dp(context,300)));
    }

    @Override
    protected int getLayoutView() {
        return R.layout.loadmore_view;
    }


}
