package com.isme.shen.sdemo.visual;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.isme.shen.sdemo.R;
import com.isme.shen.slibrary.view.BezierGuidePoint;
import com.isme.shen.slibrary.view.GuidePoint;

import java.util.ArrayList;
import java.util.List;

public class LoadingActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private GuidePoint guidePoint;
    private BezierGuidePoint bezierGuidePoint;
    private EmptyFragment emptyFragment;
    private List<EmptyFragment> fragmentList;
    String[] colcor = {"#0F6CC4", "#F7057C", "#2C7E0F", "#F52C03", "#5D085A"};
    private MiAdapter miAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        
        initView();
        initData();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_page);
        guidePoint = (GuidePoint) findViewById(R.id.guide_point);
        bezierGuidePoint = (BezierGuidePoint) findViewById(R.id.bezier_point);

    }


    private void initData() {


        //point初始化
        guidePoint.setNum(5);
        guidePoint.setRadius(10);
        guidePoint.setInterval(20);

        //point初始化
        bezierGuidePoint.setNum(5);
        bezierGuidePoint.setRadius(10);
        bezierGuidePoint.setInterval(20);

        //模拟fragment
        fragmentList = new ArrayList();
        for (int i = 0; i < 5; i++) {
            EmptyFragment emptyFragment = new EmptyFragment(i);
            fragmentList.add(emptyFragment);
            emptyFragment.setBgColcor(colcor[i]);
        }

        //viewpager适配器
        miAdapter = new MiAdapter(getSupportFragmentManager());
        viewPager.setAdapter(miAdapter);
        viewPager.setOnPageChangeListener(new MiListener());
    }


    private class MiAdapter extends FragmentPagerAdapter {

        public MiAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    private class MiListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            guidePoint.setPercent(position + positionOffset);
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
