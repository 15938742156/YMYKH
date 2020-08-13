package com.ykh.yinmeng.ymykh2.fragment.homefragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.credits.CreditsActivity;
import com.ykh.yinmeng.ymykh2.activity.myteam.MyteamActivity;
import com.ykh.yinmeng.ymykh2.activity.shanghutuozhan.ShanghutuozhanActivity;
import com.ykh.yinmeng.ymykh2.activity.poslist.PoslistActivity;
import com.ykh.yinmeng.ymykh2.activity.shiming.ShimingActivity;
import com.ykh.yinmeng.ymykh2.activity.tuiguangshouyi.TuiguangshouyiActivity;
import com.ykh.yinmeng.ymykh2.activity.xinshoujiangtangxiangqing.XinshoujiangtangxiangqingActivity;
import com.ykh.yinmeng.ymykh2.activity.xinyongka.XinyongkaActivity;
import com.ykh.yinmeng.ymykh2.activity.yaoqing.YaoqingActivity;
import com.ykh.yinmeng.ymykh2.bean.HomegoodsBean;
import com.ykh.yinmeng.ymykh2.bean.HomelogoBean;
import com.ykh.yinmeng.ymykh2.fragment.basefragment.BaseFragment;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.GlideUtils;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.VerticalScrollTextView;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogUtil;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.html.Html5Activity;
import java.util.ArrayList;
import java.util.List;


/**
 * 首页
 * @author Angel
 *
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener,HomeFragmentMVPView {
	private LinearLayout layout1,layout2,layout3,layout4,layout5,layout6,layout7,layout8;
	private RelativeLayout layout_goods;
	private VerticalScrollTextView v_text_view,tv_roll;//公告
	private RollPagerView viewPager;//轮播
	private ImageView img_goods;
	private TextView tv_money,tv_commercial,tv_loweruser,tv_goods_name,tv_attention,tv_rebate,tv_cash,tv_license;
	Intent intent;
	int isReal;

	List<HomegoodsBean> goodsList = new ArrayList<>();
	RollLoopPagerViewAdapter logoAdapter;

	private Dialog loadingDialog;
	public HomeFragmentPresenter<HomeFragmentMVPView> presenter;

	private SmartRefreshLayout refreshLayout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container,false);
		initView(view);
		initListeners();
		return view;
	}
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		presenter = new HomeFragmentPresenterImp<>();
		presenter.onAttach(this);
	}

	@Override
	public void onDestroy() {
		presenter.onDetach();
		super.onDestroy();
	}

	private void initView(View view) {
		tv_money = (TextView)view.findViewById(R.id.tv_money);
		tv_commercial = (TextView)view.findViewById(R.id.tv_commercial);
		tv_loweruser = (TextView)view.findViewById(R.id.tv_loweruser);
		tv_goods_name = (TextView)view.findViewById(R.id.tv_goods_name);
		tv_attention = (TextView)view.findViewById(R.id.tv_attention);
		tv_rebate = (TextView)view.findViewById(R.id.tv_rebate);
		tv_cash = (TextView)view.findViewById(R.id.tv_cash);
		tv_license = (TextView) view.findViewById(R.id.tv_license);
		img_goods = (ImageView)view.findViewById(R.id.img_goods);
		viewPager = (RollPagerView) view.findViewById(R.id.viewPager);
		v_text_view = (VerticalScrollTextView) view.findViewById(R.id.v_text_view);
		tv_roll = (VerticalScrollTextView) view.findViewById(R.id.tv_roll);
		layout_goods = (RelativeLayout)view.findViewById(R.id.layout_goods);
		layout1 = (LinearLayout) view.findViewById(R.id.layout1);
		layout2 = (LinearLayout) view.findViewById(R.id.layout2);
		layout3 = (LinearLayout) view.findViewById(R.id.layout3);
		layout4 = (LinearLayout) view.findViewById(R.id.layout4);
		layout5 = (LinearLayout) view.findViewById(R.id.layout5);
		layout6 = (LinearLayout) view.findViewById(R.id.layout6);
		layout7 = (LinearLayout) view.findViewById(R.id.layout7);
		layout8 = (LinearLayout) view.findViewById(R.id.layout8);
		loadingDialog = DialogUtil.createLoadingDialog(getActivity(), "");
		refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
		refreshLayout.setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
			@Override
			public void onRefresh(RefreshLayout refreshlayout) {
				presenter.onAttach(HomeFragment.this);
				refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
			}
		});

	}
	private void initListeners(){
		layout1.setOnClickListener(this);
		layout2.setOnClickListener(this);
		layout3.setOnClickListener(this);
		layout4.setOnClickListener(this);
		layout5.setOnClickListener(this);
		layout6.setOnClickListener(this);
		layout7.setOnClickListener(this);
		layout8.setOnClickListener(this);
		layout_goods.setOnClickListener(this);
		logoAdapter = new RollLoopPagerViewAdapter(viewPager);
		viewPager.setAdapter(logoAdapter);
		viewPager.setAnimationDurtion(3000);
		viewPager.setPlayDelay(2000);
		v_text_view.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.v_text_view:
				presenter.onToggleNotice(getActivity(),((VerticalScrollTextView)v).getPosition());
				break;
			case R.id.layout1:
				intent = new Intent(getActivity(), PoslistActivity.class);
				startActivity(intent);
//				isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//				if (isReal !=1 ){
//					ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//				}else {
//					intent = new Intent(getActivity(), PoslistActivity.class);
//					startActivity(intent);
//				}
				break;
			case R.id.layout2:
				intent = new Intent(getActivity(), YaoqingActivity.class);
				startActivity(intent);
//				isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//				if (isReal !=1 ){
//					ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//				}else {
//					intent = new Intent(getActivity(), YaoqingActivity.class);
//					startActivity(intent);
//				}
				break;
			case R.id.layout3:
				intent = new Intent(getActivity(), XinshoujiangtangxiangqingActivity.class);
				startActivity(intent);
//				isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//				if (isReal !=1 ){
//					ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//				}else {
//					intent = new Intent(getActivity(), CreditsActivity.class);
//					startActivity(intent);
//				}
				break;
			case R.id.layout4:
				intent = new Intent(getActivity(), MyteamActivity.class);
				startActivity(intent);
//				isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//				if (isReal !=1 ){
//					ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//				}else {
//					intent = new Intent(getActivity(), MyteamActivity.class);
//					startActivity(intent);
//				}
				break;
			case R.id.layout5:
				intent = new Intent(getActivity(), TuiguangshouyiActivity.class);
				startActivity(intent);
//				isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//				if (isReal !=1 ){
//					ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//				}else {
//					intent = new Intent(getActivity(), TuiguangshouyiActivity.class);
//					startActivity(intent);
//				}
				break;
			case R.id.layout6:
				isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
				if (isReal != 1) {
					intent = new Intent(getActivity(), ShimingActivity.class);
					startActivity(intent);
				} else {
					ToastUtils.showToast(getActivity(), "已实名认证通过", Toast.LENGTH_SHORT);
				}
				break;
			case R.id.layout7:
				intent = new Intent(getActivity(), XinyongkaActivity.class);
				startActivity(intent);
//				isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//				if (isReal !=1 ){
//					ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//				}else {
//					intent = new Intent(getActivity(), XinyongkaActivity.class);
//					startActivity(intent);
//				}
				break;
			case R.id.layout8:
				intent = new Intent(getActivity(), ShanghutuozhanActivity.class);
				startActivity(intent);
//				isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//				if (isReal !=1 ){
//					ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//				}else {
//					intent = new Intent(getActivity(), ShanghutuozhanActivity.class);
//					startActivity(intent);
//				}
				break;
			case R.id.layout_goods:
				intent = new Intent(getActivity(), PoslistActivity.class);
				startActivity(intent);
//				isReal = SharedPreferencesUtils.getInstance(getActivity()).get(Constant.SHARED_AUTHENTICATION, -1);
//				if (isReal !=1 ){
//					ToastUtils.showToast(getActivity(), "请前去实名认证", Toast.LENGTH_SHORT);
//				}else {
//					intent = new Intent(getActivity(), PoslistActivity.class);
//					startActivity(intent);
//				}
				break;
				default:
					break;
		}
	}
	/**
	 * 图片轮播
	 * @author Angel
	 *
	 */
	private class RollLoopPagerViewAdapter extends LoopPagerAdapter {
		private int[] imgs = {R.mipmap.banner};
		private List<HomelogoBean> logoList = null;

		public RollLoopPagerViewAdapter(RollPagerView viewPager) {
			super(viewPager);
		}

		public void setData(List<HomelogoBean> logoList) {
			this.logoList = logoList;
		}

		@Override
		public View getView(ViewGroup container, int position) {
			ImageView view = new ImageView(container.getContext());
			if (logoList == null) {
				view.setImageResource(imgs[position]);
				view.setScaleType(ImageView.ScaleType.CENTER_CROP);
			} else {
				if (getContext() != null) {
					GlideUtils.loadImageViewCrop(getContext(), logoList.get(position % logoList.size()).getLogo(), view);
				}
			}
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
		}

		@Override
		public int getRealCount() {
			return logoList == null ? imgs.length : logoList.size();
		}


	}
	@Override
	public void onShow() {
		super.onShow();
		if (v_text_view != null) {
			v_text_view.startPlay();
		}
	}

	@Override
	public void onHide() {
		super.onHide();
		if (v_text_view != null) {
			v_text_view.stopPlay();
		}
	}

	@Override
	public void getLogoListSuccess(List<HomelogoBean> logoList) {
		logoAdapter.setData(logoList);
		logoAdapter.notifyDataSetChanged();
	}

	@Override
	public void showGoods(HomegoodsBean goods) {
		GlideUtils.loadImageViewDiskCache(getContext(), "http://wx.ykh9.com"+goods.getImg_feng(), img_goods);
		tv_goods_name.setText(goods.getTitle());
		tv_attention.setText("关注度 "+String.valueOf(goods.getGz_num()));
		tv_rebate.setText(goods.getDescription());
		tv_cash.setText("押金:￥"+String.valueOf(goods.getPrice()));
		tv_license.setText("支付牌照 "+goods.getPaizhao());

	}

	@Override
	public void showNotice(List<String> notices) {
		v_text_view.setDataSource(notices);
		v_text_view.startPlay();

	}

	@Override
	public void showRoll(List<String> rolls) {
		tv_roll.setDataSource(rolls);
		tv_roll.startPlay();
	}

	@Override
	public void showMoney(String money) {
		tv_money.setText(money);
	}

	@Override
	public void showUser(String user) {
		tv_commercial.setText("当月激活商户"+user+"人");

	}

	@Override
	public void showShop(String shop) {
		tv_loweruser.setText("当月新增下级会员"+shop+"人");

	}

	@Override
	public void getListFailure(String msg) {
		ToastUtils.showToast(getActivity(), msg, Toast.LENGTH_SHORT);

	}

	@Override
	public void onNoticeToggled(String url) {
		Intent intent = new Intent(getActivity(), Html5Activity.class);
		Bundle bundle = new Bundle();
		bundle.putString("url", url);
		intent.putExtra("bundle", bundle);
		startActivity(intent);
	}

	@Override
	public void showDialog() {
//		loadingDialog.show();
	}

	@Override
	public void dismissDialog() {
//		if (loadingDialog != null && loadingDialog.isShowing()) {
//			loadingDialog.dismiss();
//		}
	}
}
