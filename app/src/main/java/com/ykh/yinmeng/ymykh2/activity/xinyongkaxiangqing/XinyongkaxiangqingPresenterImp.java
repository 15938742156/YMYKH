package com.ykh.yinmeng.ymykh2.activity.xinyongkaxiangqing;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.xinyongkashenqing.XinyongkashenqingActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.model.XinyongkashenqingResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class XinyongkaxiangqingPresenterImp<V extends XinyongkaxiangqingMVPView> extends BasePresenter<V> implements XinyongkaxiangqingPresenter<V> {


    String product_id = null;
    String bank_id = null;
    String expect_amount = null;
    String amount = null;
    String name = null;
    String annual_fee = null;
    String abstractx = null;
    String characteristic = null;
    String raiders = null;
    String je_amount = null;
    String max_amount = null;
    String tag = null;
    String feedback_loop = null;
    String logo = null;

    public XinyongkaxiangqingPresenterImp(){
        super();
    }

    @Override
    public void onInit(Activity activity) {
        Intent intent = activity.getIntent();
        product_id = String.valueOf(intent.getExtras().getString("product_id"));
        bank_id = String.valueOf(intent.getExtras().getString("bank_id"));
        expect_amount = String.valueOf(intent.getExtras().getString("expect_amount"));
        amount = String.valueOf(intent.getExtras().getString("amount"));

        name = String.valueOf(intent.getExtras().getString("name"));
        annual_fee = String.valueOf(intent.getExtras().getString("annual_fee"));
        abstractx = String.valueOf(intent.getExtras().getString("abstractx"));
        characteristic = String.valueOf(intent.getExtras().getString("characteristic"));
        raiders = String.valueOf(intent.getExtras().getString("raiders"));
        je_amount = String.valueOf(intent.getExtras().getString("je_amount"));
        max_amount = String.valueOf(intent.getExtras().getString("max_amount"));
        tag = String.valueOf(intent.getExtras().getString("tag"));
        feedback_loop = String.valueOf(intent.getExtras().getString("feedback_loop"));
        logo = String.valueOf(intent.getExtras().getString("banner"));
    }

    @Override
    public void onNext(Activity activity) {
        Intent intent = new Intent(App.getContext(), XinyongkashenqingActivity.class);
        intent.putExtra("product_id",product_id);//产品ID
        intent.putExtra("bank_id",bank_id);//机构ID
        intent.putExtra("expect_amount",expect_amount);//期望金额
        intent.putExtra("amount",amount);//佣金
        activity.startActivityForResult(intent, 170);
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
        getAttachedView().showNameSuccess(name);
        getAttachedView().showAnnual_feeSuccess(annual_fee);
        getAttachedView().showAbstractxSuccess(abstractx);
        getAttachedView().showCharacteristicSuccess(characteristic);
        getAttachedView().showRaidersSuccess(raiders);
        getAttachedView().showJe_amountSuccess(je_amount);
        getAttachedView().showMax_amountSuccess(max_amount);
        getAttachedView().showTagSuccess(tag);
        getAttachedView().showFeedback_loopSuccess(feedback_loop);
        getAttachedView().showLogoSuccess(logo);
        getAttachedView().showAmountSuccess(amount);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
