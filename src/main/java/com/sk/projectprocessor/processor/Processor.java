package com.sk.projectprocessor.processor;

import com.sk.projectprocessor.model.ParentInfo;

public interface Processor {
    boolean process(String projectName, ParentInfo parentInfo);

    boolean stop(String projectName, ParentInfo parentInfo);
}
