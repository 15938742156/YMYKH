package com.ykh.yinmeng.ymykh2.fragment.orderlistfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.adapter.LogainViewAdapter;
import com.ykh.yinmeng.ymykh2.fragment.lowerorderfragment.Xiajiorderfragment;
import com.ykh.yinmeng.ymykh2.fragment.myorderfragment.MyorderFragment;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;


/**
 * 订单管理
 * @author Angel
 *
 */
public class OrderFragment extends Fragment implements View.OnClickListener {
	private TextView tv_title,tv_myorder,tv_lowerorder;
	private LinearLayout ll_return,layout_my,layout_lower;
	private ImageView img_myorder,img_lowerorder;
	//定义一个ViewPager容器
	private NoScrollViewPager viewPager;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_orderlist, container,false);

		initViews(view);
		initListeners();
		initData();
		return view;
	}
	
	private void initViews(View view) {
		tv_title = (TextView)view.findViewById(R.id.tv_title);
		tv_title.setText("订单管理");
		ll_return = (LinearLayout)view.findViewById(R.id.ll_return);
		ll_return.setVisibility(View.GONE);
		tv_myorder = (TextView)view.findViewById(R.id.tv_myorder);
		tv_lowerorder = (TextView)view.findViewById(R.id.tv_lowerorder);
		layout_my = (LinearLayout)view.findViewById(R.id.layout_my);
		layout_lower = (LinearLayout)view.findViewById(R.id.layout_lower);
		img_myorder = (ImageView)view.findViewById(R.id.img_myorder);
		img_lowerorder = (ImageView)view.findViewById(R.id.img_lowerorder);
		viewPager = (NoScrollViewPager) view.findViewById(R.id.viewpager);
	}
	public void initListeners() {
		layout_my.setOnClickListener(this);
		layout_lower.setOnClickListener(this);

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
	// 获得数据源
	private List<Fragment> getDate() {
		List<Fragment> list = new ArrayList<Fragment>();

		MyorderFragment myorder = new MyorderFragment();
		Xiajiorderfragment xiajiorder = new Xiajiorderfragment();

		list.add(myorder);
		list.add(xiajiorder);
		return list;

	}


	public void initData() {
		LogainViewAdapter adapter;
		adapter = new LogainViewAdapter(getChildFragmentManager(), getDate());
		viewPager.setAdapter(adapter);
		clearColor();
		setcolor(0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.layout_my:
				clearColor();
				setcolor(v.getId());
				break;
			case R.id.layout_lower:
				clearColor();
				setcolor(v.getId());
				break;
			default:
				break;
		}
	}
	// 清除颜色置黑
	private void clearColor() {
		img_myorder.setVisibility(View.GONE);
		tv_myorder.setTextColor(this.getResources().getColor(R.color.text_black));
		img_lowerorder.setVisibility(View.GONE);
		tv_lowerorder.setTextColor(this.getResources().getColor(R.color.text_black));
	}
	// 设置颜色置橙
	private void setcolor(int num) {
		switch (num) {
			case R.id.layout_my:case 0:
				img_myorder.setImageResource(R.drawable.shape5);
				img_myorder.setVisibility(View.VISIBLE);
				tv_myorder.setTextColor(this.getResources().getColor(R.color.main));
				viewPager.setCurrentItem(0);
				break;
			case R.id.layout_lower:case 1:
				img_lowerorder.setImageResource(R.drawable.shape5);
				img_lowerorder.setVisibility(View.VISIBLE);
				tv_lowerorder.setTextColor(this.getResources().getColor(R.color.main));
				viewPager.setCurrentItem(1);
				break;

			default:
				break;
		}
	}
}
