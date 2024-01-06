package org.jeecg.flowable.service;

import com.alibaba.fastjson.JSONObject;
import liquibase.util.StringUtil;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.jeecg.flowable.entity.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.engine.*;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.jeecg.flowable.entity.ProcessDTO;

import static org.jeecg.flowable.util.CommonUtil.parseDuration;

@Service
public class UserTaskService {

    @Autowired
    private HistoryService historyService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    public List<TaskDTO> getUserTodoTaskList(String userId) {
        List<TaskDTO> taskDTOList = new ArrayList<>();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
//                .processDefinitionKey("Expense")
//                .singleResult();
        for (Task task : tasks) {
            TaskDTO taskDTO = new TaskDTO(task.getId(), task.getName(), task.getAssignee(), task.getPriority(), task.getProcessInstanceId());
            taskDTO.setDescription("结算申请流程");
            taskDTO.setCreate_time(task.getCreateTime());
            taskDTOList.add(taskDTO);
        }
        return taskDTOList;
    }

    public String startProcess(HashMap<String, Object> variables) {
        //1.启动流程，开始节点
        //这是一个在运行时设置当前用户身份的操作。
        identityService.setAuthenticatedUserId((String) variables.get("taskUser"));
        // 根据key启动，返回流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Expense", variables);
        completeFirstUserTask(processInstance.getId(), (String) variables.get("employeeName"));
        return processInstance.getId();
    }

    private void completeFirstUserTask(String processInstanceId, String assignee) {
        // Replace this with your workflow engine's API to complete the task
        // For example, using Activiti's Java API:
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskDefinitionKey("creatorTask") // Replace with the actual task definition key
                .singleResult();

        if (task != null && StringUtil.equalsIgnoreCaseAndEmpty(task.getAssignee(), assignee)) {
            // 获取当前流程实例的所有变量
            Map<String, Object> variables = runtimeService.getVariables(processInstanceId);
            //获取本次审批任务流程参数并记录局部变量
            variables.put("approval_comment", "申请");
            variables.put("outcome", "提交成功");
            taskService.setVariablesLocal(task.getId(), variables);
            taskService.complete(task.getId());
        }
    }

    public List<ProcessDTO> getUserHistoricTaskList(String userId) {
        List<ProcessDTO> processDTOs = new ArrayList<>();

        // 获取历史任务实例列表
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(userId)
                .list();

        for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
            if (historicTaskInstance.getDurationInMillis() != null) { //已办理办理时长不为空
                ProcessDTO processDTO = buildProcessDTO(historicTaskInstance);
                processDTOs.add(processDTO);
            }
        }

        return processDTOs;
    }

    private ProcessDTO buildProcessDTO(HistoricTaskInstance historicTaskInstance) {
        String processInstanceId = historicTaskInstance.getProcessInstanceId();
        String processDefinitionName = getProcessDefinitionName(historicTaskInstance);

        ProcessDTO processDTO = new ProcessDTO(
                historicTaskInstance.getId(),
                processInstanceId,
                historicTaskInstance.getName(),
                processDefinitionName
        );

        processDTO.setStart_time(historicTaskInstance.getStartTime());

        if (historicTaskInstance.getEndTime() != null) {
            processDTO.setEnd_time(historicTaskInstance.getEndTime());
            long durationMillis = historicTaskInstance.getEndTime().getTime() - historicTaskInstance.getStartTime().getTime();
            processDTO.setDuration(parseDuration(durationMillis));
        }

        Map<String, Object> approvalParameters = new HashMap<>();
        List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .taskId(historicTaskInstance.getId())
                .list();

        for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
            approvalParameters.put(historicVariableInstance.getVariableName(), historicVariableInstance.getValue());
        }

        processDTO.setApprovalParameters(approvalParameters);

        return processDTO;
    }

    private String getProcessDefinitionName(HistoricTaskInstance historicTaskInstance) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(historicTaskInstance.getProcessDefinitionId())
                .singleResult();

        return (processDefinition != null) ? processDefinition.getName() : "";
    }

    public List<ProcessDTO> getProcessInstanceList(String processInstanceId) {
        List<ProcessDTO> processDTOs = new ArrayList<>();

        // 获取流程名称
        String processDefinitionName = getProcessDefinitionName(processInstanceId);

        // 获取历史任务实例列表
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceStartTime().desc()
                .list();

        for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
            ProcessDTO processDTO = buildProcessDTO(processInstanceId, processDefinitionName, historicTaskInstance);
            processDTOs.add(processDTO);
        }

        return processDTOs;
    }

    private String getProcessDefinitionName(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();

        return (historicProcessInstance != null) ? historicProcessInstance.getProcessDefinitionName() : "";
    }

    private ProcessDTO buildProcessDTO(String processInstanceId, String processDefinitionName, HistoricTaskInstance historicTaskInstance) {
        String taskId = historicTaskInstance.getId();

        ProcessDTO processDTO = new ProcessDTO(taskId, processInstanceId, historicTaskInstance.getName(), processDefinitionName);
        processDTO.setStart_time(historicTaskInstance.getStartTime());
        processDTO.setEnd_time(historicTaskInstance.getEndTime());

        long durationMillis = historicTaskInstance.getEndTime().getTime() - historicTaskInstance.getStartTime().getTime();
        processDTO.setDuration(parseDuration(durationMillis));

        // 获取历史任务的变量
        List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId).taskId(taskId)
                .list();

        Map<String, Object> approvalParameters = new HashMap<>();

        for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
            String variableName = historicVariableInstance.getVariableName();
            Object value = historicVariableInstance.getValue();

            System.out.println("流程变量ID：" + historicVariableInstance.getId());
            System.out.println("流程实例ID：" + historicVariableInstance.getProcessInstanceId());
            System.out.println("变量名称：" + variableName);
            System.out.println("变量的值：" + value);

            approvalParameters.put(variableName, value);
            processDTO.setApprovalParameters(approvalParameters);

            System.out.println("###############################################");
        }

        return processDTO;
    }

    public void applyTask(JSONObject params) {
        String taskId = params.getString("taskId");
        String state = params.getString("state");
        String approvalComment = params.getString("approval_comment");
        // 获取当前任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        // 获取任务流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        // 获取当前流程实例的所有变量
        Map<String, Object> variables = runtimeService.getVariables(processInstanceId);
        //获取本次审批任务流程参数并记录局部变量
        variables.put("approval_comment", approvalComment);
        if (StringUtil.equalsIgnoreCaseAndEmpty(state, "reject")) {
            variables.put("outcome", "驳回");
        } else {
            variables.put("outcome", "通过");
        }
        taskService.setVariablesLocal(taskId, variables);
        taskService.complete(taskId, variables);
    }
    // 其他辅助方法和逻辑可以继续添加
    //    /**
//     * 查询流程实例详细
//     * 获取审批列表
//     */
//    @GetMapping("/process_instance_list")
//    public Object processInstanceList(String processInstanceId) {
//        List<ProcessDTO> processDTOList = new ArrayList<>();
//        // 创建一个 Map 用于存储审批参数
//        Map<String, Object> approvalParameters = new HashMap<>();
//        // 查询流程实例的任务历史(ACT_HI_TASKINST)
//        List<HistoricTaskInstance> historicTaskInstances = historyService
//                .createHistoricTaskInstanceQuery()
//                .processInstanceId(processInstanceId)
//                .orderByHistoricTaskInstanceEndTime()
//                .asc()
//                .list();
//
//        // 要查询历史任务实例的审批参数(ACT_HI_VARINST)
//        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery()
//                .processInstanceId(processInstanceId)
//                .list();
//        if (list != null && list.size() > 0) {
//            for (HistoricVariableInstance hvi : list) {
//                System.out.println("流程变量ID：" + hvi.getId());
//                System.out.println("流程实例ID：" + hvi.getProcessInstanceId());
//                System.out.println("变量名称：" + hvi.getVariableName());
//                System.out.println("变量的值：" + hvi.getValue());
//                approvalParameters.put(hvi.getVariableName(),hvi.getValue());
//                System.out.println("###############################################");
//            }
//        }
//
//        // 输出任务历史信息
//        System.out.println("Task History for Process Instance: " + processInstanceId);
//        for (HistoricTaskInstance taskInstance : historicTaskInstances) {
//            System.out.println("Task ID: " + taskInstance.getId());
//            System.out.println("ProcessInstanceId: " + taskInstance.getProcessInstanceId());
//            System.out.println("Task Name: " + taskInstance.getName());
//            System.out.println("Assignee: " + taskInstance.getAssignee());
//            System.out.println("Start Time: " + taskInstance.getStartTime());
//            System.out.println("End Time: " + taskInstance.getEndTime());
//            System.out.println("---------");
//            ProcessDTO processDTO = new ProcessDTO(taskInstance.getId(), taskInstance.getProcessInstanceId(), taskInstance.getName(), taskInstance.getAssignee(), taskInstance.getStartTime(), taskInstance.getEndTime());
//            // 将审批参数添加到 ProcessDTO 中
//            processDTO.setApprovalParameters(approvalParameters);
//            processDTOList.add(processDTO);
//        }
//
//        return Result.ok(processDTOList);
//    }
//    @GetMapping("/process_instance_list")
//    public void processInstanceList(String processInstanceId) {
//
//        // 查询流程实例的任务历史(ACT_HI_TASKINST)
//        List<HistoricTaskInstance> historicTaskInstances = historyService
//                .createHistoricTaskInstanceQuery()
//                .processInstanceId(processInstanceId)
//                .orderByHistoricTaskInstanceEndTime()
//                .asc()
//                .list();
//
//        // 输出任务历史信息
//        System.out.println("Task History for Process Instance: " + processInstanceId);
//        for (HistoricTaskInstance taskInstance : historicTaskInstances) {
//            System.out.println("Task ID: " + taskInstance.getId());
//            System.out.println("Task Name: " + taskInstance.getName());
//            System.out.println("Assignee: " + taskInstance.getAssignee());
//            System.out.println("Start Time: " + taskInstance.getStartTime());
//            System.out.println("End Time: " + taskInstance.getEndTime());
//            System.out.println("---------");
//        }
//
//        // 查询流程实例的活动历史(ACT_HI_ACTINST)
//        List<HistoricActivityInstance> historicActivityInstances = historyService
//                .createHistoricActivityInstanceQuery()
//                .processInstanceId(processInstanceId)
//                .orderByHistoricActivityInstanceEndTime()
//                .asc()
//                .list();
//
//        // 输出活动历史信息
//        System.out.println("Activity History for Process Instance: " + processInstanceId);
//        for (HistoricActivityInstance activityInstance : historicActivityInstances) {
//            System.out.println("Activity Name: " + activityInstance.getActivityName());
//            System.out.println("Start Time: " + activityInstance.getStartTime());
//            System.out.println("End Time: " + activityInstance.getEndTime());
//            System.out.println("---------");
//        }
//
//        // 查询流程实例的详细信息(ACT_HI_PROCINST)
//        HistoricProcessInstance historicProcessInstance = historyService
//                .createHistoricProcessInstanceQuery()
//                .processInstanceId(processInstanceId)
//                .singleResult();
//
//        // 输出流程实例详细信息
//        System.out.println("Process Instance Details: " + processInstanceId);
//        System.out.println("Start Time: " + historicProcessInstance.getStartTime());
//        System.out.println("End Time: " + historicProcessInstance.getEndTime());
//    }
}

