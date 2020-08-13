package com.ykh.yinmeng.ymykh2.fragment.homefragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;


import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.login.LoginActivity;
import com.ykh.yinmeng.ymykh2.bean.HomebroadcastBean;
import com.ykh.yinmeng.ymykh2.bean.HomegoodsBean;
import com.ykh.yinmeng.ymykh2.bean.HomelogoBean;
import com.ykh.yinmeng.ymykh2.bean.HomerollBean;
import com.ykh.yinmeng.ymykh2.model.HomeResponse;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;
import com.ykh.yinmeng.ymykh2.utils.StringReplaceUtil;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeFragmentPresenterImp<V extends HomeFragmentMVPView> extends BasePresenter<V> implements HomeFragmentPresenter<V> {


    private static final int TIME_INTERVAL = 2000;

    private static final int MSG_GOOD = 735;
    private int goodsPosition = 0;

    private static final int REQUEST_CODE_CARD = 169;
    private static final int REQUEST_CODE_EDIT_CARD = 170;
    private List<HomelogoBean> logoList = new ArrayList<>();
    private List<HomegoodsBean> goodsList = new ArrayList<>();
    private List<HomerollBean> rollList = new ArrayList<>();
    private List<HomebroadcastBean> noticeList = new ArrayList<>();
    public HomeFragmentPresenterImp(){
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
        getData();
    }

    @Override
    public void onDetach() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDetach();
    }



    @Override
    public void onInit(Activity activity) {

    }

    @Override
    public void onXiangQingBtn(Activity activity) {

    }

    @Override
    public void onToggleItem(Activity activity) {

    }

    @Override
    public void onToggleNotice(Activity activity, int position) {
        if (noticeList != null && position < noticeList.size()) {
            HomebroadcastBean item = noticeList.get(position);
            if (!TextUtils.isEmpty(String.valueOf(item.getId()))) {
                getAttachedView().onNoticeToggled("http://app.ykh9.com/wap/news/notice" + "?id=" + item.getId());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    private void getData() {
        ServerApi.<LzyResponse<HomeResponse>>getIndex()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "首页数据获取");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LzyResponse<HomeResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(LzyResponse<HomeResponse> response) {
                        if (response.getCode() == 1) {
                            HomeResponse data = response.getData();
                            if (data != null){
                                getAttachedView().showMoney(data.getSum_money());
                                getAttachedView().showShop(String.valueOf(data.getUser()));
                                getAttachedView().showUser(String.valueOf(data.getShop()));
                                List<HomelogoBean> logoListTemp = data.getLogo();
                                if (logoListTemp != null) {
                                    logoList.clear();
                                    logoList.addAll(logoListTemp);
                                    getAttachedView().getLogoListSuccess(logoList);

                                }

                                List<HomegoodsBean> goodsListTemp = data.getGoods();
                                if (goodsListTemp != null) {
                                    goodsList.clear();
                                    goodsList.addAll(goodsListTemp);
                                    goodsPosition = 0;
                                    mHandler.removeMessages(MSG_GOOD);
                                    mHandler.sendMessage(mHandler.obtainMessage(MSG_GOOD));
                                }

                                List<HomerollBean> rollListTemp = data.getRoll();
                                if (rollListTemp != null) {
                                    rollList.clear();
                                    rollList.addAll(rollListTemp);
                                    List<String> rolls = new ArrayList<>();
                                    for (HomerollBean roll : rollList) {
                                        rolls.add("手机尾号为"+ roll.getTel().substring(7,11)+"的客户获得收益金额"+roll.getMoney()+"元");
                                    }
                                    getAttachedView().showRoll(rolls);
                                }

                                List<HomebroadcastBean> noticeListTemp = data.getBroadcast();
                                if (noticeListTemp != null) {
                                    noticeList.clear();
                                    noticeList.addAll(noticeListTemp);
                                    List<String> notices = new ArrayList<>();
                                    for (HomebroadcastBean notice : noticeList) {
                                        notices.add(notice.getTitle());
                                    }
                                    getAttachedView().showNotice(notices);
                                }
                            }
                        }
//                        if (response.getCode() == -401){
//                            getAttachedView().getListFailure("密码已失效，请退出重新登录");
////                            Intent intent = new Intent(activity, LoginActivity.class);
////                            activity.startActivity(intent);
//                        }
                        else {
                            if (response.getCode() == -401){
                                getAttachedView().getListFailure("密码已失效，请退出重新登录");
                            }else {
                                getAttachedView().getListFailure(response.getMsg());
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().getListFailure(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });

    }


    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_GOOD:
                    getAttachedView().showGoods(goodsList.get(goodsPosition));

                    goodsPosition++;
                    if (goodsPosition >= goodsList.size()) {
                        goodsPosition = 0;
                    }
                    mHandler.removeMessages(MSG_GOOD);
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_GOOD), TIME_INTERVAL);
                    break;

            }
        }
    };

}
