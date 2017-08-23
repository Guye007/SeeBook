package com.gy.seebook.ui.presenter;

import com.gy.seebook.api.Api;
import com.gy.seebook.bean.Recommend;
import com.gy.seebook.ui.fragment.BookRecommendFragment;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * @name SeeBook
 * @class name：com.gy.seebook.ui.presenter
 * Author: apple   QQ:954401985
 * Email: guyework@163.com
 * Comment: //TODO
 * Date: 2017-08-22 18:09
 */

public class RecommendPresenter extends XPresent<BookRecommendFragment> {

    public void loadData(String gender){
        Api.getSeeBookService().getRecomendBook(gender)
                .compose(XApi.<Recommend>getApiTransformer())
                .compose(XApi.<Recommend>getScheduler())
                .compose(getV().<Recommend>bindToLifecycle())
                .subscribe(new ApiSubscriber<Recommend>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(Recommend recommend) {
                        getV().showDatas(recommend);
                    }
                });
    }
}
