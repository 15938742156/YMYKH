package com.ykh.yinmeng.ymykh2.fragment.transactionfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.fragment.tixianjilu.TixianjiluFragment;
import com.ykh.yinmeng.ymykh2.model.RebateResponse;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.TimeUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.BasListView;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.RefreshListView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;


public class TransactionFragment extends Fragment implements TransactionFragmentMVPView {
    private BasListView listview;

    private Dialog loadingDialog;
    private CommonAdapter adapter;
    private List<RebateResponse.DataBean> rebatelist = new ArrayList<>();
    private TransactionFragmentPresenter<TransactionFragmentMVPView> presenter;
    private SmartRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview,null);
        initView(view);
        presenter = new TransactionFragmentPresenterImp<>();
        presenter.onAttach(this);
        presenter.onInit(this.getActivity());
        return view;
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    private void initView(View view) {
        listview = (BasListView) view.findViewById(R.id.listview);
        loadingDialog = DialogUtil.createLoadingDialog(getActivity(), "");
        listview.setLayoutManager(new LinearLayoutManager(getActivity()));
        int space = 20;
        listview.addItemDecoration(new SpacesItemDecoration(space));
        adapter = new CommonAdapter<RebateResponse.DataBean>(getActivity(), R.layout.frgament_rebate_listview,rebatelist) {
            @Override
            protected void convert(ViewHolder holder, RebateResponse.DataBean dataBean, int position) {
                holder.setText(R.id.tv_name, dataBean.getPaizhao());
                holder.setText(R.id.tv_time, "时间："+TimeUtils.stampToDate(String.valueOf(dataBean.getAddtime())));
                holder.setText(R.id.tv_sn,"机器编码："+dataBean.getLikebian());
                holder.setText(R.id.tv_money,"￥"+dataBean.getMoney()+"元");

            }
        };
        listview.setAdapter(adapter);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.onAttach(TransactionFragment.this);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
    }

    @Override
    public void getListSuccess(List<RebateResponse.DataBean> list) {
        this.rebatelist.clear();
        rebatelist.addAll(list);
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
