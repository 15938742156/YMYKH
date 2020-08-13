package com.ykh.yinmeng.ymykh2.fragment.orderdikoujilu;

import android.app.Dialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.fragment.myorderfragment.MyorderFragment;
import com.ykh.yinmeng.ymykh2.model.OrderdikoujiluResponse;
import com.ykh.yinmeng.ymykh2.model.TixianjiluResponse;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.StringReplaceUtil;
import com.ykh.yinmeng.ymykh2.utils.TimeUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.BasListView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class OrderdikoujiluFragment extends Fragment implements OrderdikoujiluMVPView {
    private TextView tv_time,tv_money,tv_type;
    private BasListView listview;

    private List<OrderdikoujiluResponse.DataBean> list = new ArrayList<>();
    private CommonAdapter adapter;
    private Dialog loadingDialog;
    public OrderdikoujiluPresenter<OrderdikoujiluMVPView> presenter;
    private SmartRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tixianjilu,null);
        initViews(view);
        initListeners();
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new OrderdikoujiluPresenterImp<>();
        presenter.onAttach(this);
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }


    public void initViews(View view) {
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        tv_money.setText("抵扣金额");
        tv_type = (TextView) view.findViewById(R.id.tv_type);
        tv_type.setText("抵扣状态");
        listview = (BasListView) view.findViewById(R.id.listview);
        loadingDialog = DialogUtil.createLoadingDialog(getActivity(), "");
        listview.setLayoutManager(new LinearLayoutManager(getActivity()));
        int space = 24;
        listview.addItemDecoration(new SpacesItemDecoration(space));
        adapter = new CommonAdapter<OrderdikoujiluResponse.DataBean>(getActivity(), R.layout.activity_tixianjilu_listview ,list) {
            @Override
            protected void convert(ViewHolder holder, OrderdikoujiluResponse.DataBean dataBean, int position) {
                /**提现状态 -1.已抵扣，1已退还*/
                holder.setText(R.id.tv_time, TimeUtils.stampToDate(String.valueOf(dataBean.getAdd_time())));
                holder.setText(R.id.tv_money,dataBean.getChange_num()+"元");
                if (TextUtils.equals("-1",String.valueOf(dataBean.getSymbol()))){
                    holder.setText(R.id.tv_type,"已抵扣");
                }
                if (TextUtils.equals("1",String.valueOf(dataBean.getSymbol()))){
                    holder.setText(R.id.tv_type,"已退还");
                }


            }

        };
        listview.setAdapter(adapter);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.onAttach(OrderdikoujiluFragment.this);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });

    }

    public void initListeners() {

    }


    @Override
    public void getListSuccess(List<OrderdikoujiluResponse.DataBean> tixianjiluList) {
        this.list.clear();
        list.addAll(tixianjiluList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getListFailure(String msg) {
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
