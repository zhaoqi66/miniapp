package com.github.binarywang.demo.wx.miniapp.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("统一分页数据格式")
public class PageableResponseModel<T> extends ResponseModel<List<T>> {
    private static final long serialVersionUID = 6966525696481796578L;
    @ApiModelProperty("总条数")
    private long total;
    @ApiModelProperty("总页数")
    private long totalPages;
    @ApiModelProperty("当前页")
    private int pageNumber;
    @ApiModelProperty("每页显示数量")
    private int pageSize;

    public PageableResponseModel() {
    }

    public PageableResponseModel<T> total(long total) {
        this.total = total;
        return this;
    }

    public PageableResponseModel<T> totalPages(long totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public PageableResponseModel<T> pageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public PageableResponseModel<T> pageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String toString() {
        return "PageableResponseModel [total=" + this.total + ", totalPages=" + this.totalPages + ", pageNumber=" + this.pageNumber + ", pageSize=" + this.pageSize + "]";
    }
}
