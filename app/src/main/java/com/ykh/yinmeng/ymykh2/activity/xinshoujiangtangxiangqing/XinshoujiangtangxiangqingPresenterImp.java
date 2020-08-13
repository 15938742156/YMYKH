package com.ykh.yinmeng.ymykh2.activity.xinshoujiangtangxiangqing;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.utils.Urls;

public class XinshoujiangtangxiangqingPresenterImp<V extends XinshoujiangtangxiangqingMVPView> extends BasePresenter<V> implements XinshoujiangtangxiangqingPresenter<V> {


    int id = 1;
    String url = null;
    String path = null;
    String title = null;
    String text = null;


    public XinshoujiangtangxiangqingPresenterImp(){
        super();
    }

    @Override
    public void onInit(Activity activity) {
        url = "http://app.ykh9.com/ymapp/lecture.html";
        getAttachedView().getUrlSuccess(url);
        Log.v("hhhhhhhhhhhhhhhhhh",url);
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);

    }
    @Override
    public void onToggleYaoqingBtn(Activity activity) {
        getAttachedView().showShareView(title,text,url);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
