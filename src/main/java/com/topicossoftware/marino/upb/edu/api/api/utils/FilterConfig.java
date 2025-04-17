package com.topicossoftware.marino.upb.edu.api.api.utils;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JWTFilter> jwtFilter(JWTFilter jwtFilter) {
        String[] urls = new String[] {"/usuarios/*", "/compradores/*"};
        FilterRegistrationBean<JWTFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtFilter);
        registrationBean.addUrlPatterns(urls);
        registrationBean.setOrder(1);
        return registrationBean;
    }
}