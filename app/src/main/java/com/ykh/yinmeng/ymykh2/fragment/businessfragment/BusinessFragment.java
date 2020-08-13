package com.ykh.yinmeng.ymykh2.fragment.businessfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.RefreshListView;


/**
 *
 * @author Angel
 *
 */
public class BusinessFragment extends Fragment {
	private TextView tv_center;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_business, container,false);
		
		initView(view);
		
		
		return view;
	}
	
	private void initView(View view){
		tv_center=(TextView)view.findViewById(R.id.tv_center);
	}
	


}
