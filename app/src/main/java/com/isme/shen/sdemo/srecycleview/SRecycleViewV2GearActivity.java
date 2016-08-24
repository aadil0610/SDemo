package com.isme.shen.sdemo.srecycleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.isme.shen.sdemo.R;
import com.isme.shen.slibrary.recycleView.LoadMoreView;
import com.isme.shen.slibrary.recycleView.RefreshView;
import com.isme.shen.slibrary.recycleView.RefreshViewAbs;
import com.isme.shen.slibrary.recycleView.SRecycleView;
import com.isme.shen.slibrary.recycleView.SRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class SRecycleViewV2GearActivity extends AppCompatActivity {

    private SRecycleView sRecycleView;
    private List list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srecycle_view_v2_gear);

        sRecycleView = (SRecycleView) findViewById(R.id.s_recycleview);

        list = new ArrayList();
        for(int i = 0;i<20;i++){
            list.add("");
        }
        DataAdapter dataAdapter = new DataAdapter(this);
        dataAdapter.setData(list);
        sRecycleView.setLayoutManager(new LinearLayoutManager(this));
        final SRecycleViewAdapter adapter = new SRecycleViewAdapter(this,dataAdapter);
        final RefreshV2GearView refreshView = new RefreshV2GearView(this);
        adapter.setHeaderView(refreshView);
        sRecycleView.setAdapter(adapter);

       refreshView.setOnRefreshListening(new RefreshViewAbs.OnRefreshViewListening() {
           @Override
           public void refresh() {
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshView.refreshComplete();
                            }
                        });
                    }
                },3000);
           }
       });
    }
}
