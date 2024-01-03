package org.jeecg.modules.demo.settlement.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.settlement.entity.ApplyContract;
import org.jeecg.modules.demo.settlement.entity.ApplyProject;
import org.jeecg.modules.demo.settlement.vo.ApplyProjectPage;
import org.jeecg.modules.demo.settlement.service.IApplyProjectService;
import org.jeecg.modules.demo.settlement.service.IApplyContractService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;


 /**
 * @Description: 项目
 * @Author: jeecg-boot
 * @Date:   2024-01-02
 * @Version: V1.0
 */
@Api(tags="项目")
@RestController
@RequestMapping("/settlement/applyProject")
@Slf4j
public class ApplyProjectController {
	@Autowired
	private IApplyProjectService applyProjectService;
	@Autowired
	private IApplyContractService applyContractService;
	
	/**
	 * 分页列表查询
	 *
	 * @param applyProject
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "项目-分页列表查询")
	@ApiOperation(value="项目-分页列表查询", notes="项目-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ApplyProject>> queryPageList(ApplyProject applyProject,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ApplyProject> queryWrapper = QueryGenerator.initQueryWrapper(applyProject, req.getParameterMap());
		Page<ApplyProject> page = new Page<ApplyProject>(pageNo, pageSize);
		IPage<ApplyProject> pageList = applyProjectService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param applyProjectPage
	 * @return
	 */
	@AutoLog(value = "项目-添加")
	@ApiOperation(value="项目-添加", notes="项目-添加")
    @RequiresPermissions("settlement:apply_project:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ApplyProjectPage applyProjectPage) {
		ApplyProject applyProject = new ApplyProject();
		BeanUtils.copyProperties(applyProjectPage, applyProject);
		applyProjectService.saveMain(applyProject, applyProjectPage.getApplyContractList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param applyProjectPage
	 * @return
	 */
	@AutoLog(value = "项目-编辑")
	@ApiOperation(value="项目-编辑", notes="项目-编辑")
    @RequiresPermissions("settlement:apply_project:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ApplyProjectPage applyProjectPage) {
		ApplyProject applyProject = new ApplyProject();
		BeanUtils.copyProperties(applyProjectPage, applyProject);
		ApplyProject applyProjectEntity = applyProjectService.getById(applyProject.getId());
		if(applyProjectEntity==null) {
			return Result.error("未找到对应数据");
		}
		applyProjectService.updateMain(applyProject, applyProjectPage.getApplyContractList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "项目-通过id删除")
	@ApiOperation(value="项目-通过id删除", notes="项目-通过id删除")
    @RequiresPermissions("settlement:apply_project:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		applyProjectService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "项目-批量删除")
	@ApiOperation(value="项目-批量删除", notes="项目-批量删除")
    @RequiresPermissions("settlement:apply_project:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.applyProjectService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "项目-通过id查询")
	@ApiOperation(value="项目-通过id查询", notes="项目-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ApplyProject> queryById(@RequestParam(name="id",required=true) String id) {
		ApplyProject applyProject = applyProjectService.getById(id);
		if(applyProject==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(applyProject);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "合同通过主表ID查询")
	@ApiOperation(value="合同主表ID查询", notes="合同-通主表ID查询")
	@GetMapping(value = "/queryApplyContractByMainId")
	public Result<List<ApplyContract>> queryApplyContractListByMainId(@RequestParam(name="id",required=true) String id) {
		List<ApplyContract> applyContractList = applyContractService.selectByMainId(id);
		return Result.OK(applyContractList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param applyProject
    */
    @RequiresPermissions("settlement:apply_project:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ApplyProject applyProject) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<ApplyProject> queryWrapper = QueryGenerator.initQueryWrapper(applyProject, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //配置选中数据查询条件
      String selections = request.getParameter("selections");
      if(oConvertUtils.isNotEmpty(selections)) {
         List<String> selectionList = Arrays.asList(selections.split(","));
         queryWrapper.in("id",selectionList);
      }
      //Step.2 获取导出数据
      List<ApplyProject> applyProjectList = applyProjectService.list(queryWrapper);

      // Step.3 组装pageList
      List<ApplyProjectPage> pageList = new ArrayList<ApplyProjectPage>();
      for (ApplyProject main : applyProjectList) {
          ApplyProjectPage vo = new ApplyProjectPage();
          BeanUtils.copyProperties(main, vo);
          List<ApplyContract> applyContractList = applyContractService.selectByMainId(main.getId());
          vo.setApplyContractList(applyContractList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "项目列表");
      mv.addObject(NormalExcelConstants.CLASS, ApplyProjectPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("项目数据", "导出人:"+sysUser.getRealname(), "项目"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("settlement:apply_project:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          // 获取上传文件对象
          MultipartFile file = entity.getValue();
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<ApplyProjectPage> list = ExcelImportUtil.importExcel(file.getInputStream(), ApplyProjectPage.class, params);
              for (ApplyProjectPage page : list) {
                  ApplyProject po = new ApplyProject();
                  BeanUtils.copyProperties(page, po);
                  applyProjectService.saveMain(po, page.getApplyContractList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}