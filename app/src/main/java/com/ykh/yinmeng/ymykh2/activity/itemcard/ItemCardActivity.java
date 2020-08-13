package com.ykh.yinmeng.ymykh2.activity.itemcard;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.activity.kefuweixin.KefuActivity;
import com.ykh.yinmeng.ymykh2.model.ItemcardResponse;
import com.ykh.yinmeng.ymykh2.utils.GlideUtils;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.BasListView;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.RefreshListView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ItemCardActivity extends BaseActivity implements View.OnClickListener, ItemCardMVPView {
    private LinearLayout ll_return;
    private TextView tv_title,tv_earn;
    private EditText et_earn;
    private Button bt_count;
    private BasListView listview;
    private SmartRefreshLayout refreshLayout;

    private List<ItemcardResponse.DataBean> itemlist = new ArrayList<>();
    private CommonAdapter adapter;
    private Dialog loadingDialog;
    public ItemCardPresenter<ItemCardMVPView> presenter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new ItemCardPresenterImp<>();
        presenter.onAttach(this);
        presenter.onInit(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_itemcard);
    }

    @Override
    public void initViews() {
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        tv_title = (TextView) findViewById(R.id.tv_title);
        listview = (BasListView) findViewById(R.id.listview);
        tv_earn = (TextView) findViewById(R.id.tv_earn);
        et_earn = (EditText) findViewById(R.id.et_earn);
        bt_count = (Button) findViewById(R.id.bt_count);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        listview.setLayoutManager(new LinearLayoutManager(this));
        int space = 20;
        listview.addItemDecoration(new SpacesItemDecoration(space));
        adapter = new CommonAdapter<ItemcardResponse.DataBean>(this, R.layout.activity_itemcard_listview,itemlist) {
            @Override
            protected void convert(ViewHolder holder, ItemcardResponse.DataBean dataBean, int position) {
                if (dataBean.getType() == 1){
                    holder.setText(R.id.tv_goods_name, dataBean.getCateName()+"(自助)");
                    holder.setBackgroundRes(R.id.img_goods,R.mipmap.jifenzizhu);
                    holder.setOnClickListener(R.id.tv_duihuan, ItemCardActivity.this);
                    holder.setTag(R.id.tv_duihuan, position);
                }
                if (dataBean.getType() == 2){
                    holder.setText(R.id.tv_goods_name, dataBean.getCateName()+"(实物)");
                    holder.setBackgroundRes(R.id.img_goods,R.mipmap.jifenshiwu);
                    holder.setOnClickListener(R.id.tv_duihuan, ItemCardActivity.this);
                    holder.setTag(R.id.tv_duihuan, position);
                }
                if (dataBean.getType() == 3){
                    holder.setText(R.id.tv_goods_name, dataBean.getCateName()+"(客服)");
                    holder.setBackgroundRes(R.id.img_goods,R.mipmap.jifenkefu);
                    holder.setOnClickListener(R.id.tv_duihuan, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ItemCardActivity.this, KefuActivity.class);
                            startActivity(intent);
                        }
                    });

                }
                holder.setText(R.id.tv_jiazhi, "积分价值："+dataBean.getIntegral()+"元/万");
                holder.setText(R.id.tv_danwei,"起兑单位："+dataBean.getUnit()+"积分");

            }
        };
        listview.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.onInit(ItemCardActivity.this);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        bt_count.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                ItemCardActivity.this.finish();
                break;
            case R.id.bt_count:
                if (et_earn.getText().toString().isEmpty()){
                    ToastUtils.showToast(this, "请输入积分数量", Toast.LENGTH_SHORT);
                }else {
                    presenter.onBtnCount(et_earn.getText().toString());
                }
                break;
            case R.id.tv_duihuan:
                presenter.onToggleItem(this,(int)v.getTag());
                break;
                default:
                    break;
        }
    }

    @Override
    public void getTitleSuccess(String title) {
        tv_title.setText(title);
    }

    @Override
    public void showCountSuccess(String count) {
        tv_earn.setText(count);
    }

    @Override
    public void getListSuccess(List<ItemcardResponse.DataBean> list) {
        this.itemlist.clear();
        itemlist.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getListFailure(String msg) {
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
