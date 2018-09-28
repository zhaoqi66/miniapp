package com.github.binarywang.demo.wx.miniapp.serviceImpl;


import com.github.binarywang.demo.wx.miniapp.config.BusinessException;
import com.github.binarywang.demo.wx.miniapp.controller.vm.ClickVoteVm;
import com.github.binarywang.demo.wx.miniapp.controller.vm.VoteOrderAddVm;
import com.github.binarywang.demo.wx.miniapp.dao.UserApplyDao;
import com.github.binarywang.demo.wx.miniapp.dao.VoteOrderDao;
import com.github.binarywang.demo.wx.miniapp.dao.VoteUserDao;
import com.github.binarywang.demo.wx.miniapp.pojo.UserApply;
import com.github.binarywang.demo.wx.miniapp.pojo.VoteOrder;
import com.github.binarywang.demo.wx.miniapp.pojo.VoteUser;
import com.github.binarywang.demo.wx.miniapp.service.VoteUserService;
import com.github.binarywang.demo.wx.miniapp.wxpayconfig.HttpUtil;
import com.github.binarywang.demo.wx.miniapp.wxpayconfig.PayCommonUtil;
import com.github.binarywang.demo.wx.miniapp.wxpayconfig.WXConfig;
import com.github.binarywang.demo.wx.miniapp.wxpayconfig.XMLUtil;
import lombok.extern.slf4j.Slf4j;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
@Transactional
@Slf4j
public class VoteUserServiceImpl implements VoteUserService {


    @Autowired
    private VoteUserDao voteUserDao;

    @Autowired
    private UserApplyDao userApplyDao;

    @Autowired
    private VoteOrderDao voteOrderDao;


    @Override
    public void save(VoteUser user) {
        voteUserDao.save(user);
    }

    @Override
    public VoteUser findOneByOpenid(String openid) {
        return voteUserDao.findByOpenid(openid);
    }

    @Override
    public void deleteFreeTicketTask() {
        //查询免费票数大于1的投票用户
        List<VoteUser> voteUsers = voteUserDao.findByFreeticketsGreaterThan(0);
        if (voteUsers.size() != 0) {
            for (VoteUser voteUser : voteUsers) {
                voteUser.setFreetickets(0);
                voteUserDao.save(voteUser);
            }
        }
    }

    @Override
    public Map getFreeVote(String openId) {
        if (openId == null) {
            throw new BusinessException("用户的openId必传");
        }
        VoteUser voteUser = voteUserDao.findByOpenid(openId);
        if (voteUser == null) {
            throw new BusinessException("用户的openId无效");

        }
        //封装数据

        Map<String, Object> map = new HashMap<>();
        map.put("id", voteUser.getId());
        map.put("freetickets", voteUser.getFreetickets());
        map.put("totalTickets", voteUser.getFreetickets() + voteUser.getTickets());

        return map;
    }

    @Override
    public void clickVote(ClickVoteVm vm) {
        if (vm.getOpenId() == null) {
            throw new BusinessException("用户的openId必传");
        }
        if (vm.getId() == 0) {
            throw new BusinessException("参赛选手Id必传");
        }
        if (vm.getTickets() == 0) {
            throw new BusinessException("请您给喜欢的选手最少投上1票!");
        }
        UserApply userApply = userApplyDao.findOne(vm.getId());
        if (userApply == null) {
            throw new BusinessException("参赛选手Id无效");
        }
        VoteUser voteUser = voteUserDao.findByOpenid(vm.getOpenId());
        if (voteUser == null) {
            throw new BusinessException("用户的openId无效");

        }

        int totalVote = voteUser.getTickets() + voteUser.getFreetickets();
        if (totalVote > vm.getTickets()) {
            if (voteUser.getFreetickets() == 1) {
                //扣除用户的免费票数和购买票数
                voteUser.setFreetickets(0);
                voteUser.setTickets(voteUser.getTickets() - vm.getTickets() + 1);
                voteUserDao.save(voteUser);
            } else {
                //扣除用户的购买票数
                voteUser.setTickets(voteUser.getTickets() - vm.getTickets());
                voteUserDao.save(voteUser);
            }
        } else {
            throw new BusinessException("用户的余票不足,请购买后给喜欢的选手加油");
        }

        //给参赛选手投票
        userApply.setTotalVotes(userApply.getTotalVotes() + vm.getTickets());
        userApplyDao.save(userApply);

    }

    @Override
    public int createVoteOrder(VoteOrderAddVm vm) {
        //格式化当前时间
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String strDate = sfDate.format(new Date());
        //得到17位时间如：20170411094039080
        log.info("时间17位：strDate={}", strDate);
        //为了防止高并发重复,再获取3个随机数
        String random = getRandom620(3);
        //最后得到20位订单编号。
        log.info("订单号20位：orderId={}" , strDate + random);
        VoteOrder voteOrder = VoteOrder.builder()
                .openId(vm.getOpenId())
                .createTime(new Date())
                .orderId(strDate + random)
                .orderMoney(vm.getOrderMoney())
                .votePoll(vm.getVotePoll())
                .status("0")
                .activityId(vm.getActivityId())
                .orderDesc(vm.getVotePoll() + "票人气充值")
                .build();
        VoteOrder order = voteOrderDao.save(voteOrder);
        return order.getId();
    }

    @Override
    public Map payVoteOrder(int orderId, HttpServletRequest request) {
        if (orderId == 0) {
            throw new BusinessException("订单id不能为空");
        }
        VoteOrder voteOrder = voteOrderDao.findOne(orderId);
        if (voteOrder == null) {
            throw new BusinessException("无效的订单id:" + orderId);
        }
        /**
         * 标价金额  把元转化成分 再转化成字符串
         */
        int total_fee = (int) (voteOrder.getOrderMoney() * 100);


        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        packageParams.put("appid", WXConfig.appid);//微信公众号id
        packageParams.put("mch_id", WXConfig.mch_id);//商户号
        packageParams.put("nonce_str", WXConfig.nonce_str);//随机字符串
        packageParams.put("body", voteOrder.getOrderDesc());//商品描述
        packageParams.put("out_trade_no", voteOrder.getOrderId());//商户订单号
        packageParams.put("total_fee", total_fee);//订单金额
        packageParams.put("spbill_create_ip", request.getRemoteAddr());//用户的终端IP
        packageParams.put("notify_url", WXConfig.notify_url);//通知地址
        packageParams.put("openid", voteOrder.getOpenId());//用户的openid
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
                String prepay_id = (String) xmlMap.get("prepay_id");
                log.info("prepay_id = " + prepay_id);
                //把prepay_id保存到订单中
                voteOrder.setPrepayId(prepay_id);
                voteOrderDao.save(voteOrder);
                //字符串
                String nonceStr = packageParams.get("nonce_str").toString();
                // 订单详情扩展字符串
                String packages = "prepay_id=" + prepay_id;
                finalpackage = new TreeMap<Object, Object>();
                finalpackage.put("appId", WXConfig.appid);
                finalpackage.put("timeStamp", WXConfig.timeStamp);
                finalpackage.put("nonceStr", nonceStr);
                finalpackage.put("package", packages);
                finalpackage.put("signType", WXConfig.signType);
                sign = PayCommonUtil.createSign("UTF-8", finalpackage, WXConfig.paternerKey);
                finalpackage.put("paySign", sign);

                finalpackage.put("orderNo", voteOrder.getOrderId());
                finalpackage.put("totalFee", total_fee);
                log.info("finalpackage = " + finalpackage);
            }
        }
        return finalpackage;


    }

    @Override
    public void getWXPayBack(HttpServletResponse response, HttpServletRequest request) throws IOException, JDOMException {
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
                //增加人气票数，保存微信支付订单号
                VoteOrder voteOrder = voteOrderDao.findByOpenId(openId);
                if (voteOrder != null) {
                    voteOrder.setTransactionId(transactionId);
                    voteOrder.setStatus("1");
                    voteOrderDao.save(voteOrder);
                }
                VoteUser voteUser = voteUserDao.findByOpenid(openId);
                if (voteOrder != null) {
                    voteUser.setTickets(voteUser.getTickets() + voteOrder.getVotePoll());
                    voteUserDao.save(voteUser);
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


    /**
     * 获取6-10 的随机位数数字
     *
     * @param length 想要生成的长度
     * @return result
     */
    public static String getRandom620(Integer length) {
        String result = "";
        Random rand = new Random();
        int n = 20;
        if (null != length && length > 0) {
            n = length;
        }
        int randInt = 0;
        for (int i = 0; i < n; i++) {
            randInt = rand.nextInt(10);
            result += randInt;
        }
        return result;
    }

}
