package com.acron.demo.core.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Acron
 * @ClassName OptLockerConfig
 * @Description MyabtisPlus乐观锁配置
 * @since 2019/07/07 21:25
 */
@Configuration
public class OptLockerConfig {
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }
}
