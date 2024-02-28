package org.jeecg.modules.demo.settlement.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.settlement.entity.ApplyFileMenu;
import org.jeecg.modules.demo.settlement.entity.ApplyFiles;
import org.jeecg.modules.demo.settlement.entity.ApplyProject;
import org.jeecg.modules.demo.settlement.entity.ApplySupplier;
import org.jeecg.modules.demo.settlement.service.IApplyFilesService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.settlement.service.IApplyProjectService;
import org.jeecg.modules.demo.settlement.service.IApplySupplierService;
import org.jeecg.modules.flowable.apithird.entity.SysUser;
import org.jeecg.modules.flowable.apithird.service.IFlowThirdService;
import org.jeecg.modules.system.model.DepartIdModel;
import org.jeecg.modules.system.service.ISysUserDepartService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 结算文件
 * @Author: jeecg-boot
 * @Date: 2024-01-02
 * @Version: V1.0
 */
@Api(tags = "结算文件")
@RestController
@RequestMapping("/settlement/applyFiles")
@Slf4j
public class ApplyFilesController extends JeecgController<ApplyFiles, IApplyFilesService> {
    @Autowired
    ISysBaseAPI sysBaseAPI;
    @Autowired
    private IApplyFilesService applyFilesService;
    @Autowired
    private ISysUserDepartService sysUserDepartService;
    @Autowired
    private IFlowThirdService iFlowThirdService;
    @Autowired
    private IApplyProjectService applyProjectService;
    @Autowired
    private IApplySupplierService applySupplierService;
    /**
     * 分页列表查询
     *
     * @param applyFiles
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "结算文件-分页列表查询")
    @ApiOperation(value = "结算文件-分页列表查询", notes = "结算文件-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<ApplyFiles>> queryPageList(ApplyFiles applyFiles,
                                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                   HttpServletRequest req) {
        SysUser loginUser = iFlowThirdService.getLoginUser();
        List<String> roles =sysBaseAPI.getRolesByUsername(loginUser.getUsername());
        Page<ApplyFiles> page = new Page<>(pageNo, pageSize);
        // 构建查询条件
        QueryWrapper<ApplyFiles> queryWrapper = new QueryWrapper<>();
        if(!roles.contains("admin")){
            List<DepartIdModel> depIdModelList = sysUserDepartService.queryDepartIdsOfUser(loginUser.getId());
            // 如果有多个 depIdModelList，则循环查询并合并结果
            for (DepartIdModel depIdModel : depIdModelList) {
                ApplySupplier applySupplier = applySupplierService.getOne(new QueryWrapper<ApplySupplier>()
                        .lambda()
                        .eq(ApplySupplier::getSupplierName, depIdModel.getTitle()));
                List<ApplyProject> applyProjectArrayList = new ArrayList<>();
                if (applySupplier != null) {
                    applyProjectArrayList = applyProjectService.list(
                            new QueryWrapper<ApplyProject>()
                                    .lambda()
                                    .eq(ApplyProject::getBidder, applySupplier.getId())
                    );
                    // 检查是否有相关的 ApplyProject，如果有则查询对应的 ApplyFiles
                    if (applyProjectArrayList != null && !applyProjectArrayList.isEmpty()) {
                        for (ApplyProject applyProject : applyProjectArrayList) {
                            queryWrapper.or().eq("project_id", applyProject.getId());
                        }
                    }
                }
            }
            // 如果 queryWrapper 为空，则直接返回一个空的 Page 对象
            if (queryWrapper.isEmptyOfWhere()) {
                return Result.OK(page);
            }
        }

        IPage<ApplyFiles> pageList = applyFilesService.page(page, queryWrapper);
        // 返回结果
        return Result.OK(pageList);

//        QueryWrapper<ApplyFiles> queryWrapper = QueryGenerator.initQueryWrapper(applyFiles, req.getParameterMap());
//        Page<ApplyFiles> page = new Page<ApplyFiles>(pageNo, pageSize);
//        IPage<ApplyFiles> pageList = applyFilesService.page(page, queryWrapper);
//        return Result.OK(pageList);
    }

    /**
     * 通过业务id查询
     *
     * @param bizId,fc
     * @return
     */
    //@AutoLog(value = "附件管理通过主表ID查询")
    @ApiOperation(value = "附件管理主表ID查询", notes = "附件管理-通主表ID查询")
    @GetMapping(value = "/queryApplyFilesByBizId")
    public Result<List<ApplyFiles>> queryApplyFilesByBizId(@RequestParam(name = "bizId", required = true) String bizId, @RequestParam(name = "fc") String fc) {
        List<ApplyFiles> applyFilesList = applyFilesService.selectByBizId(bizId, fc);
        return Result.OK(applyFilesList);
    }

    /**
     * 查询文件二级分类
     *
     * @param parent
     * @return
     */
    //@AutoLog(value = "查询文件二级分类")
    @ApiOperation(value = "查询文件二级分类", notes = "附件管理-查询文件二级分类")
    @GetMapping(value = "/getSubFileMenu")
    public Result<List<ApplyFileMenu>> getSubFileMenu(@RequestParam(name = "parent", required = true) String parent) {
        List<ApplyFileMenu> applyFileMenus = applyFilesService.getSubFileMenu(parent);
        return Result.OK(applyFileMenus);
    }

    /**
     * 通过业务id查询
     *
     * @param bizId,fc
     * @return
     */
    //@AutoLog(value = "附件管理通过主表ID查询")
    @ApiOperation(value = "附件管理主表ID查询", notes = "附件管理-通主表ID查询")
    @GetMapping(value = "/queryApplyFilesByProjectId")
    public Result<List<ApplyFiles>> queryApplyFilesByProjectId(@RequestParam(name = "projectId", required = true) String projectId, @RequestParam(name = "fc") String fc) {
        List<ApplyFiles> applyFilesList = applyFilesService.selectByProjectId(projectId, fc);
        return Result.OK(applyFilesList);
    }

    /**
     * 添加
     *
     * @param applyFiles
     * @return
     */
    @AutoLog(value = "结算文件-添加")
    @ApiOperation(value = "结算文件-添加", notes = "结算文件-添加")
    @RequiresPermissions("settlement:apply_files:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody ApplyFiles applyFiles) {
        applyFilesService.save(applyFiles);
        return Result.OK("添加成功！");
    }

    /**
     * 添加
     *
     * @param applyFilesList
     * @return
     */
    @AutoLog(value = "结算文件-添加")
    @ApiOperation(value = "结算文件-添加", notes = "结算文件-添加")
    @RequiresPermissions("settlement:apply_files:add")
    @PostMapping(value = "/addApplyFiles")
    public Result<String> addApplyFiles(@RequestBody List<ApplyFiles> applyFilesList) {
        for (ApplyFiles applyFiles : applyFilesList) {
            applyFilesService.save(applyFiles);
        }
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param applyFiles
     * @return
     */
    @AutoLog(value = "结算文件-编辑")
    @ApiOperation(value = "结算文件-编辑", notes = "结算文件-编辑")
    @RequiresPermissions("settlement:apply_files:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody ApplyFiles applyFiles) {
        applyFilesService.updateById(applyFiles);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "结算文件-通过id删除")
    @ApiOperation(value = "结算文件-通过id删除", notes = "结算文件-通过id删除")
    @RequiresPermissions("settlement:apply_files:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        applyFilesService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "结算文件-批量删除")
    @ApiOperation(value = "结算文件-批量删除", notes = "结算文件-批量删除")
    @RequiresPermissions("settlement:apply_files:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.applyFilesService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "结算文件-通过id查询")
    @ApiOperation(value = "结算文件-通过id查询", notes = "结算文件-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<ApplyFiles> queryById(@RequestParam(name = "id", required = true) String id) {
        ApplyFiles applyFiles = applyFilesService.getById(id);
        if (applyFiles == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(applyFiles);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param applyFiles
     */
    @RequiresPermissions("settlement:apply_files:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ApplyFiles applyFiles) {
        return super.exportXls(request, applyFiles, ApplyFiles.class, "结算文件");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("settlement:apply_files:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ApplyFiles.class);
    }

}
