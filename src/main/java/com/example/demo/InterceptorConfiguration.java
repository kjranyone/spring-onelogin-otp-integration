package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.MappedInterceptor;

/**
 * Created by usr0200505 on 2017/09/19.
 */
@Configuration
public class InterceptorConfiguration {

    @Bean
    public OneLoginInterceptor oneLoginInterceptor() {
        return new OneLoginInterceptor();
    }

    @Bean
    public MappedInterceptor interceptor() {
        return new MappedInterceptor(new String[]{"/security/**"}, oneLoginInterceptor());
    }
}
