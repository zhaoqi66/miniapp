package com.github.binarywang.demo.wx.miniapp.service;

import com.github.binarywang.demo.wx.miniapp.serviceImpl.dto.GoodsCommendDTO;

import java.util.ArrayList;

/**
 * @author FHZD.xiaoxun
 * @date 2018/12/10
 */
public interface GoodsService {
    ArrayList<GoodsCommendDTO> list(String goodsName, int pageNumber, int pageSize);
}
