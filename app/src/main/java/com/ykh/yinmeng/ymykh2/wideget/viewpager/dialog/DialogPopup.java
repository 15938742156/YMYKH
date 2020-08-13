package com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.ykh.yinmeng.ymykh2.R;

import razerdp.basepopup.BasePopupWindow;
/**
 * 2019/1/18 16:11
 * Description：
 */
public class DialogPopup extends BasePopupWindow implements View.OnClickListener {

    private TextView title;
    private TextView content;
    private TextView ok;
    private TextView cancel;
    private ViewClickListener listener;

    public DialogPopup(Context context) {
        super(context);

        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        ok = (TextView) findViewById(R.id.ok);
        cancel = (TextView) findViewById(R.id.cancel);

        setViewClickListener(this, ok, cancel);
        setPopupGravity(Gravity.CENTER);
    }

    public DialogPopup setTitle(String msg) {
        if (msg != null) {
            title.setText(msg);
        }
        return this;
    }

    public DialogPopup setContent(String msg) {
        if (msg != null) {
            content.setText(msg);
        }
        return this;
    }

    public DialogPopup setCancelText(String msg) {
        if (msg != null) {
            cancel.setText(msg);
        }
        return this;
    }

    public DialogPopup setConfirmText(String msg) {
        if (msg != null) {
            ok.setText(msg);
        }
        return this;
    }

    @Override
    protected Animation onCreateShowAnimation() {
        AnimationSet set = new AnimationSet(false);
        Animation shakeAnima = new RotateAnimation(0, 15, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shakeAnima.setInterpolator(new CycleInterpolator(5));
        shakeAnima.setDuration(400);
        set.addAnimation(getDefaultAlphaAnimation());
        set.addAnimation(shakeAnima);
        return set;
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return null;
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_dialog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                if (listener != null) {
                    listener.confirm(v);
                }
                dismiss();
                break;
            case R.id.cancel:
                if (listener != null) {
                    listener.cancel(v);
                }
                dismiss();
                break;
            default:
                break;
        }

    }

    public DialogPopup setClickListener(ViewClickListener listener) {
        this.listener = listener;
        return this;
    }

    public interface ViewClickListener {
        public void confirm(View view);
        public void cancel(View view);
    }
}
