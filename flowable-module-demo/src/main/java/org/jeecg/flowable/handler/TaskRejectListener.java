//package org.jeecg.flowable.handler;
//import org.flowable.engine.delegate.TaskListener;
//import org.flowable.engine.TaskService;
//import org.flowable.task.api.Task;
//import org.flowable.task.service.delegate.DelegateTask;
//public class TaskRejectListener implements TaskListener {
//    @Override
//    public void notify(DelegateTask delegateTask) {
//        // 判断当前任务是否需要退回
//        if (shouldReject(delegateTask)) {
//            // 标记审批拒绝
//            delegateTask.setVariable("approvalResult", "rejected");
//
//            // 创建新任务并设置为开始节点
//            TaskService taskService = delegateTask.getTaskService();
//            Task newTask = taskService.newTask();
//            newTask.setName("Start Task");
//            newTask.setAssignee(delegateTask.getAssignee());
//            newTask.setTaskDefinitionKey("start"); // 替换为实际的开始节点任务定义键
//            taskService.saveTask(newTask);
//
//            // 完成当前任务
//            taskService.complete(delegateTask.getId());
//
//            // 完成新任务
//            taskService.complete(newTask.getId());
//        }
//    }
//
//    private boolean shouldReject(DelegateTask delegateTask) {
//        // 在这里编写判断是否需要退回的逻辑，例如判断特定的流程变量值等
//        // 返回 true 表示需要退回，否则返回 false
//        return true; // 仅作为示例，根据实际需求修改
//    }
//}
//
