package org.jeecg.modules.flowable.listener;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.constant.enums.DySmsEnum;
import org.jeecg.common.constant.enums.Vue3MessageHrefEnum;
import org.jeecg.common.util.DySmsHelper;
import org.jeecg.modules.message.websocket.WebSocket;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.common.engine.api.delegate.event.FlowableEventType;
import org.flowable.common.engine.impl.event.FlowableEntityEventImpl;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;

import org.flowable.engine.impl.util.ProcessDefinitionUtil;
import org.flowable.engine.runtime.Execution;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.task.api.Task;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.CommonSendStatus;
import org.jeecg.common.constant.WebsocketConst;
import org.jeecg.modules.flowable.apithird.business.entity.FlowMyBusiness;
import org.jeecg.modules.flowable.apithird.business.service.impl.FlowMyBusinessServiceImpl;
import org.jeecg.modules.flowable.apithird.entity.ActStatus;
import org.jeecg.modules.flowable.apithird.entity.SysUser;
import org.jeecg.modules.flowable.apithird.service.IFlowThirdService;
import org.jeecg.modules.flowable.flow.FindNextNodeUtil;
import org.jeecg.modules.system.entity.SysAnnouncement;
import org.jeecg.modules.system.service.ISysAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
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
public class TaskCreateListener implements FlowableEventListener {
    @Resource
    private final TaskService taskService;
    @Resource
    protected RepositoryService repositoryService;
    @Resource
    IFlowThirdService iFlowThirdService;
    @Resource
    FlowMyBusinessServiceImpl flowMyBusinessService;
    @Resource
    RuntimeService runtimeService;
    @Autowired
    ISysAnnouncementService sysAnnouncementService;
    @Resource
    private WebSocket webSocket;

    @Override
    public void onEvent(FlowableEvent flowableEvent) {
        TaskEntity taskEntity = (TaskEntity) ((FlowableEntityEventImpl) flowableEvent).getEntity();
        String taskId = taskEntity.getId();
        String dataId = (String) taskEntity.getVariable("dataId");
        FlowMyBusiness business = flowMyBusinessService.getByDataId(dataId);
        List<SysUser> sysUserList = getAssigneeFromTask(taskEntity);
        if (sysUserList.size() > 0) {
            taskEntity.setAssigneeValue(sysUserList.get(0).getUsername());
        }
        List<IdentityLink> idList = taskService.getIdentityLinksForTask(taskId);
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        List<String> userNameList = new ArrayList<>();
        // 获取接收人，此处从Identity获取，实际情况会更复杂
        idList.forEach(identityLink -> {
            if (StringUtils.isNotBlank(identityLink.getUserId())) {
                SysUser sysUser = iFlowThirdService.getUserByUsername(identityLink.getUserId());
                userNameList.add(sysUser.getId());
            }
        });
        if (CollectionUtils.isNotEmpty(userNameList)) {
            // TODO: @author azhuzhu 发送提醒消息
            for (String username :
                    userNameList) {
                System.out.println(taskEntity.getName() + ":" + username + " 发送提醒消息");
            }
            StringJoiner joiner = new StringJoiner(", ");
            for (String str : userNameList) {
                joiner.add(str);
            }
            String result = joiner.toString();
            SysAnnouncement sysAnnouncement = new SysAnnouncement();
            // 2.插入用户通告阅读标记表记录
            sysAnnouncement.setTitile(taskEntity.getName() + "办理通知");
            // update-end-author:liusq date:20210804 for:标题处理xss攻击的问题
            sysAnnouncement.setDelFlag(CommonConstant.DEL_FLAG_0.toString());
            //未发布
            sysAnnouncement.setSendStatus(CommonSendStatus.PUBLISHED_STATUS_1);
            sysAnnouncement.setMsgContent("您有一条新增" + taskEntity.getName() + "待办理，请及时处理~");
            sysAnnouncement.setSender("admin");
            sysAnnouncement.setUserIds(result);
            sysAnnouncement.setPriority(CommonConstant.PRIORITY_L);
            sysAnnouncement.setMsgType(CommonConstant.MSG_TYPE_UESR);
            sysAnnouncement.setBusType(Vue3MessageHrefEnum.BPM_TASK.getBusType());//busType用于识别枚举，找到具体的路由地址path
            sysAnnouncement.setBusId(taskEntity.getProcessInstanceId());
            // 创建一个 JSON 对象
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key", business.getProcessDefinitionKey());
            sysAnnouncement.setMsgAbstract(jsonObject.toString());
            sysAnnouncement.setSendStatus(CommonConstant.HAS_SEND);
            sysAnnouncement.setSendTime(new Date());
            sysAnnouncement.setDelFlag(CommonConstant.DEL_FLAG_0.toString());
            sysAnnouncementService.saveAnnouncement(sysAnnouncement);
            JSONObject obj = new JSONObject();
            obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_USER);
            obj.put(WebsocketConst.MSG_ID, sysAnnouncement.getId());
            obj.put(WebsocketConst.MSG_TXT, sysAnnouncement.getTitile());
            webSocket.sendMessage(userNameList.toArray(new String[0]), obj.toJSONString());
        }
        String execuId = taskEntity.getExecutionId();
        Execution execution = runtimeService.createExecutionQuery().executionId(execuId).singleResult();
        String curActId = execution.getActivityId();// 获取流程实例的当前执行节点ID
        //流程办结通知
        if (curActId == "end") {
            SysUser userByUsername = iFlowThirdService.getUserByUsername(business.getProposer());
            //消息模版
            DySmsEnum templateCode = DySmsEnum.WORKFLOW_CODE;
            //模版所需参数
            JSONObject obj = new JSONObject();
            obj.put("name", userByUsername.getRealname());
            try {
                DySmsHelper.sendSms(userByUsername.getPhone(), obj, templateCode);
            } catch (ClientException e) {
                throw new RuntimeException(e);
            }
        }
//        setNextTask(taskEntity);

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
        if (task != null) {
            List<UserTask> nextUserTask = FindNextNodeUtil.getNextUserTasks(repositoryService, task, taskEntity.getVariables());
            String dataId = (String) taskEntity.getVariable("dataId");
            FlowMyBusiness business = flowMyBusinessService.getByDataId(dataId);
            List<IdentityLink> idList = taskService.getIdentityLinksForTask(taskId);
            List<String> collect_username = new ArrayList<>();
            // 获取接收人，此处从Identity获取，实际情况会更复杂
            idList.forEach(identityLink -> {
                if (StringUtils.isNotBlank(identityLink.getUserId())) {
                    collect_username.add(identityLink.getUserId());
                }
            });
            if (nextUserTask != null && nextUserTask.size() > 0) {
                List<Task> taskList = taskService.createTaskQuery().processInstanceId(business.getProcessInstanceId()).active().list();
                Task task2 = null;
                if (CollUtil.isNotEmpty(taskList)) {
                    task2 = taskList.get(0);
                }
                UserTask userTask = nextUserTask.get(0);//多任务会签只取一个
                business.setActStatus(ActStatus.doing)
                        .setProcessInstanceId(taskEntity.getProcessInstanceId())
                        .setTaskId(task2.getId())//下一个节点
                        .setTaskNameId(userTask.getId())//下一个节点
                        .setTaskName(userTask.getName())//下一个节点
                        .setPriority(String.valueOf(taskEntity.getPriority()))//下一个节点
                        .setDoneUsers(collect_username.toString())
                        .setTodoUsers("");
            } else {
                //    **没有下一个节点，流程已经结束了
                business.setActStatus(ActStatus.pass)
                        .setDoneUsers(collect_username.toString())
                        .setTodoUsers("")
                        .setTaskId("")
                        .setTaskNameId("")
                        .setTaskName("")
                ;
            }
            flowMyBusinessService.updateById(business);
        }

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

    private List<SysUser> getAssigneeFromTask(TaskEntity taskEntity) {
        String assignee = taskEntity.getAssignee();
        if (StrUtil.isNotBlank(assignee) && !StringUtils.contains(assignee, "$")) { //非流程变量
            // 指定用户单人
            if (assignee.contains("用户")) {
                assignee = assignee.replace("用户", "");
                SysUser userByUsername = iFlowThirdService.getUserByUsername(assignee);
                return Lists.newArrayList(userByUsername);
            } else if (assignee.contains("角色")) {
                assignee = assignee.replace("角色", "");
                List<SysUser> usersByRoleId = iFlowThirdService.getUsersByRoleId(assignee);
                return usersByRoleId;
            } else if (assignee.contains("岗位")) {
                String orgCode = (String) taskEntity.getVariable("orgCode");
                String position = assignee.replace("岗位", "");
                List<SysUser> list = iFlowThirdService.getAllUser(orgCode, position);//公司当前节点所有用户
                return list.stream().collect(Collectors.toList());
            }
        }
        return Lists.newArrayList();
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


