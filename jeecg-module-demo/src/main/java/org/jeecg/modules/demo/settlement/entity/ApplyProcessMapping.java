package org.jeecg.modules.demo.settlement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value="ApplyProcessMapping对象", description="结算申请与流程实例关系")
@Data
@TableName("apply_process_mapping")
public class ApplyProcessMapping  implements Serializable {
    public ApplyProcessMapping(String apply_id, String process_instance_id) {
        this.apply_id = apply_id;
        this.process_instance_id = process_instance_id;
    }

    private static final long serialVersionUID = 1L;
    /**主键*/
    @TableId
    @ApiModelProperty(value = "结算申请主键")
    private java.lang.String apply_id;
    /**流程实例id*/
    @ApiModelProperty(value = "流程实例id")
    private java.lang.String process_instance_id;

}
