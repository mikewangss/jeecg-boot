package org.jeecg.modules.demo.settlement.mapper;

import java.util.List;

import org.jeecg.modules.demo.settlement.entity.ApplyProcessMapping;
import org.jeecg.modules.demo.settlement.entity.ApplyWorkflowDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 结算申请流程
 * @Author: jeecg-boot
 * @Date:   2023-11-20
 * @Version: V1.0
 */
public interface ApplyProcessMapper extends BaseMapper<ApplyProcessMapping> {
    /**
     * 通过process_id,查询
     * @param process_id
     * @return
     */
    public ApplyProcessMapping queryByProcessId(@Param("process_id") String process_id);
}
