package com.isme.shen.sdemo.visual;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.isme.shen.sdemo.R;

/**
 * Created by shen on 2016/8/25.
 */
public class EmptyFragment extends Fragment {

    private View view;
    private LinearLayout ll;
    private View innerView;
    private String bgColor;

    private int position;
    public EmptyFragment(int position) {
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_empty,null);
        ll = (LinearLayout) view.findViewById(R.id.ll);
        innerView = view.findViewById(R.id.view);
        Toast.makeText(getContext(),"position_color:"+position,Toast.LENGTH_SHORT).show();
//        int[] colcor = {R.color.colorGear5,R.color.colorGear1,
//                R.color.colorFefreshBg,R.color.colorGear2,R.color.cardview_shadow_start_color};
////        ll.setBackgroundColor(colcor[position]);
        innerView.setBackgroundColor(Color.parseColor(bgColor));
        return view;
    }


    public void setBgColcor(String color){
        this.bgColor = color;
    }
}
