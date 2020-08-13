package com.ykh.yinmeng.ymykh2.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;

/***
 * 意见反馈Activity
 * @author Angel
 */
public class FeedbackActivity extends BaseActivity {

    private EditText et_idea,et_tel;
    private Button bt_commit;
    private LinearLayout ll_return;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_feedback);

    }

    @Override
    public void initViews() {
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("意见反馈");
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        et_idea = (EditText) findViewById(R.id.et_idea);
        et_tel = (EditText) findViewById(R.id.et_tel);
        bt_commit = findViewById(R.id.bt_commit);
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackActivity.this.finish();
            }
        });
        et_idea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        et_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_idea.getText().toString())){
                    ToastUtils.showToast(FeedbackActivity.this,"请提出您的宝贵意见",Toast.LENGTH_SHORT);
                }else {
                    if (TextUtils.isEmpty(et_tel.getText().toString())){
                        ToastUtils.showToast(FeedbackActivity.this,"请留下您宝贵的联系方式",Toast.LENGTH_SHORT);
                    }else {
                        et_idea.setText("");
                        et_tel.setText("");
                        ToastUtils.showToast(FeedbackActivity.this,"优盛伙伴谢谢您的宝贵意见",Toast.LENGTH_SHORT);
                    }
                }
            }
        });

    }

    @Override
    public void initData() {

    }
}
