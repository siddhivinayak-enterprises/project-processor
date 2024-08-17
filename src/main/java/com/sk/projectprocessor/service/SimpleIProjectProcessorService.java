package com.sk.projectprocessor.service;

import com.sk.projectprocessor.config.SimpleProcessorProperties;
import com.sk.projectprocessor.model.ParentInfo;
import com.sk.projectprocessor.processor.Processor;
import com.sk.projectprocessor.processor.ProcessorContext;
import com.sk.projectprocessor.processor.SimpleProcessor;
import com.sk.projectprocessor.task.TaskFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleIProjectProcessorService implements IProjectProcessorService {

    @Autowired
    ProcessorContext processorContext;

    @Autowired
    TaskFactory taskFactory;

    @Autowired
    SimpleProcessorProperties simpleProcessorProperties;

    Processor processor;

    @Override
    public ParentInfo status() {
        return processorContext.getParentInfo();
    }

    @Override
    public ParentInfo start(String projectName) {
        if(null == processor) {
            processor = new SimpleProcessor(simpleProcessorProperties, taskFactory);
        }
        processor.process(projectName, processorContext.getParentInfo());
        return processorContext.getParentInfo();
    }

    @Override
    public ParentInfo stop(String projectName) {
        if(null == processor) {
            processor = new SimpleProcessor(simpleProcessorProperties, taskFactory);
        }
        processor.stop(projectName, processorContext.getParentInfo());
        return processorContext.getParentInfo();
    }
}
