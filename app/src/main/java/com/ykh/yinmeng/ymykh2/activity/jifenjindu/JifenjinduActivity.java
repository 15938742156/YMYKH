package com.ykh.yinmeng.ymykh2.activity.jifenjindu;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.model.JifenjinduResponse;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.TimeUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.BasListView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JifenjinduActivity extends BaseActivity implements View.OnClickListener,JifenjinduMVPView {
    private LinearLayout ll_return;
    private TextView tv_title,tv_time1,tv_time2;
    private BasListView listview;
    private SmartRefreshLayout refreshLayout;
    private RelativeLayout rl_confirm;

    private List<JifenjinduResponse.DataBeanX.DataBean> itemlist = new ArrayList<>();
    private CommonAdapter adapter;
    private Dialog loadingDialog;
    public JifenjinduPresenter<JifenjinduMVPView> presenter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new JifenjinduPresenterImp<>();
        presenter.onAttach(this);
        presenter.onConfirm(tv_time1.getText().toString(),tv_time2.getText().toString());
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_jindu);
    }

    @Override
    public void initViews() {
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("审核进度");
        listview = (BasListView) findViewById(R.id.listview);
        tv_time1 = (TextView) findViewById(R.id.tv_time1);
        tv_time2 = (TextView) findViewById(R.id.tv_time2);
        rl_confirm = (RelativeLayout) findViewById(R.id.rl_confirm);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        listview.setLayoutManager(new LinearLayoutManager(this));
        int space = 20;
        listview.addItemDecoration(new SpacesItemDecoration(space));
        adapter = new CommonAdapter<JifenjinduResponse.DataBeanX.DataBean>(this, R.layout.activity_jindu_listview,itemlist) {
            @Override
            protected void convert(ViewHolder holder, final JifenjinduResponse.DataBeanX.DataBean dataBean, int position) {

                if (dataBean != null){
                    holder.setText(R.id.tv_title, dataBean.getTitle());
                    holder.setText(R.id.tv_time, dataBean.getCTime());
                    if (dataBean.getType() == 1){
                        holder.setText(R.id.name,dataBean.getGoodsName()+"(自助)");
                        holder.setVisible(R.id.tv_address,false);
                    }
                    if (dataBean.getType() == 2){
                        holder.setVisible(R.id.tv_address,true);
                        holder.setText(R.id.name,dataBean.getGoodsName()+"(实物)");
                        holder.setText(R.id.tv_address,"地址："+dataBean.getAddress()+"(点击复制地址)");
                        holder.setOnClickListener(R.id.tv_address, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ClipboardManager copy;
                                copy = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                                copy.setText(dataBean.getAddress());
                                ToastUtils.showToast(mContext, "复制成功", Toast.LENGTH_SHORT);
                            }
                        });
                    }if (dataBean.getType() == 3) {
                        holder.setText(R.id.name,dataBean.getGoodsName()+"(客服)");
                        holder.setVisible(R.id.tv_address,false);
                    }
                    holder.setText(R.id.tv_integral,"交易积分："+String.valueOf(dataBean.getNum())+"积分");
                    holder.setText(R.id.tv_money,"可收益金额："+dataBean.getPrice()+"元");
                    if (dataBean.getStatus() == 1){
                        holder.setText(R.id.tv_status,"审核成功");
                        holder.setVisible(R.id.tv_cause,false);
                    }
                    if (dataBean.getStatus() == 2){
                        holder.setText(R.id.tv_status,"审核失败");
                        holder.setVisible(R.id.tv_cause,true);
                        holder.setText(R.id.tv_cause,"失败原因："+dataBean.getCause());
                    }if (dataBean.getStatus() == 0){
                        holder.setText(R.id.tv_status,"审核中");
                        holder.setVisible(R.id.tv_cause,false);
                    }
                }else {
                    ToastUtils.showToast(mContext, "暂无数据", Toast.LENGTH_SHORT);
                }

            }
        };
        listview.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.onConfirm(tv_time1.getText().toString(),tv_time2.getText().toString());
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
        tv_time1.setOnClickListener(this);
        tv_time2.setOnClickListener(this);
        rl_confirm.setOnClickListener(this);

    }

    @Override
    public void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                JifenjinduActivity.this.finish();
                break;
            case R.id.tv_time1:
                presenter.onsTimeBt(this);
                break;
            case R.id.tv_time2:
                presenter.oneTimeBt(this);
                break;
            case R.id.rl_confirm:
                if (tv_time1.getText().toString().isEmpty() || tv_time2.getText().toString().isEmpty()){
                    ToastUtils.showToast(mContext, "请选择筛选时间段", Toast.LENGTH_SHORT);
                }else {
                    presenter.onConfirm(tv_time1.getText().toString(),tv_time2.getText().toString());
                }
                break;
                default:
                    break;
        }
    }

    @Override
    public void showsTimeSuccess(String time) {
        tv_time1.setText(time);
    }
    @Override
    public void showeTimeSuccess(String time) {
        tv_time2.setText(time);
    }

    @Override
    public void getListSuccess(List<JifenjinduResponse.DataBeanX.DataBean> list) {
        this.itemlist.clear();
        itemlist.addAll(list);
        adapter.notifyDataSetChanged();
//        adapter.setHasStableIds(true);
    }

    @Override
    public void getListFailure(String msg) {
        ToastUtils.showToast(mContext, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showDialog() {
        loadingDialog.show();
    }

    @Override
    public void dismissDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }




}
