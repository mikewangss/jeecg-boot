package org.jeecg.modules.demo.settlement.service;

import org.jeecg.modules.demo.settlement.entity.ApplyContract;
import org.jeecg.modules.demo.settlement.entity.ApplyFiles;
import org.jeecg.modules.demo.settlement.entity.ApplyProject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 项目
 * @Author: jeecg-boot
 * @Date: 2024-01-02
 * @Version: V1.0
 */
public interface IApplyProjectService extends IService<ApplyProject> {

    /**
     * 添加一对多
     *
     * @param applyProject
     * @param applyContractList
     * @param applyFilesList
     */
    public void saveMain(ApplyProject applyProject, List<ApplyContract> applyContractList, List<ApplyFiles> applyFilesList);

    /**
     * 修改一对多
     *
     * @param applyProject
     * @param applyContractList
     */
    public void updateMain(ApplyProject applyProject, List<ApplyContract> applyContractList);

    /**
     * 删除一对多
     *
     * @param id
     */
    public void delMain(String id);

    /**
     * 批量删除一对多
     *
     * @param idList
     */
    public void delBatchMain(Collection<? extends Serializable> idList);

}
