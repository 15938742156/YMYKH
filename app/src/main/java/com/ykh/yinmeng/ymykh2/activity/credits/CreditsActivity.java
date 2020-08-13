package com.ykh.yinmeng.ymykh2.activity.credits;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.ykh.yinmeng.ymykh2.activity.jifenguanli.JifenguanliActivity;
import com.ykh.yinmeng.ymykh2.model.CreditsResponse;
import com.ykh.yinmeng.ymykh2.utils.GlideUtils;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CreditsActivity extends BaseActivity implements View.OnClickListener,CreditsMVPView {
    private LinearLayout ll_return,ll_menu;
    private TextView tv_title,tv_menu;
    private RecyclerView listview;

    private List<CreditsResponse.DataBean> creditslist = new ArrayList<>();
    private CommonAdapter adapter;
    private Dialog loadingDialog;
    public CreditsPresenter<CreditsMVPView> presenter;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new CreditsPresenterImp<>();
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_credits);
    }

    @Override
    public void initViews() {
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        ll_menu = (LinearLayout) findViewById(R.id.ll_menu);
        ll_menu.setVisibility(View.VISIBLE);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("积分兑换");
        tv_menu = (TextView) findViewById(R.id.tv_menu);
        tv_menu.setText("积分管理");
        listview = (RecyclerView) findViewById(R.id.listview);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");

        listview.setLayoutManager(new LinearLayoutManager(this));
        int space = 20;
        listview.addItemDecoration(new SpacesItemDecoration(space));
        adapter = new CommonAdapter<CreditsResponse.DataBean>(this, R.layout.activity_credits_listview,creditslist) {
            @Override
            protected void convert(ViewHolder holder, CreditsResponse.DataBean dataBean, int position) {
                holder.setText(R.id.tv_name, dataBean.getTitle());
                GlideUtils.loadImageView(mContext, dataBean.getLogo(), (ImageView) holder.getView(R.id.img_card));
                holder.setText(R.id.tv_price,"最高兑换价格："+dataBean.getRatio()+"元/万积分");
            }
        };
        listview.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                presenter.onToggleItem(CreditsActivity.this,position);

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        refreshLayout.setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.onAttach(CreditsActivity.this);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        ll_menu.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                CreditsActivity.this.finish();
                break;
            case R.id.ll_menu:
                presenter.onToggleTianjiaBtn(this);
                break;
                default:
                    break;
        }
    }

    @Override
    public void getListSuccess(List<CreditsResponse.DataBean> list) {
        this.creditslist.clear();
        creditslist.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getListFailure(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
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
