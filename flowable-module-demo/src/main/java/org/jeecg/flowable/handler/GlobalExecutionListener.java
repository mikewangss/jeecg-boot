package org.jeecg.flowable.handler;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;

/**
 * 用于监听与执行流程实例（Process Instance）相关的事件。
 * 它可以监听流程的开始、结束、节点的开始、结束等事件。
 */
public class GlobalExecutionListener  implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) {
        // 在这里编写执行监听器的逻辑
        String eventName = delegateExecution.getEventName();

        if ("start".equals(eventName)) {
            // 流程实例开始时执行的逻辑
        } else if ("end".equals(eventName)) {
            // 流程实例结束时执行的逻辑
        }
        // 其他事件...
    }
}
