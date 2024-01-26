package org.jeecg.modules.demo.settlement.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.settlement.entity.ApplyContract;
import org.jeecg.modules.demo.settlement.entity.ApplyInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.demo.settlement.vo.ApplyInfoPage;

import java.util.List;

/**
 * @Description: 结算申请
 * @Author: jeecg-boot
 * @Date:   2023-11-20
 * @Version: V1.0
 */
public interface ApplyInfoMapper extends BaseMapper<ApplyInfo> {
    /**
     * 通过主表id查询子表数据
     *
     * @param id 主表id
     * @return ApplyInfoPage
     */
    public ApplyInfoPage queryByMainId(@Param("id") String id);
}
