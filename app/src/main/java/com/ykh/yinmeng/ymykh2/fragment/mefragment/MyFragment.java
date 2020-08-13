package com.ykh.yinmeng.ymykh2.fragment.mefragment;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.FranchiseActivity;
import com.ykh.yinmeng.ymykh2.activity.MykefuActivity;
import com.ykh.yinmeng.ymykh2.activity.SetActivity;
import com.ykh.yinmeng.ymykh2.activity.ZhanghuanquanActivity;
import com.ykh.yinmeng.ymykh2.activity.address.AddressActivity;
import com.ykh.yinmeng.ymykh2.activity.cardlist.CardguanliActivity;
import com.ykh.yinmeng.ymykh2.activity.mydata.MydataActivity;
import com.ykh.yinmeng.ymykh2.activity.setjiaoyipassword.SetjiaoyipasswordActivity;
import com.ykh.yinmeng.ymykh2.activity.tixian.TixianActivity;
import com.ykh.yinmeng.ymykh2.activity.xinshoujiangtangxiangqing.XinshoujiangtangxiangqingActivity;
import com.ykh.yinmeng.ymykh2.activity.yaoqing.YaoqingActivity;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MyFragment extends Fragment implements View.OnClickListener,MyMVPView {
    /**头像*/
    private ImageView img_tel;
    private ImageView img_header;
    /**设置、推荐人姓名、推荐人电话*/
    private TextView tv_set,tv_name,tv_tel;
    /**我的资料、提现、我的客服、地址管理、银行卡管理、分享好友、新手讲堂、账户安全、直营店加盟*/
    private LinearLayout layout1,layout2,layout3,layout4,layout5,layout6,layout7,layout8,layout9;
    /***弹出窗口*/
    private PopupWindow popWin;
    /***保存弹出窗口布局*/
    private View popView;
    RxPermissions permissions;

    Intent intent;
    int isReal;
    private Dialog loadingDialog;
    private MyPresenter<MyMVPView> presenter;
    private SmartRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);

        initView(view);
        initListeners();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new MyPresenterImp<>();
        presenter.onAttach(this);
        presenter.onInit(getActivity());
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    /**
     * 实例化控件
     */
    private void initView(View view){
        permissions = new RxPermissions(getActivity());
        tv_set = (TextView) view.findViewById(R.id.tv_set);
        img_header = (ImageView) view.findViewById(R.id.img_header);
        img_tel = (ImageView)view.findViewById(R.id.img_tel);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_tel = (TextView) view.findViewById(R.id.tv_tel);
        layout1 = (LinearLayout)view.findViewById(R.id.layout1);
        layout2 = (LinearLayout)view.findViewById(R.id.layout2);
        layout3 = (LinearLayout)view.findViewById(R.id.layout3);
        layout4 = (LinearLayout)view.findViewById(R.id.layout4);
        layout5 = (LinearLayout)view.findViewById(R.id.layout5);
        layout6 = (LinearLayout)view.findViewById(R.id.layout6);
        layout7 = (LinearLayout)view.findViewById(R.id.layout7);
        layout8 = (LinearLayout)view.findViewById(R.id.layout8);
        layout9 = (LinearLayout)view.findViewById(R.id.layout9);
        loadingDialog = DialogUtil.createLoadingDialog(getActivity(), "");
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.onAttach(MyFragment.this);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });

    }

    private void initListeners(){
        tv_set.setOnClickListener(this);
        img_header.setOnClickListener(this);
        img_tel.setOnClickListener(this);
        tv_tel.setOnClickListener(this);
        layout1.setOnClickListener(this);
        layout2.setOnClickListener(this);
        layout3.setOnClickListener(this);
        layout4.setOnClickListener(this);
        layout5.setOnClickListener(this);
        layout6.setOnClickListener(this);
        layout7.setOnClickListener(this);
        layout8.setOnClickListener(this);
        layout9.setOnClickListener(this);

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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_set:
                intent = new Intent(getActivity(), SetActivity.class);
                startActivity(intent);
                break;
            case R.id.img_header:
                setImage(v);
//                isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//                if (isReal !=1 ){
//                    ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//                }else {
//                    setImage(v);
//                }
                break;
            case R.id.img_tel:
                call(tv_tel.getText().toString());
//                isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//                if (isReal !=1 ){
//                    ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//                }else {
//                    call(tv_tel.getText().toString());
//                }
                break;
            case R.id.tv_tel:
                call(tv_tel.getText().toString());
//                isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//                if (isReal !=1 ){
//                    ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//                }else {
//                    call(tv_tel.getText().toString());
//                }
                break;
            case R.id.layout1:
                intent = new Intent(getActivity(), MydataActivity.class);
                startActivity(intent);
//                isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//                if (isReal !=1 ){
//                    ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//                }else {
//                    intent = new Intent(getActivity(), MydataActivity.class);
//                    startActivity(intent);
//                }
                break;
            case R.id.layout2:
                intent = new Intent(getActivity(), TixianActivity.class);
                startActivity(intent);
//                isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//                if (isReal !=1 ){
//                    ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//                }else {
//                    intent = new Intent(getActivity(), TixianActivity.class);
//                    startActivity(intent);
//                }
                break;
            case R.id.layout3:
                intent = new Intent(getActivity(), MykefuActivity.class);
                startActivity(intent);
                break;
            case R.id.layout4:
                intent = new Intent(getActivity(), AddressActivity.class);
                startActivity(intent);
//                isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//                if (isReal !=1 ){
//                    ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//                }else {
//                    intent = new Intent(getActivity(), AddressActivity.class);
//                    startActivity(intent);
//                }
                break;
            case R.id.layout5:
                intent = new Intent(getActivity(), CardguanliActivity.class);
                startActivity(intent);
//                isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//                if (isReal !=1 ){
//                    ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//                }else {
//                    intent = new Intent(getActivity(), CardguanliActivity.class);
//                    startActivity(intent);
//                }
                break;
            case R.id.layout6:
                intent = new Intent(getActivity(), YaoqingActivity.class);
                startActivity(intent);
//                isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//                if (isReal !=1 ){
//                    ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//                }else {
//                    intent = new Intent(getActivity(), YaoqingActivity.class);
//                    startActivity(intent);
//                }
                break;
            case R.id.layout7:
                intent = new Intent(getActivity(), XinshoujiangtangxiangqingActivity.class);
                startActivity(intent);
//                isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//                if (isReal !=1 ){
//                    ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//                }else {
//                    intent = new Intent(getActivity(), XinshoujiangtangxiangqingActivity.class);
//                    startActivity(intent);
//                }
                break;
            case R.id.layout8:
                intent = new Intent(getActivity(), ZhanghuanquanActivity.class);
                startActivity(intent);
                break;
            case R.id.layout9:
                intent = new Intent(getActivity(), FranchiseActivity.class);
                startActivity(intent);
//                isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//                if (isReal !=1 ){
//                    ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//                }else {
//                    intent = new Intent(getActivity(), FranchiseActivity.class);
//                    startActivity(intent);
//                }
                break;
                default:
                    break;

        }

    }

    public void setImage(View view) {
        LayoutInflater li = LayoutInflater.from(getActivity());
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
                                    presenter.paiZhao(getActivity());
                                } else {
                                    ToastUtils.showToast(getActivity(), "用户拒绝了权限", Toast.LENGTH_SHORT);
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
                                    presenter.xuanZeTuPian(getActivity());
                                } else {
                                    ToastUtils.showToast(getActivity(), "用户拒绝了权限", Toast.LENGTH_SHORT);
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
    public void showNameSuccess(String name) {
        if (TextUtils.equals("推荐人 新汇平台",name)){
            img_tel.setVisibility(View.GONE);
        }else {
            img_tel.setVisibility(View.VISIBLE);
        }
        tv_name.setText(name);
    }

    @Override
    public void showTelSuccess(String tel) {
        tv_tel.setText(tel);
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(getActivity(), msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showUserHeadImg(Bitmap headBitmap) {
        img_header.setImageBitmap(headBitmap);
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
