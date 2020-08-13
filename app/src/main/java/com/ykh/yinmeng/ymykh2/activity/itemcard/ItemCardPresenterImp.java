package com.ykh.yinmeng.ymykh2.activity.itemcard;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.itemcardgoods.ItemGoodsActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.model.ItemcardResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ItemCardPresenterImp<V extends ItemCardMVPView> extends BasePresenter<V> implements ItemCardPresenter<V> {

    private List<ItemcardResponse.DataBean> itemlist = new ArrayList<>();
    Intent intent;
    String title = null;
    String id = null;
    String ratio = null;

    public ItemCardPresenterImp(){
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onInit(Activity activity) {
        intent = activity.getIntent();
        id = String.valueOf(intent.getExtras().getInt("id"));
        title = String.valueOf(intent.getExtras().getString("title"));
        ratio = String.valueOf(intent.getExtras().getString("ratio"));
        getAttachedView().getTitleSuccess(title);
        Log.v("ssssssssss",title+id);
        getData();
    }

    public void getData(){
        ServerApi.<ItemcardResponse>banklist(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("xinhui", "银行分类列表");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ItemcardResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(ItemcardResponse response) {
                        if (response.getCode() == 1) {
                            List<ItemcardResponse.DataBean> list = response.getData();
                            if (list != null){
                                itemlist.clear();
                                itemlist.addAll(list);
                                getAttachedView().getListSuccess(itemlist);
                            }
                        } else {
                            getAttachedView().getListFailure(response.getMsg());
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

    @Override
    public void onToggleItem(Activity activity, int position) {
        Intent intent = new Intent(App.getContext(), ItemGoodsActivity.class);
        intent.putExtra("id",itemlist.get(position).getId());
        intent.putExtra("typee",itemlist.get(position).getType());
        intent.putExtra("img",itemlist.get(position).getDetail());
        activity.startActivityForResult(intent, 170);

    }

    @Override
    public void onBtnCount(String count) {
        double counts = Double.valueOf(count);
        double ratios = Double.valueOf(ratio);
        getAttachedView().showCountSuccess(String.valueOf(counts/10000*ratios));

    }
}
