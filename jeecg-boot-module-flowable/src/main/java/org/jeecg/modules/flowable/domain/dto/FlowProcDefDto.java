package org.jeecg.modules.flowable.domain.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>流程定义<p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("流程定义")
public class FlowProcDefDto implements Serializable {

    @ApiModelProperty("流程id")
    private String id;

    @ApiModelProperty("流程名称")
    private String name;

    @ApiModelProperty("流程key")
    private String key;

    @ApiModelProperty("流程分类")
    private String category;

    @ApiModelProperty("配置表单名称")
    private String formName;

    @ApiModelProperty("配置表单id")
    private Long formId;

    @ApiModelProperty("版本")
    private int version;

    @ApiModelProperty("部署ID")
    private String deploymentId;

    @ApiModelProperty("流程定义状态: 1:激活 , 2:中止")
    private int suspensionState;

    @ApiModelProperty("流程定义是否最新版本")
    private int isLastVersion;

    @ApiModelProperty("部署时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deploymentTime;

    @ExcelCollection(name="xml")
    @ApiModelProperty(value = "xml")
    private String xml;
}
