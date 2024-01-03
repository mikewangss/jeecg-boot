package org.jeecg.modules.demo.settlement.mapper;

import java.util.List;
import org.jeecg.modules.demo.settlement.entity.ApplyContract;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 合同
 * @Author: jeecg-boot
 * @Date:   2024-01-02
 * @Version: V1.0
 */
public interface ApplyContractMapper extends BaseMapper<ApplyContract> {

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(@Param("mainId") String mainId);

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId 主表id
   * @return List<ApplyContract>
   */
	public List<ApplyContract> selectByMainId(@Param("mainId") String mainId);
}
