package com.ykh.yinmeng.ymykh2.activity.shanghuzicaixiangqing;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.querendingdan.QuerendingdanActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;
import com.ykh.yinmeng.ymykh2.utils.StringReplaceUtil;
import com.ykh.yinmeng.ymykh2.utils.Urls;

public class ShanghuzicaiPresenterImp<V extends ShanghuzicaiMVPView> extends BasePresenter<V> implements ShanghuzicaiPresenter<V> {


    String url = null;
    String title = null;
    String img = null;
    String miaoshu = null;
    double yun;
    double money;
    int gz_num;

    int id ;

    public ShanghuzicaiPresenterImp(){
        super();
    }

    @Override
    public void onInit(Activity activity) {
        Intent intent = activity.getIntent();
        id = Integer.valueOf(intent.getExtras().getInt("id"));
        title = String.valueOf(intent.getExtras().getString("title"));
        img = String.valueOf(intent.getExtras().getString("img"));
        money = Double.valueOf(intent.getExtras().getDouble("money"));
        miaoshu = String.valueOf(intent.getExtras().getString("miaoshu"));
        gz_num = Integer.valueOf(intent.getExtras().getInt("gz_num"));
        yun = Double.valueOf(intent.getExtras().getDouble("yun"));
        url = Urls.URL_POS_DETIL+ "?id=" + id;
        getAttachedView().getUrlSuccess(url);
        getAttachedView().getTitleSuccess(title);
        getAttachedView().getMoneySuccess(String.valueOf(money));
        getAttachedView().getYunSuccess(String.valueOf(yun));
        getAttachedView().getIdSuccess(String.valueOf(id));
        Log.v("oooooooooooooo",id+"&"+url+title+"&"+yun+"&"+money+"&"+img+"&"+miaoshu+"&"+gz_num);
    }
    @Override
    public void onNumEditTextChange(CharSequence charSequence) {
    }

    @Override
    public void onBuyBtn(Activity activity,String num) {
        Intent intent = new Intent(activity.getApplicationContext(), QuerendingdanActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("money",money);
        intent.putExtra("miaoshu",miaoshu);
        intent.putExtra("gz_num",gz_num);
        intent.putExtra("img",img);
        intent.putExtra("yun",yun);
        intent.putExtra("num",num);
        intent.putExtra("gid",id);
        activity.startActivityForResult(intent, 269);
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);

    }
    @Override
    public void onToggleYaoqingBtn(Activity activity) {
        getAttachedView().showShareView(title,miaoshu,url,img);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
