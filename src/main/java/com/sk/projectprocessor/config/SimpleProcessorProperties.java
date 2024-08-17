package com.sk.projectprocessor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(ConfigConstants.SIMPLE_PROCESSOR_PREFIX)
public class SimpleProcessorProperties {

    Integer size;

    public Integer getSize() {
        return size;
    }

    public SimpleProcessorProperties setSize(Integer size) {
        this.size = size;
        return this;
    }
}
