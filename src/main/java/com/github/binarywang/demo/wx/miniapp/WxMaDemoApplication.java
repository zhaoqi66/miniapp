package com.github.binarywang.demo.wx.miniapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages={"com.github.binarywang.demo.wx.miniapp.dao"})
public class WxMaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxMaDemoApplication.class, args);
    }
}
