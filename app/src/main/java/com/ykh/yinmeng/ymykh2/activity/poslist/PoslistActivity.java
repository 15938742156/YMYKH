package com.ykh.yinmeng.ymykh2.activity.poslist;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.activity.shanghuzicaixiangqing.ShanghuzicaixiangqingActivity;
import com.ykh.yinmeng.ymykh2.model.ShanghuzicaiResponse;
import com.ykh.yinmeng.ymykh2.utils.GlideUtils;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.BasListView;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.OnRefreshListener;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.RefreshListView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class PoslistActivity extends BaseActivity implements View.OnClickListener, PoslistMVPView {
    private BasListView listview;
    private TextView tv_title;
    private LinearLayout ll_return;
    private SmartRefreshLayout refreshLayout;

    private List<ShanghuzicaiResponse.DataBeanX.DataBean> allDataList = new ArrayList<>();
    private List<ShanghuzicaiResponse.DataBeanX.DataBean> curList = new ArrayList<>();
    private CommonAdapter adapter;
    private Dialog loadingDialog;
    public PoslistPresenter<PoslistMVPView> presenter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new PoslistPresenterImp<>();
        presenter.onAttach(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.refreshData();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_shanghuzicai);

    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("商户自采");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        listview = (BasListView) findViewById(R.id.listview);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
        listview.setLoadingMoreEnable(true);
        listview.setPullToRefreshEnable(true);

        listview.setLayoutManager(new LinearLayoutManager(this));
        int space = 20;
        listview.addItemDecoration(new SpacesItemDecoration(space));
        adapter = new CommonAdapter<ShanghuzicaiResponse.DataBeanX.DataBean>(this, R.layout.activity_shanghuzicai_listview,curList) {
            @Override
            protected void convert(ViewHolder holder, ShanghuzicaiResponse.DataBeanX.DataBean dataBean, int position) {
                holder.setText(R.id.tv_goods_name, dataBean.getTitle());
                holder.setText(R.id.tv_attention, "关注度 "+dataBean.getGz_num());
                holder.setText(R.id.tv_rebate,dataBean.getDescription());
                holder.setText(R.id.tv_cash,"押金:￥"+String.valueOf(dataBean.getPrice()));
                GlideUtils.loadImageView(mContext, "http://wx.ykh9.com"+dataBean.getImg_feng(), (ImageView) holder.getView(R.id.img_tupian));
                holder.setText(R.id.tv_license,"支付牌照 "+dataBean.getPaizhao());
            }
        };
        listview.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    presenter.onToggleItem(PoslistActivity.this,position);
                Log.v("hhhhhhhhhhhhhhhhhh",position+""+curList.get(position).getId()+curList.get(position).getTitle()+curList.get(position).getPrice());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        refreshLayout.setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.refreshData();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                loadMore();
            }
        });
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        listview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        curList.clear();
//                        presenter.refreshData();
//                        adapter.notifyDataSetChanged();
//                        listview.onRefreshFinish();
//                    }
//                }, 1000);
            }

            @Override
            public void onLoadMoreData() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        loadMore();
                        listview.onRefreshFinish();
                    }
                }, 1000);
            }
        });
    }

    private void loadMore() {
        int size = curList.size();
        if (size == allDataList.size()) {
            refreshLayout.finishLoadMore(false);
            return;
        }
        for (int i = 0; i < 15; i++) {
            if(curList.size()<allDataList.size()){
                curList.add(allDataList.get(i + size));
            }
        }
        adapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore(1000);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                PoslistActivity.this.finish();
                break;
                default:
                    break;
        }
    }

    @Override
    public void getListSuccess(List<ShanghuzicaiResponse.DataBeanX.DataBean> jijulist) {
        this.allDataList.clear();
        allDataList.addAll(jijulist);
        curList.clear();
        loadMore();
    }

    @Override
    public void getListFailure(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void getAttentionSuccess() {
    }

    @Override
    public void getAttentionFailuye(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }


    @Override
    public void onItemRemoved(int position) {
        ShanghuzicaiResponse.DataBeanX.DataBean dataBean = curList.remove(position);
        allDataList.remove(dataBean);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position,curList.size());
    }

    @Override
    public void showDialog() {
//        loadingDialog.show();
    }

    @Override
    public void dismissDialog() {
//        if (loadingDialog != null && loadingDialog.isShowing()) {
//            loadingDialog.dismiss();
//        }
    }
}
