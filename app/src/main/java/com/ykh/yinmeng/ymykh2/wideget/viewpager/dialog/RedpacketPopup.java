package com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ykh.yinmeng.ymykh2.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 2019/1/18 16:11
 * Description：
 */
public class RedpacketPopup extends BasePopupWindow implements View.OnClickListener {

    private TextView tv_money;
    private TextView tv_num;
    private Button bt_hongbao;
    private ImageView img_cha;
    private ViewClickListener listener;

    public RedpacketPopup(Context context) {
        super(context);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_num = (TextView) findViewById(R.id.tv_num);
        bt_hongbao = (Button) findViewById(R.id.bt_hongbao);
        img_cha = (ImageView) findViewById(R.id.img_cha);
        Typeface textFont1 = Typeface.createFromAsset(getContext().getAssets(), "DIN-BOLD.OTF");
        tv_money.setTypeface(textFont1);
        tv_num.setTypeface(textFont1);
        setViewClickListener(this, bt_hongbao, img_cha);
        setPopupGravity(Gravity.CENTER);

        //创建
        PopupWindow popupWindow = new PopupWindow(getContext());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(false);
        popupWindow.setTouchable(true);
    }
    public RedpacketPopup setTitle(String msg) {
        if (msg != null) {
            tv_num.append(msg);
        }
        return this;
    }

    public RedpacketPopup setContent(String msg) {
        if (msg != null) {
            tv_money.append(msg);
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
        return createPopupById(R.layout.redpacket_dialog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_hongbao:
                if (listener != null) {
                    listener.confirm(v);
                }
                dismiss();
                break;
            case R.id.img_cha:
                if (listener != null) {
                    listener.cancel(v);
                }
                dismiss();
                break;
            default:
                break;
        }

    }

    public RedpacketPopup setClickListener(ViewClickListener listener) {
        this.listener = listener;
        return this;
    }

    public interface ViewClickListener {
        public void confirm(View view);
        public void cancel(View view);
    }

}
