package org.jeecg.flowable.service.impl;
import org.flowable.task.api.Task;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.jeecg.flowable.service.MyFirstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MyFirstServiceImpl implements MyFirstService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Transactional
    public void startProcess() {
        runtimeService.startProcessInstanceByKey("oneTaskProcess");
    }

    @Transactional
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }
}
