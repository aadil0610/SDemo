package com.isme.shen.sdemo.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.isme.shen.sdemo.R;

/**
 * Created by shen on 2016/8/31.
 */
public class EventDetailActivity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        
        initView();
        initData();
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.text);
    }

    private void initData() {

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String detailResult = bundle.getString("detail_result");
            textView.setText(detailResult);
        } else {
            textView.setText("bundle为空");
        }
    }
}
