package com.wen.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.wen.application.springsession.SpringSessionHttpConfig;

/**
 * 管理系统配置
 * 
 * @author yanwen
 * 
 */
@Configuration
@Import({SpringSessionHttpConfig.class})
public class Webconfig {}
