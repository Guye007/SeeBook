package com.gy.seebook.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gy.seebook.R;
import com.gy.seebook.bean.Recommend;
import com.gy.seebook.ui.presenter.ReadActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @name SeeBook
 * @class name：com.gy.seebook.ui.activity
 * Author: apple   QQ:954401985
 * Email: guyework@163.com
 * Comment: //TODO
 * Date: 2017-08-23 11:00
 */

public class ReadActivity extends XActivity<ReadActivityPresenter> {

    public static final String INTENT_BEAN = "recommendBooksBean";
    public static final String INTENT_SD = "isFromSD";

    @BindView(R.id.flReadWidget)
    FrameLayout flReadWidget;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvBookReadTocTitle)
    TextView tvBookReadTocTitle;
    @BindView(R.id.tvBookReadReading)
    TextView tvBookReadReading;
    @BindView(R.id.tvBookReadCommunity)
    TextView tvBookReadCommunity;
    @BindView(R.id.tvBookReadIntroduce)
    TextView tvBookReadIntroduce;
    @BindView(R.id.tvBookReadSource)
    TextView tvBookReadSource;
    @BindView(R.id.llBookReadTop)
    LinearLayout llBookReadTop;
    @BindView(R.id.tvDownloadProgress)
    TextView tvDownloadProgress;
    @BindView(R.id.tvBookReadMode)
    TextView tvBookReadMode;
    @BindView(R.id.tvBookReadSettings)
    TextView tvBookReadSettings;
    @BindView(R.id.tvBookReadDownload)
    TextView tvBookReadDownload;
    @BindView(R.id.tvBookMark)
    TextView tvBookMark;
    @BindView(R.id.tvBookReadToc)
    TextView tvBookReadToc;
    @BindView(R.id.llBookReadBottom)
    LinearLayout llBookReadBottom;
    @BindView(R.id.rlBookReadRoot)
    RelativeLayout rlBookReadRoot;

    private boolean isFromSD = false;

    //添加收藏需要，所以跳转的时候传递整个实体类
    public static void startActivity(Activity activity, Recommend.BooksBean recommendBooks) {
        startActivity(activity, recommendBooks, false);
    }

    public static void startActivity(Activity activity, Recommend.BooksBean recommendBooks, boolean isFromSD) {
        Router.newIntent(activity)
                .to(ReadActivity.class)
                .putSerializable(INTENT_BEAN, recommendBooks)
                .putBoolean(INTENT_SD, isFromSD)
                .launch();
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_read;
    }

    @Override
    public ReadActivityPresenter newP() {
        return new ReadActivityPresenter();
    }

}
