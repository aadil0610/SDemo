package com.isme.shen.slibrary.recycleView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.isme.shen.slibrary.utils.L;

/**
 * Created by shen on 2016/8/19.
 */
public abstract class RefreshViewAbs extends LinearLayout {

    private int EMPTY_HEIGHT = 0;//刷新顶部空白高度
    protected LinearLayout refreshView;
    protected Context context;
    private int measureHeight;

    private RefreshViewState currentState = RefreshViewState.NO_STATE;

    public abstract void refresh();

    public abstract void refreshComplete();

    public abstract void releaseToRefresh();

    public abstract void noState();

    private OnRefreshViewPullDownListening onRefreshViewPullDownListening;
    public interface OnRefreshViewPullDownListening{
        void scrolled(float dy);
    }

    private OnRefreshViewListening onRefreshViewListening;
    public interface OnRefreshViewListening{
        void refresh();
    }

    public void setOnRefreshListening( OnRefreshViewListening onRefreshViewListening){
        this.onRefreshViewListening = onRefreshViewListening;
    }
    public void setOnRefreshViewPullDownListening( OnRefreshViewPullDownListening onRefreshViewPullDownListening){
        this.onRefreshViewPullDownListening = onRefreshViewPullDownListening;
    }

    public void mationActionUp(float rawY) {
        if (getCurrentState() == RefreshViewAbs.RefreshViewState.RELEASE_TO_REFRESH) {
            setState(RefreshViewAbs.RefreshViewState.IS_REFRESHING);
            smoothScrollTo(getMeasureHeight());
            if(onRefreshViewListening != null) onRefreshViewListening.refresh();
            refresh();
        }
        if (getCurrentState() == RefreshViewAbs.RefreshViewState.NO_STATE) {
            smoothScrollTo(0);
        }
        if (getCurrentState() == RefreshViewAbs.RefreshViewState.IS_REFRESHING && getVisibleHeight() >= measureHeight) {
            smoothScrollTo(getMeasureHeight());
        }
    }

    public void setRefreshComplete() {
        setState(RefreshViewAbs.RefreshViewState.NO_STATE);
        refreshComplete();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                smoothScrollTo(0);
            }
        },300);
    }

    public enum RefreshViewState{
        RELEASE_TO_REFRESH,NO_STATE,IS_REFRESHING
    }
    public RefreshViewState getCurrentState(){
        return currentState;
    }

    public void setState(RefreshViewState state) {
        this.currentState = state;
    }

    public RefreshViewAbs(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        refreshView = (LinearLayout) LayoutInflater.from(context).inflate(getLayoutView(), null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,0);
        this.setLayoutParams(params);
        this.setPadding(0,0,0,0);


        //初始化设置高度为零
        this.addView(refreshView,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0));

        measure(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        measureHeight = getMeasuredHeight();

        EMPTY_HEIGHT = getEmptyHeight();
    }

    protected abstract int getEmptyHeight();

    public int getMeasureHeight(){
        L.d("measureHeight","measureHeight:"+measureHeight);
        return measureHeight;
    }

    protected abstract int getLayoutView();

    /**
     * 当手指下滑时被调用
     * */
    public void putDown(float dy){
        float countPullDown = getVisibleHeight() + dy/2;

        L.d("refresh_view","countPullDown:"+countPullDown+"dy:"+dy+"measureHeight:"+measureHeight+"EMPTY_HEIGHT:"+EMPTY_HEIGHT);
        if(countPullDown <0){
            setRefreshViewHeight(0);
            return;
        }
        if(countPullDown > measureHeight + EMPTY_HEIGHT){
            countPullDown = measureHeight + EMPTY_HEIGHT;
            setRefreshViewHeight((int) (countPullDown));
            return;
        }
        setRefreshViewHeight((int) (countPullDown));
        if(onRefreshViewPullDownListening != null){
            onRefreshViewPullDownListening.scrolled(dy);
            scrolled(dy);
        }
        if(currentState == RefreshViewState.IS_REFRESHING){
            return;
        }
       if(countPullDown >= measureHeight - 20){
            setState(RefreshViewState.RELEASE_TO_REFRESH);
            releaseToRefresh();
        } else if(countPullDown >=0){
            setState(RefreshViewState.NO_STATE);
            noState();
        }

    }

    protected abstract void scrolled(float dy);

    /**
     * 设置view 的高度
     * */
    private void setRefreshViewHeight(int dy) {
        ViewGroup.LayoutParams layoutParams = refreshView.getLayoutParams();
        layoutParams.height = dy;
        refreshView.setLayoutParams(layoutParams);

    }

    public void smoothScrollTo(int destHeight) {
        if(destHeight < 0){
            return;
        }
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(200).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setRefreshViewHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    public int getVisibleHeight() {
        return refreshView.getLayoutParams().height;
    }

    public int getTotalHeight(){
        return measureHeight + EMPTY_HEIGHT;
    }
}
