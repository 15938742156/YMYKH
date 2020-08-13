package com.ykh.yinmeng.ymykh2.activity.shouyixiangqing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.adapter.LogainViewAdapter;
import com.ykh.yinmeng.ymykh2.fragment.cashfragment.CashFragment;
import com.ykh.yinmeng.ymykh2.fragment.machine.MachineFragment;
import com.ykh.yinmeng.ymykh2.fragment.redpacket.RedpacketFragment;
import com.ykh.yinmeng.ymykh2.fragment.transactionfragment.TransactionFragment;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;


public class ShouyixiangqingActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title,tv_machine,tv_transaction,tv_cash,tv_redpacket;
    private NoScrollViewPager viewPager;
    private LinearLayout ll_return,ll_machine,ll_transaction,ll_cash,ll_redpacket;
    private ImageView img_machine,img_transaction,img_cash,img_redpacket;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_balance_fanli);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("收益详情");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        ll_machine = (LinearLayout) findViewById(R.id.ll_machine);
        ll_transaction = (LinearLayout) findViewById(R.id.ll_transaction);
        ll_cash = (LinearLayout) findViewById(R.id.ll_cash);
        ll_redpacket = (LinearLayout) findViewById(R.id.ll_redpacket);
        tv_machine = (TextView) findViewById(R.id.tv_machine);
        tv_transaction = (TextView) findViewById(R.id.tv_transaction);
        tv_cash = (TextView) findViewById(R.id.tv_cash);
        tv_redpacket = (TextView) findViewById(R.id.tv_redpacket);
        img_machine = (ImageView) findViewById(R.id.img_machine);
        img_transaction = (ImageView)findViewById(R.id.img_transaction);
        img_cash = (ImageView)findViewById(R.id.img_cash);
        img_redpacket = (ImageView)findViewById(R.id.img_redpacket);
        viewPager = (NoScrollViewPager) findViewById(R.id.viewPager);
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
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
        ll_machine.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                clearColor();
                setcolor(view.getId());
            }
        });

        ll_transaction.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                clearColor();
                setcolor(view.getId());

            }
        });
        ll_cash.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                clearColor();
                setcolor(view.getId());

            }
        });
        ll_redpacket.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                clearColor();
                setcolor(view.getId());

            }
        });
    }
    // 获得数据源
    private List<Fragment> getDate() {
        List<Fragment> list = new ArrayList<Fragment>();
        RedpacketFragment redpacket = new RedpacketFragment();
        MachineFragment machine = new MachineFragment();
        TransactionFragment transaction = new TransactionFragment();
        CashFragment cash  = new CashFragment();
        list.add(redpacket);
        list.add(machine);
        list.add(transaction);
        list.add(cash);
        return list;

    }

    @Override
    public void initData() {
        LogainViewAdapter adapter;
        adapter = new LogainViewAdapter(getSupportFragmentManager(), getDate());
        viewPager.setAdapter(adapter);
        clearColor();
        setcolor(0);
    }

    // 清除颜色置黑
    private void clearColor() {
        tv_machine.setTextColor(this.getResources().getColor(R.color.text_black));
        tv_transaction.setTextColor(this.getResources().getColor(R.color.text_black));
        tv_cash.setTextColor(this.getResources().getColor(R.color.text_black));
        tv_redpacket.setTextColor(this.getResources().getColor(R.color.text_black));
        img_redpacket.setVisibility(View.GONE);
        img_machine.setVisibility(View.GONE);
        img_transaction.setVisibility(View.GONE);
        img_cash.setVisibility(View.GONE);
    }
    // 设置颜色置橙
    private void setcolor(int num) {
        switch (num) {
            case 0:
            case R.id.ll_redpacket:
                img_redpacket.setVisibility(View.VISIBLE);
                tv_redpacket.setTextColor(this.getResources().getColor(R.color.main));
                viewPager.setCurrentItem(0);
                break;
            case 1:
            case R.id.ll_machine:
                img_machine.setVisibility(View.VISIBLE);
                tv_machine.setTextColor(this.getResources().getColor(R.color.main));
                viewPager.setCurrentItem(1);
                break;
            case 2:
            case R.id.ll_transaction:
                img_transaction.setVisibility(View.VISIBLE);
                tv_transaction.setTextColor(this.getResources().getColor(R.color.main));
                viewPager.setCurrentItem(2);
                break;
            case 3:
            case R.id.ll_cash:
                img_cash.setVisibility(View.VISIBLE);
                tv_cash.setTextColor(this.getResources().getColor(R.color.main));
                viewPager.setCurrentItem(3);
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                ShouyixiangqingActivity.this.finish();
                break;
            case R.id.ll_machine:
                clearColor();
                setcolor(v.getId());
                break;
            case R.id.ll_transaction:
                clearColor();
                setcolor(v.getId());
                break;
            case R.id.ll_cash:
                clearColor();
                setcolor(v.getId());
                break;
            case R.id.ll_redpacket:
                clearColor();
                setcolor(v.getId());
                break;
                default:
                    break;
        }
    }
}
