package com.ykh.yinmeng.ymykh2.activity.yaoqinghuiyuan;

import android.Manifest;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.BaseActivity;
import com.ykh.yinmeng.ymykh2.utils.GlideUtils;
import com.ykh.yinmeng.ymykh2.utils.PermissionListener;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;

import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;


public class YaoqinghuiyuanActivity extends BaseActivity implements View.OnClickListener,YaoqinghuiyuanMVPView, View.OnLongClickListener {
    private ImageView img_huiyuan,image;
    private RelativeLayout rl_yaoqing;
    private LinearLayout ll_return;
    private TextView tv_title;
    private Dialog loadingDialog;
    private YaoqinghuiyuanPresenter<YaoqinghuiyuanMVPView> presenter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        presenter = new YaoqinghuiyuanPresenterImp<>();
        presenter.onAttach(this);
        presenter.onInit(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_yaoqinghuiyuan);
    }

    @Override
    public void initViews() {
        img_huiyuan = (ImageView) findViewById(R.id.img_huiyuan);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("邀请会员");
        image = (ImageView) findViewById(R.id.image);
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        rl_yaoqing = (RelativeLayout) findViewById(R.id.rl_yaoqing);
        loadingDialog = DialogUtil.createLoadingDialog(this, "");
    }

    @Override
    public void initListeners() {
        ll_return.setOnClickListener(this);
        img_huiyuan.setOnLongClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                YaoqinghuiyuanActivity.this.finish();
                break;
                default:
                    break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.img_huiyuan:
                String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestRuntimePermission(permissions, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        presenter.onToggleYaoqingBtn(YaoqinghuiyuanActivity.this);
                    }

                    @Override
                    public void onDenied(List<String> deniedPermissions) {
//                    ToastUtils.showToast(mContext, "权限被拒绝,请进入设置打开权限", Toast.LENGTH_SHORT);
                    }
                });
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void getBackgroundSuccess(String img) {
//        image.setBackgroundResource(img);
        GlideUtils.loadImageViewDiskCache(YaoqinghuiyuanActivity.this, img, image);
    }

    @Override
    public void getcontextSuccess(Bitmap bitmap) {
//        bitmap = ZXingUtils.createQRImage(context, 1000, 1000);
        img_huiyuan.setImageBitmap(bitmap);
    }

    @Override
    public void getDataFailure(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
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

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showShareView(String path) {
        showShare(path);
    }

    private void showShare(String picPath) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle(null);
        // titleUrl QQ和QQ空间跳转链接
//        oks.setTitleUrl("http://www.ykh9.com/");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(null);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(picPath);//确保SDcard下面存在此张图片
        //url在微信、微博，Facebook等平台中使用
//        oks.setUrl("http://www.ykh9.com/");
//        comment是我对这条分享的评论，仅在人人网使用
//        oks.setComment("我是测试评论文本");
//
//        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
//            @Override
//            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
//                paramsToShare.setText(null);
//                paramsToShare.setTitle(null);
//                paramsToShare.setTitleUrl(null);
//                paramsToShare.setImagePath(picPath);
//            }
//        });
        // 启动分享GUI
        oks.show(this);
    }
}
