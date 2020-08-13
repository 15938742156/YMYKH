package com.ykh.yinmeng.ymykh2.activity.logistics;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.activity.login.LoginActivity;
import com.ykh.yinmeng.ymykh2.model.LogisticsResponse;
import com.ykh.yinmeng.ymykh2.utils.GlideUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class LogisticsActivity extends BaseActivity implements LogisticsMVPView {
    private LinearLayout ll_return;
    private ImageView img_pos;
    private TextView tv_title,tv_name,tv_type,tv_money,tv_total,tv_jian,tv_address,tv_waybill,tv_yunfei;
    private RecyclerView listview;
    private CommonAdapter adapter;
    private Dialog loadingDialog;
    Intent intent;
    private List<LogisticsResponse.TracesBean> list = new ArrayList<>();
    private LogisticsPresenter<LogisticsMVPView> presenter;
    String waybill;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new LogisticsPresnterImp<>();
        presenter.onAttach(this);
        presenter.onInit(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_wuliu);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("查看物流");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        img_pos = (ImageView) findViewById(R.id.img_pos);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_jian = (TextView) findViewById(R.id.tv_jian);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_waybill = (TextView)findViewById(R.id.tv_waybill);
        tv_yunfei = (TextView)findViewById(R.id.tv_yunfei);
        listview = (RecyclerView) findViewById(R.id.listview);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setReverseLayout(true);//列表翻转
        layout.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
        listview.setLayoutManager(layout);
        int space = 20;
//        listview.addItemDecoration(new SpacesItemDecoration(space));

//        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.custom_divider));
//        listview.addItemDecoration(divider);

        adapter = new CommonAdapter<LogisticsResponse.TracesBean>(this, R.layout.activity_listview_wuliu ,list) {
            @Override
            protected void convert(ViewHolder holder, LogisticsResponse.TracesBean tracesBean, int position) {

                if (position == list.size()-1){
                    holder.setBackgroundRes(R.id.img,R.drawable.circle_yellow);
                    holder.setTextColor(R.id.tv_satus, getResources().getColor(R.color.main));
                    holder.setTextColor(R.id.tv_time,getResources().getColor(R.color.main));
                }else {
                    holder.setBackgroundRes(R.id.img,R.drawable.circle_gray);
                    holder.setTextColor(R.id.tv_satus, getResources().getColor(R.color.base_gray));
                    holder.setTextColor(R.id.tv_time,getResources().getColor(R.color.base_gray));
                }
                holder.setText(R.id.tv_satus, tracesBean.getAcceptStation());
                holder.setText(R.id.tv_time,tracesBean.getAcceptTime());
            }

        };
        listview.setAdapter(adapter);
    }

    @Override
    public void initListeners() {

        ll_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogisticsActivity.this.finish();
            }
        });
        tv_waybill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager copy;
                copy = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                copy.setText(waybill);
                Toast.makeText(LogisticsActivity.this,"复制成功",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void initData() {
    }

    @Override
    public void showType(String type) {
        tv_type.setText(type);
    }

    @Override
    public void showYunSucess(String yun) {
        tv_yunfei.setText("运费："+yun+"元");
    }

    @Override
    public void getWaybill(String waybill) {
        this.waybill = waybill;
        tv_waybill.setText("快递单号："+waybill+"（点击复制）");
    }

    @Override
    public void getLogoSuccess(String logo) {
        GlideUtils.loadImageView(this,logo,img_pos);
    }

    @Override
    public void getNameSuccess(String name) {
        tv_name.setText(name);
    }

    @Override
    public void getpriceSuccess(String price) {
        tv_money.setText("押金：￥"+price);
    }

    @Override
    public void getNumSuccess(String num) {
        tv_jian.setText(num);
    }

    @Override
    public void gettotalmoneySuccess(String money) {
        tv_total.setText(money);
    }

    @Override
    public void getAddressSuccess(String address) {
        tv_address.setText("收货地址："+address);
    }

    @Override
    public void getWuliuScccess(List<LogisticsResponse.TracesBean> wuliuList) {
        this.list.clear();
        list.addAll(wuliuList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getWuliuFailure(String msg) {
        ToastUtils.showToast(this   , msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void getDataFailure(String msg) {
        ToastUtils.showToast(this   , msg, Toast.LENGTH_SHORT);
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
