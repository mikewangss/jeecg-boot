package org.jeecg.modules.demo.settlement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 结算申请流程
 * @Author: jeecg-boot
 * @Date:   2023-11-20
 * @Version: V1.0
 */
@Data
public class ProcessDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**主键*/
    private String id;
	/**流程名称*/
    private String name;
    /**审核人*/
    private String assignee;
    /**开始时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date start_time;
    /**结束时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date end_time;


    public ProcessDTO(String id, String name, String assignee, Date start_time, Date end_time) {
        this.id = id;
        this.name = name;
        this.assignee = assignee;
        this.start_time = start_time;
        this.end_time = end_time;
    }

}
