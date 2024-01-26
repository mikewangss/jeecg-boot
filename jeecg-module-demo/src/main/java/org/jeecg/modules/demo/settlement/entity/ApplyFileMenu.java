package org.jeecg.modules.demo.settlement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 附件分类
 * @Author: jeecg-boot
 * @Date:   2024-01-03
 * @Version: V1.0
 */
@ApiModel(value="apply_file_menu对象", description="附件分类")
@Data
@TableName("apply_file_menu")
public class ApplyFileMenu implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**名称*/
    @ApiModelProperty(value = "名称")
    private String name;
    /**父级*/
    @ApiModelProperty(value = "父级")
    private String parent;

}
