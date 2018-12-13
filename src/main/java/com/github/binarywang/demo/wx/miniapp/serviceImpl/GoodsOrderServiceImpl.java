package com.github.binarywang.demo.wx.miniapp.serviceImpl;

import com.github.binarywang.demo.wx.miniapp.config.BusinessException;
import com.github.binarywang.demo.wx.miniapp.controller.vm.GoodsOrderVm;
import com.github.binarywang.demo.wx.miniapp.dao.GoodsDao;
import com.github.binarywang.demo.wx.miniapp.dao.GoodsOrderDao;
import com.github.binarywang.demo.wx.miniapp.dao.GoodsOrderProductDao;
import com.github.binarywang.demo.wx.miniapp.dao.GoodsOrderShippingDao;
import com.github.binarywang.demo.wx.miniapp.pojo.Goods;
import com.github.binarywang.demo.wx.miniapp.pojo.GoodsOrder;
import com.github.binarywang.demo.wx.miniapp.pojo.GoodsOrderProduct;
import com.github.binarywang.demo.wx.miniapp.pojo.GoodsOrderShipping;
import com.github.binarywang.demo.wx.miniapp.service.GoodsOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.github.binarywang.demo.wx.miniapp.serviceImpl.VoteUserServiceImpl.getRandom620;

/**
 * @author FHZD.xiaoxun
 * @date 2018/12/10
 */
@Service
@Transactional
@Slf4j
public class GoodsOrderServiceImpl implements GoodsOrderService {
    @Autowired
    private GoodsOrderDao goodsOrderDao;

    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private GoodsOrderProductDao goodsOrderProductDao;
    @Autowired
    private GoodsOrderShippingDao goodsOrderShippingDao;

    @Override
    public String createOrder(GoodsOrderVm goodsOrderVm) {
        String strId = getStrId();
        //获取商品信息
        Goods one = goodsDao.findOne(goodsOrderVm.getGoodsId());
        //商品总金额
        BigDecimal price = one.getGoodsPrice().multiply(new BigDecimal(goodsOrderVm.getOrderGoodsNumber()));
        if (!StringUtils.isEmpty(one)){
            if (one.getGoodsNum() - goodsOrderVm.getOrderGoodsNumber() <= 0){
                throw new BusinessException("商品库存不足");
            }
            //订单信息
            GoodsOrder build = GoodsOrder.builder()
                    .orderId(UUID.randomUUID() + getRandom620(3))
                    .orderShowId(strId)
                    .orderOldPrice(price).orderPrePrice(price).orderPrice(price)
                    .orderNumber(goodsOrderVm.getOrderGoodsNumber())
                    .orderLinePay(goodsOrderVm.getOrderLinePay()).expressPrice(new BigDecimal(0))
                    .orderStatus(1 + "").orderCreateTime(new Date())
                    .openId(goodsOrderVm.getOpenId())
                    .orderShowId(strId)
                    .shippingId(strId)
                    .build();
            GoodsOrder save = goodsOrderDao.save(build);

            //订单详情
            GoodsOrderProduct orderProduct = GoodsOrderProduct.builder()
                    .orderProductId(strId)
                    .goodsId(goodsOrderVm.getGoodsId())
                    .orderId(build.getOrderId())
                    .orderGoodsNumber(goodsOrderVm.getOrderGoodsNumber())
                    .orderGoodsTitle(one.getGoodsName())
                    .orderGoodsPrice(one.getGoodsPrice())
                    .orderGoodsImage(one.getGoodsImg()).build();
            goodsOrderProductDao.save(orderProduct);

            //物流信息
            GoodsOrderShipping orderShipping = GoodsOrderShipping.builder()
                    .shippingId(strId)
                    .orderId(build.getOrderId())
                    .shippingCustomerName(goodsOrderVm.getShippingCustomerName())
                    .shippingCustomerTelephone(goodsOrderVm.getShippingCustomerTelephone())
                    .shippingAreaDetail(goodsOrderVm.getShippingAreaDetail())
                    .shippingCreateTime(new Date())
                    .shippingType(goodsOrderVm.getShippingType()).build();
            goodsOrderShippingDao.save(orderShipping);

            return save.getOrderShowId();
        }else {
            throw new BusinessException("商品不能为空");
        }

    }

    @Override
    public void deleteOrder(String orderId) {
        GoodsOrder one = goodsOrderDao.getOne(orderId);
        if (StringUtils.isEmpty(one)){
            throw new BusinessException("当前订单不存在");
        }
        one.setOrderStatus(6+"");
        one.setOrderCancelTime(new Date());

    }

    private String getStrId() {
        //格式化当前时间
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String strDate = sfDate.format(new Date());
        //得到17位时间如：20170411094039080
        log.info("时间17位：strDate={}", strDate);
        //为了防止高并发重复,再获取3个随机数
        String random = getRandom620(3);
        //最后得到20位订单编号。
        log.info("订单号20位：orderId={}", strDate + random);
        return strDate + random;
    }

}
