package com.ykh.yinmeng.ymykh2.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.adapter.FranchiseAdapter;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.listview.RefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FranchiseActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_title;
    private LinearLayout ll_return;
    private RefreshListView listView;
    private List<Map<String, Object>> list = new ArrayList<>();

    private FranchiseAdapter adapter;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_franchise);
    }

    @Override
    public void initViews() {
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("直营店加盟");
        listView = (RefreshListView) findViewById(R.id.listview);

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
    }

    @Override
    public void initData() {
        adapter = new FranchiseAdapter(this,getData());
        listView.setAdapter(adapter);
    }

    /***
     * 构建数据源
     * @return list
     */
    public List<Map<String,Object>> getData(){
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("title", "郑州总部");
        map1.put("address", "郑州市金水区经三路农业路交叉口财富广场4号楼1001室");
        map1.put("phone", "15890100095");
        map1.put("tel", "4000-987-168");
        list.add(map1);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("title", "焦作武陟店");
        map2.put("address", "黄河大道与朝阳二街交叉口向西100米路北");
        map2.put("phone", "15890100095");
        map2.put("tel", "0391-6318656");
        list.add(map2);

        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("title", "商丘民权店");
        map3.put("address", "民权县消防北路东方超市（精品店）东50米路南)");
        map3.put("phone", "13148051333");
        map3.put("tel", "0370-8988188");
        list.add(map3);

        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("title", "濮阳南乐店");
        map4.put("address", "濮阳市南乐县木轮河路图书馆正对面");
        map4.put("phone", "15515626233");
        map4.put("tel", "0393-5318168");
        list.add(map4);

        Map<String, Object> map5 = new HashMap<String, Object>();
        map5.put("title", "商丘柘城店");
        map5.put("address", "柘城县西关月亮湾二期邮政银行旁");
        map5.put("phone", "15937020098");
        map5.put("tel", "0370-6099210");
        list.add(map5);
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                FranchiseActivity.this.finish();
                break;
                default:
                    break;
        }

    }

}
