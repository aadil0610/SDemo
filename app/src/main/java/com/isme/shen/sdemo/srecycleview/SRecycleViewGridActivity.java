package com.isme.shen.sdemo.srecycleview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.isme.shen.sdemo.R;
import com.isme.shen.slibrary.recycleView.LoadMoreView;
import com.isme.shen.slibrary.recycleView.RefreshView;
import com.isme.shen.slibrary.recycleView.SRecycleView;
import com.isme.shen.slibrary.recycleView.SRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shen on 2016/9/7.
 */
public class SRecycleViewGridActivity extends AppCompatActivity{

    private SRecycleView sRecycleView;
    private List list;
    private LoadMoreView loadMoreView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_recycle_view);
        sRecycleView = (SRecycleView) findViewById(R.id.s_recycleview);

        initData();
    }

    private void initData() {

        list = new ArrayList();
        for(int i = 0;i<20;i++){
            list.add("");
        }

        DataAdapter dataAdapter = new DataAdapter(this);
        dataAdapter.setData(list);
        sRecycleView.setLayoutManager(new GridLayoutManager(this,2));
        final SRecycleViewAdapter adapter = new SRecycleViewAdapter(this,dataAdapter);
        RefreshView refreshView = new RefreshView(this);
        adapter.setHeaderView(refreshView);
        loadMoreView = new LoadMoreView(this);
        adapter.setLoadMoreView(loadMoreView);

        sRecycleView.setAdapter(adapter);

    }
}
