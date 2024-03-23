package org.jeecg.modules.demo.settlement.service.impl;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.modules.demo.settlement.entity.*;
import org.jeecg.modules.demo.settlement.mapper.ApplyFilesMapper;
import org.jeecg.modules.demo.settlement.mapper.ApplyProcessMapper;
import org.jeecg.modules.demo.settlement.mapper.ApplyInfoMapper;
import org.jeecg.modules.demo.settlement.service.IApplyInfoService;
import org.jeecg.modules.demo.settlement.vo.ApplyInfoPage;
import org.jeecg.modules.flowable.apithird.business.entity.FlowMyBusiness;
import org.jeecg.modules.flowable.apithird.business.service.IFlowMyBusinessService;
import org.jeecg.modules.flowable.apithird.business.service.impl.FlowMyBusinessServiceImpl;
import org.jeecg.modules.flowable.apithird.service.FlowCallBackServiceI;
import org.jeecg.modules.flowable.domain.vo.FlowTaskVo;
import org.jeecg.modules.flowable.service.IFlowTaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Collection;
import java.util.Map;

/**
 * @Description: 结算申请
 * @Author: jeecg-boot
 * @Date: 2023-11-20
 * @Version: V1.0
 */
@Service("applyInfoService")
public class ApplyInfoServiceImpl extends ServiceImpl<ApplyInfoMapper, ApplyInfo> implements IApplyInfoService, FlowCallBackServiceI {

    @Autowired
    private ApplyInfoMapper applyInfoMapper;
    @Autowired
    private IFlowTaskService flowTaskService;
    @Autowired
    private ApplyFilesMapper applyFilesMapper;
    @Autowired
    FlowMyBusinessServiceImpl flowMyBusinessService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveMain(ApplyInfo applyInfo, List<ApplyFiles> requstFileList, List<ApplyFiles> changeFileList) {
        applyInfoMapper.insert(applyInfo);
        String apply_id = applyInfo.getId();
        //2.子表数据重新插入
        if (requstFileList != null && requstFileList.size() > 0) {
            for (ApplyFiles entity : requstFileList) {
                //外键设置
                entity.setProjectId(applyInfo.getProjectId());
                entity.setBizId(applyInfo.getId());
                applyFilesMapper.insert(entity);
            }
        }
        //2.子表数据重新插入
        if (changeFileList != null && changeFileList.size() > 0) {
            for (ApplyFiles entity : changeFileList) {
                //外键设置
                entity.setProjectId(applyInfo.getProjectId());
                entity.setBizId(applyInfo.getId());
                applyFilesMapper.insert(entity);
            }
        }
        return apply_id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveMain(ApplyInfo applyInfo) {
        applyInfoMapper.insert(applyInfo);
        String apply_id = applyInfo.getId();
        return apply_id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMain(ApplyInfo applyInfo, List<ApplyWorkflowDTO> applyWorkflowDTOList) {
        applyInfoMapper.updateById(applyInfo);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delMain(String id) {
        ApplyInfoPage applyInfoPage = applyInfoMapper.queryByMainId(id);
        ApplyInfo applyinfo =new ApplyInfo();
        BeanUtils.copyProperties(applyInfoPage, applyinfo);
        applyinfo.setDelFlag(CommonConstant.DEL_FLAG_1);
        applyInfoMapper.updateById(applyinfo);
        FlowTaskVo flowTaskVo=new FlowTaskVo();
        FlowMyBusiness business = flowMyBusinessService.getByDataId(id);
        flowTaskVo.setInstanceId(business.getProcessInstanceId());
        flowTaskVo.setComment("用户取消申请");
        flowTaskService.stopProcess(flowTaskVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            applyInfoMapper.deleteById(id);
        }
    }

    @Override
    public ApplyInfoPage queryByMainId(String id) {
        return applyInfoMapper.queryByMainId(id);
    }

    @Override
    public void afterFlowHandle(FlowMyBusiness business) {
        //流程操作后做些什么
        business.getTaskNameId();//接下来审批的节点
        business.getValues();//前端传进来的参数
        business.getActStatus();//流程状态 ActStatus.java
        ApplyInfo applyInfo = getById(business.getDataId());
        //....其他
        if (StringUtils.equals(business.getTaskNameId(), "Activity_061uiis") || StringUtils.equals(business.getTaskNameId(), "Activity_0ujjxwl") || StringUtils.equals(business.getTaskNameId(), "Activity_1keife4")) {
            applyInfo.setStatus("项目初审");
        } else if (StringUtils.equals(business.getTaskNameId(), "Activity_1vhi94l") || StringUtils.equals(business.getTaskNameId(), "Activity_11yqg39") || StringUtils.equals(business.getTaskNameId(), "Activity_0tvz5zl")) {
            applyInfo.setStatus("子集团二审");
        } else if (StringUtils.equals(business.getTaskNameId(), "Activity_18zvw97") || StringUtils.equals(business.getTaskNameId(), "Activity_0lwfr9d") || StringUtils.equals(business.getTaskNameId(), "Activity_1cfbee5")) {
            applyInfo.setStatus("工程结算三审");
        } else if (StringUtils.equals(business.getTaskNameId(), "Activity_0nzpayg")) {
            applyInfo.setStatus("结算终审");
        } else if (StringUtils.equals(business.getTaskNameId(), "Activity_1f15ijo")) {
            applyInfo.setStatus("结算归档");
        }
        saveOrUpdate(applyInfo);
    }

    @Override
    public Object getBusinessDataById(String dataId) {
        return this.queryByMainId(dataId);
    }

    @Override
    public Map<String, Object> flowValuesOfTask(String taskNameId, Map<String, Object> values) {
        return null;
    }

    @Override
    public List<String> flowCandidateUsernamesOfTask(String taskNameId, Map<String, Object> values) {
        // 案例，写死了jeecg，实际业务中通过当前节点来判断下一个节点的候选人并写回到反参中，如果为null，流程模块会根据默认设置处理
        return null;
    }
}
