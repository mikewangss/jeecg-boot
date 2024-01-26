package org.jeecg.modules.flowable.listener;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.common.engine.api.delegate.event.FlowableEventType;
import org.flowable.common.engine.impl.event.FlowableEntityEventImpl;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.event.impl.FlowableEntityWithVariablesEventImpl;
import org.flowable.engine.impl.util.ProcessDefinitionUtil;
import org.flowable.engine.runtime.Execution;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.task.api.Task;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.jeecg.modules.flowable.apithird.business.entity.FlowMyBusiness;
import org.jeecg.modules.flowable.apithird.business.service.impl.FlowMyBusinessServiceImpl;
import org.jeecg.modules.flowable.apithird.entity.ActStatus;
import org.jeecg.modules.flowable.apithird.entity.SysUser;
import org.jeecg.modules.flowable.domain.dto.FlowNextDto;
import org.jeecg.modules.flowable.flow.FindNextNodeUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局监听-工作流待办消息提醒
 * 项目需要在每个待办Task到达时，发送一个消息提醒用户过来处理。不想在每个工作流单独加监听器，这时候可以使用Flowable的全局监听器
 *
 * @author azhuzhu
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TaskCompleteListener implements FlowableEventListener {

    private final TaskService taskService;
    @Resource
    FlowMyBusinessServiceImpl flowMyBusinessService;
    @Resource
    RuntimeService runtimeService;
    @Resource
    protected RepositoryService repositoryService;

    @Override
    public void onEvent(FlowableEvent flowableEvent) {
        TaskEntity taskEntity = (TaskEntity) ((FlowableEntityWithVariablesEventImpl) flowableEvent).getEntity();
        setNextTask(taskEntity);
    }
    private void setNextTask(TaskEntity taskEntity) {
        //更新flow_my_business,当前节点信息
        String taskId = taskEntity.getId();
        String execuId = taskEntity.getExecutionId();
        Execution execution = runtimeService.createExecutionQuery().executionId(execuId).singleResult();
        String curActId = execution.getActivityId();// 获取流程实例的当前执行节点ID
        Task task = taskService.createTaskQuery().executionId(execuId).singleResult();
        String procDefId = taskEntity.getProcessDefinitionId();
        Process process = ProcessDefinitionUtil.getProcess(procDefId);
//        if (task != null) {
//            List<UserTask> nextUserTask = FindNextNodeUtil.getNextUserTasks(repositoryService, task, taskEntity.getVariables());
//            String dataId = (String) taskEntity.getVariable("dataId");
//            FlowMyBusiness business = flowMyBusinessService.getByDataId(dataId);
//            List<IdentityLink> idList = taskService.getIdentityLinksForTask(taskId);
//            List<String> collect_username = new ArrayList<>();
//        }
//        //遍历整个process,找到endEventId是什么，与当前taskId作对比
//        List<FlowElement> flowElements = (List<FlowElement>) process.getFlowElements();
//        for (FlowElement flowElement : flowElements) {
//            if (flowElement instanceof SequenceFlow) {
//                SequenceFlow flow = (SequenceFlow) flowElement;
//                FlowElement sourceFlowElement = flow.getSourceFlowElement();
//                FlowElement targetFlowElement = flow.getTargetFlowElement();
//                //如果当前边的下一个节点是endEvent，那么获取当前边
//                if(targetFlowElement instanceof EndEvent && sourceFlowElement.getId().equals(curActId))
//                {
//                    System.out.println("下一个是结束节点！！");
//                }
//            }
//        }

    }
    @Override
    public boolean isFailOnException() {
        return false;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public String getOnTransaction() {
        return null;
    }

    @Override
    public Collection<? extends FlowableEventType> getTypes() {
        return FlowableEventListener.super.getTypes();
    }

}


