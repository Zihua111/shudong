package com.jumbox.demo.controller;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Description: 继承SpringBootServletInitializer，相当于使用web.xml的形式去启动部署
 */
public class WarStartApplication extends SpringBootServletInitializer {

    /**
     * 重写配置 --> configure
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 使用web.xml运行应用程序，指向DemoControllerApplication，最后启动springboot
        return builder.sources(DemoControllerApplication.class);
    }

}
