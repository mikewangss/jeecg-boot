package org.jeecg.modules.flowable.flow;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.flowable.apithird.entity.SysCategory;
import org.jeecg.modules.flowable.apithird.entity.SysPosition;
import org.jeecg.modules.flowable.apithird.entity.SysRole;
import org.jeecg.modules.flowable.apithird.entity.SysUser;
import org.jeecg.modules.flowable.apithird.service.IFlowThirdService;
import org.jeecg.modules.system.service.impl.SysPositionServiceImpl;
import org.jeecg.modules.system.service.impl.SysRoleServiceImpl;
import org.jeecg.modules.system.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * flowable模块必需实现类
 *
 * @author PanMeiCheng
 * @version 1.0
 * @date 2021/11/22
 */
@Service
public class FlowThirdServiceImpl implements IFlowThirdService {
    @Autowired
    ISysBaseAPI sysBaseAPI;
    @Autowired
    SysUserServiceImpl sysUserService;
    @Autowired
    SysRoleServiceImpl sysRoleService;
    @Autowired
    SysPositionServiceImpl sysPositionService;

    @Override
    public SysUser getLoginUser() {
        LoginUser sysUser = null;
        SysUser copyProperties = null;
        try {
            sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            copyProperties = BeanUtil.copyProperties(sysUser, SysUser.class);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return copyProperties;
    }

    @Override
    public List<SysUser> getAllUser(String orgCode, String position) {
        Page<org.jeecg.modules.system.entity.SysUser> page = new Page<>(1, Integer.MAX_VALUE);
        IPage<org.jeecg.modules.system.model.SysUserSysDepartModel> sysUserSysDepartModelIPage = sysUserService.queryUserByOrgCode(orgCode, null, page);
        List<org.jeecg.modules.system.model.SysUserSysDepartModel> records = sysUserSysDepartModelIPage.getRecords();
        List<SysUser> userList = records.stream()
                .map(o -> BeanUtil.copyProperties(o, SysUser.class))
                .filter(o -> StringUtils.isBlank(position) || StringUtils.equals(position, o.getPost()))
                .collect(Collectors.toList());
        return userList;
    }

    @Override
    public List<SysUser> getAllUser() {
        List<org.jeecg.modules.system.entity.SysUser> list = sysUserService.list();
        List<SysUser> userList = list.stream().map(o -> BeanUtil.copyProperties(o, SysUser.class)).collect(Collectors.toList());
        return userList;
    }

    @Override
    public List<SysPosition> getAllPosition() {
        List<org.jeecg.modules.system.entity.SysPosition> list = sysPositionService.list();
        List<SysPosition> positionList = list.stream().map(o -> BeanUtil.copyProperties(o, SysPosition.class)).collect(Collectors.toList());
        return positionList;
    }

    @Override
    public List<SysUser> getUsersByRoleId(String roleId) {
        Page<org.jeecg.modules.system.entity.SysUser> page = new Page<>(1, Integer.MAX_VALUE);
        IPage<org.jeecg.modules.system.entity.SysUser> userByRoleId = sysUserService.getUserByRoleId(page, roleId, null);
        List<org.jeecg.modules.system.entity.SysUser> records = userByRoleId.getRecords();
        List<SysUser> userList = records.stream().map(o -> BeanUtil.copyProperties(o, SysUser.class)).collect(Collectors.toList());
        return userList;
    }


    @Override
    public SysUser getUserByUsername(String username) {
        LoginUser userByName = sysBaseAPI.getUserByName(username);
        return userByName == null ? null : BeanUtil.copyProperties(userByName, SysUser.class);
    }

    @Override
    public List<SysRole> getAllRole() {
        List<org.jeecg.modules.system.entity.SysRole> list = sysRoleService.list();
        List<SysRole> roleList = list.stream().map(o -> BeanUtil.copyProperties(o, SysRole.class)).collect(Collectors.toList());
        return roleList;
    }

    @Override
    public List<SysCategory> getAllCategory() {
        // todo 获取流程分类信息，此处为例子
        SysCategory category1 = new SysCategory();
        category1.setId("oa");
        category1.setName("OA");
        SysCategory category2 = new SysCategory();
        category2.setId("cw");
        category2.setName("财务");
        ArrayList<SysCategory> sysCategories = Lists.newArrayList(category1, category2);
        return sysCategories;
    }

    @Override
    public List<String> getDepartNamesByUsername(String username) {
        List<String> departNamesByUsername = sysBaseAPI.getDepartNamesByUsername(username);
        return departNamesByUsername;
    }
}
