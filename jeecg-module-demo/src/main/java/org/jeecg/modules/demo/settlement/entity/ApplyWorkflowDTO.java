package org.jeecg.modules.demo.settlement.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 结算申请流程
 * @Author: jeecg-boot
 * @Date:   2023-11-20
 * @Version: V1.0
 */
@ApiModel(value="apply_workflow对象", description="结算申请流程")
@Data
@TableName("apply_workflow")
public class ApplyWorkflowDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**审核阶段*/
	@Excel(name = "审核阶段", width = 15, dicCode = "apply_state")
    @ApiModelProperty(value = "审核阶段")
    private java.lang.String state;
	/**审核状态*/
	@Excel(name = "审核状态", width = 15, dicCode = "apply_status")
    @ApiModelProperty(value = "审核状态")
    private java.lang.String status;
	/**审核结果*/
	@Excel(name = "审核结果", width = 15)
    @ApiModelProperty(value = "审核结果")
    private java.lang.String info;
    /**操作人*/
    @Excel(name = "操作人", width = 15)
    @ApiModelProperty(value = "操作人")
    private java.lang.String operater;
	/**申请编号*/
    @ApiModelProperty(value = "申请编号")
    private java.lang.String applyId;
}
