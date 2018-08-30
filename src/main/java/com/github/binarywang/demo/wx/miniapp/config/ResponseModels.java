package com.github.binarywang.demo.wx.miniapp.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.net.URISyntaxException;
import java.util.Objects;

public class ResponseModels {
    public ResponseModels() {
    }

    public static ResponseModel newInstance() {
        ResponseModel responseModel = new ResponseModel();
        return responseModel;
    }

    public static ResponseModel newInstance(ResponseCode responseCode) {
        return newInstance().responseCode(responseCode);
    }

    public static <T> ResponseModel<T> ok(T data) {
        return (new ResponseModel()).ok(data);
    }

    public static ResponseModel ok() {
        return (new ResponseModel()).ok();
    }

    public static ResponseModel forbidden() {
        return (new ResponseModel()).forbidden();
    }

    public static ResponseModel unauthorized() {
        return (new ResponseModel()).unauthorized();
    }

    public static ResponseModel userNotActivated() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.responseCode(ResponseCode.UserNotActivated);
        return responseModel;
    }

    public static ResponseModel businessException() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.responseCode(ResponseCode.BusinessException);
        return responseModel;
    }

    public static ResponseModel businessException(BusinessException businessException) {
        ResponseModel responseModel = businessException();
        responseModel.message_en(businessException.getMessage_en()).message(businessException.getMessage()).errorCode(businessException.getCode()).data(businessException.getData());
        return responseModel;
    }

    public static ResponseModel paramValidException() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.responseCode(ResponseCode.ParamValidException);
        return responseModel;
    }

    public static ResponseModel paramValidException(String message) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.responseCode(ResponseCode.ParamValidException).message(message).message_en(message);
        return responseModel;
    }

    public static ResponseModel httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        ResponseModel responseModel = newInstance().responseCode(ResponseCode.HttpRequestMethodNotSupportedException);
        String[] allowedMethods = ex.getSupportedMethods();
        if (!Objects.isNull(allowedMethods)&&allowedMethods.length>0) {
            responseModel.message_en(String.format(responseModel.getMessage_en(), StringUtils.join(allowedMethods, ","))).message(String.format(responseModel.getMessage(), StringUtils.join(allowedMethods, ",")));
        }
        return responseModel;
    }

    public static ResponseModel URISyntaxException(URISyntaxException ex) {
        ResponseModel responseModel = newInstance().responseCode(ResponseCode.URISyntaxException);
        String reason = ex.getReason();
        responseModel.message_en(String.format(responseModel.getMessage_en(), reason)).message(String.format(responseModel.getMessage(), reason));
        return responseModel;
    }

    public static ResponseModel unkownException() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.responseCode(ResponseCode.UnkownException);
        return responseModel;
    }

    public static ResponseModel noAuthor() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.responseCode(ResponseCode.NoAuthor);
        return responseModel;
    }

    public static ResponseModel noResult() {
        return (new ResponseModel()).responseCode(ResponseCode.EMPTY);
    }

    public static <T> PageableResponseModel<T> page2ResponseModel(Page<T> page) {
        PageableResponseModel<T> responseModel = new PageableResponseModel();
        if (page.getContent() != null && page.getContent().size() != 0) {
            responseModel.ok(page.getContent());
        } else {
            responseModel.responseCode(ResponseCode.EMPTY);
        }

        responseModel.total(page.getTotalElements()).pageNumber(page.getNumber()).pageSize(page.getSize()).totalPages((long)page.getTotalPages());
        return responseModel;
    }

    public static <T, K> PageDetailResponseModel<T, K> page2ResponseModel(Page<T> page, K otherData) {
        PageDetailResponseModel<T, K> responseModel = new PageDetailResponseModel();
        responseModel.setOtherData(otherData);
        if (page.getContent() != null && page.getContent().size() != 0) {
            responseModel.ok(page.getContent());
        } else {
            responseModel.responseCode(ResponseCode.EMPTY);
        }

        responseModel.total(page.getTotalElements()).pageNumber(page.getNumber()).pageSize(page.getSize()).totalPages((long)page.getTotalPages());
        return responseModel;
    }

    public static ResponseModel tokenTimeout() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.responseCode(ResponseCode.TOKEN_TIMEOUT);
        return responseModel;
    }

    public static ResponseModel tokenError() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.responseCode(ResponseCode.TOKEN_ERROR);
        return responseModel;
    }
}