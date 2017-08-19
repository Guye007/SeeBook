package com.gy.seebook.ui.fragment;

import android.os.Bundle;

import com.gy.seebook.R;

import cn.droidlover.xdroidmvp.mvp.XFragment;

/**
 * @name SeeBook
 * @class name：com.gy.seebook.ui.fragment
 * Author: apple   QQ:954401985
 * Email: guyework@163.com
 * Comment: //TODO
 * Date: 2017-08-19 17:35
 */

public class BookFragment extends XFragment {
    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_book;
    }

    @Override
    public Object newP() {
        return null;
    }
}
