package com.github.binarywang.demo.wx.miniapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 *
 * @author LT
 * @date 2018/8/29 16:03
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()  // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.github.binarywang.demo.wx.miniapp")) // 对所有api进行监控
                .paths(PathSelectors.any()) // 对所有路径进行监控
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("小程序开发接口文档")
                .description("投票系统")
                .termsOfServiceUrl("http://www.sinopact.com")
                .version("1.0")
                .build();
    }

}
