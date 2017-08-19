package com.gy.seebook.api;

import com.gy.seebook.Constants;

import java.nio.channels.SeekableByteChannel;

import cn.droidlover.xdroidmvp.net.XApi;

/**
 * @name SeeBook
 * @class name：com.gy.seebook.api
 * Author: apple   QQ:954401985
 * Email: guyework@163.com
 * Comment: //TODO
 * Date: 2017-08-19 16:17
 */

public class Api {

    private static SeeBookService seeBookService;
    public static SeeBookService getSeeBookService(){
        if (seeBookService == null){
            synchronized (Api.class){
                if (seeBookService == null){
                    seeBookService = XApi.getInstance().getRetrofit(Constants.API_BASE_URL, true).create(SeeBookService.class);
                }
            }
        }
        return seeBookService;
    }
}
