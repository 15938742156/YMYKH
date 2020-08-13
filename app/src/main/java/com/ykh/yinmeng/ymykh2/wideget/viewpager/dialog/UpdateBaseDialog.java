package com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog;

import android.app.Dialog;
import android.content.Context;

/**
 * 2018/12/19 11:06
 * Description：
 */
public class UpdateBaseDialog extends Dialog {
    private int res;

    public UpdateBaseDialog(Context context, int theme, int res) {
        super(context, theme);
        // TODO 自动生成的构造函数存根
        setContentView(res);
        this.res = res;
        setCanceledOnTouchOutside(false);
    }
}
