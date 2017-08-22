package com.gy.seebook.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gy.seebook.R;
import com.gy.seebook.ui.fragment.BookFragment;
import com.gy.seebook.ui.fragment.BookRecommendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.base.XFragmentAdapter;
import cn.droidlover.xdroidmvp.mvp.XActivity;

public class HomeActivity extends XActivity {


    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;
    @BindView(R.id.main_rgp)
    RadioGroup mainRgp;

    private List<Fragment> fragments = new ArrayList<>();
    private XFragmentAdapter xFragmentAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {

        initFragment();
        setAdapter();
        setListener();
    }

    private void initFragment() {

        BookRecommendFragment recommendFragment = new BookRecommendFragment();
        BookFragment bookFragment = new BookFragment();
        BookFragment bookFragment3 = new BookFragment();
        BookFragment bookFragment4 = new BookFragment();

        fragments.add(recommendFragment);
        fragments.add(bookFragment);
        fragments.add(bookFragment3);
        fragments.add(bookFragment4);
    }

    private void setAdapter() {
        xFragmentAdapter = new XFragmentAdapter(getSupportFragmentManager(), fragments, null);
        mainViewpager.setAdapter(xFragmentAdapter);

        //设置viewpager的预加载页数，viewpager默认只会预加载左右两边的页面数据
        mainViewpager.setOffscreenPageLimit(4);
        //设置默认第一个为选中状态
        RadioButton rb = (RadioButton) mainRgp.getChildAt(0);
        rb.setChecked(true);
    }

    private void setListener() {
        //viewPager的滑动监听
        mainViewpager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //获取当前位置的RadioButton
                RadioButton rb = (RadioButton) mainRgp.getChildAt(position);
                //设置为true
                rb.setChecked(true);
            }
        });
        //RadioGroup的事件监听
        mainRgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int index = 0; index < mainRgp.getChildCount(); index++) {
                    RadioButton rb = (RadioButton) mainRgp.getChildAt(index);
                    if (rb.isChecked()) {
                        mainViewpager.setCurrentItem(index, false);
                        break;
                    }
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public Object newP() {
        return null;
    }

    private boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

}
