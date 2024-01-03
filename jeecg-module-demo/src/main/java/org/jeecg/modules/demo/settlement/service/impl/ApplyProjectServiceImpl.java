package org.jeecg.modules.demo.settlement.service.impl;

import org.jeecg.modules.demo.settlement.entity.ApplyProject;
import org.jeecg.modules.demo.settlement.entity.ApplyContract;
import org.jeecg.modules.demo.settlement.mapper.ApplyContractMapper;
import org.jeecg.modules.demo.settlement.mapper.ApplyProjectMapper;
import org.jeecg.modules.demo.settlement.service.IApplyProjectService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 项目
 * @Author: jeecg-boot
 * @Date:   2024-01-02
 * @Version: V1.0
 */
@Service
public class ApplyProjectServiceImpl extends ServiceImpl<ApplyProjectMapper, ApplyProject> implements IApplyProjectService {

	@Autowired
	private ApplyProjectMapper applyProjectMapper;
	@Autowired
	private ApplyContractMapper applyContractMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(ApplyProject applyProject, List<ApplyContract> applyContractList) {
		applyProjectMapper.insert(applyProject);
		if(applyContractList!=null && applyContractList.size()>0) {
			for(ApplyContract entity:applyContractList) {
				//外键设置
				entity.setContractId(applyProject.getId());
				applyContractMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(ApplyProject applyProject,List<ApplyContract> applyContractList) {
		applyProjectMapper.updateById(applyProject);
		
		//1.先删除子表数据
		applyContractMapper.deleteByMainId(applyProject.getId());
		
		//2.子表数据重新插入
		if(applyContractList!=null && applyContractList.size()>0) {
			for(ApplyContract entity:applyContractList) {
				//外键设置
				entity.setContractId(applyProject.getId());
				applyContractMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		applyContractMapper.deleteByMainId(id);
		applyProjectMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			applyContractMapper.deleteByMainId(id.toString());
			applyProjectMapper.deleteById(id);
		}
	}
	
}