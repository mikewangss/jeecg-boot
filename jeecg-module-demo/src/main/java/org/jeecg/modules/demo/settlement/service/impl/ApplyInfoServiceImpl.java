package org.jeecg.modules.demo.settlement.service.impl;

import org.jeecg.modules.demo.settlement.entity.ApplyInfo;
import org.jeecg.modules.demo.settlement.entity.ApplyWorkflowDTO;
import org.jeecg.modules.demo.settlement.mapper.ApplyProcessMapper;
import org.jeecg.modules.demo.settlement.mapper.ApplyInfoMapper;
import org.jeecg.modules.demo.settlement.service.IApplyInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 结算申请
 * @Author: jeecg-boot
 * @Date:   2023-11-20
 * @Version: V1.0
 */
@Service
public class ApplyInfoServiceImpl extends ServiceImpl<ApplyInfoMapper, ApplyInfo> implements IApplyInfoService {

	@Autowired
	private ApplyInfoMapper applyInfoMapper;
	@Autowired
	private ApplyProcessMapper applyProcessMapping;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String saveMain(ApplyInfo applyInfo, List<ApplyWorkflowDTO> applyWorkflowDTOList) {
		applyInfoMapper.insert(applyInfo);
		String apply_id = applyInfo.getId();
		return apply_id;
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String saveMain(ApplyInfo applyInfo) {
		applyInfoMapper.insert(applyInfo);
		String apply_id = applyInfo.getId();
		return apply_id;
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(ApplyInfo applyInfo,List<ApplyWorkflowDTO> applyWorkflowDTOList) {
		applyInfoMapper.updateById(applyInfo);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		applyInfoMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			applyInfoMapper.deleteById(id);
		}
	}
	
}
