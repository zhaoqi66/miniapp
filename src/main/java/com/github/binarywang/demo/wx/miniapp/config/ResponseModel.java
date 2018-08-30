package com.github.binarywang.demo.wx.miniapp.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

@ApiModel("统一数据格式")
public class ResponseModel<T> implements Serializable {
    private static final long serialVersionUID = 4525431233220956770L;
    @ApiModelProperty("错误码,值为0(默认)时表示没有错误,50002表示查询结果为空")
    private int errorCode;
    @ApiModelProperty("提示信息")
    private String message;
    @ApiModelProperty("提示信息 英文")
    private String message_en;
    @ApiModelProperty("返回code,OK表示成功,EMPTY表示查询结果为空")
    private ResponseCode responseCode;
    @ApiModelProperty("需要传递的数据")
    private T data;
    @ApiModelProperty("调用是否成功,返回结果为空或ok都表示调用成功")
    private Boolean success;

    public ResponseModel() {
    }

    public ResponseModel<T> ok(T data) {
        if (Objects.isNull(data)) {
            return this.noResult();
        } else {
            this.data = data;
            return this.ok();
        }
    }

    public ResponseModel<T> ok() {
        this.responseCode(ResponseCode.OK);
        return this;
    }

    public ResponseModel<T> forbidden() {
        this.responseCode(ResponseCode.Forbidden);
        return this;
    }

    public ResponseModel<T> unauthorized() {
        this.responseCode(ResponseCode.Unauthorized);
        return this;
    }

    public ResponseModel<T> unkownException() {
        this.responseCode(ResponseCode.UnkownException);
        return this;
    }

    public ResponseModel<T> noResult() {
        this.responseCode(ResponseCode.EMPTY);
        return this;
    }

    public ResponseModel<T> exception(String msg, String msg_en) {
        return this.responseCode(ResponseCode.Exception).message(msg).message_en(msg_en);
    }

    public ResponseModel(ResponseCode responseCode) {
        this.responseCode(responseCode);
    }

    public ResponseModel responseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
        this.errorCode = responseCode.getErrorcode();
        this.message = responseCode.getMessage();
        this.message_en = responseCode.getMessage_en();
        return this;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseCode getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage_en() {
        return this.message_en;
    }

    public void setMessage_en(String message_en) {
        this.message_en = message_en;
    }

    public Boolean getSuccess() {
        return this.success != null ? this.success.booleanValue() : this.responseCode == ResponseCode.OK || this.responseCode == ResponseCode.EMPTY || this.errorCode == ResponseCode.OK.getErrorcode() || this.errorCode == ResponseCode.EMPTY.getErrorcode();
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String toString() {
        return "ResponseModel [errorCode=" + this.errorCode + ", message=" + this.message + ", message_en=" + this.message_en + ", responseCode=" + this.responseCode + ", data=" + this.data + "]";
    }

    public ResponseModel errorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ResponseModel message(String message) {
        this.message = message;
        return this;
    }

    public ResponseModel message_en(String message_en) {
        this.message_en = message_en;
        return this;
    }

    public ResponseModel data(T data) {
        this.data = data;
        return this;
    }
}