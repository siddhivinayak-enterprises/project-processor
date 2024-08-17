package com.sk.projectprocessor.processor;

import com.sk.projectprocessor.config.ParentProperties;
import com.sk.projectprocessor.model.ParentInfo;
import com.sk.projectprocessor.model.ProjectInfo;
import com.sk.projectprocessor.model.StatusConstants;
import com.sk.projectprocessor.model.TaskInfo;
import com.sk.projectprocessor.util.ProjectInfoUtil;
import com.sk.projectprocessor.util.XmlUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ProcessorContext {

    @Autowired
    ParentProperties parentProperties;

    ParentInfo parentInfo;

    public ParentInfo getParentInfo() {
        return parentInfo;
    }

    public ProcessorContext setParentInfo(ParentInfo parentInfo) {
        this.parentInfo = parentInfo;
        return this;
    }

    @PostConstruct
    void sanitize() {
        enhanceParentInfo(parentProperties.getParent());
        parentInfo = XmlUtil.read(parentProperties.getParent());
        XmlUtil.registerUpdateStatusJob(parentInfo);
    }

    void enhanceParentInfo(ParentInfo parentInfo) {
        if(null != parentInfo.getProjects()) {
            for(ProjectInfo projectInfo: parentInfo.getProjects()) {
                if(null == projectInfo.getLocalDir() || projectInfo.getLocalDir().isEmpty()) {
                    projectInfo.setLocalDir(parentInfo.getLocalDir() + File.separator + projectInfo.getName());
                }
                if(null == projectInfo.getRunId() || projectInfo.getRunId().isEmpty()) {
                    projectInfo.setRunId(parentInfo.getRunId());
                }
                if(null == projectInfo.getToken() || projectInfo.getToken().isEmpty()) {
                    projectInfo.setToken(parentInfo.getToken());
                }
                if(null == projectInfo.getSource() || projectInfo.getSource().isEmpty()) {
                    projectInfo.setSource(parentInfo.getSource());
                }
                if(null == projectInfo.getWork() || projectInfo.getWork().isEmpty()) {
                    projectInfo.setWork(parentInfo.getWork());
                }
                if(null == projectInfo.getTarget() || projectInfo.getTarget().isEmpty()) {
                    projectInfo.setTarget(parentInfo.getTarget());
                }
                if(null == projectInfo.getRepoUrl() || projectInfo.getRepoUrl().isEmpty()) {
                    projectInfo.setRepoUrl(parentInfo.getRepoUrl());
                }
                if(null == projectInfo.getBaseUrl() || projectInfo.getBaseUrl().isEmpty()) {
                    projectInfo.setBaseUrl(parentInfo.getBaseUrl());
                }
                if(null == projectInfo.getTaskRetry() && null != parentInfo.getTaskRetry()) {
                    projectInfo.setTaskRetry(parentInfo.getTaskRetry());
                }
                if(null == projectInfo.getUrl()) {
                    projectInfo.setUrl(projectInfo.getBaseUrl() + projectInfo.getName());
                }
                if(null != parentInfo.getTasks()) {
                    TreeSet parentTasks = parentInfo.getTasks().stream().map(TaskInfo::clone).collect(Collectors.toCollection(TreeSet::new));
                    if(null == projectInfo.getTasks()) {
                        projectInfo.setTasks(parentTasks);
                    } else {
                        projectInfo.getTasks().addAll(parentTasks);
                    }
                    if(null != projectInfo.getTaskRetry()) {
                        for(TaskInfo taskInfo: projectInfo.getTasks()) {
                            if(null == taskInfo.getTaskRetry()) {
                                taskInfo.setTaskRetry(projectInfo.getTaskRetry());
                            }
                        }
                    }
                }
                if(null != parentInfo.getOthers()) {
                    if(null == projectInfo.getOthers()) {
                        projectInfo.setOthers((HashMap<String, String>) parentInfo.getOthers().clone());
                    } else {
                        projectInfo.getOthers().putAll(parentInfo.getOthers());
                    }
                }
                ProjectInfoUtil.setProjectInfoInitialStatus(projectInfo);
            }
            if(parentInfo.isSortProject()) {
                setOrderProjectInfo(parentInfo);
            }
        }
    }

    void setOrderProjectInfo(ParentInfo parentInfo) {
        TreeSet<ProjectInfo> projects = parentInfo.getProjects();
        projects
                .stream()
                .flatMap(project ->
                    null != project.getDependencies() ?
                    project.getDependencies().stream() :
                            Stream.empty()
                ).distinct()
                .forEach(dependency -> {
                    for(ProjectInfo project: projects) {
                        if(null != project.getDependencies() && project.getDependencies().contains(dependency)) {
                            ProjectInfo parentProject = ProjectInfoUtil.byName(dependency, projects);
                            int highestOrder = parentProject.getOrder() > project.getOrder() ? parentProject.getOrder() : project.getOrder();
                            project.setOrder(highestOrder + 1);
                        }
                    }
                });
    }
}
