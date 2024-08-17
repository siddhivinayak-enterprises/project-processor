package com.sk.projectprocessor.task;

import com.sk.projectprocessor.model.ParentInfo;
import com.sk.projectprocessor.model.ProjectInfo;
import com.sk.projectprocessor.model.StatusConstants;
import com.sk.projectprocessor.model.TaskInfo;
import com.sk.projectprocessor.processor.SimpleProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class GitTask implements Task {

    Logger logger = LoggerFactory.getLogger(SimpleProcessor.class);

    @Override
    public boolean perform(ParentInfo parentInfo, ProjectInfo projectInfo, TaskInfo taskInfo) {
        taskInfo.setStartTime(Instant.now().toString());
        taskInfo.setStatus(StatusConstants.TASK_STARTED);
        try {
            logger.info("Task " + taskInfo.getName() + " started");
            taskInfo.setStatus(StatusConstants.TASK_IN_PROGRESS);

            Thread.sleep(5000);

            logger.info("Task " + taskInfo.getName() + " completed");
            taskInfo.setFinishTime(Instant.now().toString());
            taskInfo.setStatus(StatusConstants.TASK_COMPLETED);
            taskInfo.setCompleted(true);
            return true;
        } catch (Exception ex) {
            logger.error("Task " + taskInfo.getName() + " generated exception", ex);
            taskInfo.setStatus(StatusConstants.TASK_EXCEPTION);
            taskInfo.setException(true);
            taskInfo.setExceptionDetail(ex.getMessage());
            return false;
        }
    }
}
