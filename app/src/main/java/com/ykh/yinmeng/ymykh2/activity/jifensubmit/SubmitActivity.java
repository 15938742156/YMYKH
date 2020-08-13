package com.ykh.yinmeng.ymykh2.activity.jifensubmit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.activity.jifencardlist.CardguanliActivity;
import com.ykh.yinmeng.ymykh2.model.JifenItemgoodsResponse;
import com.ykh.yinmeng.ymykh2.utils.SpacesItemDecoration;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.GridSpacingItemDecoration;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogPopup;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SubmitActivity extends BaseActivity implements View.OnClickListener, SubmitMVPView {
    private LinearLayout ll_return;
    private TextView tv_title;
    private EditText et_code,et_remark;
    private Button bt_submit;
    private RecyclerView gridview;
    private ImageView img_photo;

    private List<JifenItemgoodsResponse.DataBean> itemlist = new ArrayList<>();
    private CommonAdapter adapter;
    private Dialog loadingDialog;
    public SubmitPresenter<SubmitMVPView> presenter;
    int type;
    /***弹出窗口*/
    private PopupWindow popWin;
    /***保存弹出窗口布局*/
    private View popView;
    RxPermissions permissions;
    Bitmap img;
    String sn;
    String remark;
    String gid;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new SubmitPresenterImp<>();
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
        setContentView(R.layout.activity_submit_info);
    }

    @Override
    public void initViews() {
        permissions = new RxPermissions(SubmitActivity.this);
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("提交资料");
        gridview = (RecyclerView) findViewById(R.id.gridview);
        bt_submit = (Button) findViewById(R.id.bt_submit);
        img_photo = (ImageView) findViewById(R.id.img_photo);
        et_code = (EditText) findViewById(R.id.et_code);
        et_remark = (EditText) findViewById(R.id.et_remark);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
        gridview.setLayoutManager(new GridLayoutManager(this,3));
        gridview.addItemDecoration(new GridSpacingItemDecoration(3, 20, true));
        adapter = new CommonAdapter<JifenItemgoodsResponse.DataBean>(this, R.layout.activity_submit_info_gridview,itemlist) {
            @Override
            protected void convert(ViewHolder holder, JifenItemgoodsResponse.DataBean dataBean, int position) {
                holder.setText(R.id.bt_goods, dataBean.getGoodsName());
                holder.setOnClickListener(R.id.bt_goods,SubmitActivity.this);
                holder.setTag(R.id.bt_goods, position);
                if (dataBean.isSelect) {
                    holder.setBackgroundRes(R.id.bt_goods, R.drawable.btn_bg_corner_bule);
                    holder.setTextColorRes(R.id.bt_goods, R.color.text_white);
                } else {
                    holder.setBackgroundRes(R.id.bt_goods, R.drawable.btn_bg_corner_yellow);
                    holder.setTextColorRes(R.id.bt_goods, R.color.main);
                }

            }
        };
        gridview.setAdapter(adapter);
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        img_photo.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.ll_return:
                SubmitActivity.this.finish();
                break;
            case R.id.bt_submit:
                sn = et_code.getText().toString().trim();
                remark = et_remark.getText().toString().trim();
                if (gid == null){
                    ToastUtils.showToast(this, "请选择需要兑换的商品", Toast.LENGTH_SHORT);
                }if (sn == null || sn.isEmpty()){
                    ToastUtils.showToast(this, "请填写兑换码", Toast.LENGTH_SHORT);
                }if (img == null){
                    ToastUtils.showToast(this, "请选择要上传的图片", Toast.LENGTH_SHORT);
                }else{
                    {
                        presenter.onExchangeBt(this,gid,sn,String.valueOf(img),remark);
                    }

                }
                break;
            case R.id.img_photo:
                setImage(v);
                break;
            case R.id.bt_goods:
                TextView bt_goods = (TextView) v;
                int position = (int) bt_goods.getTag();

                for (int i = 0; i < itemlist.size(); i++) {
                    JifenItemgoodsResponse.DataBean bean = itemlist.get(i);
                    if (position == i) {
                        bean.isSelect = true;
                        gid = String.valueOf(bean.getId());
                    } else {
                        bean.isSelect = false;
                    }
                }
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    public void setImage(View view) {
        LayoutInflater li = LayoutInflater.from(SubmitActivity.this);
        popView = li.inflate(R.layout.activity_xiugaitouxing, null); // 将布局文件转换为View
        popWin = new PopupWindow(popView, LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT, true);// 实例化PopupWindow
//        popWin.setFocusable(true);// 如果不在弹出之前加上这条语句,你是无法在editText中输入任何东西的。当设置为true时，系统会捕获到焦点给popupWindow
        // 获取自定义布局文件中的控件
        final Button bt_paizhao = (Button) popView.findViewById(R.id.bt_paizhao);
        final Button bt_xuanzhephtope = (Button) popView.findViewById(R.id.bt_xuanzhephtope);
        final Button bt_quxiao = (Button) popView.findViewById(R.id.bt_quxiao);

        ColorDrawable dw = new ColorDrawable(0000000000);// 实例化一个ColorDrawable颜色为半透明
        popView.setBackgroundDrawable(dw);// 设置PopupWindow的背景颜色
        popWin.setAnimationStyle(R.style.mypopwindow_style);// 设置SelectPicPopupWindow弹出窗体动画效果

        // 拍照按钮
        bt_paizhao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                permissions.request(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Boolean isgrant) {
                                if (isgrant) {
                                    presenter.paiZhao(SubmitActivity.this);
                                } else {
                                    ToastUtils.showToast(SubmitActivity.this, "用户拒绝了权限", Toast.LENGTH_SHORT);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                popWin.dismiss();
                            }

                            @Override
                            public void onComplete() {
                                popWin.dismiss();
                            }
                        });

            }
        });
        //popWin.setView(view);
        //popWin.show();


        // 选择图片按钮
        bt_xuanzhephtope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                permissions.request(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                if (aBoolean) {
                                    presenter.xuanZeTuPian(SubmitActivity.this);
                                } else {
                                    ToastUtils.showToast(SubmitActivity.this, "用户拒绝了权限", Toast.LENGTH_SHORT);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                popWin.dismiss();
                            }

                            @Override
                            public void onComplete() {
                                popWin.dismiss();
                            }
                        });
            }
        });

        // 取消按钮
        bt_quxiao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popWin.dismiss();
            }
        });

        popWin.showAsDropDown(view);
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
    public void getTypeSuccess(int type) {
        this.type = type;
    }

    @Override
    public void showSubmitSuccess(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showSubmitFailure(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showUserHeadImg(Bitmap headBitmap) {
        this.img = headBitmap;
        img_photo.setImageBitmap(headBitmap);

    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
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
