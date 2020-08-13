package com.ykh.yinmeng.ymykh2.activity.mymember;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.model.MyhuiyuanResponse;
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


public class MymemberActivity extends BaseActivity implements MymemberMVPView,View.OnClickListener {
    private TextView tv_title,tv_summ,tv_tel;
    private LinearLayout ll_return;
    private BasListView listview_tuanduixinxi;
    private ImageView spinner;
    private Menu sum1,sum2,sum3;
    Intent intent;

    private Dialog loadingDialog;
    private List<MyhuiyuanResponse.DataBean.LowInfoBean> list = new ArrayList<>();
    private List<MyhuiyuanResponse.DataBean.LowInfoBean> alllist = new ArrayList<>();
    private CommonAdapter adapter;
    public MymemberPresenter<MymemberMVPView> presenter;

    private SmartRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new MymemberPresenterImp<>();
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_member);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("我的会员");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        spinner = (ImageView) findViewById(R.id.spinner);
        tv_summ = (TextView)findViewById(R.id.tv_summ);
        tv_tel = (TextView)findViewById(R.id.tv_tel);
        sum1 = (Menu) findViewById(R.id.sum1);
        sum2 = (Menu) findViewById(R.id.sum2);
        sum3 = (Menu) findViewById(R.id.sum3);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        listview_tuanduixinxi = (BasListView) findViewById(R.id.listview_tuanduixinxi);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
        int space = 10;
        listview_tuanduixinxi.addItemDecoration(new SpacesItemDecoration(space));
        listview_tuanduixinxi.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonAdapter<MyhuiyuanResponse.DataBean.LowInfoBean>(this, R.layout.activity_member_listview,list) {
            @Override
            protected void convert(ViewHolder holder, MyhuiyuanResponse.DataBean.LowInfoBean huiyuanBean, int position) {
                if (huiyuanBean.getName() != null){
                    holder.setText(R.id.tv_name,huiyuanBean.getName());
                }else {
                    holder.setText(R.id.tv_name,"未认证");
                }
                if (huiyuanBean.getTel() != null || TextUtils.equals("",huiyuanBean.getTel())){
                    holder.setText(R.id.tv_tel, huiyuanBean.getTel());
                }else {
                    holder.setText(R.id.tv_tel, "未留下联系方式");
                }
                holder.setText(R.id.tv_num,String.valueOf(huiyuanBean.getCount())+"人");
                holder.setOnClickListener(R.id.tv_tel, MymemberActivity.this);
                holder.getView(R.id.tv_tel).setTag(position);
            }

        };
        listview_tuanduixinxi.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                call(list.get(position).tel);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
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
        spinner.setOnClickListener(this);
        listview_tuanduixinxi.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMoreData() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        loadMore();
                        listview_tuanduixinxi.onRefreshFinish();
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
    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    private void call(String phone) {
        intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public void getDataSuccess(List<MyhuiyuanResponse.DataBean.LowInfoBean> huiyuanBeanList) {
        this.list.clear();
        list.addAll(huiyuanBeanList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getDataFailure(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showTotalSumSuccess(MyhuiyuanResponse.DataBean.InfoBean infoBean) {
        tv_summ.setText(infoBean.getSum()+"人");
    }

    @Override
    public void showPopupMenu(MyhuiyuanResponse.DataBean.InfoBean infoBean,View view) {
        PopupMenu popupMenu = new PopupMenu(this,view);
        /*MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popupmenu, popupMenu.getMenu());*/
        Menu menu = popupMenu.getMenu();
        menu.add("一级会员"+infoBean.getSum1()+"人");
        menu.add("二级会员"+infoBean.getSum2()+"人");
        menu.add("三级会员"+infoBean.getSum3()+"人");
        popupMenu.show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                MymemberActivity.this.finish();
                break;
            case R.id.tv_tel:
                call(list.get((int)v.getTag()).tel);
                break;
            case R.id.spinner:
                presenter.onToggleSpinnerBtn(v);
                break;
            default:
                break;

        }
    }
}
