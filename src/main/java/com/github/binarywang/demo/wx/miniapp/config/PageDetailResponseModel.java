package com.github.binarywang.demo.wx.miniapp.config;

public class PageDetailResponseModel<T, K> extends PageableResponseModel<T> {
    private K otherData;

    public PageDetailResponseModel() {
    }

    public K getOtherData() {
        return this.otherData;
    }

    public void setOtherData(K otherData) {
        this.otherData = otherData;
    }
}
