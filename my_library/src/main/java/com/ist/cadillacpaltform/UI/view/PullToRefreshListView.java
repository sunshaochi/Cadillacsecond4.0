package com.ist.cadillacpaltform.UI.view;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ist.cadillacpaltform.R;


//底部加载方式为 见到底部自动加载
public class PullToRefreshListView extends ListView implements
        OnScrollListener {

    public static final int HVDT = 300;//headview 隐藏过程300毫秒
    public int HVPT = 10;//默认10毫秒一桢
    public static final int UP = 0;
    public static final int DOWN = 1;

    private final static int RELEASE_To_REFRESH = 0;
    private final static int PULL_To_REFRESH = 1;
    private final static int REFRESHING = 2;
    private final static int DONE = 3;
    private final static int LOADING = 4;

    private final static int NO_MORE = 5;

    private Context mContext;

    // 实际的padding的距离与界面上偏移距离的比例
    private final static int RATIO = 2;
    private boolean bottomFreshOpen = true;
    private LayoutInflater inflater;

    private LinearLayout headView;

    private TextView tipsTextview;
    private TextView lastUpdatedTextView;
    private ImageView arrowImageView;
    private ProgressBar progressBar;
    private Button mRefreshButton;

    private RotateAnimation animation;
    private RotateAnimation reverseAnimation;

    private RelativeLayout mRefreshView2;
    private RelativeLayout mRelativeLayoutFootBlank;
    private ProgressBar mRefreshViewProgress2;

    // 用于保证startY的值在一个完整的touch事件中只被记录一次
    private boolean isRecored;

    private int headContentWidth;
    private int headContentHeight;

    private int startY;
    protected int firstItemIndex;
    private Handler mHandler = new Handler();
    private int mHeaderState;
    private int mFooterState;

    private boolean isBack;

    private OnRefreshListener refreshListener;

    private boolean isRefreshable;

    private boolean isRefreshValid = true;// 是否支持下拉刷新

    private boolean isRefreshBottomAuto = true;// 是否滑倒底部自动加载，false则为点击更多加载。

    public interface OnRefreshListener {
        /**
         * Called when the list should be refreshed.
         * <p>
         * A call to {#onRefreshComplete()} is
         * expected to indicate that the refresh has completed.
         */
        /**
         * called when ask for pre-page
         */
        void onRefresh();

        /**
         * called when ask for post-page
         */
        void onLoadMore();
    }

    public boolean isRefreshBottomAuto() {
        return isRefreshBottomAuto;
    }

    public void setRefreshBottomAuto(boolean isRefreshBottomAuto) {
        this.isRefreshBottomAuto = isRefreshBottomAuto;
    }

    public boolean isRefreshValid() {
        return isRefreshValid;
    }

    public void setRefreshValid(boolean isRefreshValid) {
        this.isRefreshValid = isRefreshValid;
    }

    public boolean isRefreshable() {
        return isRefreshable;
    }

    public void setRefreshable(boolean isRefreshable) {
        this.isRefreshable = isRefreshable;
    }

    public PullToRefreshListView(Context context) {
        super(context);
        init(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public RelativeLayout getBottomLayout() {
        return mRefreshView2;
    }

    public RelativeLayout getBottomLayoutBlack() {
        mRefreshView2.setBackgroundColor(0x000000);
        mRefreshButton.setTextColor(Color.WHITE);
        return mRefreshView2;
    }

    private void init(Context context) {
        // setCacheColorHint(context.getResources().getColor(R.color.transparent));
        mContext = context;
        inflater = LayoutInflater.from(context);

        headView = (LinearLayout) inflater.inflate(
                R.layout.guba_item_refresh_head, null);


        mRefreshView2 = (RelativeLayout) inflater.inflate(
                R.layout.guba_pull_to_refresh_footer, this, false);
        mRelativeLayoutFootBlank = (RelativeLayout) inflater.inflate(R.layout.guba_pull_to_refresh_footer2_blank, this, false);
        mRefreshViewProgress2 = (ProgressBar) mRefreshView2
                .findViewById(R.id.pull_to_refresh_progress);
        mRefreshViewProgress2.setVisibility(View.VISIBLE);

        mRefreshButton = (Button) mRefreshView2.findViewById(R.id.button1);
        mRefreshButton.setVisibility(View.GONE);
        mRefreshButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mRefreshButton.setVisibility(View.GONE);
                mRefreshViewProgress2.setVisibility(View.VISIBLE);
                // prepareForRefresh();
                onLoadMore();
            }

        });

        arrowImageView = (ImageView) headView
                .findViewById(R.id.head_arrowImageView);
        arrowImageView.setMinimumWidth(70);
        arrowImageView.setMinimumHeight(50);
        progressBar = (ProgressBar) headView
                .findViewById(R.id.head_progressBar);
        tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
        lastUpdatedTextView = (TextView) headView
                .findViewById(R.id.head_lastUpdatedTextView);

        measureView(headView);
        headContentHeight = headView.getMeasuredHeight();
        headContentWidth = headView.getMeasuredWidth();

        headView.invalidate();

        addHeaderView(headView, null, false);

        setOnScrollListener(this);

        animation = new RotateAnimation(0, 180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(250);
        animation.setFillAfter(true);

        reverseAnimation = new RotateAnimation(180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        reverseAnimation.setInterpolator(new LinearInterpolator());
        reverseAnimation.setDuration(200);
        reverseAnimation.setFillAfter(true);

        mHeaderState = DONE;
        mFooterState = DONE;
        isRefreshable = false;
        // iniList();

        HVPT = HVDT / headContentHeight;

    }

    public void enlargeBottomPad() {
        mRefreshView2.findViewById(R.id.pad).setVisibility(View.VISIBLE);
    }

    public void reset() {
        resetFooter();
    }


    public void RemoveFooterBlank() {
        if (getFooterViewsCount() > 0) {
            try {
                removeFooterView(mRelativeLayoutFootBlank);
            } catch (Exception e) {
            }
        }
    }

    public void AddFooterBlank() {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                if (getFooterViewsCount() == 1) {
                    try {
                        removeFooterView(mRefreshView2);
                    } catch (Exception e) {
                    }
                }
                bottomFreshOpen = false;
                if (getFooterViewsCount() == 0)
                    addFooterView(mRelativeLayoutFootBlank);
            }
        });

    }

    private void resetFooter() {

        if (getFooterViewsCount() > 0) {
            try {
                removeFooterView(mRefreshView2);
            } catch (Exception e) {
            }
        }
        // mRefreshViewProgress2.setVisibility(View.INVISIBLE);
    }

    public void prepareForRefreshBottom() {
        // log("xxxxxxx", "prepareForRefreshBottom");
        if (getFooterViewsCount() == 0)
            addFooterView(mRefreshView2);
    }

    public void removeHeaderFooter() {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                if (getFooterViewsCount() == 1) {
                    try {
                        removeFooterView(mRefreshView2);
                    } catch (Exception e) {
                    }
                }
                bottomFreshOpen = false;
            }

        });

    }

    public void setFooterHeight(int height) {
        ViewGroup.LayoutParams params = mRefreshView2
                .getLayoutParams();
        params.height = height;
        mRefreshView2.setLayoutParams(params);
    }

    @Override
    public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2,
                         int arg3) {
        firstItemIndex = firstVisiableItem;
    }

    @Override
    public void onScrollStateChanged(AbsListView arg0, int arg1) {
        if (bottomFreshOpen
                && this.getLastVisiblePosition() == this.getCount() - 1
                && getFirstVisiblePosition() != 0                       // TODO 如果加载更多出现很多，很可能来自这里
                && getFooterViewsCount() != 0 && isRefreshBottomAuto) {
            onLoadMore();
        }
    }

    public void resetFooter(Boolean isFootAvilable) {
        if (isFootAvilable) {
            if (getFooterViewsCount() == 0) {
                addFooterView(mRefreshView2);
            }
        } else {
            if (getFooterViewsCount() == 1) {
                try {
                    removeFooterView(mRefreshView2);
                } catch (Exception e) {
                }
            }
        }
    }

    public void iniList() {
        if (!isRefreshable)
            return;

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                setSelection(0);
            }
        });
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... params) {

                mHeaderState = DONE;
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                MotionEvent e =
                        MotionEvent.obtain(SystemClock.uptimeMillis(),
                                SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0,
                                100, 0);
                dispatchTouchEvent(e);

                for (int i = 110; i < headContentHeight * 4 + 100; i += 100) {
                    MotionEvent e1 = MotionEvent.obtain(
                            SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis(),
                            MotionEvent.ACTION_MOVE, 100, i, 0);
                    onTouchEvent(e1);

                }

                MotionEvent e4 = MotionEvent.obtain(SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 100,
                        headContentHeight * 4 + 100, 0);

                onTouchEvent(e4);

            }

        }.execute();

    }

    private int mLastMotionY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (isRefreshable) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    if (firstItemIndex == 0 && !isRecored) {
                        isRecored = true;
                        startY = (int) event.getY();
                        // Log.v(TAG, "在down时候记录当前位置‘");
                    }
                    mLastMotionY = (int) event.getY();
                    break;

                case MotionEvent.ACTION_MOVE:

                    int tempY = (int) event.getY();

                    if (bottomFreshOpen && getFooterViewsCount() == 0
                            && this.computeVerticalScrollOffset() > 0
                            && event.getY() < mLastMotionY) {
                        prepareForRefreshBottom();

                    }

                    if (!isRefreshValid)
                        break;

                    if (!isRecored && firstItemIndex == 0) {
                        isRecored = true;
                        startY = tempY;
                    }

                    if (mHeaderState != REFRESHING && isRecored && mHeaderState != LOADING) {

                        // 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动

                        // 可以松手去刷新了
                        if (mHeaderState == RELEASE_To_REFRESH) {

                            setSelection(0);

                            // 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
                            if (((tempY - startY) / RATIO < headContentHeight)
                                    && (tempY - startY) > 0) {
                                mHeaderState = PULL_To_REFRESH;
                                changeHeaderViewByState();

                                // Log.v(TAG, "由松开刷新状态转变到下拉刷新状态");
                            }
                            // 一下子推到顶了
                            else if (tempY - startY <= 0) {
                                mHeaderState = DONE;
                                changeHeaderViewByState();

                                // Log.v(TAG, "由松开刷新状态转变到done状态");
                            }
                            // 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
                            else {
                                // 不用进行特别的操作，只用更新paddingTop的值就行了
                            }
                        }
                        // 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
                        if (mHeaderState == PULL_To_REFRESH) {

                            setSelection(0);

                            // 下拉到可以进入RELEASE_TO_REFRESH的状态
                            if ((tempY - startY) / RATIO >= headContentHeight) {
                                mHeaderState = RELEASE_To_REFRESH;
                                isBack = true;
                                changeHeaderViewByState();

                                // Log.v(TAG, "由done或者下拉刷新状态转变到松开刷新");
                            }
                            // 上推到顶了
                            else if (tempY - startY <= 0) {
                                mHeaderState = DONE;
                                changeHeaderViewByState();

                                // Log.v(TAG, "由DOne或者下拉刷新状态转变到done状态");
                            }
                        }

                        // done状态下
                        if (mHeaderState == DONE) {
                            if (tempY - startY > 0) {
                                mHeaderState = PULL_To_REFRESH;
                                changeHeaderViewByState();
                                // Log.v(TAG, "更细nsize");
                            }
                        }

                        // 更新headView的size
                        if (mHeaderState == PULL_To_REFRESH) {
                            int h = headContentHeight;
                            headView.setPadding(0, -1 * h
                                    + (tempY - startY) / RATIO, 0, 0);
//                            Log.i("LKHListView", "PULL_To_REFRESH action=" + event.getAction() + " eventY=" + event.getY()+" dy="+(tempY - startY));
                            // Log.v(TAG, "更细ssize");

                        }

                        // 更新headView的paddingTop
                        if (mHeaderState == RELEASE_To_REFRESH) {
                            int h = headContentHeight;
                            headView.setPadding(0, (tempY - startY) / RATIO
                                    - h, 0, 0);
//                            Log.i("LKHListView", "RELEASE_To_REFRESH action=" + event.getAction() + " eventY=" + event.getY() + " dy=" + (tempY - startY));
                        }

                    }

                    break;

                case MotionEvent.ACTION_UP:
                default:
                    if (!isRefreshValid)
                        break;
                    if (mHeaderState != REFRESHING && mHeaderState != LOADING) {
                        if (mHeaderState == DONE) {
                            // 什么都不做
                        }
                        if (mHeaderState == PULL_To_REFRESH) {
                            mHeaderState = DONE;
                            changeHeaderViewByState();

                            // Log.v(TAG, "由下拉刷新状态，到done状态");
                        }
                        if (mHeaderState == RELEASE_To_REFRESH) {
                            mHeaderState = REFRESHING;
                            isRefreshable = false;
                            changeHeaderViewByState();
                            onRefresh();
                            // Log.v(TAG, "由松开刷新状态，到done状态");
                        }
                    }

                    isRecored = false;
                    isBack = false;

                    break;
            }
        }

        //上拉加载更多
        onTouchGetDown(event);

        return super.onTouchEvent(event);
    }

    public void setBottomTouchValid(boolean isValid) {
        isBottomTouchValid = isValid;
    }

    private int startYDown;
    private boolean isStartDown;
    /**
     * 是否支持上拉加载更多
     */
    private boolean isBottomTouchValid = true;
    private String bottomText;

    private void onTouchGetDown(MotionEvent event) {
        if (!isBottomTouchValid || mFooterState == LOADING || !bottomFreshOpen || isRefreshBottomAuto) {
            return;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (this.getLastVisiblePosition() == this.getCount() - 1 && !isStartDown) {
                    isStartDown = true;
                    startYDown = (int) event.getY();
                    bottomText = mRefreshButton.getText().toString();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int tempY = (int) event.getY();
                if (this.getLastVisiblePosition() == this.getCount() - 1 && !isStartDown) {
                    isStartDown = true;
                    startYDown = tempY;
                    bottomText = mRefreshButton.getText().toString();
                }
                if (isStartDown) {
                    if (mFooterState == DONE) {
                        if (tempY < startYDown) {
                            mFooterState = PULL_To_REFRESH;
                            changeFooterViewByState();
                        }
                    } else if (mFooterState == PULL_To_REFRESH) {
                        setSelection(getCount() - 1);
                        if (tempY > startYDown) {
                            mFooterState = DONE;
                            changeFooterViewByState();
                        } else if ((startYDown - tempY) / RATIO > 80) {
                            mFooterState = RELEASE_To_REFRESH;
                            changeFooterViewByState();
                        }

                    } else if (mFooterState == RELEASE_To_REFRESH) {
                        setSelection(getCount() - 1);
                        if ((startYDown - tempY) / RATIO <= 80) {
                            mFooterState = PULL_To_REFRESH;
                            changeFooterViewByState();
                        }

                        if ((startYDown - tempY) <= 0) {
                            mFooterState = DONE;
                            changeFooterViewByState();
                        }
                    }

                    if (mFooterState == PULL_To_REFRESH || mFooterState == RELEASE_To_REFRESH) {
                        mRefreshView2.setPadding(0, 0, 0, (startYDown - tempY) / RATIO);
                    }

                }
                break;
            case MotionEvent.ACTION_UP:
                if (isStartDown) {
                    isStartDown = false;
                    mRefreshView2.setPadding(0, 0, 0, 0);

                    if (mFooterState == RELEASE_To_REFRESH) {
                        if (refreshListener != null) {
                            mFooterState = LOADING;
                            changeFooterViewByState();
                            refreshListener.onLoadMore();
                        }
                    } else {
                        mFooterState = DONE;
                        changeFooterViewByState();
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (isStartDown) {
                    isStartDown = false;
                    mRefreshView2.setPadding(0, 0, 0, 0);
                    mFooterState = DONE;
                    changeFooterViewByState();
                }
                break;
        }
    }

    private void changeFooterViewByState() {
        switch (mFooterState) {
            case DONE:
                mRefreshButton.setVisibility(View.VISIBLE);
                mRefreshViewProgress2.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(bottomText)) {
                    mRefreshButton.setText(bottomText);
                } else {
                    mRefreshButton.setText("点击加载更多");
                }
                break;
            case PULL_To_REFRESH:
                mRefreshButton.setVisibility(View.VISIBLE);
                mRefreshViewProgress2.setVisibility(View.GONE);
                mRefreshButton.setText("点击加载更多");
                break;
            case RELEASE_To_REFRESH:
                mRefreshButton.setVisibility(View.VISIBLE);
                mRefreshViewProgress2.setVisibility(View.GONE);
                mRefreshButton.setText("松开加载更多");
                break;
            case LOADING:
                mRefreshButton.setVisibility(View.GONE);
                mRefreshViewProgress2.setVisibility(View.VISIBLE);
                break;
            case NO_MORE:
                mRefreshButton.setVisibility(VISIBLE);
                mRefreshViewProgress2.setVisibility(GONE);
                mRefreshButton.setText("没有更多数据了");
        }
    }


    // 当状态改变时候，调用该方法，以更新界面
    private void changeHeaderViewByState() {
        switch (mHeaderState) {
            case RELEASE_To_REFRESH:
                arrowImageView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                tipsTextview.setVisibility(View.VISIBLE);
                lastUpdatedTextView.setVisibility(View.VISIBLE);

                arrowImageView.clearAnimation();
                arrowImageView.startAnimation(animation);

                tipsTextview.setText("松开刷新");

                // Log.v(TAG, "当前状态，松开刷新");
                break;
            case PULL_To_REFRESH:
                progressBar.setVisibility(View.INVISIBLE);
                tipsTextview.setVisibility(View.VISIBLE);
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.setVisibility(View.VISIBLE);
                // 是由RELEASE_To_REFRESH状态转变来的
                if (isBack) {
                    isBack = false;
                    arrowImageView.clearAnimation();
                    arrowImageView.startAnimation(reverseAnimation);

                    tipsTextview.setText("下拉刷新");
                } else {
                    tipsTextview.setText("下拉刷新");
                }
                // Log.v(TAG, "当前状态，下拉刷新");
                break;

            case REFRESHING:

                headView.setPadding(0, 0, 0, 0);

                progressBar.setVisibility(View.VISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.setVisibility(View.INVISIBLE);
                tipsTextview.setText("正在刷新...");
                lastUpdatedTextView.setVisibility(View.VISIBLE);

                // Log.v(TAG, "当前状态,正在刷新...");
                break;
            case DONE:
                if (isNeedHeadViewDoning()) {
                    new HeadThread().start();
                }

                progressBar.setVisibility(View.INVISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.setImageResource(R.drawable.icon_arrow_down);
                tipsTextview.setText("下拉刷新");
                lastUpdatedTextView.setVisibility(View.VISIBLE);

                // Log.v(TAG, "当前状态，done");
                break;
        }
    }

    private boolean isNeedHeadViewDoning() {
        if (headView.getPaddingTop() == -1 * headContentHeight) return false;
        else return true;
    }

    Handler handle = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int padtop = headView.getPaddingTop();

            int h = headContentHeight;

            if (padtop > -1 * h) {
                padtop -= 1;
            } else {
                padtop = -1 * h;
                isHeadViewDoning = false;
            }
            headView.setPadding(0, padtop, 0, 0);
            headView.invalidate();
        }

        ;
    };

    boolean isHeadViewDoning = false;

    class HeadThread extends Thread {
        @Override
        public void run() {
            isHeadViewDoning = true;
            while (isHeadViewDoning) {
                try {
                    Thread.sleep(HVPT);//HVPT是根据headviewheight 和 HVDT计算得出
                } catch (Exception e) {

                }
                handle.sendEmptyMessage(0);
            }
        }
    }

    public void onRefreshComplete() {
        onRefreshComplete(null);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        checkBottom();
    }

    public void onRefresh() {
        if (refreshListener != null) {
            refreshListener.onRefresh();
            checkBottom();
        }
    }

    public void setBottomView() {
        if (!isRefreshBottomAuto) {
            if (mRefreshView2.getVisibility() != View.VISIBLE) {
                mRefreshView2.setVisibility(View.VISIBLE);
            }
            mRefreshButton.setVisibility(View.VISIBLE);
            mRefreshViewProgress2.setVisibility(View.GONE);
        }
    }

    public void checkBottom() {
        mFooterState = DONE;

        if (!isRefreshBottomAuto) {
            if (mRefreshView2.getVisibility() != View.VISIBLE) {
                mRefreshView2.setVisibility(View.VISIBLE);
            }
            mRefreshButton.setVisibility(View.VISIBLE);
            mRefreshViewProgress2.setVisibility(View.GONE);
        }

        mHandler.post(new Runnable() {

            @Override
            public void run() {
                resetFooter(bottomFreshOpen);
            }

        });
    }

    public void setBottomEnable(Boolean b) {
        bottomFreshOpen = b;
        checkBottom();
    }

    /**
     * 加载异常后显示重试按钮
     */
    public void showRetryBottom(String text) {
        mRefreshButton.setVisibility(View.VISIBLE);
        mRefreshButton.setText(text);
        mRefreshViewProgress2.setVisibility(View.GONE);
        mRefreshButton.setClickable(true);
        bottomFreshOpen = true;
        prepareForRefreshBottom();
    }

    /**
     * 下拉刷新出现异常后恢复原始状态
     */
    public void resumeBottom() {
        mRefreshButton.setVisibility(View.GONE);
        mRefreshViewProgress2.setVisibility(View.VISIBLE);
    }

    public void setNoMoreDataView(boolean isEnd, String showText) {
        bottomFreshOpen = !isEnd;

        if (!isRefreshBottomAuto) {
            if (showText == null || showText.equals("")) {
                if (isEnd) {
                    showText = "已无更多帖子";
                } else {
                    showText = "点此查看更多";
                }
            }
            if (mRefreshView2.getVisibility() != View.VISIBLE) {
                mRefreshView2.setVisibility(View.VISIBLE);
            }
            mRefreshButton.setVisibility(View.VISIBLE);
            mRefreshViewProgress2.setVisibility(View.GONE);
            mRefreshButton.setText(showText);
            mRefreshButton.setClickable(!isEnd);
            //2015年4月20日15:51:47 罗康辉 分时页下股吧列表首次加载有时没有出现“加载更多”bug更改
            prepareForRefreshBottom();
        } else {

            // BUG #320 去掉帖子列表底部的“已无更多帖子” 和回复列表 ”已无更多回复“的提示语，但保留该行单元格
//			showText = "";
            //BUG#3882 正文页在显示完所有评论后，显示一行灰色小字“已无更多评论，欢迎您发表您的观点”
            if (isEnd) {
                mRefreshButton.setVisibility(View.VISIBLE);
                mRefreshButton.setText(showText);
                mRefreshViewProgress2.setVisibility(View.GONE);
                mRefreshButton.setClickable(false);
                if (TextUtils.isEmpty(showText)) {
                    removeHeaderFooter();
                } else {
                    prepareForRefreshBottom();
                }
            } else {
                mRefreshButton.setVisibility(View.GONE);
                mRefreshButton.setText(showText);
                mRefreshViewProgress2.setVisibility(View.VISIBLE);
                mRefreshButton.setClickable(false);
            }
        }

    }

    // 此方法直接照搬自网络上的一个下拉刷新的demo，此处是“估计”headView的width以及height
    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public void setAdapter(BaseAdapter adapter) {
        String time = "2017.1.1";
        lastUpdatedTextView.setText("最近更新:" + time);
        super.setAdapter(adapter);

    }

    public void onRefreshComplete(String lastUpdated) {
        mHeaderState = DONE;
        if (lastUpdated != null) {
            lastUpdatedTextView.setText("最近更新:" + lastUpdated);
        }
        changeHeaderViewByState();

        isRefreshable = true;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        refreshListener = onRefreshListener;
        isRefreshable = true;
    }

    public synchronized void onLoadMore() {
        onRefreshComplete(null);
        if (refreshListener != null) {

            if (mFooterState == DONE || mFooterState == NO_MORE) {
                mFooterState = LOADING;
                refreshListener.onLoadMore();
            }
        }
    }




    // ------------------dong,准备的东西---------------------------------


    public void onLoadMoreComplete(boolean hasMore) {
        if (hasMore) {
            mFooterState = DONE;
        } else {
            mFooterState = NO_MORE;
        }
        changeFooterViewByState();
    }
}
