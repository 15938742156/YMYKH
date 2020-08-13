package com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ykh.yinmeng.ymykh2.R;


public class SysProgressDialog extends ProgressDialog{
	
	private String message;

	public SysProgressDialog(Context context) {
		super(context);
	}
	
	public SysProgressDialog(Context context, String message) {
        super(context);
        this.message = message;
    }
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sysdialog);
        TextView current_action = (TextView) findViewById(R.id.current_action);
        current_action.setText(message);

        ImageView v = (ImageView) findViewById(R.id.progressbar);
        AnimationDrawable anima = (AnimationDrawable) v.getDrawable();
        anima.start();
    }

}
