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
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.settlement.entity.Announcement;
import org.jeecg.modules.demo.settlement.service.IAnnouncementService;

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
 * @Description: 通知公告
 * @Author: jeecg-boot
 * @Date:   2023-11-16
 * @Version: V1.0
 */
@Api(tags="通知公告")
@RestController
@RequestMapping("/settlement/announcement")
@Slf4j
public class AnnouncementController extends JeecgController<Announcement, IAnnouncementService> {
	@Autowired
	private IAnnouncementService announcementService;
	
	/**
	 * 分页列表查询
	 *
	 * @param announcement
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "通知公告-分页列表查询")
	@ApiOperation(value="通知公告-分页列表查询", notes="通知公告-分页列表查询")
	@GetMapping(value = "/list")
	@PermissionData(pageComponent="settlement/announcement/AnnouncementList")
	public Result<IPage<Announcement>> queryPageList(Announcement announcement,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Announcement> queryWrapper = QueryGenerator.initQueryWrapper(announcement, req.getParameterMap());
		Page<Announcement> page = new Page<Announcement>(pageNo, pageSize);
		IPage<Announcement> pageList = announcementService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param announcement
	 * @return
	 */
	@AutoLog(value = "通知公告-添加")
	@ApiOperation(value="通知公告-添加", notes="通知公告-添加")
	@RequiresPermissions("settlement:announcement:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Announcement announcement) {
		announcementService.save(announcement);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param announcement
	 * @return
	 */
	@AutoLog(value = "通知公告-编辑")
	@ApiOperation(value="通知公告-编辑", notes="通知公告-编辑")
	@RequiresPermissions("settlement:announcement:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Announcement announcement) {
		announcementService.updateById(announcement);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "通知公告-通过id删除")
	@ApiOperation(value="通知公告-通过id删除", notes="通知公告-通过id删除")
	@RequiresPermissions("settlement:announcement:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		announcementService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "通知公告-批量删除")
	@ApiOperation(value="通知公告-批量删除", notes="通知公告-批量删除")
	@RequiresPermissions("settlement:announcement:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.announcementService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "通知公告-通过id查询")
	@ApiOperation(value="通知公告-通过id查询", notes="通知公告-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Announcement> queryById(@RequestParam(name="id",required=true) String id) {
		Announcement announcement = announcementService.getById(id);
		if(announcement==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(announcement);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param announcement
    */
    @RequiresPermissions("settlement:announcement:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Announcement announcement) {
        return super.exportXls(request, announcement, Announcement.class, "通知公告");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("settlement:announcement:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Announcement.class);
    }

}
