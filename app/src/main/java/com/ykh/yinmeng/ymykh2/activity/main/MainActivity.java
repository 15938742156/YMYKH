package com.ykh.yinmeng.ymykh2.activity.main;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.activity.shouyixiangqing.ShouyixiangqingActivity;
import com.ykh.yinmeng.ymykh2.adapter.LogainViewAdapter;
import com.ykh.yinmeng.ymykh2.fragment.mefragment.MyFragment;
import com.ykh.yinmeng.ymykh2.fragment.orderlistfragment.OrderFragment;
import com.ykh.yinmeng.ymykh2.fragment.homefragment.HomeFragment;
import com.ykh.yinmeng.ymykh2.utils.PermissionListener;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.utils.UpdateManager;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.NoScrollViewPager;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.RedpacketPopup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener,MainMVPView {
    //下面每个Layout对象
    private RelativeLayout home_layout,order_layout,me_layout;
    //依次获得ImageView与TextView
    private ImageView home_img,order_img,me_img;
    private TextView home_txt,order_txt,me_txt;
    //定义一个ViewPager容器
    private NoScrollViewPager viewPager;
    private Dialog loadingDialog;
    public MainPresenter<MainMVPView> presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainPresenterImp<>();
        presenter.onAttach(this);

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initViews() {
        viewPager = (NoScrollViewPager) findViewById(R.id.viewpager);
        home_layout = (RelativeLayout) findViewById(R.id.home_layout);
//        business_layout = (RelativeLayout) findViewById(R.id.business_layout);
        order_layout = (RelativeLayout) findViewById(R.id.order_layout);
        me_layout = (RelativeLayout) findViewById(R.id.me_layout);
        home_img = (ImageView) findViewById(R.id.home_img);
//        business_img = (ImageView) findViewById(R.id.business_img);
        order_img = (ImageView) findViewById(R.id.order_img);
        me_img = (ImageView) findViewById(R.id.me_img);
        home_txt = (TextView) findViewById(R.id.home_txt);
//        business_txt = (TextView) findViewById(R.id.business_txt);
        order_txt = (TextView) findViewById(R.id.order_txt);
        me_txt = (TextView) findViewById(R.id.me_txt);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");

    }

    @Override
    public void initListeners() {
        home_layout.setOnClickListener(this);
//        business_layout.setOnClickListener(this);
        order_layout.setOnClickListener(this);
        me_layout.setOnClickListener(this);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int flog) {
                if (flog == 2) {
                    int i = viewPager.getCurrentItem();
                    clearColor();
                    setcolor(i);
                }

            }
        });
        new UpdateManager().sendRequest(this);
        String[] permissions = new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES};
        requestRuntimePermission(permissions, new PermissionListener() {
            @Override
            public void onGranted() {
            }

            @Override
            public void onDenied(List<String> deniedPermissions) {
//                    ToastUtils.showToast(mContext, "权限被拒绝,请进入设置打开权限", Toast.LENGTH_SHORT);
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_layout:
                clearColor();
                setcolor(v.getId());
                break;
//            case R.id.business_layout:
//                clearColor();
//                setcolor(v.getId());
//                break;
            case R.id.order_layout:
                clearColor();
                setcolor(v.getId());
                break;
            case R.id.me_layout:
                clearColor();
                setcolor(v.getId());
                break;
                default:
                    break;
        }

    }

    // 获得数据源
    private List<Fragment> getDate() {
        List<Fragment> list = new ArrayList<Fragment>();

        HomeFragment home = new HomeFragment();
//        BusinessFragment business = new BusinessFragment();
        OrderFragment order = new OrderFragment();
        MyFragment my = new MyFragment();

        list.add(home);
//        list.add(business);
        list.add(order);
        list.add(my);

        return list;

    }
    @Override
    public void initData() {
        LogainViewAdapter adapter;
        adapter = new LogainViewAdapter(getSupportFragmentManager(), getDate());
        viewPager.setAdapter(adapter);
        clearColor();
        setcolor(0);

        /*int isReal = SharedPreferencesUtils.getInstance(this).get(Constant.SHARED_AUTHENTICATION, -1);
        if (isReal != 1 && isReal != -1) {
            startActivity(new Intent(App.getContext(), ShimingActivity.class));
        }*/
    }


       // 清除颜色置黑
    private void clearColor() {
        home_img.setImageResource(R.mipmap.home_gray);
        home_txt.setTextColor(this.getResources().getColor(R.color.text_black));
//        business_img.setImageResource(R.mipmap.business_gray);
//        business_txt.setTextColor(this.getResources().getColor(R.color.text_black));
        order_img.setImageResource(R.mipmap.order_gray);
        order_txt.setTextColor(this.getResources().getColor(R.color.text_black));
        me_img.setImageResource(R.mipmap.my_gray);
        me_txt.setTextColor(this.getResources().getColor(R.color.text_black));
    }
    // 设置颜色置橙
    private void setcolor(int num) {
        switch (num) {
            case R.id.home_layout:case 0:
                home_img.setImageResource(R.mipmap.home_bule);
                home_txt.setTextColor(this.getResources().getColor(R.color.main));
                viewPager.setCurrentItem(0);
                break;
//            case R.id.business_layout:case 1:
//                business_img.setImageResource(R.mipmap.business_bule);
//                business_txt.setTextColor(this.getResources().getColor(R.color.main));
//                viewPager.setCurrentItem(1);
//                break;
            case R.id.order_layout:case 1:
                order_img.setImageResource(R.mipmap.order_bule);
                order_txt.setTextColor(this.getResources().getColor(R.color.main));
                viewPager.setCurrentItem(1);
                break;
            case R.id.me_layout:case 2:
                me_img.setImageResource(R.mipmap.my_bule);
                me_txt.setTextColor(this.getResources().getColor(R.color.main));
                viewPager.setCurrentItem(2);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private long exitTime = 0;
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showToast(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT);
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        UpdateManager.cancelAllMission(this);
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showNumSuccess(int num,String money) {
        if (num >0){
            new RedpacketPopup(mContext)
                    .setTitle(String.valueOf(num))
                    .setContent(money)
                    .setClickListener(new RedpacketPopup.ViewClickListener() {
                        @Override
                        public void confirm(View view) {
                            Intent intent = new Intent(MainActivity.this, ShouyixiangqingActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void cancel(View view) {

                        }
                    }).showPopupWindow();
        }
    }
    @Override
    public void getDataFailure(String msg) {
        ToastUtils.showToast(mContext, msg, Toast.LENGTH_SHORT);
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
