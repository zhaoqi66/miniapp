package com.github.binarywang.demo.wx.miniapp.serviceImpl;

import com.github.binarywang.demo.wx.miniapp.dao.GoodsDao;
import com.github.binarywang.demo.wx.miniapp.pojo.Goods;
import com.github.binarywang.demo.wx.miniapp.service.GoodsService;
import com.github.binarywang.demo.wx.miniapp.serviceImpl.dto.GoodsCommendDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author FHZD.xiaoxun
 * @date 2018/12/10
 */
@Service
@Transactional
@Slf4j
public class GoodsServiceImpl implements GoodsService{

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public ArrayList<GoodsCommendDTO> list(String goodsName, int pageNumber, int pageSize) {
        //排序条件
        List<Sort.Order> orders=new ArrayList<Sort.Order>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "goodsSort"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "goodsAddedTime"));
        Sort sort = new Sort(orders);
        Pageable pageable = new PageRequest(pageNumber, pageSize, sort);

        Goods goods = new Goods();
        goods.setGoodsDelflag((byte) 1);
        Example<Goods> example = Example.of(goods);
        Page<Goods> all = goodsDao.findAll(example, pageable);

        ArrayList<GoodsCommendDTO> list = new ArrayList<>();
        if (all.getSize() != 0){
            for (Goods g: all) {
                String[] imgSplit = g.getGoodsImg().split(",");
                GoodsCommendDTO goodsDTO = new GoodsCommendDTO(g,imgSplit);
                list.add(goodsDTO);
            }
        }

        return list;
    }
}
