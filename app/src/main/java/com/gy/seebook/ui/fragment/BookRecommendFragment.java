package com.gy.seebook.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gy.seebook.R;
import com.gy.seebook.bean.Recommend;
import com.gy.seebook.bean.RecommendBookData;
import com.gy.seebook.ui.activity.ReadActivity;
import com.gy.seebook.ui.adapter.RecommendAdapter;
import com.gy.seebook.ui.presenter.RecommendPresenter;
import com.gy.seebook.view.RecommendView;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XFragment;

/**
 * @name SeeBook
 * @class name：com.gy.seebook.ui.fragment
 * Author: apple   QQ:954401985
 * Email: guyework@163.com
 * Comment: //TODO
 * Date: 2017-08-21 15:45
 */

public class BookRecommendFragment extends XFragment<RecommendPresenter> {

    @BindView(R.id.recommend_view)
    RecommendView recommendView;
    @BindView(R.id.text_select_all)
    TextView textSelectAll;
    @BindView(R.id.text_complete)
    TextView textComplete;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @BindView(R.id.ic_delete)
    AppCompatImageView icDelete;
    @BindView(R.id.ic_delete_badge)
    TextView icDeleteBadge;
    @BindView(R.id.text_delete)
    TextView textDelete;
    @BindView(R.id.container_delete)
    RelativeLayout containerDelete;
    @BindView(R.id.ic_move)
    AppCompatImageView icMove;
    @BindView(R.id.text_move)
    TextView textMove;
    @BindView(R.id.container_move)
    RelativeLayout containerMove;
    @BindView(R.id.ic_share)
    AppCompatImageView icShare;
    @BindView(R.id.text_share)
    TextView textShare;
    @BindView(R.id.container_share)
    RelativeLayout containerShare;
    @BindView(R.id.ic_order)
    AppCompatImageView icOrder;
    @BindView(R.id.text_order)
    TextView textOrder;
    @BindView(R.id.container_order)
    RelativeLayout containerOrder;
    @BindView(R.id.ic_detail)
    AppCompatImageView icDetail;
    @BindView(R.id.text_detail)
    TextView textDetail;
    @BindView(R.id.container_detail)
    RelativeLayout containerDetail;
    @BindView(R.id.bottom_bar)
    LinearLayout bottomBar;

    private RecommendAdapter mAdapter;
    private Random mRandom;
    private RecommendBookData bookData;

    @Override
    public void initData(Bundle savedInstanceState) {

        getP().loadData("male");
        setHasOptionsMenu(true);
        mRandom = new Random(System.currentTimeMillis());
        mAdapter = new RecommendAdapter(getContext());

        //跳转到阅读
        mAdapter.setItemClickListener(new RecommendAdapter.ItemClickListener() {
            @Override
            public void onClick(RecommendBookData recommendBookData, int mainPosition, int subPosition) {
                Recommend.BooksBean booksBean = recommendBookData.getBooksBean();
                ReadActivity.startActivity(getActivity(), booksBean);
            }
        });

        mAdapter.registerObserver(new RecommendAdapter.RecommendObserver() {

            int count = 0;

            @Override
            public void onChecked(boolean isChecked) {
                count += isChecked ? 1 : -1;
                if (count <= 0) {
                    count = 0;
                    icDeleteBadge.setVisibility(View.GONE);
                    setBottomEnable(false);
                } else {
                    if (icDeleteBadge.getVisibility() == View.INVISIBLE || icDeleteBadge.getVisibility() == View.GONE) {
                        icDeleteBadge.setVisibility(View.VISIBLE);
                    }
                    icDeleteBadge.setText(String.valueOf(count));
                    setBottomEnable(true);
                    String s = icDeleteBadge.getText().toString();
                    Log.e("TAG====", "11111     " + count + "---" + s);
                }
            }

            @Override
            public void onEditChanged(boolean inEdit) {
                Log.e("TAG====", "2222     " + count);
                if (inEdit) {
                    showEditMode();
                } else {
                    hideEditMode();
                }
            }

            @Override
            public void onRestore() {
                count = 0;
                icDeleteBadge.setVisibility(View.INVISIBLE);
                setBottomEnable(false);
                Log.e("TAG====", "3333     " + count);
            }

            @Override
            public void onHideSubDialog() {
                Log.e("TAG====", "4444     " + count);
                recommendView.hideSubContainer();
            }
        });

        recommendView.setAdapter(mAdapter);
        recommendView.setDebugAble(true);
        textComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.setEditMode(false);
            }
        });

        containerDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.removeAllCheckedBook();
                Log.e("dianji===", "//////");
            }
        });

        float density = getResources().getDisplayMetrics().density;
        bottomBar.setTranslationY(55 * density);
        toolBar.setTranslationY(-60 * density);
    }

    private void setBottomEnable(boolean enable) {
        containerDelete.setEnabled(enable);
        containerMove.setEnabled(enable);
        containerShare.setEnabled(enable);
        containerOrder.setEnabled(enable);
        containerDetail.setEnabled(enable);
    }

    private void showEditMode() {
        toolBar.animate().translationY(0).start();
        bottomBar.animate().translationY(0).start();
    }

    private void hideEditMode() {
        toolBar.animate().translationY(-toolBar.getHeight()).start();
        bottomBar.animate().translationY(bottomBar.getHeight()).start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_i_reader, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            mAdapter.addBook(generateRandomMockData());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private RecommendBookData generateRandomMockData() {
        RecommendBookData mockData = new RecommendBookData();
        mockData.setColor(Color.rgb(mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256)));
        return mockData;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public RecommendPresenter newP() {
        return new RecommendPresenter();
    }

    @Override
    public boolean onBackPressed() {
        if (mAdapter.isEditMode()) {
            mAdapter.setEditMode(false);
            return true;
        }
        return super.onBackPressed();
    }

    public void showDatas(Recommend recommend) {

        List<Recommend.BooksBean> books = recommend.getBooks();
        for (Recommend.BooksBean bean: books) {
            bookData = new RecommendBookData();
            bookData.setCover(bean.getCover());
            bookData.setBooksBean(bean);
            mAdapter.addBook(bookData);
        }
    }
}
