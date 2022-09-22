package com.wen.application.springsession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.util.StringUtils;

@EnableRedisHttpSession(redisNamespace = "testSpringBootSession",maxInactiveIntervalInSeconds=100)
public class SpringSessionHttpConfig {
    private static final Logger logger = LoggerFactory.getLogger(SpringSessionHttpConfig.class);
    @Autowired
    RedisOperationsSessionRepository messageListener;
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
    @EventListener
    public void onSessionExpired(SessionExpiredEvent expiredEvent) {
        System.out.println("session expire:"+expiredEvent.getSessionId());
        if(!StringUtils.isEmpty(expiredEvent.getSessionId())){
            messageListener.delete(expiredEvent.getSessionId());
        }
    }

    @EventListener
    public void onSessionDeleted(SessionDeletedEvent deletedEvent) {
        System.out.println("session被销毁"+deletedEvent.getSessionId());
        if(!StringUtils.isEmpty(deletedEvent.getSessionId())){
            messageListener.delete(deletedEvent.getSessionId());
        }
    }
    @EventListener
    public void onSessionCreate(SessionCreatedEvent createdEvent) {
        System.out.println("session 创建"+createdEvent.getSessionId());
    }
}
