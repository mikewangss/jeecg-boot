package org.jeecg.modules.demo.settlement.service.impl;

import org.jeecg.modules.demo.settlement.entity.ApplyFileMenu;
import org.jeecg.modules.demo.settlement.entity.ApplyFiles;
import org.jeecg.modules.demo.settlement.mapper.ApplyFilesMapper;
import org.jeecg.modules.demo.settlement.service.IApplyFilesService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 附件管理
 * @Author: jeecg-boot
 * @Date:   2024-01-03
 * @Version: V1.0
 */
@Service
public class ApplyFilesServiceImpl extends ServiceImpl<ApplyFilesMapper, ApplyFiles> implements IApplyFilesService {
	
	@Autowired
	private ApplyFilesMapper applyFilesMapper;
	
	@Override
	public List<ApplyFiles> selectByMainId(String mainId) {
		return applyFilesMapper.selectByMainId(mainId);
	}

	@Override
	public List<ApplyFiles> selectByBizId(String bizId,String fc) {
		return applyFilesMapper.selectByBizId(bizId,fc);
	}
	@Override
	public List<ApplyFiles> selectByProjectId(String projectId,String fc) {
		return applyFilesMapper.selectByProjectId(projectId,fc);
	}
	@Override
	public List<ApplyFileMenu> getSubFileMenu(String parent){
		return applyFilesMapper.selectSubFileMenu(parent);
	}
}
