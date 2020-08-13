package com.ykh.yinmeng.ymykh2.activity.myorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.adapter.LogainViewAdapter;
import com.ykh.yinmeng.ymykh2.fragment.allorder.AllorderFragment;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;


public class MyOrderActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout linearLayout1,linearLayout2,linearLayout3,linearLayout4;
    private TextView tv_title,tv_quanbu,tv_daifukuan,tv_daifahuo,tv_daishouhuo;
    private NoScrollViewPager viewPager;
    private LinearLayout ll_return;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }
    protected void onResume() {
        int id = getIntent().getIntExtra("id", 0);
        if (id == 1) {
            AllorderFragment fragmen = new AllorderFragment();
            FragmentManager fmanger = getSupportFragmentManager();
            FragmentTransaction transaction = fmanger.beginTransaction();
            transaction.replace(R.id.viewpager, fragmen);
            transaction.commit();
            viewPager.setCurrentItem(0);//
            setcolor(0);
            //帮助跳转到指定子fragment
            Intent i=new Intent();
            i.setClass(this,AllorderFragment.newInstance(-1).getClass());
            i.putExtra("id",0);
        }
        if (id == 2) {
            AllorderFragment fragmen = new AllorderFragment();
            FragmentManager fmanger = getSupportFragmentManager();
            FragmentTransaction transaction = fmanger.beginTransaction();
            transaction.replace(R.id.viewpager, fragmen);
            transaction.commit();
            viewPager.setCurrentItem(1);//
            setcolor(1);
            //帮助跳转到指定子fragment
            Intent i=new Intent();
            i.setClass(this,AllorderFragment.newInstance(0).getClass());
            i.putExtra("id",1);
        }
        if (id == 3) {
            AllorderFragment fragmen = new AllorderFragment();
            FragmentManager fmanger = getSupportFragmentManager();
            FragmentTransaction transaction = fmanger.beginTransaction();
            transaction.replace(R.id.viewpager, fragmen);
            transaction.commit();
            viewPager.setCurrentItem(2);//
            setcolor(2);
            //帮助跳转到指定子fragment
            Intent i=new Intent();
            i.setClass(this,AllorderFragment.newInstance(1).getClass());
            i.putExtra("id",2);
        }
        if (id == 4) {
            AllorderFragment fragmen = new AllorderFragment();
            FragmentManager fmanger = getSupportFragmentManager();
            FragmentTransaction transaction = fmanger.beginTransaction();
            transaction.replace(R.id.viewpager, fragmen);
            transaction.commit();
            viewPager.setCurrentItem(3);//
            setcolor(3);
            //帮助跳转到指定子fragment
            Intent i=new Intent();
            i.setClass(this,AllorderFragment.newInstance(2).getClass());
            i.putExtra("id",3);
        }
        super.onResume();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.fragement_myorder);

    }

    @Override
    public void initViews() {
        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        linearLayout4 = (LinearLayout) findViewById(R.id.linearLayout4);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("我的订单");
        tv_quanbu = (TextView) findViewById(R.id.tv_quanbu);
        tv_daifukuan = (TextView) findViewById(R.id.tv_daifukuan);
        tv_daifahuo = (TextView) findViewById(R.id.tv_daifahuo);
        tv_daishouhuo = (TextView) findViewById(R.id.tv_daishouhuo);
        viewPager = (NoScrollViewPager) findViewById(R.id.viewpager);
        ll_return = (LinearLayout) findViewById(R.id.ll_return);

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        linearLayout4.setOnClickListener(this);
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
    }
    @Override
    public void initData() {
        LogainViewAdapter adapter;
        adapter = new LogainViewAdapter(getSupportFragmentManager(), getDate());
        viewPager.setAdapter(adapter);
        clearColor();
    }

    // 获得数据源
    private List<Fragment> getDate() {
        List<Fragment> list = new ArrayList<Fragment>();

        AllorderFragment fragment1 = AllorderFragment.newInstance(-1);
        AllorderFragment fragment2 = AllorderFragment.newInstance(0);
        AllorderFragment fragment3 = AllorderFragment.newInstance(1);
        AllorderFragment fragment4 = AllorderFragment.newInstance(2);


        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        list.add(fragment4);

        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                MyOrderActivity.this.finish();
                break;
            case R.id.linearLayout1:
                clearColor() ;
                setcolor(v.getId());
                break;
            case R.id.linearLayout2:
                clearColor() ;
                setcolor(v.getId());
                break;
            case R.id.linearLayout3:
                clearColor() ;
                setcolor(v.getId());
                break;
            case R.id.linearLayout4:
                clearColor() ;
                setcolor(v.getId());
                break;
        }
    }
    // 清除颜色置黑
    private void clearColor() {
        tv_quanbu.setTextColor(getResources().getColor(R.color.text_black));
        tv_daifukuan.setTextColor(getResources().getColor(R.color.text_black));
        tv_daifahuo.setTextColor(getResources().getColor(R.color.text_black));
        tv_daishouhuo.setTextColor(getResources().getColor(R.color.text_black));

        linearLayout1.setBackgroundResource(0);
        linearLayout2.setBackgroundResource(0);
        linearLayout3.setBackgroundResource(0);
        linearLayout4.setBackgroundResource(0);
    }
    // 设置颜色置橙
    private void setcolor(int num) {
        switch (num) {
            case 0:
            case R.id.linearLayout1:
                tv_quanbu.setTextColor(getResources().getColor(R.color.main));
                viewPager.setCurrentItem(0);
                break;
            case 1:
            case R.id.linearLayout2:
                tv_daifukuan.setTextColor(getResources().getColor(R.color.main));
                viewPager.setCurrentItem(1);
                break;
            case 2:
            case R.id.linearLayout3:
                tv_daifahuo.setTextColor(getResources().getColor(R.color.main));
                viewPager.setCurrentItem(2);
                break;
            case 3:
            case R.id.linearLayout4:
                tv_daishouhuo.setTextColor(getResources().getColor(R.color.main));
                viewPager.setCurrentItem(3);
                break;

            default:
                break;
        }
    }
}
