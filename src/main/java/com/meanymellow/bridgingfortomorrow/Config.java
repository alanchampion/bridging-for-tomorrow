package com.meanymellow.bridgingfortomorrow;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/*
@Configuration
public class Config extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

    public Config(ResourceProperties resourceProperties, WebMvcProperties mvcProperties, ListableBeanFactory beanFactory,
                  ObjectProvider<HttpMessageConverters> messageConvertersProvider,
                  ObjectProvider<WebMvcAutoConfiguration.ResourceHandlerRegistrationCustomizer> resourceHandlerRegistrationCustomizerProvider) {
        super(resourceProperties, mvcProperties, beanFactory, messageConvertersProvider, resourceHandlerRegistrationCustomizerProvider);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/**");
    }
}*/
