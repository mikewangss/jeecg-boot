package org.jeecg.modules.demo.settlement.service.impl;

import org.jeecg.modules.demo.settlement.entity.ApplySupplier;
import org.jeecg.modules.demo.settlement.mapper.ApplySupplierMapper;
import org.jeecg.modules.demo.settlement.service.IApplyProjectService;
import org.jeecg.modules.demo.settlement.service.IApplySupplierService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


/**
 * @Description: 供应商
 * @Author: jeecg-boot
 * @Date: 2024-01-02
 * @Version: V1.0
 */
@Service("applySupplierService")
public class ApplySupplierServiceImpl extends ServiceImpl<ApplySupplierMapper, ApplySupplier> implements IApplySupplierService {
//    @Autowired
//    private ISysDepartService sysDepartService;
//    @Autowired
//    FlowCommonService flowCommonService;
//    @Autowired
//    private ISysUserDepartService sysUserDepartService;
//    @Autowired
//    private ISysUserService sysUserService;
//    @Autowired
//    private IFlowDefinitionService flowDefinitionService;
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public String supplierRegister(JSONObject jsonObject, SysUser user, SysDepart sysDepart) {
//        user.setCreateTime(new Date());// 设置创建时间
//        ApplySupplier applySupplier = new ApplySupplier();
//        String salt = oConvertUtils.randomGen(8);
//        String passwordEncode = PasswordUtil.encrypt(jsonObject.getString("username"), jsonObject.getString("password"), salt);
//        user.setSalt(salt);
//        user.setUsername(jsonObject.getString("username"));
//        user.setRealname(jsonObject.getString("username"));
//        user.setPassword(passwordEncode);
//        user.setEmail(jsonObject.getString("email"));
//        user.setPhone(jsonObject.getString("phone"));
//        user.setStatus(CommonConstant.USER_FREEZE);//冻结
//        user.setDelFlag(CommonConstant.DEL_FLAG_0);
//        user.setActivitiSync(CommonConstant.ACT_SYNC_1);
//        user.setAvatar("https://static.jeecg.com/temp/jmlogo_1606575041993.png");
//        sysUserService.addUserWithRole(user, "1744230647813816321");//默认临时角色 供应商
//        //2.新增部门，部门用户关联
//        sysDepart.setOrgType("1");
//        sysDepart.setOrgCode(jsonObject.getString("unifiedSocialCreditCode"));
//        sysDepart.setDepartName(jsonObject.getString("supplierName"));
//        sysDepart.setIzLeaf(CommonConstant.IS_LEAF);
//        sysDepart.setDelFlag(String.valueOf(CommonConstant.DEL_FLAG_0));
//        sysDepart.setStatus(CommonConstant.STATUS_0);//待审核
//        sysDepartService.saveOrUpdate(sysDepart);
//        //新增用户部门关系表
//        sysUserDepartService.saveUserDepart(sysDepart.getId(), user.getId());
//        //2.供应商注册
//        applySupplier.setSupplierName(jsonObject.getString("supplierName"));
//        applySupplier.setLegalPersonName(jsonObject.getString("legalPersonName"));
//        applySupplier.setLegalPersonIdcard(jsonObject.getString("legalPersonIdcard"));
//        applySupplier.setLegalPersonPhone(jsonObject.getString("legalPersonPhone"));
//        applySupplier.setUnifiedSocialCreditCode(jsonObject.getString("unifiedSocialCreditCode"));
//        applySupplier.setBusinessLicenseStartDate(jsonObject.getJSONArray("businessLicenseDate").get(0).toString());
//        applySupplier.setBusinessLicenseEndDate(jsonObject.getJSONArray("businessLicenseDate").get(1).toString());
//        applySupplier.setBusinessLicenseFile(jsonObject.getString("businessLicenseFile"));
//        applySupplier.setFirmAddress(jsonObject.getString("firmAddress"));
//        applySupplier.setStatus(CommonConstant.STATUS_1);//1供应商、2内部单位
//        applySupplier.setStatus(CommonConstant.STATUS_0);//待审核
//        saveOrUpdate(applySupplier);
//        String supplierId = applySupplier.getId();
//        //4、流程供应商入驻申请流程
//        HashMap variables = new HashMap<>();
//        variables.put("dataId", supplierId);
//        ApplyProject applyProject = applyProjectService.getById(jsonObject.getString("projectId"));
//        SysDepart sysDepart = sysDepartService.getDepartById(applyProject.getUnit());
//        variables.put("orgCode", sysDepart.getOrgCode());
//        variables.put(ProcessConstants.PROCESS_INITIATOR, jsonObject.getString("username"));
//        flowCommonService.initActBusiness("供应商入驻申请流程", supplierId, "applySupplierService", "Process_1706093271175");
//        flowDefinitionService.startProcessInstanceByKey("Process_1706093271175", variables);
//        return supplierId;
//    }

//    @Override
//    public void afterFlowHandle(FlowMyBusiness business) {
//        //流程操作后做些什么
//        business.getTaskNameId();//接下来审批的节点
//        business.getValues();//前端传进来的参数
//        String actStatus = business.getActStatus();//流程状态 ActStatus.java
//        SysUser sysUser = sysUserService.getUserByName(business.getProposer());
//        if(StringUtils.equals("审批通过",actStatus)){
//            sysUserService.updateStatus(sysUser.getId(),CommonConstant.STATUS_1);
//            ApplySupplier applySupplier= getById(business.getDataId());
//            applySupplier.setStatus(CommonConstant.STATUS_1);//待审核
//            saveOrUpdate(applySupplier);
//        }
//    }

}
