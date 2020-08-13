package com.ykh.yinmeng.ymykh2.fragment.myorderfragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.MykefuActivity;
import com.ykh.yinmeng.ymykh2.activity.myorder.MyOrderActivity;
import com.ykh.yinmeng.ymykh2.fragment.allorder.AllorderFragment;
import com.ykh.yinmeng.ymykh2.fragment.allorder.AllorderMVPView;
import com.ykh.yinmeng.ymykh2.fragment.allorder.AllorderPresenter;
import com.ykh.yinmeng.ymykh2.fragment.machine.MachineFragment;
import com.ykh.yinmeng.ymykh2.model.MyOrderResponse;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.GlideUtils;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.OnRefreshListener;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.RefreshListView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MyorderFragment extends Fragment implements View.OnClickListener,MyorderMVPView {
    private RelativeLayout rl_daifukuan,rl_daifahuo,rl_daishouhuo,rl_shouhou;
    private TextView tv_allorder;
    private RecyclerView listview;
    Intent intent;
    int isReal;
    private CommonAdapter adapter;
    private Dialog loadingDialog;
    private List<MyOrderResponse.DataBeanX.OrderBean> list = new ArrayList<>();
    public MyorderPresenter<MyorderMVPView> presenter;
    private static final String ARG = "arg";

    private SmartRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myorder, container,false);

        initView(view);
        initListeners();
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new MyorderPresenterImp<>();
        presenter.onAttach(this);
        presenter.setType(getArguments() != null ? getArguments().getInt(ARG) : -1);
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    private void initView(View view) {
        rl_daifukuan = (RelativeLayout)view.findViewById(R.id.rl_daifukuan);
        rl_daifahuo = (RelativeLayout)view.findViewById(R.id.rl_daifahuo);
        rl_daishouhuo = (RelativeLayout)view.findViewById(R.id.rl_daishouhuo);
        rl_shouhou = (RelativeLayout)view.findViewById(R.id.rl_shouhou);
        tv_allorder = (TextView)view.findViewById(R.id.tv_allorder);
        listview = (RecyclerView)view.findViewById(R.id.listview);
        listview.setLayoutManager(new LinearLayoutManager(getActivity()));
        int space = 20;
        listview.addItemDecoration(new SpacesItemDecoration(space));
        loadingDialog = DialogUtil.createLoadingDialog(getActivity(), "");
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
                }
                if (orderBean.status == 3){
                    holder.setText(R.id.tv_type,"已收货");
                    holder.setVisible(R.id.bt_quxiao,false);
                    holder.setText(R.id.bt_fukuan,"查看物流");
                }
                holder.setTag(R.id.bt_quxiao, position);
                holder.setTag(R.id.bt_fukuan, position);
                holder.setOnClickListener(R.id.bt_quxiao, onClickListener);
                holder.setOnClickListener(R.id.bt_fukuan, onClickListener);
                holder.setText(R.id.tv_name,orderBean.title);//商品名称
                holder.setText(R.id.tv_yunfei,"运费:"+orderBean.exp_fee+"元");
                holder.setText(R.id.tv_jian,String.valueOf(orderBean.num));//订单数量
                holder.setText(R.id.tv_total,String.valueOf(orderBean.getOrder_money()+"元"));//订单总金额
                holder.setText(R.id.tv_money,"押金:￥"+orderBean.price);//单价
                holder.setText(R.id.tv_rebate,orderBean.getDescription());

            }

        };
        listview.setAdapter(adapter);

        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.onAttach(MyorderFragment.this);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });

    }

    private void initListeners() {
        rl_daifukuan.setOnClickListener(this);
        rl_daifahuo.setOnClickListener(this);
        rl_daishouhuo.setOnClickListener(this);
        rl_shouhou.setOnClickListener(this);
        tv_allorder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_daifukuan:
                intent = new Intent(getActivity(), MyOrderActivity.class);
                intent.putExtra("id",2);
                startActivity(intent);
//                isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//                if (isReal !=1 ){
//                    ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//                }else {
//                    intent = new Intent(getActivity(), MyOrderActivity.class);
//                    intent.putExtra("id",2);
//                    startActivity(intent);
//                }
                break;
            case R.id.rl_daifahuo:
                intent = new Intent(getActivity(), MyOrderActivity.class);
                intent.putExtra("id",3);
                startActivity(intent);
//                isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//                if (isReal !=1 ){
//                    ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//                }else {
//                    intent = new Intent(getActivity(), MyOrderActivity.class);
//                    intent.putExtra("id",3);
//                    startActivity(intent);
//                }

                break;
            case R.id.rl_daishouhuo:
                intent = new Intent(getActivity(), MyOrderActivity.class);
                intent.putExtra("id",4);
                startActivity(intent);
//                isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//                if (isReal !=1 ){
//                    ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//                }else {
//                    intent = new Intent(getActivity(), MyOrderActivity.class);
//                    intent.putExtra("id",4);
//                    startActivity(intent);
//                }

                break;
            case R.id.rl_shouhou:
                intent = new Intent(getActivity(), MykefuActivity.class);
                startActivity(intent);
//                isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//                if (isReal !=1 ){
//                    ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//                }else {
//                    intent = new Intent(getActivity(), MykefuActivity.class);
//                    startActivity(intent);
//                }

                break;
            case R.id.tv_allorder:
                intent = new Intent(getActivity(), MyOrderActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);
//                isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//                if (isReal !=1 ){
//                    ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//                }else {
//                    intent = new Intent(getActivity(), MyOrderActivity.class);
//                    intent.putExtra("id",1);
//                    startActivity(intent);
//                }

                break;
                default:
                    break;
        }
    }

    @Override
    public void getDataSuccess(List<MyOrderResponse.DataBeanX.OrderBean> allorderList) {
        this.list.clear();
        list.addAll(allorderList);
        adapter.notifyDataSetChanged();
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
}
