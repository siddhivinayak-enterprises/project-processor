package com.sk.projectprocessor.model;

import java.io.Serializable;
import java.util.TreeSet;
import java.util.HashMap;

public class ParentInfo implements Cloneable, Serializable {

    String localDir;
    String runId;
    String token;
    String source;
    String work;
    String target;
    String repoUrl;
    String baseUrl;
    TreeSet<TaskInfo> tasks;
    TreeSet<ProjectInfo> projects;

    HashMap<String, String> others;

    boolean sortProject = true;

    Integer taskRetry;

    public String getLocalDir() {
        return localDir;
    }

    public ParentInfo setLocalDir(String localDir) {
        this.localDir = localDir;
        return this;
    }

    public String getRunId() {
        return runId;
    }

    public ParentInfo setRunId(String runId) {
        this.runId = runId;
        return this;
    }

    public String getToken() {
        return token;
    }

    public ParentInfo setToken(String token) {
        this.token = token;
        return this;
    }

    public String getSource() {
        return source;
    }

    public ParentInfo setSource(String source) {
        this.source = source;
        return this;
    }

    public String getWork() {
        return work;
    }

    public ParentInfo setWork(String work) {
        this.work = work;
        return this;
    }

    public String getTarget() {
        return target;
    }

    public ParentInfo setTarget(String target) {
        this.target = target;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public ParentInfo setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
        return this;
    }

    public ParentInfo setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public TreeSet<TaskInfo> getTasks() {
        return tasks;
    }

    public ParentInfo setTasks(TreeSet<TaskInfo> tasks) {
        this.tasks = tasks;
        return this;
    }

    public HashMap<String, String> getOthers() {
        return others;
    }

    public ParentInfo setOthers(HashMap<String, String> others) {
        this.others = others;
        return this;
    }

    public TreeSet<ProjectInfo> getProjects() {
        return projects;
    }

    public ParentInfo setProjects(TreeSet<ProjectInfo> projects) {
        this.projects = projects;
        return this;
    }

    public boolean isSortProject() {
        return sortProject;
    }

    public ParentInfo setSortProject(boolean sortProject) {
        this.sortProject = sortProject;
        return this;
    }

    public Integer getTaskRetry() {
        return taskRetry;
    }

    public ParentInfo setTaskRetry(Integer taskRetry) {
        this.taskRetry = taskRetry;
        return this;
    }

    @Override
    public ParentInfo clone() {
        ParentInfo parentInfo = new ParentInfo();
        parentInfo.localDir = localDir;
        parentInfo.runId = runId;
        parentInfo.token = token;
        parentInfo.source = source;
        parentInfo.work = work;
        parentInfo.target = target;
        parentInfo.repoUrl = repoUrl;
        parentInfo.baseUrl = baseUrl;
        parentInfo.tasks = null != tasks ? (TreeSet<TaskInfo>) tasks.clone() : null;
        parentInfo.projects = null != projects ? (TreeSet<ProjectInfo>) projects.clone() : null;
        parentInfo.others = null != others ? (HashMap<String, String>) others.clone() : null;
        parentInfo.sortProject = sortProject;
        parentInfo.taskRetry = taskRetry;
        return parentInfo;
    }
}
