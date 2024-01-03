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
import org.jeecg.modules.demo.settlement.entity.ApplyNews;
import org.jeecg.modules.demo.settlement.service.IApplyNewsService;

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
 * @Description: 行业动态
 * @Author: jeecg-boot
 * @Date:   2023-12-21
 * @Version: V1.0
 */
@Api(tags="行业动态")
@RestController
@RequestMapping("/settlement/applyNews")
@Slf4j
public class ApplyNewsController extends JeecgController<ApplyNews, IApplyNewsService> {
	@Autowired
	private IApplyNewsService applyNewsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param applyNews
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "行业动态-分页列表查询")
	@ApiOperation(value="行业动态-分页列表查询", notes="行业动态-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ApplyNews>> queryPageList(ApplyNews applyNews,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ApplyNews> queryWrapper = QueryGenerator.initQueryWrapper(applyNews, req.getParameterMap());
		Page<ApplyNews> page = new Page<ApplyNews>(pageNo, pageSize);
		IPage<ApplyNews> pageList = applyNewsService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param applyNews
	 * @return
	 */
	@AutoLog(value = "行业动态-添加")
	@ApiOperation(value="行业动态-添加", notes="行业动态-添加")
	@RequiresPermissions("settlement:apply_news:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ApplyNews applyNews) {
		applyNewsService.save(applyNews);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param applyNews
	 * @return
	 */
	@AutoLog(value = "行业动态-编辑")
	@ApiOperation(value="行业动态-编辑", notes="行业动态-编辑")
	@RequiresPermissions("settlement:apply_news:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ApplyNews applyNews) {
		applyNewsService.updateById(applyNews);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "行业动态-通过id删除")
	@ApiOperation(value="行业动态-通过id删除", notes="行业动态-通过id删除")
	@RequiresPermissions("settlement:apply_news:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		applyNewsService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "行业动态-批量删除")
	@ApiOperation(value="行业动态-批量删除", notes="行业动态-批量删除")
	@RequiresPermissions("settlement:apply_news:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.applyNewsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "行业动态-通过id查询")
	@ApiOperation(value="行业动态-通过id查询", notes="行业动态-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ApplyNews> queryById(@RequestParam(name="id",required=true) String id) {
		ApplyNews applyNews = applyNewsService.getById(id);
		if(applyNews==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(applyNews);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param applyNews
    */
    @RequiresPermissions("settlement:apply_news:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ApplyNews applyNews) {
        return super.exportXls(request, applyNews, ApplyNews.class, "行业动态");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("settlement:apply_news:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ApplyNews.class);
    }

}
