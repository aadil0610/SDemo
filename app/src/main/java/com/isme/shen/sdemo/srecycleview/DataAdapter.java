package com.isme.shen.sdemo.srecycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.isme.shen.sdemo.R;

import java.util.List;

/**
 * 通用adapter
 * Created by shen on 2016/8/24.
 */
public class DataAdapter extends RecyclerView.Adapter{

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
        miHolder.tv.setText("position:"+position);

        miHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"position:"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    private List data;
    public void setData(List data) {
        this.data = data;
    }

    public class MiHolder extends RecyclerView.ViewHolder{

        TextView tv;
        public MiHolder(View view) {
            super(view);

            tv = (TextView) view.findViewById(R.id.tv_name);
        }
    }
}