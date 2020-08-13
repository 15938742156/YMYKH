package com.ykh.yinmeng.ymykh2.activity.cardlist;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.bean.CardBean;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.StringReplaceUtil;
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


public class CardguanliActivity extends BaseActivity implements View.OnClickListener,CardguanliMVPView {
    private TextView tv_title,tv_menu;
    private BasListView rv_cardlist;
    private LinearLayout ll_return,ll_menu;

    private List<CardBean> cardlist = new ArrayList<>();
    private List<CardBean> alllist = new ArrayList<>();
    private CommonAdapter adapter;
    private Dialog loadingDialog;
    public CardguanliPresenter<CardguanliMVPView> presenter;
    private SmartRefreshLayout refreshLayout;
    int isReal;

    @Override
    protected void onCreate(Bundle arg0) {
        presenter = new CardguanliPresenterImp<>();
        presenter.onAttach(this);
        super.onCreate(arg0);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_card_guanli);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("银行卡管理");
        tv_menu = (TextView)findViewById(R.id.tv_menu);
        tv_menu.setText("添加");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        ll_menu = (LinearLayout)findViewById(R.id.ll_menu);
        ll_menu.setVisibility(View.VISIBLE);
        rv_cardlist = (BasListView) findViewById(R.id.rv_cardlist);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
        rv_cardlist.setLayoutManager(new LinearLayoutManager(this));
        int space = 20;
        rv_cardlist.addItemDecoration(new SpacesItemDecoration(space));
        adapter = new CommonAdapter<CardBean>(this, R.layout.activity_card_guaanli_listview,cardlist) {
            @Override
            protected void convert(ViewHolder holder, CardBean cardBean, int position) {
                String name = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_NAME);
                if(TextUtils.equals(cardBean.banksAccount, name)){
                    holder.setText(R.id.tv_bankname, cardBean.banks);
                    holder.setVisible(R.id.cb_moren, cardBean.status == 1);
                    String banksNumber = StringReplaceUtil.bankCardReplaceWithStar(cardBean.getBanksNumber().replace(" ",""));
                    holder.setText(R.id.tv_banksNumber, banksNumber);
                    holder.setOnClickListener(R.id.ll_del, CardguanliActivity.this);
                    holder.setTag(R.id.ll_del, position);
                    holder.setOnClickListener(R.id.ll_bianji, CardguanliActivity.this);
                    holder.setTag(R.id.ll_bianji, position);
                }else {
                    holder.setVisible(R.id.ll_select_card,false);
                }
            }
        };
        rv_cardlist.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                presenter.onToggleItem(CardguanliActivity.this,position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        refreshLayout.setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.onInit(CardguanliActivity.this);
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
        ll_menu.setOnClickListener(this);
        rv_cardlist.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMoreData() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        loadMore();
                        rv_cardlist.onRefreshFinish();
                    }
                }, 1000);
            }
        });
    }
    private void loadMore() {
        int size = cardlist.size();
        if (size == alllist.size()) {
            refreshLayout.finishLoadMore(false);
            return;
        }
        for (int i = 0; i < 10; i++) {
            if(cardlist.size()<alllist.size()){
                cardlist.add(alllist.get(i + size));
            }
        }
        adapter.notifyDataSetChanged();
        refreshLayout.finishLoadMore(1000);
    }

    @Override
    public void initData() {
        presenter.onInit(this);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.ll_return:
                CardguanliActivity.this.finish();
                break;
            case R.id.ll_menu:
                isReal = SharedPreferencesUtils.getInstance(this).get(Constant.SHARED_AUTHENTICATION, -1);
                if (isReal !=1 ){
                    ToastUtils.showToast(this, "亲爱的用户,为确保您的账户安全,进行此业务之前请先进行用户认证。", Toast.LENGTH_SHORT);
                }else {
                    presenter.onToggleTianjiaBtn(this);
                }

                break;
            case R.id.ll_del:
                new DialogPopup(mContext)
                        .setTitle("提示")
                        .setContent("是否删除银行卡")
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
    public void getListSuccess(List<CardBean> list) {
        this.cardlist.clear();
        cardlist.addAll(list);
        adapter.notifyDataSetChanged();
        loadMore();
    }

    @Override
    public void getListFailure(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void delCardSuccess() {

    }

    @Override
    public void delCardFailure(String msg) {

    }

    @Override
    public void onItemRemoved(int position) {
        cardlist.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position,cardlist.size());
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }
}
