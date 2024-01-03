package org.jeecg.modules.demo.settlement.controller;

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
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.settlement.entity.ApplySupplier;
import org.jeecg.modules.demo.settlement.service.IApplySupplierService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
 * @Description: 供应商
 * @Author: jeecg-boot
 * @Date:   2024-01-02
 * @Version: V1.0
 */
@Api(tags="供应商")
@RestController
@RequestMapping("/settlement/applySupplier")
@Slf4j
public class ApplySupplierController extends JeecgController<ApplySupplier, IApplySupplierService> {
	@Autowired
	private IApplySupplierService applySupplierService;
	
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
	@ApiOperation(value="供应商-分页列表查询", notes="供应商-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ApplySupplier>> queryPageList(ApplySupplier applySupplier,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ApplySupplier> queryWrapper = QueryGenerator.initQueryWrapper(applySupplier, req.getParameterMap());
		Page<ApplySupplier> page = new Page<ApplySupplier>(pageNo, pageSize);
		IPage<ApplySupplier> pageList = applySupplierService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param applySupplier
	 * @return
	 */
	@AutoLog(value = "供应商-添加")
	@ApiOperation(value="供应商-添加", notes="供应商-添加")
	@RequiresPermissions("settlement:apply_supplier:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ApplySupplier applySupplier) {
		applySupplierService.save(applySupplier);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param applySupplier
	 * @return
	 */
	@AutoLog(value = "供应商-编辑")
	@ApiOperation(value="供应商-编辑", notes="供应商-编辑")
	@RequiresPermissions("settlement:apply_supplier:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ApplySupplier applySupplier) {
		applySupplierService.updateById(applySupplier);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "供应商-通过id删除")
	@ApiOperation(value="供应商-通过id删除", notes="供应商-通过id删除")
	@RequiresPermissions("settlement:apply_supplier:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		applySupplierService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "供应商-批量删除")
	@ApiOperation(value="供应商-批量删除", notes="供应商-批量删除")
	@RequiresPermissions("settlement:apply_supplier:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
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
	@ApiOperation(value="供应商-通过id查询", notes="供应商-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ApplySupplier> queryById(@RequestParam(name="id",required=true) String id) {
		ApplySupplier applySupplier = applySupplierService.getById(id);
		if(applySupplier==null) {
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
