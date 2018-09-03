package com.github.binarywang.demo.wx.miniapp.utils;

import com.github.binarywang.demo.wx.miniapp.config.SystemTime;
import com.github.binarywang.demo.wx.miniapp.domain.PhoneCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * RedisUtil
 *
 * @author juan
 * @date 2018/8/30 14:29
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 创建短信验证码在redis中的缓存
     */
    public  PhoneCode createPhoneCode(String prefix, String phone, Long activeMinutes) {
        //改成以"业务关键词前缀+手机号码"为键值对的key(非随机生成的key)，这样可以有效避免被恶意对同一个手机号码连续点击发送短信验证码 - 存储在redis中做校验
        PhoneCode phoneCode = new PhoneCode(prefix + phone, phone, createRandomVcode(), SystemTime.asDate());
        String key = phoneCode.getPhoneCodeId();
        if (null != get(key)) {
            throw new IllegalArgumentException("您已成功获取短信验证码，请在" + activeMinutes + "分钟之后才可以重新获取！");
        }
        set(key, phoneCode, activeMinutes * 60); //验证码有效时间为activeMinutes分钟

        return phoneCode;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public  boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 随机生成6位随机验证码
     */
    public static String createRandomVcode() {
        //验证码
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int) (Math.random() * 9);
        }
        return vcode;
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }
    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

}
