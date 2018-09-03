package com.github.binarywang.demo.wx.miniapp.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 阿里云大于短信API产品的DEMO类
 * 执行main函数即可体验短信产品API功能(只需要将AK替换成开通了云通信-短信产品功能的AK即可)
 * 备注:Demo编码采用UTF-8
 * 国际短信发送请勿参照此DEMO
 *
 * @author zhaoke
 *         Created on 2017/11/26.
 */
public class SendSms {
    //产品名称(云通信短信API产品),开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIqFVCQ7tXGOLh";
    static final String accessKeySecret = "fQqRzfue0DjAdqK1x8qNlcGKHtVFMo";

    public static SendSmsResponse send(SendSmsReq req) throws Exception {
        //其中参数类SendSmsReq：phone、signName、templateCode、templateParam四个字段
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(req.getPhone());//必填:待发送手机号
        request.setSignName(req.getSignName());//必填:短信签名-可在短信控制台中找到
        request.setTemplateCode(req.getTemplateCode());//必填:短信模板-可在短信控制台中找到
        request.setTemplateParam(req.getTemplateParam());//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为："{\"name\":\"Tom\", \"code\":\"123\"}"

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");


        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }

    public static QuerySendDetailsResponse query(String bizId) throws Exception {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("13310099269");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }

//    public static void main(String[] args) throws Exception {
//        String name = "caizhaoke";
//        String code = "123456";
//        SendSmsReq req = new SendSmsReq("13310099269", "创学轨迹", "SMS_113461130",
//                "{\"name\":\"" + name + "\", \"code\":\"" + code + "\"}");
//
//        //发短信
//        SendSmsResponse response = send(req);
//        System.out.println("短信接口返回的数据----------------");
//        System.out.println("Code=" + response.getCode());
//        System.out.println("Message=" + response.getMessage());
//        System.out.println("RequestId=" + response.getRequestId());
//        System.out.println("BizId=" + response.getBizId());
//
//        Thread.sleep(3000L);
//
//        //查明细
//        if (response.getCode() != null && response.getCode().equals("OK")) {
//            QuerySendDetailsResponse querySendDetailsResponse = query(response.getBizId());
//            System.out.println("短信明细查询接口返回数据----------------");
//            System.out.println("Code=" + querySendDetailsResponse.getCode());
//            System.out.println("Message=" + querySendDetailsResponse.getMessage());
//            int i = 0;
//            for (QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs()) {
//                System.out.println("SmsSendDetailDTO[" + i + "]:");
//                System.out.println("Content=" + smsSendDetailDTO.getContent());
//                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
//                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
//                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
//                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
//                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
//                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
//                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
//            }
//            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
//            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
//        }
//    }

}
