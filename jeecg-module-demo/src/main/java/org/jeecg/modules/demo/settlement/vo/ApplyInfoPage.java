package org.jeecg.modules.demo.settlement.vo;

import java.util.List;

import org.jeecg.modules.demo.settlement.entity.*;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 结算申请
 * @Author: jeecg-boot
 * @Date:   2023-11-20
 * @Version: V1.0
 */
@Data
@ApiModel(value="apply_infoPage对象", description="结算申请详情")
public class ApplyInfoPage {

	/**主键*/
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
	/**表单名称*/
	@Excel(name = "表单名称", width = 15)
	@ApiModelProperty(value = "表单名称")
	private java.lang.String name;
	/**金额*/
	@Excel(name = "金额", width = 15)
	@ApiModelProperty(value = "金额")
	private java.lang.Double amounts;
	/**初审审批金额*/
	@Excel(name = "初审审批金额", width = 15)
	@ApiModelProperty(value = "初审审批金额")
	private java.lang.Double firstAmounts;
	/**复审审批金额*/
	@Excel(name = "复审审批金额", width = 15)
	@ApiModelProperty(value = "复审审批金额")
	private java.lang.Double secondAmounts;
	/**终审审批金额*/
	@Excel(name = "终审审批金额", width = 15)
	@ApiModelProperty(value = "终审审批金额")
	private java.lang.Double thirdAmounts;
	/**总面积*/
	@Excel(name = "总面积", width = 15)
	@ApiModelProperty(value = "总面积")
	private java.lang.Double totalArea;
	/**申请日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "申请日期")
	private java.util.Date reviewDate;
	/**项目名称*/
	@Excel(name = "项目名称", width = 15)
	@ApiModelProperty(value = "项目名称")
    private java.lang.String projectName;
	/**所属单位*/
	@Excel(name = "所属单位", width = 15)
	@ApiModelProperty(value = "所属单位")
    private java.lang.String unit;
	/**中标单位*/
	@Excel(name = "中标单位", width = 15)
	@ApiModelProperty(value = "中标单位")
    private java.lang.String bidder;
	/**项目状态*/
	@Excel(name = "项目状态", width = 15)
	@ApiModelProperty(value = "项目状态")
    private java.lang.String status;
	/**合同编号*/
	@Excel(name = "合同编号", width = 15)
	@ApiModelProperty(value = "合同编号")
    private java.lang.String contractNum;
	/**合同名称*/
	@Excel(name = "合同名称", width = 15)
	@ApiModelProperty(value = "合同名称")
    private java.lang.String contractName;
	/**签订时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "签订时间")
    private java.lang.String signDate;
	/**合同总价*/
	@Excel(name = "合同总价", width = 15)
	@ApiModelProperty(value = "合同总价")
    private java.lang.String totalPrice;
	/**合同开始时间*/
	@Excel(name = "合同开始时间", width = 15)
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "合同开始时间")
    private java.lang.String startDate;
	/**合同结束时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "合同结束时间")
	private java.lang.String endDate;
	@Excel(name = "专业", width = 15)
	@ApiModelProperty(value = "专业")
	private java.lang.String major;

	/**供应商外键*/
	@ApiModelProperty(value = "供应商外键")
	private java.lang.String supplierId;

	/**项目外键*/
	@ApiModelProperty(value = "项目外键")
	private java.lang.String projectId;

	/**合同外键*/
	@ApiModelProperty(value = "合同外键")
	private java.lang.String contractId;

	@ExcelCollection(name="结算申报材料")
	@ApiModelProperty(value = "结算申报材料")
	private List<ApplyFiles> requestFileList;

	@ExcelCollection(name="变更签证材料")
	@ApiModelProperty(value = "变更签证材料")
	private List<ApplyFiles> changeFileList;
}
