package org.jeecg.modules.demo.settlement.service.impl;

import org.jeecg.modules.demo.settlement.entity.ApplyContract;
import org.jeecg.modules.demo.settlement.mapper.ApplyContractMapper;
import org.jeecg.modules.demo.settlement.service.IApplyContractService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 合同
 * @Author: jeecg-boot
 * @Date:   2024-01-02
 * @Version: V1.0
 */
@Service
public class ApplyContractServiceImpl extends ServiceImpl<ApplyContractMapper, ApplyContract> implements IApplyContractService {
	
	@Autowired
	private ApplyContractMapper applyContractMapper;
	
	@Override
	public List<ApplyContract> selectByMainId(String mainId) {
		return applyContractMapper.selectByMainId(mainId);
	}
}
