package org.jeecg.modules.demo.settlement.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.common.aspect.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 附件管理
 * @Author: jeecg-boot
 * @Date:   2024-01-03
 * @Version: V1.0
 */
@ApiModel(value="apply_files对象", description="附件管理")
@Data
@TableName("apply_files")
public class ApplyFiles implements Serializable {
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
	/**文件名称*/
	@Excel(name = "文件名称", width = 15)
    @ApiModelProperty(value = "文件名称")
    private java.lang.String fileName;
    /**业务实例外键ID*/
    @Excel(name = "业务实例外键ID", width = 15)
    @ApiModelProperty(value = "业务实例外键ID")
    private java.lang.String bizId;
	/**文件类型*/
	@Excel(name = "文件类型", width = 15, dicCode = "apply_file_type")
    @Dict(dicCode = "apply_file_type")
    @ApiModelProperty(value = "文件类型")
    private java.lang.String bizType;
    /**文件来源*/
    @Excel(name = "文件来源", width = 15)
    @ApiModelProperty(value = "文件来源")
    private java.lang.String source;
	/**是否必填*/
	@Excel(name = "是否通过审核", width = 15)
    @ApiModelProperty(value = "是否通过审核")
    private java.lang.String flag;
	/**描述*/
	@Excel(name = "审批意见", width = 15)
    @ApiModelProperty(value = "审批意见")
    private java.lang.String description;
	/**分册*/
	@Excel(name = "分册", width = 15, dicCode = "apply_fc")
    @Dict(dicCode = "apply_fc")
    @ApiModelProperty(value = "分册")
    private java.lang.String fc;
	/**文件*/
	@Excel(name = "文件", width = 15)
    @ApiModelProperty(value = "文件")
    private java.lang.String file;
	/**项目外键*/
    @ApiModelProperty(value = "项目外键")
    private java.lang.String projectId;
}
