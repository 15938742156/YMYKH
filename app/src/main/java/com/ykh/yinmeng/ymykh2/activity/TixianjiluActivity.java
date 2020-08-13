package com.ykh.yinmeng.ymykh2.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.adapter.LogainViewAdapter;
import com.ykh.yinmeng.ymykh2.fragment.machine.MachineFragment;
import com.ykh.yinmeng.ymykh2.fragment.orderdikoujilu.OrderdikoujiluFragment;
import com.ykh.yinmeng.ymykh2.fragment.tixianjilu.TixianjiluFragment;
import com.ykh.yinmeng.ymykh2.fragment.transactionfragment.TransactionFragment;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;


public class TixianjiluActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title,tv_machine,tv_transaction;
    private NoScrollViewPager viewPager;
    private LinearLayout ll_return,ll_machine,ll_transaction;
    private ImageView img_machine,img_transaction;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_balance_fan);
    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("提现记录");
        ll_return = (LinearLayout)findViewById(R.id.ll_return);
        ll_machine = (LinearLayout) findViewById(R.id.ll_machine);
        ll_transaction = (LinearLayout) findViewById(R.id.ll_transaction);
        tv_machine = (TextView) findViewById(R.id.tv_machine);
        tv_machine.setText("提现记录");
        tv_transaction = (TextView) findViewById(R.id.tv_transaction);
        tv_transaction.setText("订单抵扣记录");
        img_machine = (ImageView) findViewById(R.id.img_machine);
        img_transaction = (ImageView)findViewById(R.id.img_transaction);
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
    }
    // 获得数据源
    private List<Fragment> getDate() {
        List<Fragment> list = new ArrayList<Fragment>();
        TixianjiluFragment tixian = new TixianjiluFragment();
        OrderdikoujiluFragment orderdikoujilu = new OrderdikoujiluFragment();

        list.add(tixian);
        list.add(orderdikoujilu);

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
        img_machine.setVisibility(View.GONE);
        img_transaction.setVisibility(View.GONE);
    }
    // 设置颜色置橙
    private void setcolor(int num) {
        switch (num) {
            case 0:
            case R.id.ll_machine:
                img_machine.setVisibility(View.VISIBLE);
                tv_machine.setTextColor(this.getResources().getColor(R.color.main));
                viewPager.setCurrentItem(0);
                break;
            case 1:
            case R.id.ll_transaction:
                img_transaction.setVisibility(View.VISIBLE);
                tv_transaction.setTextColor(this.getResources().getColor(R.color.main));
                viewPager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                TixianjiluActivity.this.finish();
                break;
            case R.id.ll_machine:
                clearColor();
                setcolor(v.getId());
                break;
            case R.id.ll_transaction:
                clearColor();
                setcolor(v.getId());
                break;
                default:
                    break;
        }
    }
}
