package com.gan.myrecycleview;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gan.myrecycleview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;

/**
 * 用途: 自定义recycleview实现下拉刷新和自动加载
 * 创建者:ganyufei
 * 时间: 2017/2/8
 */

public class MyRecycleView<T> extends LinearLayout {
    private static int VERTICAL = LinearLayout.VERTICAL;
    private static int HORIZONTAL = LinearLayout.HORIZONTAL;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRfl;
   // private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener;
    private CommonAdapter mAdapter;
    private RefreshLoadMoreListener mRefreshLoadMoreListner;//下拉和加载更多监听
    private ItemClickListener itemClickListener;//item点击监听
    private LinearLayout mExceptView;
    private LinearLayout mLoadingView;
    private boolean hasMore = false;//是否还有更多数据加载
    private boolean canMore = true;//是否可以加载更多
    private boolean isCanRefresh = true;//是否可以刷新更多
    private boolean isRefresh = false;//正在刷新
    private boolean isLoadMore = false;//正在加载更多
    private LoadMoreWrapper mLoadMoreWrapper;//为了实现加载更多footview

    private static ImageView exceptIv;//异常图片控件
    private static TextView exceptTv;//异常内容文本控件

    private static ProgressBar loadingIv;//正在加载图片控件
    private static TextView loadingTv;//正在加载文本控件


    public MyRecycleView(Context context) {
        super(context);
    }

    public MyRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LinearLayout rootLl = new LinearLayout(context);
        rootLl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        mLoadingView = initLoadingView(context);
        mLoadingView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        mExceptView = initExceptionView(context);
        mExceptView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        mExceptView.setVisibility(View.GONE);
        swipeRfl = new SwipeRefreshLayout(context);
        swipeRfl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        swipeRfl.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light);
        FrameLayout bootLl = new FrameLayout(context);
        bootLl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        recyclerView = new RecyclerView(context);
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setHorizontalScrollBarEnabled(true);
        recyclerView.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        bootLl.addView(mLoadingView);
        bootLl.addView(recyclerView);
        bootLl.addView(mExceptView);
        swipeRfl.addView(bootLl);
        rootLl.addView(swipeRfl);
        this.addView(rootLl);
        /**
         * 下拉至顶部刷新监听
         */
        mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if (!isRefresh && !isLoadMore) {
                    isRefresh = true;
                    refresh();
                }
            }
        };
        swipeRfl.setOnRefreshListener(mRefreshListener);
        recyclerView.setHasFixedSize(true);//不是瀑布流这个将可以优化性能
    }


    public MyRecycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 错误提示界面初始化
     *
     * @param context
     * @return
     */
    private LinearLayout initExceptionView(Context context) {
        LinearLayout rootLl = new LinearLayout(context);
        rootLl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        rootLl.setOrientation(LinearLayout.VERTICAL);
        rootLl.setGravity(Gravity.CENTER);
        exceptIv = new ImageView(context);
        exceptIv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 点击图片刷新
                if (!isRefresh) {
                    swipeRfl.setRefreshing(true);
                    isRefresh = true;
                    refresh();
                }
            }
        });
        //底部边距
        LinearLayout.LayoutParams ll = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        ll.setMargins(0, 0, 0, 32);
        exceptIv.setLayoutParams(ll);
        exceptTv = new TextView(context);
        exceptTv.setLayoutParams(ll);
        exceptTv.setTextSize(20.0f);
        exceptTv.setTextColor(Color.DKGRAY);
        //提示操作文本
        TextView alertTv = new TextView(context);
        alertTv.setLayoutParams(ll);
        alertTv.setLayoutParams(ll);
        alertTv.setTextSize(18.0f);
        alertTv.setTextColor(Color.LTGRAY);
        alertTv.setText("点击图片刷新");
        rootLl.addView(exceptIv);
        rootLl.addView(exceptTv);
        rootLl.addView(alertTv);
        return rootLl;
    }

    /**
     * 初始化正在加载页面
     *
     * @param context
     * @return
     */
    private LinearLayout initLoadingView(Context context) {
        LinearLayout rootLl = new LinearLayout(context);
        rootLl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        rootLl.setOrientation(LinearLayout.VERTICAL);
        rootLl.setGravity(Gravity.CENTER);
        loadingIv = new ProgressBar(context);
        //底部边距
        LinearLayout.LayoutParams ll = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        ll.setMargins(0, 0, 0, 32);
        loadingIv.setLayoutParams(ll);
        loadingTv = new TextView(context);
        loadingTv.setLayoutParams(ll);
        loadingTv.setTextSize(20.0f);
        loadingTv.setTextColor(Color.LTGRAY);

        rootLl.addView(loadingIv);
        rootLl.addView(loadingTv);
        return rootLl;
    }

    /**
     * drawableId 错误提示图片
     * exceptStr 错误提示语
     */
    public void customExceptView(int drawableId, String exceptStr) {
        recyclerView.setVisibility(View.INVISIBLE);
        mExceptView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.INVISIBLE);
        exceptIv.setImageResource(drawableId);
        exceptTv.setText(exceptStr);
        swipeRfl.setEnabled(false);//出现错误之后，将设定无法下拉，运用点击图片进行刷新
    }

    /**
     * drawableId 正在加载提示图片
     * exceptStr 正在加载提示语
     */
    public void customLoadView(String exceptStr) {
        recyclerView.setVisibility(View.INVISIBLE);
        mLoadingView.setVisibility(View.VISIBLE);
        mExceptView.setVisibility(View.INVISIBLE);
        loadingTv.setText(exceptStr);
        swipeRfl.setEnabled(false);
    }

    public void scrollToTop() {
        recyclerView.scrollToPosition(0);
    }

    public void setAdapter(CommonAdapter adapter) {
        if (adapter != null) {
            this.mAdapter = adapter;
            if (canMore) {//是否可以加载更多
                mLoadMoreWrapper = new LoadMoreWrapper(mAdapter);
                mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
                mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        /**
                         * 无论水平还是垂直
                         */
                        if (hasMore && !isLoadMore && !isRefresh && canMore) {
                            isLoadMore = true;
                            loadMore();
                        }
                    }
                });

                recyclerView.setAdapter(mLoadMoreWrapper);
            } else {
                recyclerView.setAdapter(mAdapter);
            }

            mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    if (itemClickListener != null)
                        itemClickListener.onClick(view, holder, position);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    if (itemClickListener != null)
                        itemClickListener.onLongClick(view, holder, position);
                    return true;
                }
            });
        }
    }


   /* public void setOrientation(int orientation) {
        if (orientation != VERTICAL && orientation != HORIZONTAL) {
            layoutManager.setOrientation(VERTICAL);
        } else {
            layoutManager.setOrientation(orientation);
        }
    }

    public int getOrientation() {
        return layoutManager.getOrientation();
    }

    public void setVertical() {
        layoutManager.setOrientation(VERTICAL);
    }

    public void setHorizontal() {
        layoutManager.setOrientation(HORIZONTAL);
    }*/

    public void setHasMore(boolean enable) {
        this.hasMore = enable;
        mLoadMoreWrapper.setFootCanLoad(hasMore);
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public boolean isCanMore() {
        return canMore;
    }

    public void setCanMore(boolean canMore) {
        this.canMore = canMore;
    }

    public void setPullRefreshEnable(boolean enable) {
        isCanRefresh = enable;
        swipeRfl.setEnabled(enable);
    }

    public boolean getPullRefreshEnable() {
        return swipeRfl.isEnabled();
    }

    public void loadMore() {
        if (mRefreshLoadMoreListner != null && hasMore && canMore) {
            mRefreshLoadMoreListner.onLoadMore();
        }
    }

    /**
     * 加载更多完毕,为防止频繁网络请求,isLoadMore为false才可再次请求更多数据
     */
    public void setLoadMoreCompleted() {
        isLoadMore = false;
    }

    public void stopRefresh() {
        isRefresh = false;
        swipeRfl.setRefreshing(false);
        if (isCanRefresh) swipeRfl.setEnabled(true);
    }

    public void setRefreshLoadMoreListener(RefreshLoadMoreListener listener) {
        mRefreshLoadMoreListner = listener;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        itemClickListener = listener;
    }

    /**
     * 刷新动作，用于请求网络数据
     */
    public void refresh() {
        swipeRfl.setRefreshing(true);
        //recyclerView.setVisibility(View.VISIBLE);
        mExceptView.setVisibility(View.INVISIBLE);
        if (mRefreshLoadMoreListner != null) {
            mRefreshLoadMoreListner.onRefresh();
        }
    }

    public void notifyDataSetChanged() {
        //firstload布局只能出现一次，所以这里判断如果显示，就隐藏
        if (mLoadingView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.VISIBLE);
            mExceptView.setVisibility(View.INVISIBLE);
            mLoadingView.setVisibility(View.INVISIBLE);
        }
        if (mLoadMoreWrapper != null)
            mLoadMoreWrapper.notifyDataSetChanged();
        else
            mAdapter.notifyDataSetChanged();
    }

    /**
     * 第一次自动加载，不与无数据用同样布局是因为，这里要有动画效果，所以单独一个布局
     */
    public void firstLoadingView(String exceptStr) {
        customLoadView(exceptStr);
        isRefresh = true;
        if (mRefreshLoadMoreListner != null) {
            mRefreshLoadMoreListner.onRefresh();
        }
    }

    /**
     * 获取刷新数据以后的处理
     *
     * @param actAllList
     * @param tmp
     */
    public void setDateRefresh(ArrayList<T> actAllList, ArrayList<T> tmp) {
        actAllList.clear();
        stopRefresh();//如果刷新则停止刷新
        if (tmp.isEmpty()) {
            customExceptView(R.drawable.no_data, "这里空空如也");
            setHasMore(false);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            setHasMore(true);
            actAllList.addAll(tmp);
        }
        notifyDataSetChanged();//刷新完毕
    }

    /**
     * 获取加载更多数据的处理
     *
     * @param actAllList
     * @param tmpLoadmore
     */
    public void setDateLoadMore(ArrayList<T> actAllList, ArrayList<T> tmpLoadmore) {
        if (tmpLoadmore.isEmpty()) {
            setHasMore(false);//如果没有更多数据则设置不可加载更多
            setLoadMoreCompleted();//加载完毕
            stopRefresh();//如果刷新则停止刷新
            return;
        }
        setHasMore(true);
        actAllList.addAll(tmpLoadmore);
        setLoadMoreCompleted();//加载完毕
        notifyDataSetChanged();//加载更多完毕
        stopRefresh();//如果刷新则停止刷新
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * 下拉刷新和自动加载监听
     */
    public interface RefreshLoadMoreListener {
        public void onRefresh();

        public void onLoadMore();
    }

    public interface ItemClickListener {
        public void onClick(View view, RecyclerView.ViewHolder holder, int position);

        public void onLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

}
