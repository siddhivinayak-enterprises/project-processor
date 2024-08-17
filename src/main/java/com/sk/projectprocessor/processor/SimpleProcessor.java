package com.sk.projectprocessor.processor;

import com.sk.projectprocessor.config.SimpleProcessorProperties;
import com.sk.projectprocessor.model.ParentInfo;
import com.sk.projectprocessor.model.ProjectInfo;
import com.sk.projectprocessor.model.StatusConstants;
import com.sk.projectprocessor.task.Task;
import com.sk.projectprocessor.task.TaskFactory;
import com.sk.projectprocessor.util.ProjectInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(SimpleProcessor.class);

    SimpleProcessorProperties simpleProcessorProperties;

    TaskFactory taskFactory;

    ScheduledExecutorService scheduledExecutorService;

    LinkedBlockingQueue<ProjectInfo> executionPool;

    public SimpleProcessor(SimpleProcessorProperties simpleProcessorProperties, TaskFactory taskFactory) {
        this.simpleProcessorProperties = simpleProcessorProperties;
        this.taskFactory = taskFactory;
    }

    @Override
    public boolean process(String projectName, ParentInfo parentInfo) {
        if(null == executionPool) {
            executionPool = new LinkedBlockingQueue<>(simpleProcessorProperties.getSize());
        }
        if(null == scheduledExecutorService || scheduledExecutorService.isShutdown() || scheduledExecutorService.isTerminated()) {
            scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        }

        if(null != parentInfo.getProjects()) {
            if(null != projectName && !projectName.isEmpty()) {
                ProjectInfo projectInfo = ProjectInfoUtil.byName(projectName, parentInfo.getProjects());
                projectInfo.setForceStop(false);
                projectInfo.setException(false);
                projectInfo.setIgnore(false);
                ProjectInfoUtil.setProjectInfoInitialStatus(projectInfo);
                return executionPool.offer(projectInfo);
            } else {
                scheduledExecutorService.scheduleWithFixedDelay(
                        () -> {
                            logger.debug("Eligible projects being added in pool");
                            parentInfo
                                    .getProjects()
                                    .stream()
                                    .filter(projectInfo -> ProjectInfoUtil.checkProjectInfoEligibility(projectInfo, parentInfo))
                                    .forEach(project -> {
                                        ProjectInfo projectInfo = ProjectInfoUtil.byName(project.getName(), parentInfo.getProjects());
                                        executionPool.offer(projectInfo);
                                    });
                        }, 1, 1, TimeUnit.SECONDS);
            }
        }
        submitJobsFromPool(executionPool, parentInfo);
        return true;
    }

    @Override
    public boolean stop(String projectName, ParentInfo parentInfo) {
        if(null != projectName) {
            ProjectInfo projectInfo = ProjectInfoUtil.byName(projectName, parentInfo.getProjects());
            projectInfo.setForceStop(true);
        } else {
            forceStop();
        }
        return true;
    }

    private void forceStop() {
        scheduledExecutorService.shutdownNow();
    }

    private void submitJobsFromPool(LinkedBlockingQueue<ProjectInfo> executionPool, ParentInfo parentInfo) {
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            if(!executionPool.isEmpty()) {
                ProjectInfo projectInfo = executionPool.poll();
                logger.info("Submit Project: " + projectInfo.getName());
                new ProjectExecutable(projectInfo.getName(), parentInfo).run();
            }
        }, 2, 1, TimeUnit.SECONDS);
    }

    private class ProjectExecutable implements Runnable {

        String projectName;
        ParentInfo parentInfo;

        public ProjectExecutable(String projectName, ParentInfo parentInfo) {
            this.projectName = projectName;
            this.parentInfo = parentInfo;
        }

        @Override
        public void run() {
            ProjectInfo projectInfo = ProjectInfoUtil.byName(projectName, parentInfo.getProjects());
            synchronized (projectInfo) {
                ProjectInfoUtil.changeStatus(projectInfo);
                if(!ProjectInfoUtil.checkProjectInfoEligibility(projectInfo, parentInfo)) return;
                projectInfo.setStartTime(Instant.now().toString());
                projectInfo.setProcessing(true);
                projectInfo.setStatus(StatusConstants.PROJECT_IN_PROGRESS);
                try {
                    int costPerTask = 100 / projectInfo.getTasks().size();
                    AtomicInteger taskCounter = new AtomicInteger();
                    projectInfo.getTasks().stream().filter(taskInfo -> !taskInfo.isIgnore()).forEach(taskInfo -> {
                        Task task = taskFactory.createTask(taskInfo.getName());
                        task.perform(parentInfo, projectInfo, taskInfo);
                        projectInfo.setProcessPercentage(taskCounter.incrementAndGet() * costPerTask);
                    });
                    projectInfo.setProcessing(false);
                    projectInfo.setCompleted(true);
                    projectInfo.setException(false);
                    if(projectInfo.getProcessPercentage() < 100) {
                        projectInfo.setProcessPercentage(100);
                    }
                } catch (Throwable ex) {
                    projectInfo.setProcessing(false);
                    projectInfo.setCompleted(false);
                    projectInfo.setException(true);
                    projectInfo.setExceptionDetail(ex.getMessage());
                }
                ProjectInfoUtil.changeStatus(projectInfo);
                projectInfo.setFinishTime(Instant.now().toString());
            }
        }
    }
}
