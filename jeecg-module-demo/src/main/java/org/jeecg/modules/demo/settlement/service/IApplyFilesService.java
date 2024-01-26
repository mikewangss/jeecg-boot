package org.jeecg.modules.demo.settlement.service;

import org.jeecg.modules.demo.settlement.entity.ApplyFileMenu;
import org.jeecg.modules.demo.settlement.entity.ApplyFiles;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 附件管理
 * @Author: jeecg-boot
 * @Date:   2024-01-03
 * @Version: V1.0
 */
public interface IApplyFilesService extends IService<ApplyFiles> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<ApplyFiles>
	 */
	public List<ApplyFiles> selectByMainId(String mainId);

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param bizId 业务id
	 * =@param fc   fc 分册
	 * @return List<ApplyFiles>
	 */
	public List<ApplyFiles> selectByBizId(String bizId,String fc);
	/**
	 * 通过主表id查询子表数据
	 *
	 * @param parent
	 * @return List<ApplyFileMenu>
	 */
	public List<ApplyFileMenu> getSubFileMenu(String parent);
	/**
	 * 通过主表id查询子表数据
	 *
	 * @param projectId 项目id
	 * =@param fc   fc 分册
	 * @return List<ApplyFiles>
	 */
	public List<ApplyFiles> selectByProjectId(String projectId,String fc);
}
