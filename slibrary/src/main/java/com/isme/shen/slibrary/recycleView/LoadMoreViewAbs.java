package com.isme.shen.slibrary.recycleView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.isme.shen.slibrary.utils.L;

/**
 * Created by shen on 2016/8/19.
 */
public abstract class LoadMoreViewAbs extends LinearLayout{

    private int EMPTY_HEIGHT = 0;//刷新底部空白高度
    private static final int ISLOADMORING = 1000;
    private static final int NORMAL = 1001;
    private int currentState = NORMAL;

    protected LinearLayout loadMoreView;
    protected Context context;
    private int measureHeight;
//    private boolean isLoadMore = true;//是否可以继续加载

    private OnLoadMoreListener onLoadMoreListener;

    public void loadMoreComplete(boolean isLoadMore){
        loadMoreView.setEnabled(isLoadMore);
        loadMoreCompleteSub(isLoadMore);
        setCurrentState(NORMAL);
    }

    protected abstract void loadMoreCompleteSub(boolean allTouch);

    public interface OnLoadMoreListener{
        void onLoadMoreClick();
    }
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public LoadMoreViewAbs(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        loadMoreView = (LinearLayout) LayoutInflater.from(context).inflate(getLayoutView(), null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,0);
        this.setLayoutParams(params);
        this.setPadding(0,0,0,0);


        //初始化设置高度为零
        this.addView(loadMoreView,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));

        measure(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        measureHeight = getMeasuredHeight();

        EMPTY_HEIGHT = getEmptyHeight();

        loadMoreView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onLoadMoreListener!= null && currentState != ISLOADMORING){
                    onLoadMoreListener.onLoadMoreClick();
                    setCurrentState(ISLOADMORING);
                    loadMoring();
                }
            }
        });
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public void mationActionUp(float rawY) {
        if(getVisibleHeight() > measureHeight){
            smoothScrollTo(measureHeight);
        }
    }

    public int getMeasureHeight(){
        L.d("measureHeight","measureHeight:"+measureHeight);
        return measureHeight;
    }

    /**
     * 当手指下滑时被调用
     * */
    public void putDown(float dy){
        float countPullDown = getVisibleHeight() - dy/2;

        L.d("refresh_view","countPullDown:"+countPullDown+"dy:"+dy+"measureHeight:"+measureHeight+"EMPTY_HEIGHT:"+EMPTY_HEIGHT);
        if(countPullDown < getMeasureHeight()){
            setRefreshViewHeight(getMeasureHeight());
            return;
        }
        if(countPullDown > measureHeight + EMPTY_HEIGHT){
            countPullDown = measureHeight + EMPTY_HEIGHT;
            return;
        }
        setRefreshViewHeight((int) (countPullDown));
    }

    /**
     * 设置view 的高度
     * */
    private void setRefreshViewHeight(int dy) {
        ViewGroup.LayoutParams layoutParams = loadMoreView.getLayoutParams();
        layoutParams.height = dy;
        loadMoreView.setLayoutParams(layoutParams);

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
        return loadMoreView.getLayoutParams().height;
    }

    public int getTotalHeight(){
        return measureHeight + EMPTY_HEIGHT;
    }

    protected abstract void loadMoring();

    protected abstract int getEmptyHeight();

    protected abstract int getLayoutView();
}
