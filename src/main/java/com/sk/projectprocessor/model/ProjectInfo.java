package com.sk.projectprocessor.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeSet;

public class ProjectInfo implements Cloneable, Serializable, Comparable<ProjectInfo> {

    String name;
    String localDir;
    String runId;
    String token;
    String source;
    String work;
    String target;
    String repoUrl;
    String baseUrl;
    TreeSet<TaskInfo> tasks;
    TreeSet<String> dependencies;
    Integer order = 0;
    String status;
    String startTime;
    String finishTime;
    boolean ignore;

    HashMap<String, String> others;

    boolean exception;

    String exceptionDetail;

    boolean completed;
    Integer taskRetry;

    boolean forceStop;

    boolean processing;

    int processPercentage;

    String url;

    public String getName() {
        return name;
    }

    public ProjectInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getLocalDir() {
        return localDir;
    }

    public ProjectInfo setLocalDir(String localDir) {
        this.localDir = localDir;
        return this;
    }

    public String getRunId() {
        return runId;
    }

    public ProjectInfo setRunId(String runId) {
        this.runId = runId;
        return this;
    }

    public String getToken() {
        return token;
    }

    public ProjectInfo setToken(String token) {
        this.token = token;
        return this;
    }

    public String getSource() {
        return source;
    }

    public ProjectInfo setSource(String source) {
        this.source = source;
        return this;
    }

    public String getWork() {
        return work;
    }

    public ProjectInfo setWork(String work) {
        this.work = work;
        return this;
    }

    public String getTarget() {
        return target;
    }

    public ProjectInfo setTarget(String target) {
        this.target = target;
        return this;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public ProjectInfo setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public ProjectInfo setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public TreeSet<TaskInfo> getTasks() {
        return tasks;
    }

    public ProjectInfo setTasks(TreeSet<TaskInfo> tasks) {
        this.tasks = tasks;
        return this;
    }

    public TreeSet<String> getDependencies() {
        return dependencies;
    }

    public ProjectInfo setDependencies(TreeSet<String> dependencies) {
        this.dependencies = dependencies;
        return this;
    }

    public Integer getOrder() {
        return order;
    }

    public ProjectInfo setOrder(Integer order) {
        this.order = order;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ProjectInfo setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public ProjectInfo setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public ProjectInfo setFinishTime(String finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public ProjectInfo setIgnore(boolean ignore) {
        this.ignore = ignore;
        return this;
    }

    public HashMap<String, String> getOthers() {
        return others;
    }

    public ProjectInfo setOthers(HashMap<String, String> others) {
        this.others = others;
        return this;
    }

    public boolean isException() {
        return exception;
    }

    public ProjectInfo setException(boolean exception) {
        this.exception = exception;
        return this;
    }

    public String getExceptionDetail() {
        return exceptionDetail;
    }

    public ProjectInfo setExceptionDetail(String exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
        return this;
    }

    public boolean isCompleted() {
        if(null != tasks) {
            completed = !tasks.stream().anyMatch(taskInfo -> !taskInfo.completed);
        }
        return completed;
    }

    public ProjectInfo setCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }

    public Integer getTaskRetry() {
        return taskRetry;
    }

    public ProjectInfo setTaskRetry(Integer taskRetry) {
        this.taskRetry = taskRetry;
        return this;
    }

    public boolean isForceStop() {
        return forceStop;
    }

    public ProjectInfo setForceStop(boolean forceStop) {
        this.forceStop = forceStop;
        return this;
    }

    public boolean isProcessing() {
        return processing;
    }

    public ProjectInfo setProcessing(boolean processing) {
        this.processing = processing;
        return this;
    }

    public int getProcessPercentage() {
        return processPercentage;
    }

    public ProjectInfo setProcessPercentage(int processPercentage) {
        this.processPercentage = processPercentage;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ProjectInfo setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectInfo that = (ProjectInfo) o;
        return Objects.equals(name, that.name) && Objects.equals(runId, that.runId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, runId);
    }

    @Override
    public ProjectInfo clone() {
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.name = name;
        projectInfo.localDir = localDir;
        projectInfo.runId = runId;
        projectInfo.token = token;
        projectInfo.source = source;
        projectInfo.work = work;
        projectInfo.target = target;
        projectInfo.repoUrl = repoUrl;
        projectInfo.baseUrl = baseUrl;
        projectInfo.tasks = null != tasks ? (TreeSet<TaskInfo>) tasks.clone() : null;
        projectInfo.dependencies = null != dependencies ? (TreeSet<String>) dependencies.clone() : null;
        projectInfo.order = order;
        projectInfo.status = status;
        projectInfo.startTime = startTime;
        projectInfo.finishTime = finishTime;
        projectInfo.ignore = ignore;
        projectInfo.others = null != others ? (HashMap<String, String>) others.clone() : null;
        projectInfo.exception = exception;
        projectInfo.exceptionDetail = exceptionDetail;
        projectInfo.completed = completed;
        projectInfo.taskRetry = taskRetry;
        projectInfo.forceStop = forceStop;
        projectInfo.processing = processing;
        projectInfo.processPercentage = processPercentage;
        projectInfo.url = url;
        return projectInfo;
    }

    @Override
    public int compareTo(ProjectInfo projectInfo) {
        return name.compareTo(projectInfo.name);
    }
}
