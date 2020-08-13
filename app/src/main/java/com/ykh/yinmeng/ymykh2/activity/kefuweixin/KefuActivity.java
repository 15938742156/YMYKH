package com.ykh.yinmeng.ymykh2.activity.kefuweixin;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class KefuActivity extends BaseActivity implements View.OnClickListener, KefuMVPView {
    private TextView tv_title,tv_weixintel;

    private Dialog loadingDialog;
    private LinearLayout ll_return;
    private RecyclerView listview;
    private List<String> weixinlist = new ArrayList<>();
    private CommonAdapter adapter;

    private KefuPresenter<KefuMVPView> presenter;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new KefuPresenterImp<>();
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_kefuweixin);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("我的客服");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        listview = (RecyclerView) findViewById(R.id.listview);
        tv_weixintel = (TextView) findViewById(R.id.tv_weixintel);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
        listview.setLayoutManager(new LinearLayoutManager(this));
        int space = 20;
        listview.addItemDecoration(new SpacesItemDecoration(space));
        adapter = new CommonAdapter<String>(this, R.layout.activity_kefuweixin_listview,weixinlist) {
            @Override
            protected void convert(ViewHolder holder, String response, int position) {
                holder.setText(R.id.tv_weixintel,response);
                holder.setText(R.id.tv_position,position+1+"号微信客服");
                holder.setOnClickListener(R.id.tv_weixintel,KefuActivity.this);
                holder.setTag(R.id.tv_weixintel, position);
            }
        };
        listview.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                presenter.onToggleCopyBtn(KefuActivity.this,position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        tv_weixintel.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                KefuActivity.this.finish();
                break;
            case R.id.tv_weixintel:
                presenter.onToggleCopyBtn(KefuActivity.this,(int)v.getTag());
                break;
                default:
                    break;
        }
    }

    @Override
    public void getListSuccess(List<String> list) {
        this.weixinlist.clear();
        weixinlist.addAll(list);
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
