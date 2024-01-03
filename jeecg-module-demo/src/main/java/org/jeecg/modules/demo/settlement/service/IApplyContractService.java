package org.jeecg.modules.demo.settlement.service;

import org.jeecg.modules.demo.settlement.entity.ApplyContract;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 合同
 * @Author: jeecg-boot
 * @Date:   2024-01-02
 * @Version: V1.0
 */
public interface IApplyContractService extends IService<ApplyContract> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<ApplyContract>
	 */
	public List<ApplyContract> selectByMainId(String mainId);
}
