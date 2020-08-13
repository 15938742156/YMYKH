package com.ykh.yinmeng.ymykh2.activity.shanghuchaxun;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.model.ShanghuchaxunResponse;
import com.ykh.yinmeng.ymykh2.model.ShanghuzicaiResponse;
import com.ykh.yinmeng.ymykh2.utils.GlideUtils;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ShanghuchaxunActivity extends BaseActivity implements View.OnClickListener,ShanghuchaxunMVPView {
    private TextView tv_title;
    private LinearLayout ll_return,ll_sn,ll_tel,ll_name;
    private ImageButton ib_sousuo;
    private EditText et_SN;
    private TextView tv_sn,tv_tel,tv_name,tv_phone,tv_lianxi;
    private ImageView img_sn,img_tel,img_name;
    private RecyclerView listview;
    private CommonAdapter adapter;
    private Dialog loadingDialog;
    public ShanghuchaxunPresenter<ShanghuchaxunMVPView> presenter;
    private List<ShanghuchaxunResponse.DataBean> list = new ArrayList<>();
    private int type = 1;
    Intent intent;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new ShanghuchaxunPresenterImp<>();
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_business_query);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("商户查询 ");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        ll_sn = (LinearLayout)findViewById(R.id.ll_sn);
        ll_tel = (LinearLayout)findViewById(R.id.ll_tel);
        ll_name = (LinearLayout)findViewById(R.id.ll_name);
        ib_sousuo = (ImageButton)findViewById(R.id.ib_sousuo);
        et_SN = (EditText)findViewById(R.id.et_SN);
        tv_sn = (TextView)findViewById(R.id.tv_sn);
        tv_tel = (TextView)findViewById(R.id.tv_tel);
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_phone = (TextView)findViewById(R.id.tv_phone);
        tv_lianxi = (TextView)findViewById(R.id.tv_lianxi);
        img_sn = (ImageView)findViewById(R.id.img_sn);
        img_tel = (ImageView)findViewById(R.id.img_tel);
        img_name = (ImageView)findViewById(R.id.img_name);
        listview = (RecyclerView)findViewById(R.id.listview);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
        listview.setLayoutManager(new LinearLayoutManager(this));
        int space = 10;
        listview.addItemDecoration(new SpacesItemDecoration(space));
        adapter = new CommonAdapter<ShanghuchaxunResponse.DataBean>(this, R.layout.activity_shanghuchaxun_listview,list) {
            @Override
            protected void convert(ViewHolder holder, final ShanghuchaxunResponse.DataBean dataBean, int position) {
                holder.setText(R.id.tv_name, dataBean.getName());
                holder.setText(R.id.tv_phone, dataBean.getTel());
                holder.setText(R.id.tv_title,dataBean.getTitle());
                holder.setText(R.id.tv_price,"￥"+String.valueOf(dataBean.getPrice()));
                holder.setText(R.id.tv_no,"机器编号："+dataBean.getXinghao());
                GlideUtils.loadImageView(mContext, "http://wx.ykh9.com"+dataBean.getImg_feng(), (ImageView) holder.getView(R.id.img_tupian));
                holder.setOnClickListener(R.id.tv_lianxi, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tel = dataBean.getTel();
                        call(tel);
                    }
                });
            }
        };
        listview.setAdapter(adapter);

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        ll_sn.setOnClickListener(this);
        ll_tel.setOnClickListener(this);
        ll_name.setOnClickListener(this);
        ib_sousuo.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }
    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    private void call(String phone) {
        intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                ShanghuchaxunActivity.this.finish();
                break;
            case R.id.ll_sn:
                presenter.setType(1);
                img_sn.setImageResource(R.drawable.shape5);
                img_sn.setVisibility(View.VISIBLE);
                tv_sn.setTextColor(this.getResources().getColor(R.color.main));
                img_tel.setVisibility(View.GONE);
                tv_tel.setTextColor(this.getResources().getColor(R.color.text_black));
                img_name.setVisibility(View.GONE);
                tv_name.setTextColor(this.getResources().getColor(R.color.text_black));
                break;
            case R.id.ll_tel:
                presenter.setType(2);
                img_tel.setImageResource(R.drawable.shape5);
                img_tel.setVisibility(View.VISIBLE);
                tv_tel.setTextColor(this.getResources().getColor(R.color.main));
                img_sn.setVisibility(View.GONE);
                tv_sn.setTextColor(this.getResources().getColor(R.color.text_black));
                img_name.setVisibility(View.GONE);
                tv_name.setTextColor(this.getResources().getColor(R.color.text_black));
                break;
            case R.id.ll_name:
                presenter.setType(3);
                img_name.setImageResource(R.drawable.shape5);
                img_name.setVisibility(View.VISIBLE);
                tv_name.setTextColor(this.getResources().getColor(R.color.main));
                img_tel.setVisibility(View.GONE);
                tv_tel.setTextColor(this.getResources().getColor(R.color.text_black));
                img_sn.setVisibility(View.GONE);
                tv_sn.setTextColor(this.getResources().getColor(R.color.text_black));
                break;
            case R.id.ib_sousuo:
                presenter.onSousuoBtn(et_SN.getText().toString());
                break;
            default:
                    break;
        }
    }

    @Override
    public void getListSuccess(List<ShanghuchaxunResponse.DataBean> datalist) {
        this.list.clear();
        list.addAll(datalist);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getDataFailure(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
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
