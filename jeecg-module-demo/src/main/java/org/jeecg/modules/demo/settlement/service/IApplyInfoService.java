package org.jeecg.modules.demo.settlement.service;

import org.jeecg.modules.demo.settlement.entity.ApplyWorkflowDTO;
import org.jeecg.modules.demo.settlement.entity.ApplyInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 结算申请
 * @Author: jeecg-boot
 * @Date:   2023-11-20
 * @Version: V1.0
 */
public interface IApplyInfoService extends IService<ApplyInfo> {

	/**
	 * 添加一对多
	 *
	 * @param applyInfo
	 * @param applyWorkflowDTOList
	 */
	public String saveMain(ApplyInfo applyInfo,List<ApplyWorkflowDTO> applyWorkflowDTOList) ;
	public String saveMain(ApplyInfo applyInfo) ;
	/**
	 * 修改一对多
	 *
   * @param applyInfo
   * @param applyWorkflowDTOList
	 */
	public void updateMain(ApplyInfo applyInfo,List<ApplyWorkflowDTO> applyWorkflowDTOList);
	
	/**
	 * 删除一对多
	 *
	 * @param id
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 *
	 * @param idList
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
