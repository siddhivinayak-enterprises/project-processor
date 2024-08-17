package com.sk.projectprocessor.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class TaskInfo implements Comparable<TaskInfo>, Cloneable, Serializable {
    String name;
    Integer order;
    HashMap<String, String> params;
    String status;
    String startTime;
    String finishTime;
    boolean ignore;
    HashMap<String, String> others;

    boolean exception;

    String exceptionDetail;
    boolean completed;
    Integer taskRetry;
    public String getName() {
        return name;
    }

    public TaskInfo setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getOrder() {
        return order;
    }

    public TaskInfo setOrder(Integer order) {
        this.order = order;
        return this;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public TaskInfo setParams(HashMap<String, String> params) {
        this.params = params;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TaskInfo setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public TaskInfo setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public TaskInfo setFinishTime(String finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public TaskInfo setIgnore(boolean ignore) {
        this.ignore = ignore;
        return this;
    }

    public HashMap<String, String> getOthers() {
        return others;
    }

    public TaskInfo setOthers(HashMap<String, String> others) {
        this.others = others;
        return this;
    }

    public boolean isException() {
        return exception;
    }

    public TaskInfo setException(boolean exception) {
        this.exception = exception;
        return this;
    }

    public String getExceptionDetail() {
        return exceptionDetail;
    }

    public TaskInfo setExceptionDetail(String exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
        return this;
    }

    public boolean isCompleted() {
        return completed;
    }

    public TaskInfo setCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }

    public Integer getTaskRetry() {
        return taskRetry;
    }

    public TaskInfo setTaskRetry(Integer taskRetry) {
        this.taskRetry = taskRetry;
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
        TaskInfo taskInfo = (TaskInfo) o;
        return Objects.equals(name, taskInfo.name) && Objects.equals(order, taskInfo.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, order);
    }

    @Override
    public int compareTo(TaskInfo taskInfo) {
        return order - taskInfo.order;
    }

    @Override
    public TaskInfo clone() {
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.name = name;
        taskInfo.order = order;
        taskInfo.params = null != params ? (HashMap<String, String>) params.clone() : null;
        taskInfo.status = status;
        taskInfo.startTime = startTime;
        taskInfo.finishTime = finishTime;
        taskInfo.ignore = ignore;
        taskInfo.others = null != others ? (HashMap<String, String>) others.clone() : null;
        taskInfo.exception = exception;
        taskInfo.exceptionDetail = exceptionDetail;
        taskInfo.completed = completed;
        taskInfo.taskRetry = taskRetry;
        return taskInfo;
    }
}
