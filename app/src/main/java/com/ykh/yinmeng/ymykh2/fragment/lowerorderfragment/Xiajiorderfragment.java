package com.ykh.yinmeng.ymykh2.fragment.lowerorderfragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.fragment.homefragment.HomeFragment;
import com.ykh.yinmeng.ymykh2.model.XiajiorderResponse;
import com.ykh.yinmeng.ymykh2.utils.GlideUtils;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.BasListView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class Xiajiorderfragment extends Fragment implements XiajiorderMVPView {
    private RecyclerView listview;
    private CommonAdapter adapter;
    private TextView tv_tel;
    Intent intent;

    private Dialog loadingDialog;
    private List<XiajiorderResponse.DataBeanX.DataBean> list = new ArrayList<>();
    public XiajiorderPresenter<XiajiorderMVPView> presenter;

    private SmartRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xiajiorder,container,false);
        initViews(view);
        presenter = new XiajiorderPresenterImp<>();
        presenter.onAttach(this);
        return view;
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
    public void initViews(View view ) {
        listview = (RecyclerView) view.findViewById(R.id.listview);
        tv_tel = (TextView) view.findViewById(R.id.tv_tel);
        loadingDialog = DialogUtil.createLoadingDialog(getActivity(), "");
        listview.setLayoutManager(new LinearLayoutManager(getActivity()));
        int space = 20;
        listview.addItemDecoration(new SpacesItemDecoration(space));
        adapter = new CommonAdapter<XiajiorderResponse.DataBeanX.DataBean>(getActivity(), R.layout.fragment_xiajiorder_listview,list) {
            @Override
            protected void convert(ViewHolder holder, final XiajiorderResponse.DataBeanX.DataBean orderBean, int position) {
                if (orderBean != null){
                    if (TextUtils.isEmpty(orderBean.getName())){
                        holder.setText(R.id.tv_xingming,"姓名：无");//姓名
                    }else {
                        holder.setText(R.id.tv_xingming,"姓名："+orderBean.getName());//姓名
                    }
                    if (TextUtils.isEmpty(orderBean.getTel())){
                        holder.setText(R.id.tv_tel,"");//电话
                    }else {
                        holder.setText(R.id.tv_tel,orderBean.getTel());//电话
                    }
                    GlideUtils.loadImageView(mContext, "http://wx.ykh9.com"+orderBean.getImg_feng(), (ImageView) holder.getView(R.id.img_pos));//图片
                    holder.setText(R.id.tv_name,orderBean.getTitle());
                    //pay_status为支付状态：0：待付款、1：待发货、2：已发货、3：已收货
                    // pay_status为支付状态为0待付款，pay_type=4时是积分兑换的订单
                    if (orderBean.getStatus() == 0){
                        holder.setText(R.id.tv_type,"待付款");
                    }
                    if (orderBean.getStatus() == 1){
                        holder.setText(R.id.tv_type,"待发货");
                    }
                    if (orderBean.getStatus() == 2){
                        holder.setText(R.id.tv_type,"待收货");
                    }
                    if (orderBean.getStatus() == 3){
                        holder.setText(R.id.tv_type,"已收货");
                    }
                    holder.setText(R.id.tv_money,"押金:￥"+orderBean.getPrice());//单价
                    holder.setText(R.id.tv_yunfei,"运费:"+orderBean.getExp_fee()+"元");//运费
                    holder.setText(R.id.tv_rebate,orderBean.getDescription());
                    Double money = 0.00;
                    int num = 0;
                    Double yun = 0.00;
                    num = orderBean.getNum();
                    yun = Double.valueOf(orderBean.getExp_fee());
                    money= Double.valueOf(orderBean.getPrice());
                    Double total = money*num+yun;
                    holder.setText(R.id.tv_total,String.valueOf(total));//订单总金额
                    holder.setText(R.id.tv_jian,String.valueOf(orderBean.getNum()));//订单数量

                    holder.setOnClickListener(R.id.tv_lianxi, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String tel = orderBean.getTel();
                            call(tel);
                        }
                    });

                }
                }


        };
        listview.setAdapter(adapter);

        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.onAttach(Xiajiorderfragment.this);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });

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
    public void getDataSuccess(List<XiajiorderResponse.DataBeanX.DataBean> xiajiorderList) {
        this.list.clear();
        list.addAll(xiajiorderList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getDataFailure(String msg) {
        ToastUtils.showToast(getActivity(), msg, Toast.LENGTH_SHORT);
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
