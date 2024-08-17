package com.sk.projectprocessor.util;

import com.sk.projectprocessor.model.ParentInfo;
import com.sk.projectprocessor.model.ProjectInfo;
import com.sk.projectprocessor.model.StatusConstants;
import com.sk.projectprocessor.model.TaskInfo;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ProjectInfoUtil {

    public static ProjectInfo byName(String projectName, TreeSet<ProjectInfo> projects) {
        return projects
                .stream()
                .filter(projectInfo -> projectInfo.getName().equals(projectName))
                .findAny()
                .orElseThrow();
    }

    public static Set<ProjectInfo> byNames(Set<String> projectNames, TreeSet<ProjectInfo> projects) {
        return projects
                .stream()
                .filter(projectInfo -> projectNames.contains(projectInfo.getName()))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public static void setProjectInfoInitialStatus(ProjectInfo projectInfo) {
        if(null != projectInfo.getDependencies() && !projectInfo.getDependencies().isEmpty()) {
            projectInfo.setStatus(StatusConstants.PROJECT_WAITING);
        } else {
            projectInfo.setStatus(StatusConstants.PROJECT_YET_TO_START);
        }
        if(projectInfo.isIgnore()) {
            projectInfo.setStatus(StatusConstants.PROJECT_IGNORED);
        }
        if(null != projectInfo.getTasks()) {
            for(TaskInfo taskInfo: projectInfo.getTasks()) {
                taskInfo.setStatus(StatusConstants.TASK_YET_TO_START);
                if(projectInfo.isIgnore()) {
                    taskInfo.setIgnore(true);
                }
                if(taskInfo.isIgnore()) {
                    taskInfo.setStatus(StatusConstants.TASK_IGNORED);
                }
            }
        }
    }

    public static boolean isAbleToRun(ProjectInfo projectInfo) {
        return !(projectInfo.isIgnore()
           || projectInfo.isCompleted()
           || projectInfo.isException()
           || projectInfo.isForceStop()
           || projectInfo.isProcessing()
           || null == projectInfo.getTasks());
    }

    public static void changeStatus(ProjectInfo projectInfo) {
        if(null != projectInfo.getTasks()) {
            for(TaskInfo taskInfo: projectInfo.getTasks()) {
                if(!taskInfo.isCompleted() && !taskInfo.isIgnore()) {
                    projectInfo.setCompleted(false);
                }
                if(taskInfo.isException() && !taskInfo.isIgnore()) {
                    projectInfo.setException(true);
                    projectInfo.setExceptionDetail("Exception in task: " + taskInfo.getName());
                }
            }
        }
        if(projectInfo.isForceStop()) {
            projectInfo.setStatus(StatusConstants.PROJECT_FORCE_STOP);
        }
        if(projectInfo.isException()) {
            projectInfo.setStatus(StatusConstants.PROJECT_EXCEPTION);
        }
        if(projectInfo.isCompleted()) {
            projectInfo.setStatus(StatusConstants.PROJECT_COMPLETED);
        }
        if(projectInfo.isProcessing()) {
            projectInfo.setStatus(StatusConstants.PROJECT_IN_PROGRESS);
        }
    }

    public static boolean checkProjectInfoEligibility(ProjectInfo projectInfo, ParentInfo parentInfo) {
        boolean isReady = true;
        if(null != projectInfo.getDependencies()) {
            Set<ProjectInfo> dependencies = ProjectInfoUtil.byNames(projectInfo.getDependencies(), parentInfo.getProjects());
            for(ProjectInfo temp: dependencies) {
                if(!temp.isCompleted()) {
                    projectInfo.setStatus(StatusConstants.PROJECT_WAITING);
                    isReady = false;
                }
                if(temp.isException()) {
                    projectInfo.setStatus(StatusConstants.PROJECT_REJECTED);
                    isReady = false;
                }
            }
        }
        return isReady && ProjectInfoUtil.isAbleToRun(projectInfo);
    }
}
