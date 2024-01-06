package org.jeecg.flowable.controller;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import org.flowable.engine.*;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.flowable.common.Result;
import org.jeecg.flowable.entity.ProcessDTO;
import org.jeecg.flowable.entity.TaskDTO;
import org.jeecg.flowable.service.ProcessDiagramService;
import org.jeecg.flowable.service.ProcessService;
import org.jeecg.flowable.service.UserTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/workflow")
public class workflowController {
    @Autowired
    private ProcessService processService;
    @Autowired
    private UserTaskService userTaskService;
    @Autowired
    private ProcessDiagramService processDiagramService;

    /**
     * 通过接收用户的一个请求传入用户的ID和金额以及描述信息来开启一个结算流程，并返回给用户这个流程的Id
     * 添加报销
     *
     * @param params 用户Id
     */
    @PostMapping("/start")
    public String start(@RequestBody JSONObject params) {
        //1.启动流程，开始节点
        log.info("params: " + params.toJSONString());
        log.info("params.taskUser: " + params.getString("taskUser"));
        HashMap<String, Object> variables = new HashMap<>();
        //启动前必须先设置下个审批节点变量
        variables.put("money", params.getString("money"));
        // 设置流程启动用户
        variables.put("employeeName", params.getString("taskUser"));
        String processInstanceId = userTaskService.startProcess(variables);

        return processInstanceId;
    }

    /**
     * 查询流程实例详细
     * 获取审批列表
     */
    @GetMapping("/process_instance_list")
    public Object processInstanceList(String processInstanceId) {
        List<ProcessDTO> processDTOList = processService.getProcessInstanceList(processInstanceId);
        return Result.ok(processDTOList);
    }


    /**
     * 查询流程列表，待办列表，通过代码获取出用户需要处理的流程
     * 获取审批管理列表
     */
    @GetMapping("/todoList")
    public Object todoList(String userId) {
        List<TaskDTO> taskDTOList = userTaskService.getUserTodoTaskList(userId);
        return Result.ok(taskDTOList);
    }

    /**
     * 获取审批管理列表
     * params processInstanceId
     */
    @GetMapping("/taskHiList")
    public Object taskHiList(String process_instance_id) {
        List<ProcessDTO> processDTOList = processService.getProcessInstanceList(process_instance_id);
        return Result.ok(processDTOList);
    }

    /**
     * 获取用户已办
     */
    @GetMapping("/historicTaskList")
    public Object historicTaskList(String userId) {
        List<ProcessDTO> processDTOList = userTaskService.getUserHistoricTaskList(userId);
        return Result.ok(processDTOList);
    }

    /**
     * 批准/拒绝
     * 通过前端传入的任务ID来对此流程进行同意处理
     *
     * @param params
     */
    @PostMapping("/apply")
    public Object apply(@RequestBody JSONObject params) {
        userTaskService.applyTask(params);
        return Result.ok("processed ok!");
    }

    /**
     * 生成当前流程图表
     * 生成流程图
     *
     * @param processId 任务ID
     */
    @GetMapping("/processDiagram")
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception {
        processDiagramService.generateProcessDiagram(httpServletResponse, processId);
    }

}
