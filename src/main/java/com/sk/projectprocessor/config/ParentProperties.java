package com.sk.projectprocessor.config;

import com.sk.projectprocessor.model.ParentInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties
public class ParentProperties {

    ParentInfo parent;

    Map<String, String> tasks;

    public ParentInfo getParent() {
        return parent;
    }

    public ParentProperties setParent(ParentInfo parent) {
        this.parent = parent;
        return this;
    }

    public Map<String, String> getTasks() {
        return tasks;
    }

    public ParentProperties setTasks(Map<String, String> tasks) {
        this.tasks = tasks;
        return this;
    }
}
