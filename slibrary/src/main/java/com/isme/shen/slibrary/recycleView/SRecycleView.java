package com.isme.shen.slibrary.recycleView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Adapter;

import com.isme.shen.slibrary.utils.L;

/**
 * Created by shen on 2016/8/19.
 */
public class SRecycleView extends RecyclerView {

    private static final int IS_REFRESHING = 1000;
    private static final int NORMAL = 1001;
    private int currentRecycleViewState = NORMAL;

    private RefreshViewAbs refreshView;
    private LoadMoreViewAbs loadMoreView;
    private SRecycleViewAdapter sRecycleViewAdapter;

    private ISRecycleView.OnSRecycleViewScrollListening onSRecycleViewScrollListening;

    public void setOnSRecycleViewScrollListening(ISRecycleView.OnSRecycleViewScrollListening onSRecycleViewScrollListening) {
        this.onSRecycleViewScrollListening = onSRecycleViewScrollListening;
    }

    public SRecycleView(Context context) {
        this(context, null);
    }

    public SRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        sRecycleViewAdapter = (SRecycleViewAdapter) getAdapter();
        refreshView = sRecycleViewAdapter.getRefreshView();
        loadMoreView = sRecycleViewAdapter.getLoadMoreView();
    }

    float lastY = -1;
    float lastX = -1;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = e.getRawY();
                lastX = e.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                final float intervalY = e.getRawY() - lastY;
                final float intervalX = e.getRawX() - lastX;
                lastY = e.getRawY();
                lastX = e.getRawX();
                if (isOnTop() && refreshView != null) {
                    if (refreshView.getCurrentState() == RefreshViewAbs.RefreshViewState.IS_REFRESHING) {
                        if(intervalY > 0){
                            refreshView.putDown(intervalY);
                        }
                    } else {
                        refreshView.putDown(intervalY);
                        if (refreshView.getVisibleHeight() > 0) {//当refreshview出现时，静止recycleview本身的滑动，只是改变refreshview高度
                            return false;
                        }
                    }
                }
                if(isOnBottom() && loadMoreView!= null){
                    loadMoreView.putDown(intervalY);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(isOnTop() && refreshView!= null){
                    refreshView.mationActionUp(e.getRawY());
                    if (refreshView.getCurrentState() == RefreshViewAbs.RefreshViewState.IS_REFRESHING) {
                        if (SRecycleView.this.getRecycleViewState() != IS_REFRESHING) {
                            setRecycleViewState(IS_REFRESHING);
                            if(loadMoreView != null)loadMoreView.loadMoreComplete(true);
                        }
                    }
                }

                if(isOnBottom() && loadMoreView != null){
                    loadMoreView.mationActionUp(e.getRawY());
                }

                break;
        }
        return super.onTouchEvent(e);
    }

    private void setRecycleViewState(int state) {
        this.currentRecycleViewState = state;
    }

    public int getRecycleViewState() {
        return currentRecycleViewState;
    }

    private boolean isOnTop() {
        if (refreshView != null && refreshView.getParent() != null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isOnBottom() {
        if (loadMoreView != null && loadMoreView.getParent() != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        if(onSRecycleViewScrollListening != null){
            onSRecycleViewScrollListening.onScrolled(dx,dy);
        }
    }

    public void refreshComplete() {
        if(refreshView == null){
            return;
        }
        scrollToPosition(0);
        setRecycleViewState(NORMAL);
        refreshView.setRefreshComplete();
    }
}
