package com.isme.shen.sdemo.srecycleview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isme.shen.sdemo.R;
import com.isme.shen.slibrary.recycleView.SRecycleView;
import com.isme.shen.slibrary.recycleView.SRecycleViewAdapter;

/**
 * 一步一步的封装自己的RecycleView
 * Created by shen on 2016/8/24.
 */
public class SRecycleViewActivity extends AppCompatActivity {

    private Class[] clazz = {SRecycleViewV1Activity.class};
    private SRecycleView sRecycleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_recycle_view);
        
        initView();
        initData();
    }

    private void initView() {
        sRecycleView = (SRecycleView) findViewById(R.id.s_recycleview);
    }


    private void initData() {

        MiAdapter miAdapter = new MiAdapter();
        SRecycleViewAdapter sRecycleViewAdapter = new SRecycleViewAdapter(this,miAdapter);
        sRecycleView.setLayoutManager(new LinearLayoutManager(this));
        sRecycleView.setAdapter(sRecycleViewAdapter);

    }

    private class MiAdapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(SRecycleViewActivity.this).inflate(R.layout.item_main, null);
            MiHolder miHolder = new MiHolder(view);
            return miHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            MiHolder miHolder = (MiHolder) holder;
            switch (position){
                case 0:
                    miHolder.tv.setText("Normal SRecycleView");
                    break;
                case 1:
                    break;
            }

            miHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(SRecycleViewActivity.this,clazz[position]));
                }
            });
        }

        @Override
        public int getItemCount() {
            return clazz.length;
        }
    }

    private class MiHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public MiHolder(View view) {
            super(view);

            tv = (TextView) view.findViewById(R.id.tv_name);
        }
    }
}
