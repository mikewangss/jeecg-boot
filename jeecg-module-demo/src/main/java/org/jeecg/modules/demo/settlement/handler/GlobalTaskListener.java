package org.jeecg.modules.demo.settlement.handler;

import liquibase.util.StringUtil;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

import java.util.ArrayList;
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
        String eventName = delegateTask.getEventName();
//        delegateTask.setAssignee(assginee);
//        HashMap<String, Object> variables = new HashMap<>();
//        variables.put("assigneeList", new String[]{"wangwei","admin"});
//        String orgCode = (String) delegateTask.getVariable("orgCode");
//        delegateTask.setVariablesLocal(variables);
//        delegateTask.setVariables(variables);



    }

}