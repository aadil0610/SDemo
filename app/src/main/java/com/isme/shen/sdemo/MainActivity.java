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
import android.widget.TextView;

import com.isme.shen.sdemo.event.EventActivity;
import com.isme.shen.sdemo.srecycleview.SRecycleViewActivity;
import com.isme.shen.sdemo.ui.UiEntranceActivity;
import com.isme.shen.sdemo.visual.ViscidityWidgetActivity;
import com.isme.shen.sdemo.visual.VisualActivity;
import com.isme.shen.slibrary.recycleView.SRecycleView;
import com.isme.shen.slibrary.recycleView.SRecycleViewAdapter;

public class MainActivity extends AppCompatActivity {

    private Class[] clazz = {SRecycleViewActivity.class, UiEntranceActivity.class, VisualActivity.class,EventActivity.class};
    private SRecycleView sRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        sRecycleView = (SRecycleView) findViewById(R.id.s_recycleview);
    }

    private void initData() {

        DataAdapter dataAdapter = new DataAdapter(this);
        dataAdapter.setData(clazz);
        sRecycleView.setLayoutManager(new LinearLayoutManager(this));
        final SRecycleViewAdapter adapter = new SRecycleViewAdapter(this,dataAdapter);
        sRecycleView.setAdapter(adapter);
    }

    private class DataAdapter extends RecyclerView.Adapter{

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
            miHolder.tv.setText(data[position].getSimpleName());

            miHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.this.startActivity(new Intent(MainActivity.this,data[position]));
                }
            });
        }

        @Override
        public int getItemCount() {
            return data == null?0:data.length;
        }

        private Class[] data;
        public void setData(Class[] data) {
            this.data = data;
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
}
