package com.ykh.yinmeng.ymykh2.activity.jifenshouzhi;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.model.ItemcardResponse;
import com.ykh.yinmeng.ymykh2.model.JifenShouzhiResponse;
import com.ykh.yinmeng.ymykh2.model.JifenjinduResponse;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.TimeUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.BasListView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class JifenshouzhiActivity extends BaseActivity implements View.OnClickListener, JifenshouzhiMVPView {
    private LinearLayout ll_return;
    private TextView tv_title,tv_time1,tv_time2;
    private BasListView listview;
    private SmartRefreshLayout refreshLayout;
    private RelativeLayout rl_confirm;

    private List<JifenShouzhiResponse.DataBeanX.DataBean> itemlist = new ArrayList<>();
    private List<JifenShouzhiResponse.DataBeanX.DataBean> alllist = new ArrayList<>();
    private CommonAdapter adapter;
    private Dialog loadingDialog;
    public JifenshouzhiPresenter<JifenshouzhiMVPView> presenter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new JifenshouzhiPresenterImp<>();
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
        tv_title.setText("收支明细");
        listview = (BasListView) findViewById(R.id.listview);
        tv_time1 = (TextView) findViewById(R.id.tv_time1);
        tv_time2 = (TextView) findViewById(R.id.tv_time2);
        rl_confirm = (RelativeLayout) findViewById(R.id.rl_confirm);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        listview.setLayoutManager(new LinearLayoutManager(this));
        int space = 20;
        listview.addItemDecoration(new SpacesItemDecoration(space));
        adapter = new CommonAdapter<JifenShouzhiResponse.DataBeanX.DataBean>(this, R.layout.activity_shouzhi_listview,itemlist) {
            @Override
            protected void convert(ViewHolder holder, JifenShouzhiResponse.DataBeanX.DataBean dataBean, int position) {
                if (dataBean != null){
                    //1购买vip返利，2兑换积分，3下级购买分润，4提现
                    if (dataBean.getType() == 1){
                        holder.setText(R.id.tv_title,dataBean.getDesc());
                        holder.setText(R.id.tv_time,dataBean.getCreatetime());
                        if (dataBean.getLevel() == 1){
                            holder.setText(R.id.tv_integral,"会员充值类型：黄金会员");
                        }
                        if (dataBean.getLevel() == 2){
                            holder.setText(R.id.tv_integral,"会员充值类型：钻石会员");
                        }else {
                            holder.setText(R.id.tv_integral,"会员充值类型：普通会员");
                        }
                        if (dataBean.getName() != null){
                            holder.setText(R.id.tv_name,"下级会员： "+dataBean.getName());
                        }else {
                            holder.setText(R.id.tv_name,"下级会员： 未实名认证");
                        }
                        holder.setText(R.id.tv_tel,dataBean.getTel());
                        holder.setText(R.id.tv_money,"+"+dataBean.getNum()+"元");
                    }
                    if (dataBean.getType() == 2){
                        holder.setText(R.id.tv_title, dataBean.getGoodsName());
                        holder.setText(R.id.tv_integral,"交易积分： "+String.valueOf(dataBean.getCoin())+"积分");
                        holder.setText(R.id.tv_name,"银行： "+dataBean.getTitle());
                        holder.setText(R.id.tv_money,"+"+dataBean.getNum()+"元");
                        holder.setText(R.id.tv_time,dataBean.getCreatetime());
                    }
                    if (dataBean.getType() == 3) {
                        holder.setText(R.id.tv_title, dataBean.getDesc());
                        holder.setText(R.id.tv_integral,"交易积分： "+String.valueOf(dataBean.getCoin())+"积分");
                        holder.setText(R.id.tv_time,dataBean.getCreatetime());
                        if (dataBean.getName() != null){
                            holder.setText(R.id.tv_name,"下级会员： "+dataBean.getName());
                        }else {
                            holder.setText(R.id.tv_name,"下级会员： 未实名认证");
                        }
                        holder.setText(R.id.tv_tel,dataBean.getTel());
                        holder.setText(R.id.tv_money,"+"+dataBean.getNum()+"元");
                    }
                    if (dataBean.getType() == 4){
                        holder.setText(R.id.tv_name,dataBean.getDesc());
                        holder.setText(R.id.tv_integral,dataBean.getCreatetime());
                        holder.setText(R.id.tv_money,"-"+dataBean.getNum()+"元");
                        holder.setVisible(R.id.rl_1,false);
                        holder.setVisible(R.id.view,false);
                        holder.setTextColorRes(R.id.tv_money,R.color.text_green);
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

    }

    private void loadMore() {
        int size = itemlist.size();
        if (size == alllist.size()) {
            refreshLayout.finishLoadMore(false);
            return;
        }
        for (int i = 0; i < 10; i++) {
            if(itemlist.size()<alllist.size()){
                itemlist.add(alllist.get(i + size));
            }
        }
        adapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore(1000);
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
                JifenshouzhiActivity.this.finish();
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
    public void getListSuccess(List<JifenShouzhiResponse.DataBeanX.DataBean> list) {
        this.itemlist.clear();
        itemlist.addAll(list);
        adapter.notifyDataSetChanged();
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
