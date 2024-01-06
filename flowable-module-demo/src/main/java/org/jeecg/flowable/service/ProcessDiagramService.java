package org.jeecg.flowable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.flowable.engine.*;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;

@Service
public class ProcessDiagramService {

    @Autowired
    private RuntimeService runtimeService;  // 假设您已经注入了 RuntimeService

    @Autowired
    private TaskService taskService;  // 假设您已经注入了 TaskService

    @Autowired
    private RepositoryService repositoryService;  // 假设您已经注入了 RepositoryService

    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;  // 假设您已经注入了 ProcessEngineConfiguration

    public void generateProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();

        // 流程走完的不显示图
        if (processInstance == null) {
            return;
        }

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        // 使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String instanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(instanceId).list();

        // 得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution execution : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(execution.getId());
            activityIds.addAll(ids);
        }

        // 图片输出要加上这个不会显示二进制数据，有些浏览器正常，有些浏览器是直接显示二进制数据，因此修改
        httpServletResponse.setContentType("image/png".concat(";charset=UTF-8"));

        // 获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, processEngineConfiguration.getActivityFontName(), processEngineConfiguration.getLabelFontName(), processEngineConfiguration.getAnnotationFontName(), processEngineConfiguration.getClassLoader(), 1.0, true);
        OutputStream out = null;
        byte[] buf = new byte[1024];
        int length = 0;
        try {
            out = httpServletResponse.getOutputStream();
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}

