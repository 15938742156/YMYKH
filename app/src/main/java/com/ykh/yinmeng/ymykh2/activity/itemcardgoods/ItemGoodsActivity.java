package com.ykh.yinmeng.ymykh2.activity.itemcardgoods;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.activity.itemcard.ItemCardActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.model.JifenItemgoodsResponse;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.GlideUtils;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogPopup;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.BasListView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ItemGoodsActivity extends BaseActivity implements View.OnClickListener, ItemGoodsMVPView {
    private LinearLayout ll_return;
    private TextView tv_title;
    private Button bt_exchange;
    private ImageView img_jifengoods;
    private BasListView listview;

    private List<JifenItemgoodsResponse.DataBean> itemlist = new ArrayList<>();
    private CommonAdapter adapter;
    private Dialog loadingDialog;
    public ItemGoodsPresenter<ItemGoodsMVPView> presenter;
    int type;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new ItemGoodsPresenterImp<>();
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
        setContentView(R.layout.activity_card_goods);
    }

    @Override
    public void initViews() {
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("商品兑换");
        listview = (BasListView) findViewById(R.id.listview);
        bt_exchange = (Button) findViewById(R.id.bt_exchange);
        img_jifengoods = (ImageView) findViewById(R.id.img_jifengoods);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
        listview.setLayoutManager(new LinearLayoutManager(this));
        Log.v("level", SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_LEVEL,0) + "");
        int space = 30;
        listview.addItemDecoration(new SpacesItemDecoration(space));
        adapter = new CommonAdapter<JifenItemgoodsResponse.DataBean>(ItemGoodsActivity.this, R.layout.activity_card_goods_listview,itemlist) {
            @Override
            protected void convert(ViewHolder holder, JifenItemgoodsResponse.DataBean dataBean, int position) {
                if (dataBean != null){
                    holder.setText(R.id.tv_goods, dataBean.getGoodsName());
                    holder.setText(R.id.tv_count, dataBean.getNum()+"元/万");
                    int level = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_LEVEL,0);

                    if (level == 0) {
                        holder.setText(R.id.tv_money, dataBean.getPrice() + "元");
                    }
                    if (level == 1) {
                        holder.setText(R.id.tv_money, dataBean.getOnePrice() + "元");
                    }
                    if (level == 2) {
                        holder.setText(R.id.tv_money, dataBean.getHighPrice() + "元");
                    }

                }else {
                    ToastUtils.showToast(ItemGoodsActivity.this, "暂无数据", Toast.LENGTH_LONG);
                }
            }
        };
        listview.setAdapter(adapter);
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        bt_exchange.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                ItemGoodsActivity.this.finish();
                break;
            case R.id.bt_exchange:
                presenter.onExchangeBt(this);
                break;
                default:
                    break;
        }
    }


    @Override
    public void getListSuccess(List<JifenItemgoodsResponse.DataBean> list) {
        this.itemlist.clear();
        itemlist.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getListFailure(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showImgSuccess(String img) {
        GlideUtils.loadImageView(ItemGoodsActivity.this, img,img_jifengoods);
    }

    @Override
    public void showDialog() {
        loadingDialog.show();
    }

    @Override
    public void dismissDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
