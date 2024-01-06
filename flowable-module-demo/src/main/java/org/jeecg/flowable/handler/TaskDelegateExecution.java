package org.jeecg.flowable.handler;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * 用于在流程执行到 Service Task 时执行自定义的 Java 代码。
 */
public class TaskDelegateExecution implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        // 执行你的业务逻辑，可以通过 DelegateExecution 获取流程变量等信息
        String approval = (String) execution.getVariable("approval");
        System.out.println("Executing JavaDelegate for approval: " + approval);

        // 在这里可以执行更多的业务逻辑
    }

}
