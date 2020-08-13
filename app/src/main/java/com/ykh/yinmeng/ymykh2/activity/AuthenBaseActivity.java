package com.ykh.yinmeng.ymykh2.activity;

import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;


public abstract class AuthenBaseActivity extends BaseActivity {

    @Override
    protected void onResume() {
        super.onResume();
        int isReal = SharedPreferencesUtils.getInstance(this).get(Constant.SHARED_AUTHENTICATION, -1);
        if (isReal != 1) {
            finish();
        }
    }
}
