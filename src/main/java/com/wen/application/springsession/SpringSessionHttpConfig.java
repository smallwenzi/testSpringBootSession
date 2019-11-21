package com.wen.application.springsession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession(redisNamespace = "testSpringBootSession")
public class SpringSessionHttpConfig {
    private static final Logger logger = LoggerFactory.getLogger(SpringSessionHttpConfig.class);
    @Value("${redis.taskexecutor.corepoolsize}")
    private String corepoolsize;

    @Value("${redis.taskexecutor.maxpoolsize}")
    private String maxpoolsize;

    @Value("${redis.taskexecutor.keepaliveseconds}")
    private String keepaliveseconds;

    @Value("${redis.taskexecutor.queuecapacity}")
    private String queuecapacity;

    @Value("${redis.taskexecutor.threadnameprefix}")
    private String threadnameprefix;

    @Bean
    public ThreadPoolTaskExecutor springSessionRedisTaskExecutor() {
        logger.info("JedisPool注入成功！！");
        ThreadPoolTaskExecutor springSessionRedisTaskExecutor = new ThreadPoolTaskExecutor();
        springSessionRedisTaskExecutor.setCorePoolSize(getRedisTaskexecutorStrToInt(this.corepoolsize, 16));
        springSessionRedisTaskExecutor.setMaxPoolSize(getRedisTaskexecutorStrToInt(this.maxpoolsize, 300));
        springSessionRedisTaskExecutor.setKeepAliveSeconds(getRedisTaskexecutorStrToInt(this.keepaliveseconds, 30));
        springSessionRedisTaskExecutor.setQueueCapacity(getRedisTaskexecutorStrToInt(this.queuecapacity, 500));
        springSessionRedisTaskExecutor.setThreadNamePrefix(this.threadnameprefix);
        return springSessionRedisTaskExecutor;
    }

    private int getRedisTaskexecutorStrToInt(String size, int defaultSize) {
        try {
            int sizeInt = Integer.parseInt(size);
            return sizeInt;
        } catch (Exception e) {
            return defaultSize;
        }
    }
}
