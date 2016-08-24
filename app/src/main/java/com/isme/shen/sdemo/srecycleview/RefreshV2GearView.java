package com.isme.shen.sdemo.srecycleview;

import android.content.Context;
import android.view.View;

import com.isme.shen.sdemo.R;
import com.isme.shen.slibrary.recycleView.RefreshView;
import com.isme.shen.slibrary.recycleView.RefreshViewAbs;
import com.isme.shen.slibrary.view.GearView;

/**
 * Created by shen on 2016/8/24.
 */
public class RefreshV2GearView extends RefreshViewAbs {

    private GearView gearView;
    public RefreshV2GearView(Context context) {
        super(context);
        gearView = (GearView) refreshView.findViewById(R.id.gear_view);
    }
    public GearView getGearView(){
        return gearView;
    }

    @Override
    public void refresh() {
        gearView.startTranslation();
    }

    @Override
    public void refreshComplete() {
        gearView.backInit();
    }

    @Override
    public void releaseToRefresh() {

    }

    @Override
    public void noState() {

    }

    @Override
    protected int getEmptyHeight() {
        return 0;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.view_frefresh_gear;
    }

    @Override
    protected void scrolled(float dy) {
        gearView.startRotato(dy);
    }
}
