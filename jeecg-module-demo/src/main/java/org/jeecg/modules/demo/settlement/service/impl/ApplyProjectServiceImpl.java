package org.jeecg.modules.demo.settlement.service.impl;

import org.jeecg.modules.demo.settlement.entity.ApplyProject;
import org.jeecg.modules.demo.settlement.entity.ApplyContract;
import org.jeecg.modules.demo.settlement.entity.ApplyFiles;
import org.jeecg.modules.demo.settlement.mapper.ApplyContractMapper;
import org.jeecg.modules.demo.settlement.mapper.ApplyFilesMapper;
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
 * @Date:   2024-01-03
 * @Version: V1.0
 */
@Service
public class ApplyProjectServiceImpl extends ServiceImpl<ApplyProjectMapper, ApplyProject> implements IApplyProjectService {

	@Autowired
	private ApplyProjectMapper applyProjectMapper;
	@Autowired
	private ApplyContractMapper applyContractMapper;
	@Autowired
	private ApplyFilesMapper applyFilesMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(ApplyProject applyProject, List<ApplyContract> applyContractList,List<ApplyFiles> applyFilesList) {
		applyProjectMapper.insert(applyProject);
		if(applyContractList!=null && applyContractList.size()>0) {
			for(ApplyContract entity:applyContractList) {
				//外键设置
				entity.setProjectId(applyProject.getId());
				applyContractMapper.insert(entity);
			}
		}
		if(applyFilesList!=null && applyFilesList.size()>0) {
			for(ApplyFiles entity:applyFilesList) {
				//外键设置
				entity.setProjectId(applyProject.getId());
				applyFilesMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(ApplyProject applyProject,List<ApplyContract> applyContractList,List<ApplyFiles> applyFilesList) {
		applyProjectMapper.updateById(applyProject);
		
		//1.先删除子表数据
		applyContractMapper.deleteByMainId(applyProject.getId());
		applyFilesMapper.deleteByMainId(applyProject.getId());
		
		//2.子表数据重新插入
		if(applyContractList!=null && applyContractList.size()>0) {
			for(ApplyContract entity:applyContractList) {
				//外键设置
				entity.setProjectId(applyProject.getId());
				applyContractMapper.insert(entity);
			}
		}
		if(applyFilesList!=null && applyFilesList.size()>0) {
			for(ApplyFiles entity:applyFilesList) {
				//外键设置
				entity.setProjectId(applyProject.getId());
				applyFilesMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		applyContractMapper.deleteByMainId(id);
		applyFilesMapper.deleteByMainId(id);
		applyProjectMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			applyContractMapper.deleteByMainId(id.toString());
			applyFilesMapper.deleteByMainId(id.toString());
			applyProjectMapper.deleteById(id);
		}
	}
	
}
