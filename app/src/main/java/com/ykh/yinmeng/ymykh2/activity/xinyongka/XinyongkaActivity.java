package com.ykh.yinmeng.ymykh2.activity.xinyongka;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.ykh.yinmeng.ymykh2.activity.xinyongkajindu.XinyongkajinduActivity;
import com.ykh.yinmeng.ymykh2.model.NewXinyongkaResponse;
import com.ykh.yinmeng.ymykh2.model.XinyongkaResponse;
import com.ykh.yinmeng.ymykh2.utils.GlideUtils;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.BasListView;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.OnRefreshListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class XinyongkaActivity extends BaseActivity implements View.OnClickListener,XinyongkaMVPView {
    private LinearLayout ll_return,ll_menu;
    private BasListView listview;
    private TextView tv_guanzhu,tv_menu,tv_title;
    private int page = 1;
    private int totalpage;

    private List<NewXinyongkaResponse.DataBean.ListBean> list = new ArrayList<>();
    private List<NewXinyongkaResponse.DataBean.ListBean> alllist = new ArrayList<>();
    private CommonAdapter adapter;
    private Dialog loadingDialog;
    public XinyongkaPresenter<XinyongkaMVPView> presenter;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new XinyongkaPresenterImp<>();
        presenter.onAttach(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.Data(1);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_xinyongka);

    }

    @Override
    public void initViews() {
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
//        ll_menu = (LinearLayout) findViewById(R.id.ll_menu);
        listview = (BasListView) findViewById(R.id.listview);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("信用卡");
//        tv_menu = (TextView)findViewById(R.id.tv_menu);
//        tv_menu.setText("进度查询");
//        ll_menu.setVisibility(View.VISIBLE);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
        listview.setLayoutManager(new LinearLayoutManager(this));
        int space = 20;
        listview.addItemDecoration(new SpacesItemDecoration(space));
        adapter = new CommonAdapter<NewXinyongkaResponse.DataBean.ListBean>(this, R.layout.activity_xinyongka_listview,list) {
            @Override
            protected void convert(ViewHolder holder, NewXinyongkaResponse.DataBean.ListBean dataBean, int position) {
                holder.setText(R.id.tv_cardName, dataBean.getName());
                double jiesuan = Double.valueOf(dataBean.getCo_amount());
                double fanli = jiesuan - 10;
                holder.setText(R.id.tv_tuiguang, "推广返利"+String.valueOf(fanli)+"元");
                holder.setText(R.id.tv_money,"￥0.00");
                holder.setText(R.id.tv_guanzhu,dataBean.getTag());
                holder.setOnClickListener(R.id.bt_shenqing,XinyongkaActivity.this);
                holder.getView(R.id.bt_shenqing).setTag(position);
                GlideUtils.loadImageView(mContext, "https://img.sjlr365.com"+dataBean.getLogo_img(), (ImageView) holder.getView(R.id.img_logo));

            }
        };
        listview.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                presenter.onToggleItem(XinyongkaActivity.this,position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        refreshLayout.setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.Data(1);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                presenter.Data(page);
                loadMore();
            }
        });
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        tv_menu.setOnClickListener(this);
        listview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
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
        if (totalpage > page){
            page++;
        }else {
            page = totalpage-1;
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
                XinyongkaActivity.this.finish();
                break;
            case R.id.bt_shenqing:
                presenter.onToggleItem(this,(int)v.getTag());
                break;
            case R.id.tv_menu:
//                intent = new Intent(XinyongkaActivity.this, XinyongkajinduActivity.class);
//                startActivity(intent);
                break;
                default:
                    break;
        }
    }

    @Override
    public void getListSuccess(List<NewXinyongkaResponse.DataBean.ListBean> shanghulist) {
        this.list.clear();
        list.addAll(shanghulist);
        adapter.notifyDataSetChanged();
        loadMore();
    }

    @Override
    public void getListFailure(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void getTotalpage(int page) {
        this.totalpage = page;
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
        list.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position,list.size());
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
