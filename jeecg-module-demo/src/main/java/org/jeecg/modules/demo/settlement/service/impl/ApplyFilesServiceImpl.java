package org.jeecg.modules.demo.settlement.service.impl;

import org.jeecg.modules.demo.settlement.entity.ApplyContract;
import org.jeecg.modules.demo.settlement.entity.ApplyFiles;
import org.jeecg.modules.demo.settlement.mapper.ApplyContractMapper;
import org.jeecg.modules.demo.settlement.mapper.ApplyFilesMapper;
import org.jeecg.modules.demo.settlement.service.IApplyFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 供应商
 * @Author: jeecg-boot
 * @Date:   2024-01-02
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
}
