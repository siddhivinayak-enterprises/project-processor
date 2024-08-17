package com.sk.projectprocessor.service;

import com.sk.projectprocessor.model.ParentInfo;

public interface IProjectProcessorService {

    ParentInfo status();
    ParentInfo start(String projectName);
    ParentInfo stop(String projectName);

}
