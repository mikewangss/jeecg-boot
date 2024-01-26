package org.jeecg.modules.demo.settlement.service;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.demo.settlement.entity.ApplyContract;
import org.jeecg.modules.demo.settlement.entity.ApplyFiles;
import org.jeecg.modules.demo.settlement.entity.ApplyProject;
import org.jeecg.modules.demo.settlement.entity.ApplySupplier;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.entity.SysUser;

import java.util.List;

/**
 * @Description: 供应商
 * @Author: jeecg-boot
 * @Date: 2024-01-02
 * @Version: V1.0
 */
public interface IApplySupplierService extends IService<ApplySupplier> {
    /**
     * 供应商入驻
     *
     * @param jsonObject
     * @param user
     * @param sysDepart
     */
    public String supplierRegister(JSONObject jsonObject, SysUser user, SysDepart sysDepart);

}
