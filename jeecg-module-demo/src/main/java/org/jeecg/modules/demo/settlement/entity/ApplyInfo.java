package org.jeecg.modules.demo.settlement.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 结算申请
 * @Author: jeecg-boot
 * @Date:   2023-11-20
 * @Version: V1.0
 */
@ApiModel(value="apply_info对象", description="结算申请")
@Data
@TableName("apply_info")
public class ApplyInfo implements Serializable {
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
    /**手机号*/
    @Excel(name = "表单名称", width = 15)
    @ApiModelProperty(value = "表单名称")
    private java.lang.String name;
	/**手机号*/
	@Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "手机号")
    private java.lang.String phoneNumber;
	/**债券人姓名*/
	@Excel(name = "债券人姓名", width = 15)
    @ApiModelProperty(value = "债券人姓名")
    private java.lang.String bondholdersName;
	/**证件号*/
	@Excel(name = "证件号", width = 15)
    @ApiModelProperty(value = "证件号")
    private java.lang.String idCode;
	/**收款银行*/
	@Excel(name = "收款银行", width = 15)
    @ApiModelProperty(value = "收款银行")
    private java.lang.String bankName;
	/**银行账户*/
	@Excel(name = "银行账户", width = 15)
    @ApiModelProperty(value = "银行账户")
    private java.lang.String bankAccount;
	/**户名*/
	@Excel(name = "户名", width = 15)
    @ApiModelProperty(value = "户名")
    private java.lang.String accountName;
    /**承包单位送审时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "承包单位送审时间")
    private java.util.Date reviewDate;
	/**金额*/
	@Excel(name = "承包单位送审额", width = 15)
    @ApiModelProperty(value = "承包单位送审额")
    private java.lang.Double money;
	/**债权类型*/
	@Excel(name = "债权类型", width = 15, dicCode = "zqlx")
    @Dict(dicCode = "zqlx")
    @ApiModelProperty(value = "债权类型")
    private java.lang.String bondholdersType;
	/**说明*/
	@Excel(name = "说明", width = 15)
    @ApiModelProperty(value = "说明")
    private java.lang.String remark;
	/**附件*/
	@Excel(name = "附件", width = 15)
    @ApiModelProperty(value = "附件")
    private java.lang.String file;

    /**供应商外键*/
    @ApiModelProperty(value = "供应商外键")
    private java.lang.String supplierId;
}
