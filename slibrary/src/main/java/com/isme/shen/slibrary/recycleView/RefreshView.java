package com.isme.shen.slibrary.recycleView;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.isme.shen.slibrary.R;
import com.isme.shen.slibrary.utils.DensityUtils;

/**
 * Created by shen on 2016/8/19.
 */
public class RefreshView extends RefreshViewAbs {

    private TextView tv;

    private View view;

    public RefreshView(Context context) {
        super(context);
        init();

    }

    public void init(){
        tv = (TextView) refreshView.findViewById(R.id.tv);
    }

    @Override
    protected int getEmptyHeight() {
        return ((int) (DensityUtils.px2dp(context,50)));
    }


    @Override
    protected int getLayoutView() {
        return R.layout.refresh_view;
    }

    @Override
    protected void scrolled(float dy) {

    }

    @Override
    public void refresh() {
        tv.setText("正在刷新....");
    }

    @Override
    public void refreshComplete() {
        tv.setText("刷新完成！");
    }

    @Override
    public void releaseToRefresh() {
        tv.setText("松开刷新");
    }

    @Override
    public void noState() {
        tv.setText("下拉刷新");
    }

}
