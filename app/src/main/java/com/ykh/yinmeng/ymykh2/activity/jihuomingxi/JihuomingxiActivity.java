package com.ykh.yinmeng.ymykh2.activity.jihuomingxi;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.model.JihuomingxiResponse;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.TimeUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.BasListView;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.OnRefreshListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class JihuomingxiActivity extends BaseActivity implements View.OnClickListener, JihuomingxiMVPView {
    private TextView tv_title;
    private LinearLayout ll_return;
    private BasListView listview;
    String string;

    private List<JihuomingxiResponse.DataBean> list = new ArrayList<>();
    private List<JihuomingxiResponse.DataBean> alllist = new ArrayList<>();
    private CommonAdapter adapter;
    private Dialog loadingDialog;
    public JihuomingxiPresenter<JihuomingxiMVPView> presenter;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new JihuomingxiPresenterImp<>();
        presenter.onAttach(this);

    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_jihuomingxi);

    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("激活明细");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        listview = (BasListView)findViewById(R.id.listview);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
        listview.setLayoutManager(new LinearLayoutManager(this));
        int space = 10;
        listview.addItemDecoration(new SpacesItemDecoration(space));
        adapter = new CommonAdapter<JihuomingxiResponse.DataBean>(this, R.layout.activity_jihuomingxi_listview,list) {
            @Override
            protected void convert(ViewHolder holder, JihuomingxiResponse.DataBean jihuoBean, int position) {
                if (jihuoBean.getName() != null){
                    holder.setText(R.id.tv_name, jihuoBean.getName());
                }else {
                    holder.setText(R.id.tv_name, "未认证");
                }
                holder.setText(R.id.tv_tel, jihuoBean.getTel());
                holder.setText(R.id.tv_title, jihuoBean.getTitle());
                string = TimeUtils.stampToDate(String.valueOf(jihuoBean.getAddtime()));
                holder.setText(R.id.tv_date, string);
                holder.setText(R.id.tv_sn,"机器编号："+jihuoBean.getXinghao());
                if (TextUtils.equals("3",String.valueOf(jihuoBean.getIsxian()))){
                    holder.setText(R.id.tv_status,"已激活");
                }else {
                    holder.setText(R.id.tv_status,"未激活");
                }
                
            }
        };
        listview.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.getData();
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

    @Override
    public void initData() {

    }

    private void loadMore() {
        int size = list.size();
        if (size == alllist.size()) {
            refreshLayout.finishLoadMore(false);
            return;
        }
        for (int i = 0; i < 10; i++) {
            if(list.size()<alllist.size()){
                list.add(alllist.get(i + size));
            }
        }
        adapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore(1000);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                JihuomingxiActivity.this.finish();
                break;
        }
    }

    @Override
    public void getListSuccess(List<JihuomingxiResponse.DataBean> jihuolist) {
        this.list.clear();
        list.addAll(jihuolist);
        adapter.notifyDataSetChanged();
        loadMore();
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
