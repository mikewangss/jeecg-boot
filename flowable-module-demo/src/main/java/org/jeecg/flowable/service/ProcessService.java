package org.jeecg.flowable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.jeecg.flowable.common.Result;
import org.jeecg.flowable.entity.ProcessDTO;
import static org.jeecg.flowable.util.CommonUtil.parseDuration;
@Service
public class ProcessService {
    @Autowired
    private HistoryService historyService;  // 假设您已经注入了 HistoryService

    public  List<ProcessDTO> getProcessInstanceList(String processInstanceId) {
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
        if (historicTaskInstance.getEndTime() != null) {
            long durationMillis = historicTaskInstance.getEndTime().getTime() - historicTaskInstance.getStartTime().getTime();
            processDTO.setDuration(parseDuration(durationMillis));
        }
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
}

