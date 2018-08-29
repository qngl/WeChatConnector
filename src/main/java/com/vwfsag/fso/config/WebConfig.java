package com.vwfsag.fso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.vwfsag.fso.view.interceptor.ContentInterceptor;
import com.vwfsag.fso.view.interceptor.SecurityInterceptor;

/**
 * All the web app configuration happens here
 * 
 * @author liqiang
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.vwfsag.fso.view.controller")
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxUploadSize(10 * 1000 * 1000);
		return resolver;
	}

	@Bean
	public SecurityInterceptor securityInterceptor() {
		return new SecurityInterceptor();
	}

	@Bean
	public ContentInterceptor contentInterceptor() {
		return new ContentInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(securityInterceptor());
		registry.addInterceptor(contentInterceptor());
	}

}
