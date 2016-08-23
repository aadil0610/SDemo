package com.isme.shen.slibrary.recycleView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shen on 2016/8/16.
 */
public class SRecycleViewAdapter extends RecyclerView.Adapter {

    private static final int TYPE_HEADER_REFRESH = 1;
    private static final int TYPE_NORMAL = 2;
    private static final int TYPE_LOAD_MORE = 3;
    private Context context;
    private RefreshViewAbs refreshView;
    private LoadMoreViewAbs loadMoreView;

    private RecyclerView.Adapter innerAdapter;
    public SRecycleViewAdapter(Context context, RecyclerView.Adapter innerAdapter) {
        this.context = context;
        setAdapter(innerAdapter);
    }

    public LoadMoreViewAbs getLoadMoreView() {
        return loadMoreView;
    }

    public void setLoadMoreView(LoadMoreViewAbs loadMoreView) {
        this.loadMoreView = loadMoreView;
    }

    public void setHeaderView(RefreshViewAbs refreshView) {
        this.refreshView = refreshView;
    }

    public RefreshViewAbs getRefreshView(){
        return refreshView;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HEADER_REFRESH;
        }else if(position ==getItemCount() -1){
            return TYPE_LOAD_MORE;
        } else{
            return innerAdapter.getItemViewType(position+1);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER_REFRESH && refreshView != null){
            return new HeaderHolder(refreshView);
        } else if(viewType == TYPE_LOAD_MORE && loadMoreView != null){
            return new HeaderHolder(loadMoreView);
        }
        return innerAdapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(refreshView != null && position == 0){
            return;
        } else if(loadMoreView != null && position == getItemCount() -1 ){
            return;
        }
        int newPosition = 0;
        if(refreshView != null){
            newPosition --;
        }
        newPosition = holder.getLayoutPosition() + newPosition;
        innerAdapter.onBindViewHolder(holder,newPosition);
    }

    @Override
    public int getItemCount() {
        int count = innerAdapter.getItemCount();
        if(refreshView != null){
            count ++;
        }
        if(loadMoreView != null){
            count ++;
        }
        return count;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {

        if (adapter != null) {
            if (!(adapter instanceof RecyclerView.Adapter))
                throw new RuntimeException("your adapter must be a RecyclerView.Adapter");
        }

        if (innerAdapter != null) {
            notifyItemRangeRemoved(getFristNormalItem(), innerAdapter.getItemCount());
            innerAdapter.unregisterAdapterDataObserver(mDataObserver);
        }

        this.innerAdapter = adapter;
        innerAdapter.registerAdapterDataObserver(mDataObserver);
        notifyItemRangeInserted(getFristNormalItem(), innerAdapter.getItemCount());

    }

    private int getFristNormalItem() {
        if(refreshView != null){
            return 1;
        }
        return 0;
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 监听传经来的adapter的变化，通知自己随之变化
     * */
    public RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {

        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            notifyItemRangeChanged(positionStart +  getFristNormalItem(), itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            notifyItemRangeInserted(positionStart +getFristNormalItem(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            notifyItemRangeRemoved(positionStart +  getFristNormalItem(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            notifyItemRangeChanged(fromPosition +  getFristNormalItem(), toPosition + getFristNormalItem()+ itemCount);
        }
    };

}

