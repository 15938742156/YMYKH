package com.ykh.yinmeng.ymykh2.fragment.basefragment;

import android.support.v4.app.Fragment;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 2019/1/25 14:30
 * Description：
 */
public class BaseFragment extends Fragment {
    private AtomicBoolean showLock = new AtomicBoolean(false);
    private AtomicBoolean hideLock = new AtomicBoolean(false);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            handleShow();
        } else {
            handleHide();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()) {
            handleShow();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        handleHide();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (isResumed()) {
            if (hidden) {
                handleHide();
            } else {
                handleShow();
            }
        }
    }

    public final void handleShow() {
        if (showLock.get()) return;
        showLock.compareAndSet(false, true);
        onShow();
        hideLock.compareAndSet(true, false);
    }

    public final void handleHide() {
        if (hideLock.get()) return;
        hideLock.compareAndSet(false, true);
        onHide();
        showLock.compareAndSet(true, false);
    }

    public void onShow(){

    }

    public void onHide(){

    }
}
