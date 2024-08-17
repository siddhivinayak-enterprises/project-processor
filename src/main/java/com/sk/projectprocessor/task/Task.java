package com.sk.projectprocessor.task;

import com.sk.projectprocessor.model.ParentInfo;
import com.sk.projectprocessor.model.ProjectInfo;
import com.sk.projectprocessor.model.TaskInfo;

public interface Task {
    boolean perform(ParentInfo parentInfo, ProjectInfo projectInfo, TaskInfo taskInfo);
}
