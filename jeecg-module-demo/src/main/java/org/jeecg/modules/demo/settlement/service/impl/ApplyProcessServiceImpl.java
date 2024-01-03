package org.jeecg.modules.demo.settlement.service.impl;
import org.jeecg.modules.demo.settlement.entity.ApplyProcessMapping;
import org.jeecg.modules.demo.settlement.mapper.ApplyProcessMapper;
import org.jeecg.modules.demo.settlement.service.IApplyProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 结算申请流程
 * @Author: jeecg-boot
 * @Date:   2023-11-20
 * @Version: V1.0
 */
@Service
public class ApplyProcessServiceImpl  extends ServiceImpl<ApplyProcessMapper, ApplyProcessMapping>  implements IApplyProcessService {

    @Autowired
    private ApplyProcessMapper applyProcessMapper;
    @Override
    public ApplyProcessMapping queryByProcessId(String process_id) {
        return applyProcessMapper.queryByProcessId(process_id);
    }
}
