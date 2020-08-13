package com.ykh.yinmeng.ymykh2.activity.yaoqing;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.model.BackgroundResponse;
import com.ykh.yinmeng.ymykh2.utils.GlideUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import java.util.List;

import static com.ykh.yinmeng.ymykh2.app.App.getContext;


public class YaoqingActivity extends BaseActivity implements YaoqingMVPView {
    private LinearLayout ll_return;
    private TextView tv_title;
    private RollPagerView viewPager;

    public YaoqingPresenter<YaoqingMVPView> presenter;
    RollLoopPagerViewAdapter logoAdapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new YaoqingPresenterImp<>();
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_yaoqing);
    }

    @Override
    public void initViews() {
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("点击图片选择分享的背景");
        viewPager = (RollPagerView) findViewById(R.id.viewPager);

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YaoqingActivity.this.finish();
            }
        });
        viewPager.setAdapter(logoAdapter = new RollLoopPagerViewAdapter(viewPager));//设置适配器
        viewPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                presenter.onToggleItem(YaoqingActivity.this,position);
            }
        });
    }


    @Override
    public void initData() {

    }

    /**
     * 图片轮播
     * @author Angel
     *
     */
    private class RollLoopPagerViewAdapter extends LoopPagerAdapter {
        private int[] imgs = {0};
        private List<BackgroundResponse.DataBean> list = null;

        public RollLoopPagerViewAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        public void setData(List<BackgroundResponse.DataBean> logoList) {
            this.list = logoList;
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            if (list == null) {
                view.setImageResource(imgs[position]);
                view.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {
                GlideUtils.loadImageViewCrop(getContext(), list.get(position % list.size()).getLogo(), view);

            }
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getRealCount() {
            return list == null ? imgs.length : list.size();
        }


    }

    @Override
    public void getListSuccess(List<BackgroundResponse.DataBean> backlist) {
        logoAdapter.setData(backlist);
        logoAdapter.notifyDataSetChanged();
    }

    @Override
    public void getDataFailure(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {

    }
}
