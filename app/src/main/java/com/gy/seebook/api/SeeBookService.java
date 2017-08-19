package com.gy.seebook.api;

import com.gy.seebook.bean.Recommend;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @name SeeBook
 * @class name：com.gy.seebook.api
 * Author: apple   QQ:954401985
 * Email: guyework@163.com
 * Comment: //TODO
 * Date: 2017-08-19 16:19
 */

public interface SeeBookService {

    @GET("/book/recommend")
    Observable<Recommend> getRecomend(@Query("gender") String gender);
}
