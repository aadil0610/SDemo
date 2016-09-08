package com.isme.shen.sdemo.event;

import android.content.Context;
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

import com.facebook.drawee.backends.pipeline.Fresco;
import com.isme.shen.sdemo.R;
import com.isme.shen.sdemo.srecycleview.SRecycleViewActivity;
import com.isme.shen.sdemo.ui.UiEntranceActivity;
import com.isme.shen.sdemo.visual.VisualActivity;
import com.isme.shen.slibrary.recycleView.SRecycleView;
import com.isme.shen.slibrary.recycleView.SRecycleViewAdapter;

/**
 * Created by shen on 2016/8/30.
 */
public class EventActivity extends AppCompatActivity{
    private Class[] clazz = {RxJavaRxAndroidActivity.class,RetrofitActivity.class,OkHttpActivity.class, FrescoActivity.class};
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

//        MainActivity.this.startActivity(new Intent(MainActivity.this,SplashActivity.class));
//        overridePendingTransition(R.anim.anim_activity_fade_in,-1);

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
                    EventActivity.this.startActivity(new Intent(EventActivity.this,data[position]));
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
