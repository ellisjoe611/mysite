package com.douzone.mysite.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

// @Configuration
// @PropertySource("classpath:com/douzone/mysite/config/web/properties/fileupload.properties")
public class FileUploadConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	// Multipart Resolver
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxUploadSize(env.getProperty("fileupload.maxUploadSize", Long.class));
		resolver.setMaxInMemorySize(env.getProperty("fileupload.maxInMemorySize", Integer.class));
		resolver.setDefaultEncoding(env.getProperty("fileupload.defaultEncoding"));

		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(env.getProperty("fileupload.resourceMapping")).addResourceLocations("file:" + env.getProperty("fileupload.uploadLocation"));
	}

}
