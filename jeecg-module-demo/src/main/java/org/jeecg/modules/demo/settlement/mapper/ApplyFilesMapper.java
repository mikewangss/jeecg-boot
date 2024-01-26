package org.jeecg.modules.demo.settlement.mapper;

import java.util.List;

import org.jeecg.modules.demo.settlement.entity.ApplyFileMenu;
import org.jeecg.modules.demo.settlement.entity.ApplyFiles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 附件管理
 * @Author: jeecg-boot
 * @Date: 2024-01-03
 * @Version: V1.0
 */
public interface ApplyFilesMapper extends BaseMapper<ApplyFiles> {


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
     * @return List<ApplyFiles>
     */
    public List<ApplyFiles> selectByMainId(@Param("mainId") String mainId);

    /**
     * 通过业务id查询子表数据
     *
     * @param bizId 业务id
     * @return List<ApplyFiles>
     */
    public List<ApplyFiles> selectByBizId(@Param("bizId") String bizId,@Param("fc") String fc);

    /**
     * 通过业务id查询子表数据
     *
     * @param bizId 业务id
     * @return List<ApplyFiles>
     */
    public List<ApplyFiles> selectByProjectId(@Param("projectId") String bizId,@Param("fc") String fc);

    public List<ApplyFileMenu>  selectSubFileMenu(@Param("parent") String parent);
}
