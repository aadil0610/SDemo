package com.isme.shen.sdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isme.shen.sdemo.event.EventActivity;
import com.isme.shen.sdemo.srecycleview.SRecycleViewActivity;
import com.isme.shen.sdemo.ui.UiEntranceActivity;
import com.isme.shen.sdemo.visual.VisualActivity;
import com.isme.shen.slibrary.recycleView.SRecycleView;
import com.isme.shen.slibrary.recycleView.SRecycleViewAdapter;

import java.lang.reflect.Array;

/**
 * Created by shen on 2016/8/30.
 */
public abstract  class BaseButtonListActivity extends AppCompatActivity {

    private SRecycleView sRecycleView;
    protected RelativeLayout rlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        sRecycleView = (SRecycleView) findViewById(R.id.s_recycleview);
        rlContent = (RelativeLayout) findViewById(R.id.rl_content);
    }

    private void initData() {

        DataAdapter dataAdapter = new DataAdapter(this);
        sRecycleView.setLayoutManager(new LinearLayoutManager(this));
        final SRecycleViewAdapter adapter = new SRecycleViewAdapter(this,dataAdapter);
        sRecycleView.setAdapter(adapter);
    }

    private class DataAdapter<T extends Array> extends RecyclerView.Adapter{

        private Context context;
        public DataAdapter(Context context) {
            this.context = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_main, null);
            MiHolder miHolder = new MiHolder(view);
            return miHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            MiHolder miHolder = (MiHolder) holder;
            miHolder.tv.setText(getBtnName(position));

            miHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BaseButtonListActivity.this.onClick(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return getCount();
        }

        private class MiHolder extends RecyclerView.ViewHolder{

            TextView tv;
            public MiHolder(View view) {
                super(view);
                init(view);
            }
            private void init(View view) {
                tv = (TextView) view.findViewById(R.id.tv_name);
            }
        }
    }

    protected abstract int getCount();

    protected abstract void onClick(int position);

    protected abstract String getBtnName(int position);

}
