package com.ykh.yinmeng.ymykh2.adapter;

import java.util.List;
import java.util.Map;
import com.ykh.yinmeng.ymykh2.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FranchiseAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Context context;
	
	
    public FranchiseAdapter(Context context, List<Map<String, Object>> list){
		
		this.context=context;
		this.list=list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int index, View view, ViewGroup arg2) {
		ViewHolder holder=null;
		
		if(view==null){
			holder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.activity_franchise_listview, null);
			holder.tv_title=(TextView) view.findViewById(R.id.tv_title);
			holder.tv_address=(TextView) view.findViewById(R.id.tv_address);
			holder.tv_phone=(TextView) view.findViewById(R.id.tv_phone);
			holder.tv_tel=(TextView) view.findViewById(R.id.tv_tel);
			view.setTag(holder);
		}else{
			holder=(ViewHolder) view.getTag();
		}
		holder.tv_title.setText(list.get(index).get("title").toString());
		holder.tv_address.setText(list.get(index).get("address").toString());
		holder.tv_phone.setText(list.get(index).get("phone").toString());
		holder.tv_tel.setText(list.get(index).get("tel").toString());
		return view;
	}
	
	public final class ViewHolder{
		public TextView tv_title,tv_address,tv_phone,tv_tel;
	}


}
