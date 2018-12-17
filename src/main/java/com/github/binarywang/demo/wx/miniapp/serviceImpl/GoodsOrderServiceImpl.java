package com.github.binarywang.demo.wx.miniapp.serviceImpl;

import com.github.binarywang.demo.wx.miniapp.config.BusinessException;
import com.github.binarywang.demo.wx.miniapp.controller.vm.GoodsOrderVm;
import com.github.binarywang.demo.wx.miniapp.dao.GoodsDao;
import com.github.binarywang.demo.wx.miniapp.dao.GoodsOrderDao;
import com.github.binarywang.demo.wx.miniapp.dao.GoodsOrderProductDao;
import com.github.binarywang.demo.wx.miniapp.dao.GoodsOrderShippingDao;
import com.github.binarywang.demo.wx.miniapp.pojo.*;
import com.github.binarywang.demo.wx.miniapp.service.GoodsOrderService;
import com.github.binarywang.demo.wx.miniapp.wxpayconfig.HttpUtil;
import com.github.binarywang.demo.wx.miniapp.wxpayconfig.PayCommonUtil;
import com.github.binarywang.demo.wx.miniapp.wxpayconfig.WXConfig;
import com.github.binarywang.demo.wx.miniapp.wxpayconfig.XMLUtil;
import lombok.extern.slf4j.Slf4j;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
        if (!StringUtils.isEmpty(one)) {
            if (one.getGoodsNum() - goodsOrderVm.getOrderGoodsNumber() <= 0) {
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
                    .orderTotalFee(one.getGoodsPrice().multiply(new BigDecimal(goodsOrderVm.getOrderGoodsNumber())))
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
        } else {
            throw new BusinessException("商品不能为空");
        }

    }

    @Override
    public void deleteOrder(String orderId) {
        GoodsOrder one = goodsOrderDao.getOne(orderId);
        if (StringUtils.isEmpty(one)) {
            throw new BusinessException("当前订单不存在");
        }
        one.setOrderStatus(6 + "");
        one.setOrderCancelTime(new Date());

    }

    @Override
    public Map payGoodsOrder(String orderId, HttpServletRequest request) {
        if (StringUtils.isEmpty(orderId)) {
            throw new BusinessException("订单id不能为空");
        }
        GoodsOrder one = goodsOrderDao.findOne(orderId);
        if (StringUtils.isEmpty(one)) {
            throw new BusinessException("当前订单id不存在");
        }
        String orderStatus = one.getOrderStatus();
        if (!"1".equals(orderStatus)) {
            throw new BusinessException("当前订单为已支付或过期订单");
        }

        /**
         * 标价金额  把元转化成分 再转化成字符串
         */
        int totalFee = one.getOrderPrice().multiply(new BigDecimal(100)).intValue();

        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        packageParams.put("appid", WXConfig.appid);//微信公众号id
        packageParams.put("mch_id", WXConfig.mch_id);//商户号
        packageParams.put("nonce_str", WXConfig.nonce_str);//随机字符串
        packageParams.put("total_fee", totalFee);//订单金额
        packageParams.put("spbill_create_ip", request.getRemoteAddr());//用户的终端IP
        packageParams.put("notify_url", WXConfig.notify_url);//通知地址
        packageParams.put("body", one.getOrderDesc());//商品描述
        packageParams.put("out_trade_no", one.getOrderShowId());//商户订单号
        packageParams.put("openid", one.getOpenId());//用户的openid
        packageParams.put("trade_type", WXConfig.trade_type);

        //获取签名
        String sign = PayCommonUtil.createSign("UTF-8", packageParams, WXConfig.paternerKey);
        log.info("sign = " + sign);

        //封装签名
        packageParams.put("sign", sign);
        String requestXML = PayCommonUtil.getRequestXml(packageParams);
        log.info(requestXML);
        String resXml = HttpUtil.postData(WXConfig.url, requestXML);
        Map xmlMap = null;
        try {
            xmlMap = XMLUtil.doXMLParse(resXml);
        } catch (JDOMException e) {
            e.printStackTrace();
            log.info("xmlMap出现JDOMException异常");
        } catch (IOException e) {
            e.printStackTrace();
            log.info("xmlMap出现IOException异常");
        }
        log.info("xmlMap = " + xmlMap);

        String returnCode = (String) xmlMap.get("return_code");
        String returnMsg = (String) xmlMap.get("return_msg");
        SortedMap<Object, Object> finalpackage = null;
        if ("SUCCESS".equals(returnCode)) {
            String resultCode = (String) xmlMap.get("result_code");
            String errCodeDes = (String) xmlMap.get("err_code_des");
            if ("SUCCESS".equals(resultCode)) {
                //获取预支付交易会话标识
                String prepayId = (String) xmlMap.get("prepay_id");
                log.info("prepay_id = " + prepayId);
                //把prepay_id保存到订单中
                one.setPrepayId(prepayId);
                goodsOrderDao.save(one);
                //字符串
                String nonceStr = packageParams.get("nonce_str").toString();
                // 订单详情扩展字符串
                String packages = "prepay_id=" + prepayId;
                finalpackage = new TreeMap<Object, Object>();
                finalpackage.put("appId", WXConfig.appid);
                finalpackage.put("timeStamp", WXConfig.timeStamp);
                finalpackage.put("nonceStr", nonceStr);
                finalpackage.put("package", packages);
                finalpackage.put("signType", WXConfig.signType);
                sign = PayCommonUtil.createSign("UTF-8", finalpackage, WXConfig.paternerKey);
                finalpackage.put("paySign", sign);

                finalpackage.put("orderNo", one.getOrderShowId());
                finalpackage.put("totalFee", totalFee);
                log.info("finalpackage = " + finalpackage);
            }
        }
        return finalpackage;
    }

    @Override
    public void wxPayBack(HttpServletResponse response, HttpServletRequest request) throws IOException, JDOMException{
        // 读取参数
        InputStream inputStream = request.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String s = in.readLine();
        log.info("in.toString() ={} ", in.toString());
        StringBuffer sb = new StringBuffer();
        while (s != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        log.info("sb = " + sb);
        // 解析xml成map
        Map<String, String> map = XMLUtil.doXMLParse(sb.toString());
        log.info("解析xml成map={}", map);

        Iterator it = map.keySet().iterator();
        // 过滤空 设置 TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = map.get(parameter);

            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        // 账号信息
        String key = WXConfig.paternerKey; // key
        // 判断签名是否正确
        if (PayCommonUtil.isTenpaySign("UTF-8", packageParams, key)) {
            log.info("微信支付成功回调");
            // 处理业务开始
            String resXml = "";
            if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
                /**
                 * 这里是支付成功
                 * 这里 根据实际业务场景 做相应的操作
                 * 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了
                 */
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                String openId = (String) packageParams.get("openid");
                //微信支付订单号
                String transactionId = (String) packageParams.get("transaction_id");
                //成功完成支付,修改订单状态,保存微信支付订单号
                GoodsOrder goodsOrder = goodsOrderDao.findByOpenId(openId);

                //保存商品订单信息
                if (goodsOrder != null){
                    goodsOrder.setTransactionId(transactionId);
                    String shippingType = goodsOrderShippingDao.findOne(goodsOrder.getShippingId()).getShippingType();
                    if ("0".equals(shippingType)){
                        //物流方式为物流配送
                        goodsOrder.setOrderStatus("2");
                    }
                    if ("1".equals(shippingType)){
                        //物流方式为自提
                        goodsOrder.setOrderStatus("3");
                    }
                    goodsOrder.setOrderPayTime(new Date());
                    goodsOrderDao.save(goodsOrder);
                }
            } else {
                log.info("支付失败,错误信息：{}", packageParams.get("err_code"));
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            // ------------------------------
            // 处理业务完毕
            // ------------------------------
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } else {
            log.info("通知签名验证失败");
        }
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
