package org.jeecg.modules.demo.settlement.vo;

import java.util.List;
import org.jeecg.modules.demo.settlement.entity.ApplyProject;
import org.jeecg.modules.demo.settlement.entity.ApplyContract;
import org.jeecg.modules.demo.settlement.entity.ApplyFiles;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 项目
 * @Author: jeecg-boot
 * @Date:   2024-01-03
 * @Version: V1.0
 */
@Data
@ApiModel(value="apply_projectPage对象", description="项目")
public class ApplyProjectPage {

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
	/**项目名称*/
	@Excel(name = "项目名称", width = 15)
	@ApiModelProperty(value = "项目名称")
    private java.lang.String name;
	/**项目编号*/
	@Excel(name = "项目编号", width = 15)
	@ApiModelProperty(value = "项目编号")
    private java.lang.String code;
	/**所属单位*/
	@Excel(name = "所属单位", width = 15, dictTable = "sys_depart", dicText = "depart_name", dicCode = "id")
    @Dict(dictTable = "sys_depart", dicText = "depart_name", dicCode = "id")
	@ApiModelProperty(value = "所属单位")
    private java.lang.String unit;
	/**负责主体*/
	@Excel(name = "负责主体", width = 15)
	@ApiModelProperty(value = "负责主体")
    private java.lang.String onwer;
	/**开始时间*/
	@Excel(name = "开始时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "开始时间")
    private java.util.Date startDate;
	/**结束时间*/
	@Excel(name = "结束时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "结束时间")
    private java.util.Date endDate;
	/**中标单位*/
	@Excel(name = "中标单位", width = 15, dictTable = "sys_depart", dicText = "depart_name", dicCode = "id")
    @Dict(dictTable = "sys_depart", dicText = "depart_name", dicCode = "id")
	@ApiModelProperty(value = "中标单位")
    private java.lang.String bidder;
	/**描述*/
	@Excel(name = "描述", width = 15)
	@ApiModelProperty(value = "描述")
    private java.lang.String description;
	/**附件*/
	@Excel(name = "附件", width = 15)
	@ApiModelProperty(value = "附件")
    private java.lang.String file;
	/**是否结案*/
	@Excel(name = "是否结案", width = 15)
	@ApiModelProperty(value = "是否结案")
	private java.lang.String status;

	@ExcelCollection(name="合同")
	@ApiModelProperty(value = "合同")
	private List<ApplyContract> applyContractList;
	@ExcelCollection(name="附件管理")
	@ApiModelProperty(value = "附件管理")
	private List<ApplyFiles> applyFilesList;

}
