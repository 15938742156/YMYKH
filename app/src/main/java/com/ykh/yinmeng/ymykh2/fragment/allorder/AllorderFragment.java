package com.ykh.yinmeng.ymykh2.fragment.allorder;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.model.MyOrderResponse;
import com.ykh.yinmeng.ymykh2.utils.GlideUtils;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.BasListView;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.OnRefreshListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class AllorderFragment extends Fragment implements AllorderMVPView,View.OnClickListener{
    private static final String ARG = "arg";
    private BasListView listview;
    private CommonAdapter adapter;
    private Dialog loadingDialog;
    private List<MyOrderResponse.DataBeanX.OrderBean> list = new ArrayList<>();
    private List<MyOrderResponse.DataBeanX.OrderBean> alllist = new ArrayList<>();
    public AllorderPresenter<AllorderMVPView> presenter;
    private SmartRefreshLayout refreshLayout;

    public AllorderFragment(){
    }

    public static AllorderFragment newInstance(int arg){
        AllorderFragment fragment = new AllorderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG, arg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allorder,null);
        initView(view);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new AllorderPresenterImp<>();
        presenter.onAttach(this);
        presenter.setType(getArguments() != null ? getArguments().getInt(ARG) : -1);
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    /***
     * 实例化控件
     * @param view
     */
    private void initView(View view) {
        listview = (BasListView) view.findViewById(R.id.listview);
        loadingDialog = DialogUtil.createLoadingDialog(getActivity(), "");
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        listview.setLayoutManager(new LinearLayoutManager(getActivity()));
        int space = 20;
        listview.addItemDecoration(new SpacesItemDecoration(space));
        adapter = new CommonAdapter<MyOrderResponse.DataBeanX.OrderBean>(getActivity(), R.layout.fragment_allorder_listview ,list) {
            @Override
            protected void convert(ViewHolder holder, MyOrderResponse.DataBeanX.OrderBean orderBean, int position) {
                holder.setText(R.id.tv_orderid,orderBean.out_trade_no);//订单号
                GlideUtils.loadImageView(mContext, "http://wx.ykh9.com"+orderBean.getImg_feng(), (ImageView) holder.getView(R.id.img_pos));//图片
                //pay_status为支付状态：0：待付款、1：待发货、2：已发货、3：已收货
                // pay_status为支付状态为0待付款，pay_type=4时是积分兑换的订单
                if (orderBean.status == 0){
                    holder.setText(R.id.tv_type,"待付款");
                    holder.setVisible(R.id.bt_quxiao,true);//加上这句，保证快速滑动正常显示
                    holder.setText(R.id.bt_quxiao,"取消订单");
                    holder.setText(R.id.bt_fukuan,"立即付款");
                }
                if (orderBean.status == 1){
                    holder.setText(R.id.tv_type,"待发货");
                    holder.setVisible(R.id.bt_quxiao,false);
                    holder.setText(R.id.bt_fukuan,"提醒卖家");
                }
                if (orderBean.status == 2){
                    holder.setText(R.id.tv_type,"待收货");
                    holder.setVisible(R.id.bt_quxiao,true);
                    holder.setText(R.id.bt_quxiao,"查看物流");
                    holder.setText(R.id.bt_fukuan,"确认收货");
//                    bt_fukuan.setBackgroundResource(R.drawable.btn_bg_corner_gray);
                }
                if (orderBean.status == 3){
                    holder.setText(R.id.tv_type,"已收货");
                    holder.setVisible(R.id.bt_quxiao,false);
                    holder.setText(R.id.bt_fukuan,"查看物流");
//                    bt_fukuan.setBackgroundResource(R.drawable.btn_bg_corner_gray);
                }
                holder.setTag(R.id.bt_quxiao, position);
                holder.setTag(R.id.bt_fukuan, position);
                holder.setOnClickListener(R.id.bt_quxiao, onClickListener);
                holder.setOnClickListener(R.id.bt_fukuan, onClickListener);
                holder.setText(R.id.tv_name,orderBean.title);//商品名称
                holder.setText(R.id.tv_jian,String.valueOf(orderBean.num));//订单数量
//                Double price = Double.valueOf(orderBean.price);
//                Double num = Double.valueOf(orderBean.num);
//                Double money = price*num;
                holder.setText(R.id.tv_total,String.valueOf(orderBean.getPay_money())+"元");//订单总金额
                holder.setText(R.id.tv_money,"￥"+orderBean.price+"元");//单价
                holder.setText(R.id.tv_yunfei,"运费："+String.valueOf(orderBean.getExp_fee())+"元");//运费
                holder.setText(R.id.tv_rebate,orderBean.getDescription());//描述

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
    public void getDataSuccess(List<MyOrderResponse.DataBeanX.OrderBean> allorderList) {
        this.list.clear();
        list.addAll(allorderList);
        adapter.notifyDataSetChanged();
        loadMore();
    }

    @Override
    public void showFailureMsg(String msg) {
        ToastUtils.showToast(getActivity(), msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void notifyItemRemoved(int position) {
        list.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, list.size());
    }

    @Override
    public void showDialog() {
//        loadingDialog.show();
    }

    @Override
    public void showTixing(String tixing) {
        ToastUtils.showToast(getActivity(), "提醒成功", Toast.LENGTH_SHORT);
    }

    @Override
    public void dismissDialog() {
//        if (loadingDialog != null && loadingDialog.isShowing()) {
//            loadingDialog.dismiss();
//        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            presenter.onToggleItem(getActivity(), view);

        }
    };

    @Override
    public void onClick(View view) {

    }
}
