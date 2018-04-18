package com.cootf.log4droid.config;

import com.cootf.log4droid.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration   //标注此文件为一个配置项，spring boot才会扫描到该配置。该注解类似于之前使用xml进行配置
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/wx/index",
                        "/wx/authwx"
                ); // 对来自/user/** 这个链接来的请求进行拦截
    }
}