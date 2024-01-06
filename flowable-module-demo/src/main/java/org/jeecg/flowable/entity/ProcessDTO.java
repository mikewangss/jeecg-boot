package org.jeecg.flowable.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Map;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 结算申请流程
 * @Author: jeecg-boot
 * @Date: 2023-11-20
 * @Version: V1.0
 */
@Data
public class ProcessDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * task_id主键
     */
    private String task_id;
    /**
     * 流程名称
     */
    private String process_definition_name;
    
    /**
     * process_instance_id
     */
    private String process_instance_id;
    /**
     * 节点名称
     */
    private String name;

    /**
     * 开始时间
     */
    /**
     * 创建一个 Map 用于存储审批参数
     */
    private Map<String, Object> approvalParameters;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date start_time;
    /**
     * 结束时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date end_time;
    /**
     * 处理时长
     */
    String duration;

    public ProcessDTO(String task_id, String process_instance_id, String name,String process_definition_name) {
        this.task_id = task_id;
        this.process_instance_id = process_instance_id;
        this.name = name;
        this.process_definition_name= process_definition_name;
    }


}
