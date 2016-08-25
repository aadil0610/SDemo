package com.isme.shen.sdemo.visual;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.isme.shen.sdemo.R;
import com.isme.shen.slibrary.view.GooView;

import java.util.ArrayList;
import java.util.List;

public class ViscidityWidgetActivity extends AppCompatActivity {

    private GooView mGooView;
    private RelativeLayout rl;
    private ViewPager viewPager;
    private EmptyFragment emptyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viscidity_widget);

        viewPager = (ViewPager) findViewById(R.id.view_page);

        mGooView = new GooView(this);
        rl = (RelativeLayout) findViewById(R.id.rl);
        mGooView.setmMaxDistance(100);
        mGooView.setmStaicCircleCenter(80);
        rl.addView(mGooView);
        
        initData();
    }

    private void initData() {



        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return new EmptyFragment(position);
            }

            @Override
            public int getCount() {
                return 5;
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
