package org.jeecg.modules.demo.settlement.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.demo.settlement.entity.ApplySupplierForm;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.entity.SysUser;

/**
 * @Description: 供应商
 * @Author: jeecg-boot
 * @Date: 2024-01-02
 * @Version: V1.0
 */
public interface IApplySupplierFormService extends IService<ApplySupplierForm> {
    /**
     * 供应商入驻
     *
     * @param jsonObject
     * @param user
     * @param sysDepart
     */
    public String supplierRegister(JSONObject jsonObject, SysUser user, SysDepart sysDepart);


}
