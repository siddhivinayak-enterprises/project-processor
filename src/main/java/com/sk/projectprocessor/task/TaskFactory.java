package com.sk.projectprocessor.task;

import com.sk.projectprocessor.config.ParentProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Component
public class TaskFactory {

    @Autowired
    ParentProperties parentProperties;

    public Task createTask(String name) {
        try {
            Class taskClass = Class.forName(parentProperties.getTasks().get(name));
            Constructor taskConstructor = taskClass.getDeclaredConstructor();
            return (Task) taskConstructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
                 | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
