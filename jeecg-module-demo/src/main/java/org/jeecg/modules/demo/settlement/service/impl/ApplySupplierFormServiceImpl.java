package org.jeecg.modules.demo.settlement.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.modules.demo.settlement.entity.ApplySupplier;
import org.jeecg.modules.demo.settlement.entity.ApplySupplierForm;
import org.jeecg.modules.demo.settlement.mapper.ApplySupplierFormMapper;
import org.jeecg.modules.demo.settlement.service.IApplyProjectService;
import org.jeecg.modules.demo.settlement.service.IApplySupplierFormService;
import org.jeecg.modules.demo.settlement.service.IApplySupplierService;
import org.jeecg.modules.flowable.apithird.business.entity.FlowMyBusiness;
import org.jeecg.modules.flowable.apithird.service.FlowCallBackServiceI;
import org.jeecg.modules.flowable.apithird.service.FlowCommonService;
import org.jeecg.modules.flowable.common.constant.ProcessConstants;
import org.jeecg.modules.flowable.service.IFlowDefinitionService;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.model.SysDepartTreeModel;
import org.jeecg.modules.system.service.ISysDepartService;
import org.jeecg.modules.system.service.ISysUserDepartService;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.*;

import org.jeecg.common.util.PasswordUtil;

import org.jeecg.common.util.oConvertUtils;

/**
 * @Description: 供应商注册
 * @Author: jeecg-boot
 * @Date: 2024-01-02
 * @Version: V1.0
 */
@Service("applySupplierFormService")
public class ApplySupplierFormServiceImpl extends ServiceImpl<ApplySupplierFormMapper, ApplySupplierForm> implements IApplySupplierFormService, FlowCallBackServiceI {
    @Autowired
    private ISysDepartService sysDepartService;
    @Autowired
    FlowCommonService flowCommonService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IFlowDefinitionService flowDefinitionService;
    @Autowired
    private IApplySupplierService applySupplierService;
    @Autowired
    private ISysUserDepartService sysUserDepartService;

    public List<String> getTaskAssaginlist(String region, String orgCode) {
        List<String> assaginlist = new ArrayList<>();
        //1.根据region,orgCode获取部门
        List<SysDepartTreeModel> sysDepartTreeModels = sysDepartService.queryTreeByRegion(region, orgCode);
        for (SysDepartTreeModel sysDepartTreeModel : sysDepartTreeModels) {
            List<SysUser> sysUserList = new ArrayList<>();
            sysUserList = sysUserDepartService.queryUserByDepCode(sysDepartTreeModel.getOrgCode(), "");
            for (SysUser sysUser : sysUserList) {
                assaginlist.add(sysUser.getUsername());//3.获取部门所有用户
            }
        }
        if(assaginlist.size()==0){
            assaginlist.add("admin");
        }
        return assaginlist;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String supplierRegister(JSONObject jsonObject, SysUser user, SysDepart sysDepart) {
        //1.新增表单
        ApplySupplierForm applySupplierForm = new ApplySupplierForm();
        applySupplierForm.setSupplierName(jsonObject.getString("supplierName"));
        applySupplierForm.setLegalPersonName(jsonObject.getString("legalPersonName"));
        applySupplierForm.setLegalPersonIdcard(jsonObject.getString("legalPersonIdcard"));
        applySupplierForm.setLegalPersonPhone(jsonObject.getString("legalPersonPhone"));
        applySupplierForm.setUnifiedSocialCreditCode(jsonObject.getString("unifiedSocialCreditCode"));
        applySupplierForm.setBusinessLicenseStartDate(jsonObject.getJSONArray("businessLicenseDate").get(0).toString());
        applySupplierForm.setBusinessLicenseEndDate(jsonObject.getJSONArray("businessLicenseDate").get(1).toString());
        applySupplierForm.setBusinessLicenseFile(jsonObject.getString("businessLicenseFile"));
        applySupplierForm.setFirmAddress(jsonObject.getString("firmAddress"));
        applySupplierForm.setUsername(jsonObject.getString("username"));
        applySupplierForm.setEmail(jsonObject.getString("email"));
        applySupplierForm.setPhone(jsonObject.getString("phone"));
        applySupplierForm.setProjectName(jsonObject.getString("projectName"));
        String region = jsonObject.getString("region");
        String depart = jsonObject.getString("depart");
        applySupplierForm.setRegion(region);
        applySupplierForm.setDepart(depart);
        applySupplierForm.setProjectManger(jsonObject.getString("projectManger"));
        applySupplierForm.setProjectMangerPhone(jsonObject.getString("projectMangerPhone"));
        saveOrUpdate(applySupplierForm);
        String supplierId = applySupplierForm.getId();
        //2.新增用户
        user.setCreateTime(new Date());// 设置创建时间
        String salt = oConvertUtils.randomGen(8);
        String passwordEncode = PasswordUtil.encrypt(jsonObject.getString("username"), jsonObject.getString("password"), salt);
        user.setSalt(salt);
        user.setUsername(jsonObject.getString("username"));
        user.setRealname(jsonObject.getString("username"));
        user.setPassword(passwordEncode);
        user.setEmail(jsonObject.getString("email"));
        user.setPhone(jsonObject.getString("phone"));
        user.setStatus(CommonConstant.USER_FREEZE);//冻结
        user.setDelFlag(CommonConstant.DEL_FLAG_0);
        user.setActivitiSync(CommonConstant.ACT_SYNC_1);
        user.setAvatar("https://static.jeecg.com/temp/jmlogo_1606575041993.png");
        sysUserService.addUserWithRole(user, "1744230647813816321");//默认临时角色 供应商
        //3.新增部门，部门用户关联
        sysDepart.setOrgType("1");
        sysDepart.setOrgCode(jsonObject.getString("unifiedSocialCreditCode"));
        sysDepart.setDepartName(jsonObject.getString("supplierName"));
        sysDepart.setIzLeaf(CommonConstant.IS_LEAF);
        sysDepart.setDelFlag(String.valueOf(CommonConstant.DEL_FLAG_0));
        sysDepart.setStatus(CommonConstant.STATUS_0);//待审核
        sysDepartService.saveOrUpdate(sysDepart);
        //4.新增用户部门关系表
        sysUserDepartService.saveUserDepart(sysDepart.getId(), user.getId());
        //5、流程供应商入驻申请流程
        HashMap variables = new HashMap<>();
        variables.put("dataId", supplierId);

        List<String> assaginlist = getTaskAssaginlist(region, depart);
        variables.put("assigneeList", assaginlist);
        variables.put(ProcessConstants.PROCESS_INITIATOR, jsonObject.getString("username"));
        flowCommonService.initActBusiness("供应商入驻申请流程", supplierId, "applySupplierFormService", "Process_1706093271175");
        flowDefinitionService.startProcessInstanceByKey("Process_1706093271175", variables);
        return supplierId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void afterFlowHandle(FlowMyBusiness business) {
        //流程操作后做些什么，如果会签，逻辑有问题，应该等待会签完毕
        business.getTaskNameId();//接下来审批的节点
        business.getValues();//前端传进来的参数
        String actStatus = business.getActStatus();//流程状态 ActStatus.java
        SysUser sysUser = sysUserService.getUserByName(business.getProposer());
        List<SysDepart> sysDepartList = sysDepartService.queryDepartsByUsername(sysUser.getUsername());
        ApplySupplierForm applySupplierForm = getById(business.getDataId());
        if (StringUtils.equals("审批通过", actStatus)) {
            ApplySupplier applySupplier = new ApplySupplier();
            BeanUtils.copyProperties(applySupplierForm, applySupplier);
            applySupplier.setStatus(CommonConstant.STATUS_1);
            applySupplierService.saveOrUpdate(applySupplier);
            if (sysDepartList.size() > 0) {
                SysDepart sysDepart = sysDepartList.get(0);
                sysDepart.setStatus(CommonConstant.STATUS_1);
                sysDepartService.saveOrUpdate(sysDepart);
            }
            sysUserService.updateStatus(sysUser.getId(),CommonConstant.USER_UNFREEZE.toString());


        }
    }

    @Override
    public Object getBusinessDataById(String dataId) {
        return this.getById(dataId);
    }

    @Override
    public Map<String, Object> flowValuesOfTask(String taskNameId, Map<String, Object> values) {
        return null;
    }

    @Override
    public List<String> flowCandidateUsernamesOfTask(String taskNameId, Map<String, Object> values) {
        return Lists.newArrayList("");
    }
}
