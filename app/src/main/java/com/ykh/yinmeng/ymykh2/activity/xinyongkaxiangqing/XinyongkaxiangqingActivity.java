package com.ykh.yinmeng.ymykh2.activity.xinyongkaxiangqing;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.GlideUtils;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class XinyongkaxiangqingActivity extends BaseActivity implements XinyongkaxiangqingMVPView {

    private LinearLayout ll_return;
    private TextView tv_title,tv_cardName,tv_tag,tv_average,tv_max,tv__nianfei_text,tv__jianjie_text,tv__fanli_text,tv__zhouqi_text,tv__characteristic_text,tv__raiders_text;
    private ImageView img_logo;
    private Button bt_shenqing;

    private Dialog loadingDialog;
    public XinyongkaxiangqingPresenter<XinyongkaxiangqingMVPView> presenter;
    int isReal;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new XinyongkaxiangqingPresenterImp<>();
        presenter.onInit(this);
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_xinyongkaxiangqing);
    }

    @Override
    public void initViews() {
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("信用卡详情");
        img_logo = (ImageView) findViewById(R.id.img_logo);
        tv_cardName = (TextView) findViewById(R.id.tv_cardName);
        tv_tag = (TextView) findViewById(R.id.tv_tag);
        tv_average = (TextView) findViewById(R.id.tv_average);
        tv_max = (TextView) findViewById(R.id.tv_max);
        tv__nianfei_text = (TextView) findViewById(R.id.tv__nianfei_text);
        tv__jianjie_text = (TextView) findViewById(R.id.tv__jianjie_text);
        tv__fanli_text = (TextView) findViewById(R.id.tv__fanli_text);
        tv__zhouqi_text = (TextView) findViewById(R.id.tv__zhouqi_text);
        tv__characteristic_text = (TextView) findViewById(R.id.tv__characteristic_text);
        tv__raiders_text = (TextView) findViewById(R.id.tv__raiders_text);
        bt_shenqing = (Button) findViewById(R.id.bt_shenqing);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");

    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XinyongkaxiangqingActivity.this.finish();
            }
        });
        bt_shenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isReal = SharedPreferencesUtils.getInstance(XinyongkaxiangqingActivity.this).get(Constant.SHARED_AUTHENTICATION, -1);
                if (isReal !=1 ){
                    ToastUtils.showToast(XinyongkaxiangqingActivity.this, "亲爱的用户,为确保您的账户安全,进行此业务之前请先进行用户认证。", Toast.LENGTH_SHORT);
                }else {
                    presenter.onNext(XinyongkaxiangqingActivity.this);
                }

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void showNameSuccess(String name) {
        tv_cardName.setText(name);
    }

    @Override
    public void showAnnual_feeSuccess(String annual_fee) {
        tv__nianfei_text.setText(annual_fee);
    }

    @Override
    public void showAbstractxSuccess(String abstractx) {
        tv__jianjie_text.setText(abstractx);
    }

    @Override
    public void showCharacteristicSuccess(final String characteristic) {
        ImageLoader imageLoader = ImageLoader.getInstance();//ImageLoader需要实例化
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        URLImageParser imageGetter = new URLImageParser(tv__characteristic_text);
        tv__characteristic_text.setText(Html.fromHtml(characteristic,imageGetter,null));
    }
            public class URLImageParser implements Html.ImageGetter {
                TextView mTextView;
                public URLImageParser(TextView textView) {
                    this.mTextView = textView;
                }
                @Override
                public Drawable getDrawable(String source) {
                    final URLDrawable urlDrawable = new URLDrawable();
                    ImageLoader.getInstance().loadImage(source,
                            new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                    urlDrawable.bitmap = loadedImage;
                                    urlDrawable.setBounds(0, 0, loadedImage.getWidth(), loadedImage.getHeight());
                                    mTextView.invalidate();
                                    mTextView.setText(mTextView.getText());
                                }
                            });
                    return urlDrawable;
                }
            }
            public class URLDrawable extends BitmapDrawable {
                protected Bitmap bitmap;
                @Override
                public void draw(Canvas canvas) {
                    if (bitmap != null) {
                        canvas.drawBitmap(bitmap, 0, 0, getPaint());
                    }
                }
            }

    @Override
    public void showRaidersSuccess(String raiders) {
        tv__raiders_text.setText(Html.fromHtml(raiders));
    }

    @Override
    public void showJe_amountSuccess(String je_amount) {
        tv_average.setText("平均开卡金额："+je_amount+"元");
    }

    @Override
    public void showMax_amountSuccess(String max_amount) {
        tv_max.setText("最大开卡金额："+max_amount+"元");
    }

    @Override
    public void showTagSuccess(String tag) {
        tv_tag.setText(tag);
    }

    @Override
    public void showFeedback_loopSuccess(String feedback_loop) {
        tv__zhouqi_text.setText(feedback_loop);
    }

    @Override
    public void showLogoSuccess(String logo) {
        GlideUtils.loadImageView(XinyongkaxiangqingActivity.this,"https://img.sjlr365.com"+logo,img_logo);
    }

    @Override
    public void showAmountSuccess(String amount) {
        tv__fanli_text.setText(String.valueOf(Float.valueOf(amount)-10)+"元");
    }

    @Override
    public void showDialog() {
        loadingDialog.show();
    }

    @Override
    public void dismissDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }



}
