package org.jeecg.modules.demo.settlement.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.StringUtil;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.settlement.entity.ApplyInfo;
import org.jeecg.modules.demo.settlement.entity.ApplySupplier;
import org.jeecg.modules.demo.settlement.service.IApplySupplierFormService;
import org.jeecg.modules.demo.settlement.service.IApplySupplierService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.flowable.apithird.service.FlowCommonService;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.model.DepartIdModel;
import org.jeecg.modules.system.service.ISysUserDepartService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 供应商
 * @Author: jeecg-boot
 * @Date: 2024-01-02
 * @Version: V1.0
 */
@Api(tags = "供应商")
@RestController
@RequestMapping("/settlement/applySupplier")
@Slf4j
public class ApplySupplierController extends JeecgController<ApplySupplier, IApplySupplierService> {
    @Autowired
    private IApplySupplierService applySupplierService;
    @Autowired
    private ISysUserDepartService sysUserDepartService;
    @Autowired
    FlowCommonService flowCommonService;
    @Autowired
    IApplySupplierFormService applySupplierFormService;

    /**
     * @param userid
     * @return
     */
    //@AutoLog(value = "供应商-分页列表查询")
    @ApiOperation(value = "供应商-我的列表查询", notes = "供应商-我的列表查询")
    @GetMapping(value = "/mySupplierList")
    public Result<List<ApplySupplier>> mySupplierList(@RequestParam(name = "userid") String userid) {
        List<ApplySupplier> applySupplierList = new ArrayList<>();
        List<DepartIdModel> depIdModelList = sysUserDepartService.queryDepartIdsOfUser(userid);
        for (DepartIdModel depIdModel : depIdModelList) {
            ApplySupplier applySupplier = applySupplierService.getOne(new QueryWrapper<ApplySupplier>()
                    .lambda()
                    .eq(ApplySupplier::getSupplierName, depIdModel.getTitle()));
            if (applySupplier != null) {
                applySupplierList.add(applySupplier);
            }
        }
        return Result.OK(applySupplierList);
    }

    /**
     * 分页列表查询
     *
     * @return
     */
    @ApiOperation(value = "供应商-列表查询", notes = "供应商-列表查询")
    @GetMapping(value = "/querySupplierList")
    public Result<List<ApplySupplier>> querySupplierList(@RequestParam(name = "type", required = false) String type,
                                                         @RequestParam(name = "keyword", required = false) String keyword) {
        List<ApplySupplier> applySupplierList = new ArrayList<>();
        QueryWrapper<ApplySupplier> queryWrapper = new QueryWrapper<ApplySupplier>();
        if (!StringUtil.isNullOrEmpty(type)) {
            queryWrapper.lambda().eq(ApplySupplier::getType, type);
        }
        if (!StringUtil.isNullOrEmpty(keyword)) {
            queryWrapper.lambda().like(ApplySupplier::getSupplierName, keyword);
        }
        applySupplierList = applySupplierService.list(queryWrapper);
        if (applySupplierList == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(applySupplierList);
    }

    /**
     * 分页列表查询
     *
     * @param applySupplier
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "供应商-分页列表查询")
    @ApiOperation(value = "供应商-分页列表查询", notes = "供应商-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<ApplySupplier>> queryPageList(ApplySupplier applySupplier,
                                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                      HttpServletRequest req) {
        QueryWrapper<ApplySupplier> queryWrapper = QueryGenerator.initQueryWrapper(applySupplier, req.getParameterMap());
        Page<ApplySupplier> page = new Page<ApplySupplier>(pageNo, pageSize);
        IPage<ApplySupplier> pageList = applySupplierService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     * @param applySupplier
     * @return
     */
    @AutoLog(value = "供应商-添加")
    @ApiOperation(value = "供应商-添加", notes = "供应商-添加")
    @RequiresPermissions("settlement:apply_supplier:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody ApplySupplier applySupplier) {
        applySupplierService.save(applySupplier);
        return Result.OK("添加成功！");
    }

    /**
     * 供应商注册接口
     *
     * @param jsonObject
     * @param user
     * @return
     */
    @PostMapping("/supplierRegister")
    public Result<JSONObject> supplierRegister(@RequestBody JSONObject jsonObject, SysUser user, SysDepart sysDepart) {
        Result<JSONObject> result = new Result<JSONObject>();
        try {
            QueryWrapper<ApplySupplier> queryWrapper = new QueryWrapper<ApplySupplier>();
            queryWrapper.or().eq("supplier_name", jsonObject.getString("supplierName")).or().
                    eq("unified_social_credit_code", jsonObject.getString("unifiedSocialCreditCode"));
            ApplySupplier applySupplier = applySupplierService.getOne(queryWrapper);
            if (applySupplier != null) {
                return  result.error500("注册失败，供应商已存在~");
            }
            String supplierId = applySupplierFormService.supplierRegister(jsonObject, user, sysDepart);
            result.success("注册成功");
        } catch (Exception e) {
            result.error500("注册失败");
        }
        return result;
    }

    /**
     * 编辑
     *
     * @param applySupplier
     * @return
     */
    @AutoLog(value = "供应商-编辑")
    @ApiOperation(value = "供应商-编辑", notes = "供应商-编辑")
    @RequiresPermissions("settlement:apply_supplier:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody ApplySupplier applySupplier) {
        applySupplierService.updateById(applySupplier);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "供应商-通过id删除")
    @ApiOperation(value = "供应商-通过id删除", notes = "供应商-通过id删除")
    @RequiresPermissions("settlement:apply_supplier:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        applySupplierService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "供应商-批量删除")
    @ApiOperation(value = "供应商-批量删除", notes = "供应商-批量删除")
    @RequiresPermissions("settlement:apply_supplier:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.applySupplierService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "供应商-通过id查询")
    @ApiOperation(value = "供应商-通过id查询", notes = "供应商-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<ApplySupplier> queryById(@RequestParam(name = "id", required = true) String id) {
        ApplySupplier applySupplier = applySupplierService.getById(id);
        if (applySupplier == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(applySupplier);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param applySupplier
     */
    @RequiresPermissions("settlement:apply_supplier:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ApplySupplier applySupplier) {
        return super.exportXls(request, applySupplier, ApplySupplier.class, "供应商");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("settlement:apply_supplier:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ApplySupplier.class);
    }

}
