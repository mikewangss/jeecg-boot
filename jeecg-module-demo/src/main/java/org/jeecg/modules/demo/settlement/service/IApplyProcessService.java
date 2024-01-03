package org.jeecg.modules.demo.settlement.service;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.settlement.entity.ApplyProcessMapping;
import org.jeecg.modules.demo.settlement.entity.ApplyWorkflowDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 结算申请流程
 * @Author: jeecg-boot
 * @Date:   2023-11-20
 * @Version: V1.0
 */
public interface IApplyProcessService extends IService<ApplyProcessMapping> {

    /**
     *  process_id,查询
     * @param process_id
     * @return
     */
    ApplyProcessMapping queryByProcessId(@Param("process_id")String process_id);

}
