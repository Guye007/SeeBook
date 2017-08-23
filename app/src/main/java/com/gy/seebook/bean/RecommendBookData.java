package com.gy.seebook.bean;

import android.graphics.Color;

/**
 * @name SeeBook
 * @class name：com.gy.seebook.bean
 * Author: apple   QQ:954401985
 * Email: guyework@163.com
 * Comment: //TODO
 * Date: 2017-08-22 09:44
 */

public class RecommendBookData {

    private boolean isChecked;
    private int color = Color.BLUE;
    private RecommendBookGroup mParent;
    private String cover;
    private Recommend.BooksBean booksBean;

    public Recommend.BooksBean getBooksBean() {
        return booksBean;
    }

    public void setBooksBean(Recommend.BooksBean booksBean) {
        this.booksBean = booksBean;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public RecommendBookGroup getParent() {
        return mParent;
    }

    public void setParent(RecommendBookGroup parent) {
        mParent = parent;
    }
}
