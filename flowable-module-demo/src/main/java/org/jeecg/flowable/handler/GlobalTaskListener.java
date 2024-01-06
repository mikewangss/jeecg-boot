package org.jeecg.flowable.handler;

import liquibase.util.StringUtil;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.jeecg.flowable.util.SpringContextUtil;

import java.util.HashMap;

/**
 * 用于监听与任务（User Task 或 Service Task 等）相关的事件。
 * 它允许你在任务创建、完成等事件发生时执行自定义逻辑。
 */

public class GlobalTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        String assginee = delegateTask.getAssignee();
        // 在这里编写任务监听器的逻辑
        String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
        if ("directorTak".equals(taskDefinitionKey)) {
            assginee = "Jeff";
        } else if ("bossTask".equals(taskDefinitionKey)) {
            assginee = "Tom";
        } else if ("fillTask".equals(taskDefinitionKey)) {
            assginee = "wangwei2";
        }
        //解决任务完成但是表act_hi_taskinst中assignee不更新问题
        if (StringUtil.isNotEmpty(assginee)) {
            TaskService taskService = SpringContextUtil.getBean(TaskService.class);
            taskService.setAssignee(delegateTask.getId(), assginee);
        }
        String eventName = delegateTask.getEventName();
        // 创建事件，流程需要先定义监听器方法
        if ("create".equals(eventName)) {
            System.out.println("GlobalTaskListener开始。。。。。");
            if (StringUtil.isNotEmpty(assginee)) {
                delegateTask.setAssignee(assginee);
                HashMap<String, Object> variables = new HashMap<>();
                variables.put("assignee", assginee);
                delegateTask.setVariablesLocal(variables);
            }
        } else if ("complete".equals(eventName)) {
            System.out.println("GlobalTaskListener-complete。。。。。");
        }

    }

}