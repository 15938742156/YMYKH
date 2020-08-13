package com.ykh.yinmeng.ymykh2.activity.tuanduipaiming;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.ykh.yinmeng.ymykh2.model.TuanduipaimingResponse;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.StringReplaceUtil;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.BasListView;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.OnRefreshListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.ykh.yinmeng.ymykh2.utils.StringReplaceUtil.createAsterisk;


public class TuanduipaimingActivity extends BaseActivity implements View.OnClickListener,TuanduipaimingMVPView {
    private TextView tv_title,tv_name,tv_tel,tv_money,tv_paiming;
    private BasListView listview_tuanduipaiming;
    private LinearLayout ll_return;

    private Dialog loadingDialog;
    private CommonAdapter adapter;
    private List<TuanduipaimingResponse.PaimingBean> list = new ArrayList<>();
    private List<TuanduipaimingResponse.PaimingBean> alllist = new ArrayList<>();
    public TuanduipaimingPresenter<TuanduipaimingMVPView> presenter;
    private SmartRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new TuanduipaimingPresenterImp<>();
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_tuanduipaiming);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("团队排名");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_tel = (TextView) findViewById(R.id.tv_tel);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_paiming = (TextView) findViewById(R.id.tv_paiming);
        listview_tuanduipaiming = (BasListView) findViewById(R.id.listview_tuanduipaiming);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");

        listview_tuanduipaiming.setLayoutManager(new LinearLayoutManager(this));
        int space = 10;
        listview_tuanduipaiming.addItemDecoration(new SpacesItemDecoration(space));
        adapter = new CommonAdapter<TuanduipaimingResponse.PaimingBean>(this, R.layout.activity_tuanduipaiming_listview ,list) {
            @Override
            protected void convert(ViewHolder holder, TuanduipaimingResponse.PaimingBean paimingBean, int position) {
                if (paimingBean.getName() != null ){
                    holder.setText(R.id.tv_name,StringReplaceUtil.replaceNameX(paimingBean.getName()));
                }else {
                    holder.setText(R.id.tv_name,"未认证");
                }
                if (paimingBean.getTel() != null ){
                    holder.setText(R.id.tv_tel, StringReplaceUtil.isMobileNum(paimingBean.getTel()));
                }else {
                    holder.setText(R.id.tv_tel, "");
                }
                double Count = Double.valueOf(paimingBean.getInRank())/10000;
                BigDecimal bg2 = new BigDecimal(Count);
                double Counts = bg2.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                if (paimingBean.getInRank() == null || paimingBean.getInRank().equals("0")){
                    holder.setText(R.id.tv_count,"0.00万元");
                }else {
                    holder.setText(R.id.tv_count,Counts+"万元");
                }
                holder.setText(R.id.list,String.valueOf(position+1));
            }

        };
        listview_tuanduipaiming.setAdapter(adapter);
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
        tv_name.setOnClickListener(this);
        tv_tel.setOnClickListener(this);
        tv_money.setOnClickListener(this);
        tv_paiming.setOnClickListener(this);
        listview_tuanduipaiming.setOnClickListener(this);
        listview_tuanduipaiming.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMoreData() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        loadMore();
                        listview_tuanduipaiming.onRefreshFinish();
                    }
                }, 1000);
            }
        });

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
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                TuanduipaimingActivity.this.finish();
                break;
        }
    }

    @Override
    public void getDataSuccess(List<TuanduipaimingResponse.PaimingBean> paiminglist) {
        this.list.clear();
        list.addAll(paiminglist);
        adapter.notifyDataSetChanged();
        loadMore();
    }

    @Override
    public void getDataFailure(String msg) {
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
