package org.jeecg.modules.demo.settlement.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.settlement.entity.ApplyContract;
import org.jeecg.modules.demo.settlement.entity.ApplyFiles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 供应商
 * @Author: jeecg-boot
 * @Date:   2024-01-02
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
}
