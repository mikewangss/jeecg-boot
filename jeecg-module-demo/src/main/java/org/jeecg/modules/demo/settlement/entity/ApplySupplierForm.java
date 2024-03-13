package org.jeecg.modules.demo.settlement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 供应商注册信息
 * @Author: jeecg-boot
 * @Date:   2024-01-02
 * @Version: V1.0
 */
@Data
@TableName("apply_supplier_form")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="apply_supplier_form对象", description="供应商注册信息")
public class ApplySupplierForm implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**供应商名称*/
	@Excel(name = "供应商名称", width = 15)
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    /**供应商名称*/
    @Excel(name = "统一社会信用代码", width = 15)
    @ApiModelProperty(value = "统一社会信用代码")
    private String unifiedSocialCreditCode;
	/**法人姓名*/
	@Excel(name = "法人姓名", width = 15)
    @ApiModelProperty(value = "法人姓名")
    private String legalPersonName;
	/**法人身份证*/
	@Excel(name = "法人身份证", width = 15)
    @ApiModelProperty(value = "法人身份证")
    private String legalPersonIdcard;
	/**法人电话*/
	@Excel(name = "法人电话", width = 15)
    @ApiModelProperty(value = "法人电话")
    private String legalPersonPhone;
	/**公司地址*/
	@Excel(name = "公司地址", width = 15)
    @ApiModelProperty(value = "公司地址")
    private String firmAddress;
	/**营业执照开始日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "营业执照开始日期")
    private String businessLicenseStartDate;
	/**营业执照结束日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "营业执照结束日期")
    private String businessLicenseEndDate;
	/**营业执照*/
	@Excel(name = "营业执照", width = 15)
    @ApiModelProperty(value = "营业执照")
    private String businessLicenseFile;
    /**项目名称*/
    @Excel(name = "项目名称", width = 15)
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    /**所属区域*/
    @Excel(name = "所属区域", width = 15)
    @ApiModelProperty(value = "所属区域")
    private String region;
    /**合作单位*/
    @Excel(name = "合作单位", width = 15)
    @ApiModelProperty(value = "合作单位")
    private String depart;
    /**项目经理*/
    @Excel(name = "项目经理", width = 15)
    @ApiModelProperty(value = "项目经理")
    private String projectManger;
    /**营业执照*/
    @Excel(name = "营业执照", width = 15)
    @ApiModelProperty(value = "营业执照")
    private String projectMangerPhone;
    /**营业执照*/
    @Excel(name = "用户名", width = 15)
    @ApiModelProperty(value = "用户名")
    private String username;
    /**用户邮箱*/
    @Excel(name = "用户邮箱", width = 15)
    @ApiModelProperty(value = "用户邮箱")
    private String email;
    /**用户手机号*/
    @Excel(name = "用户手机号", width = 15)
    @ApiModelProperty(value = "用户手机号")
    private String phone;

}
