package org.jeecg.modules.flowable.config;

import lombok.RequiredArgsConstructor;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEventDispatcher;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.jeecg.modules.flowable.listener.TaskCompleteListener;
import org.jeecg.modules.flowable.listener.TaskCreateListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Flowable添加全局监听器
 *
 * @author azhuzhu
 */
@Configuration
@RequiredArgsConstructor
public class FlowableGlobalListenerConfig implements ApplicationListener<ContextRefreshedEvent> {

    private final SpringProcessEngineConfiguration configuration;

    private final TaskCreateListener taskCreateListener;
    private final TaskCompleteListener taskCompleteListener;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        FlowableEventDispatcher dispatcher = configuration.getEventDispatcher();
        // 任务创建全局监听-待办消息发送
        dispatcher.addEventListener(taskCreateListener, FlowableEngineEventType.TASK_CREATED);
        // 任务完成全局监听-分配下一个节点
        dispatcher.addEventListener(taskCompleteListener, FlowableEngineEventType.TASK_COMPLETED);
    }

}

