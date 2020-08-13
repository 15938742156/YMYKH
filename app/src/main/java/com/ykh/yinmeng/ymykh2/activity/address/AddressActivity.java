package com.ykh.yinmeng.ymykh2.activity.address;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.ykh.yinmeng.ymykh2.bean.AddressBean;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogPopup;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.BasListView;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.OnRefreshListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class AddressActivity extends BaseActivity implements View.OnClickListener,AddressMVPView{

    private LinearLayout ll_return,ll_menu;
    private TextView tv_title,tv_menu;
    private BasListView rv_address;
    private List<AddressBean> addressList = new ArrayList<>();
    private List<AddressBean> alllist = new ArrayList<>();
    private CommonAdapter adapter;
    private Dialog loadingDialog;
    public AddressPresenter<AddressMVPView> presenter;

    private SmartRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle arg0) {
        presenter = new AddressPresenterImp<>();
        presenter.onAttach(this);
        super.onCreate(arg0);

    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_shouhuoaddress);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("收货地址");
        tv_menu = (TextView)findViewById(R.id.tv_menu);
        tv_menu.setText("添加");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        ll_menu = (LinearLayout)findViewById(R.id.ll_menu);
        ll_menu.setVisibility(View.VISIBLE);
        rv_address = (BasListView) findViewById(R.id.rv_address);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");

        rv_address.setLayoutManager(new LinearLayoutManager(this));
        int space = 20;
        rv_address.addItemDecoration(new SpacesItemDecoration(space));
//        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.custom_divider));
//        rv_address.addItemDecoration(divider);

        adapter = new CommonAdapter<AddressBean>(this, R.layout.activity_shouhuoaddress_listview,addressList) {
            @Override
            protected void convert(ViewHolder holder, AddressBean addressBean, int position) {
                holder.setText(R.id.tv_name, addressBean.shrName);
                holder.setText(R.id.tv_tel, addressBean.shrMobile);
                holder.setVisible(R.id.cb_moren, addressBean.isDefault == 1);
                holder.setText(R.id.tv_address, addressBean.shrProvince+" "+addressBean.shrCity+" "+addressBean.shrArea+" "+addressBean.shrAddress);
                holder.setOnClickListener(R.id.ll_del, AddressActivity.this);
                holder.setTag(R.id.ll_del, position);
                holder.setOnClickListener(R.id.ll_bianji, AddressActivity.this);
                holder.setTag(R.id.ll_bianji, position);
            }
        };
        rv_address.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                presenter.onToggleItem(AddressActivity.this, position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        refreshLayout.setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.onInit(AddressActivity.this);
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
    private void loadMore() {
        int size = addressList.size();
        if (size == alllist.size()) {
            refreshLayout.finishLoadMore(false);
            return;
        }
        for (int i = 0; i < 10; i++) {
            if(addressList.size()<alllist.size()){
                addressList.add(alllist.get(i + size));
            }
        }
        adapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore(1000);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        ll_menu.setOnClickListener(this);
        rv_address.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMoreData() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        loadMore();
                        rv_address.onRefreshFinish();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void initData() {
        presenter.onInit(this);
    }
    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.ll_return:
                AddressActivity.this.finish();
                break;
            case R.id.ll_menu:
                presenter.onToggleTianjiaBtn(this);
                break;
            case R.id.ll_del:
                new DialogPopup(mContext)
                        .setTitle("提示")
                        .setContent("是否删除地址")
                        .setConfirmText("确定")
                        .setCancelText("取消")
                        .setClickListener(new DialogPopup.ViewClickListener() {
                            @Override
                            public void confirm(View view) {
                                presenter.onToggleDeleteBtn((int) v.getTag());
                            }

                            @Override
                            public void cancel(View view) {

                            }
                        }).showPopupWindow();
                break;
            case R.id.ll_bianji:
                presenter.onToggleEditBtn(this, (int) v.getTag());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void getListSuccess(List<AddressBean> list) {
        this.addressList.clear();
        addressList.addAll(list);
        adapter.notifyDataSetChanged();
        loadMore();
    }

    @Override
    public void getListFailure(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void delAddressSuccess() {
    }

    @Override
    public void delAddressFailure(String msg) {
        ToastUtils.showToast(mContext, "删除地址失败", Toast.LENGTH_SHORT);
    }

    @Override
    public void onItemRemoved(int position) {
        addressList.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position,addressList.size());
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
