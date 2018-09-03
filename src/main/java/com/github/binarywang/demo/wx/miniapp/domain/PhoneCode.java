package com.github.binarywang.demo.wx.miniapp.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * PhoneCode
 *
 * @author juan
 * @date 2018/8/30 14:38
 */
@Data
public class PhoneCode implements Serializable {
    private String phoneCodeId;
    private String phone;
    private String code;
    private Date createTime;

    public PhoneCode() {
    }

    public PhoneCode(String phoneCodeId, String phone, String code, Date createTime) {
        this.phoneCodeId = phoneCodeId;
        this.phone = phone;
        this.code = code;
        this.createTime = createTime;
    }

    public String getPhoneCodeId() {
        return phoneCodeId;
    }


}

