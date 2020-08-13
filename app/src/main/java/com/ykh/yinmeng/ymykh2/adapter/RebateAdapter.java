package com.ykh.yinmeng.ymykh2.adapter;

import android.content.Context;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.model.RebateResponse;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class RebateAdapter extends CommonAdapter<RebateResponse.DataBean> {
    public RebateAdapter(Context context, int layoutId, List<RebateResponse.DataBean> list) {
        super(context, layoutId, list);
    }

    @Override
    protected void convert(ViewHolder holder, RebateResponse.DataBean rebateBean, int position) {
        holder.setText(R.id.tv_name, rebateBean.getPaizhao());
        holder.setText(R.id.tv_sn,"机器编号 "+rebateBean.getLikebian());
        holder.setText(R.id.tv_time, String.valueOf(rebateBean.getAddtime()));

    }
}
