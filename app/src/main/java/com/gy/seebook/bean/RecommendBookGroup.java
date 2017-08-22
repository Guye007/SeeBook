package com.gy.seebook.bean;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @name SeeBook
 * @class name：com.gy.seebook.bean
 * Author: apple   QQ:954401985
 * Email: guyework@163.com
 * Comment: //TODO
 * Date: 2017-08-22 09:44
 */

public class RecommendBookGroup extends RecommendBookData{

    private List<RecommendBookData> mChild = new ArrayList<>();
    private String mCategory;

    public void addChild(@NonNull RecommendBookData recommendBookData){
        recommendBookData.setParent(this);
        mChild.add(recommendBookData);
    }

    public void addChild(int location,@NonNull RecommendBookData recommendBookData){
        recommendBookData.setParent(this);
        mChild.add(location,recommendBookData);
    }

    public RecommendBookData removeChild(int location){
        RecommendBookData mockData = mChild.remove(location);
        mockData.setParent(null);
        return mockData;
    }

    public boolean removeChild(@NonNull RecommendBookData recommendBookData){
        recommendBookData.setParent(null);
        return mChild.remove(recommendBookData);
    }


    public int getChildCount(){
        return mChild.size();
    }


    public RecommendBookData getChild(int position){
        return mChild.get(position);
    }


    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public int getCheckedCount(){
        if(mChild != null){
            int i = 0;
            for(RecommendBookData data:mChild){
                if(data.isChecked()){
                    i++;
                }
            }
            return i;
        }
        return 0;
    }
}
