package org.jeecg.modules.flowable.apithird.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用户对象 <br/>
 *  //todo 暂时支持用于jeecg，如需迁移其他框架，需要改动
 * @author pmc
 */
@Data
public class SysPosition {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 登录账号
     */
    private String name;

    /**
     * 真实姓名
     */
    private String code;



}
