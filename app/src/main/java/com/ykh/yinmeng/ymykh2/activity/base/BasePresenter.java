package com.ykh.yinmeng.ymykh2.activity.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 2018/11/10 9:54
 * Description：
 */
public abstract class BasePresenter<V extends IMVPView> implements IMVPPresenter<V> {
    private V mAttachedView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BasePresenter() {
    }

    @Override
    public void onAttach(V view) {
        mAttachedView = view;
    }

    @Override
    public void onDetach() {
        compositeDisposable.clear();
        mAttachedView = null;
    }

    public V getAttachedView() {
        return mAttachedView;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}
