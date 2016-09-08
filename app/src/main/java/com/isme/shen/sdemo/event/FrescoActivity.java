package com.isme.shen.sdemo.event;

import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.isme.shen.sdemo.Const;
import com.isme.shen.sdemo.R;
import com.isme.shen.sdemo.event.http.fresco.PictureBean;
import com.isme.shen.sdemo.event.http.fresco.PictureWrap;
import com.isme.shen.sdemo.event.http.retrofit.RetrofitManager;
import com.isme.shen.slibrary.http.ServiceSubscribe;
import com.isme.shen.slibrary.recycleView.ISRecycleView;
import com.isme.shen.slibrary.recycleView.LoadMoreView;
import com.isme.shen.slibrary.recycleView.LoadMoreViewAbs;
import com.isme.shen.slibrary.recycleView.SRecycleView;
import com.isme.shen.slibrary.recycleView.SRecycleViewAdapter;
import com.isme.shen.slibrary.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shen on 2016/9/8.
 */
public class FrescoActivity extends AppCompatActivity {

    private SRecycleView sRecycleView;
    private MiDataAdapter dataAdapter;
    private List<PictureBean> pictures;
    private SRecycleViewAdapter sRecycleViewAdapter;
    private LoadMoreView loadMoreView;
    private SimpleDraweeView simpleDraweeView;
    private RelativeLayout rlSimple;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);

        sRecycleView = (SRecycleView) findViewById(R.id.s_recycleview);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.simple_view);
        rlSimple = (RelativeLayout) findViewById(R.id.rl_simple);

        initData();
    }

    private void initData() {
        pictures = new ArrayList<PictureBean>();
        getData();
        sRecycleView.setLayoutManager(new GridLayoutManager(this,2));
        dataAdapter = new MiDataAdapter();
        sRecycleViewAdapter = new SRecycleViewAdapter(this, dataAdapter);
        sRecycleView.setAdapter(sRecycleViewAdapter);
        loadMoreView = new LoadMoreView(this);
        sRecycleViewAdapter.setLoadMoreView(loadMoreView);

        loadMoreView.setOnLoadMoreListener(new LoadMoreViewAbs.OnLoadMoreListener() {
            @Override
            public void onLoadMoreClick() {
                getData();
            }
        });

        sRecycleView.setOnSRecycleViewScrollListening(new ISRecycleView.OnSRecycleViewScrollListening() {
            @Override
            public void onScrolled(int dx, int dy) {
                rlSimple.setVisibility(View.GONE);
            }
        });

        simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlSimple.setVisibility(View.GONE);
            }
        });
    }

    private void getData() {
        RetrofitManager.getInstance().changedUrlTemp(Const.PICTURE_URL);
        new PictureWrap(this)
                .obtainPictures(10)
                .subscribe(new ServiceSubscribe<List<PictureBean>>(this) {

                    @Override
                    public void onNext(List<PictureBean> pictureBeen) {
                        pictures.addAll(pictureBeen);
                        dataAdapter.notifyDataSetChanged();
                        loadMoreView.loadMoreComplete(true);
//                        ToastUtils.showShort(FrescoActivity.this,pictureBeen.get(0).toString());
                    }
                });
        RetrofitManager.getInstance().reset();
    }

    private class MiDataAdapter extends RecyclerView.Adapter{
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(FrescoActivity.this).inflate(R.layout.item_fresco, null);
            MiHolder miHolder = new MiHolder(view);
            return miHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            MiHolder miHolder = (MiHolder) holder;
            Uri uri = Uri.parse(pictures.get(position).getPicUrl());
            miHolder.simpleDraweeView.setImageURI(uri);

            miHolder.tvDesc.setText(pictures.get(position).getTitle());

            miHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rlSimple.setVisibility(View.VISIBLE);
                    ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(pictures.get(position).getPicUrl()))
                            .setProgressiveRenderingEnabled(true)
                            .build();
                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(request)
                            .setOldController(simpleDraweeView.getController())
                            .build();
                    simpleDraweeView.setController(controller);
                }
            });
        }

        @Override
        public int getItemCount() {
            return pictures == null?0:pictures.size();
        }

        private class MiHolder extends RecyclerView.ViewHolder {

            SimpleDraweeView simpleDraweeView;
            TextView tvDesc;
            public MiHolder(View itemView) {
                super(itemView);
                simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.simple_drawee);
                tvDesc = (TextView) itemView.findViewById(R.id.tv_des);
            }
        }
    }
}
