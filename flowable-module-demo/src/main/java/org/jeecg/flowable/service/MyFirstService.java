package org.jeecg.flowable.service;

import org.flowable.task.api.Task;

import java.util.List;

public interface MyFirstService {

    public void startProcess() ;

    public List<Task> getTasks(String assignee) ;
}
