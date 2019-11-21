package com.wen.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 开发时执行该类的main方法可直接启动项目， 默认采用Spring boot内置的tomcat容器启动应用。
 * 
 * @author yanwen
 * 
 */
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.wen")
public class WebApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebApplication.class, args);
    }
}
