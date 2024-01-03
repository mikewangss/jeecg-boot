package org.jeecg.modules.demo.settlement.controller;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.modules.demo.settlement.entity.ApplyProcessMapping;
import org.jeecg.modules.demo.settlement.entity.ApplyWorkflowDTO;
import org.jeecg.modules.demo.settlement.entity.ProcessDTO;
import org.jeecg.modules.demo.settlement.service.impl.ApplyProcessServiceImpl;
import org.jeecg.modules.demo.settlement.util.HttpGetAndPost;
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
import org.jeecg.modules.demo.settlement.entity.ApplyInfo;
import org.jeecg.modules.demo.settlement.vo.ApplyInfoPage;
import org.jeecg.modules.demo.settlement.service.IApplyInfoService;
import org.jeecg.modules.demo.settlement.service.IApplyProcessService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * @Description: 结算申请
 * @Author: jeecg-boot
 * @Date: 2023-11-20
 * @Version: V1.0
 */
@Api(tags = "结算申请")
@RestController
@RequestMapping("/settlement/applyInfo")
@Slf4j
public class ApplyInfoController {
    @Autowired
    private IApplyInfoService applyInfoService;
    @Autowired
    private IApplyProcessService applyWorkflowService;
    @Autowired
    HttpGetAndPost httpGetAndPost;
    @Autowired
    private IApplyProcessService applyProcessService;

    /**
     * 分页列表查询
     *
     * @param applyInfo
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "结算申请-分页列表查询")
    @ApiOperation(value = "结算申请-分页列表查询", notes = "结算申请-分页列表查询")
    @GetMapping(value = "/list")
    @PermissionData(pageComponent = "settlement/apply/applyInfoList")
    public Result<IPage<ApplyInfo>> queryPageList(ApplyInfo applyInfo,
                                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                  HttpServletRequest req) {
        QueryWrapper<ApplyInfo> queryWrapper = new QueryWrapper<ApplyInfo>();
        //编程方式，给queryWrapper装载数据权限规则,,request.MENU_DATA_AUTHOR_RULES
        QueryGenerator.installAuthMplus(queryWrapper, ApplyInfo.class);
        Page<ApplyInfo> page = new Page<ApplyInfo>(pageNo, pageSize);
        IPage<ApplyInfo> pageList = applyInfoService.page(page, queryWrapper);
        return Result.OK(pageList);
//		QueryWrapper<ApplyInfo> queryWrapper = QueryGenerator.initQueryWrapper(applyInfo, req.getParameterMap());
//		Page<ApplyInfo> page = new Page<ApplyInfo>(pageNo, pageSize);
//		IPage<ApplyInfo> pageList = applyInfoService.page(page, queryWrapper);
//		return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param applyInfoPage
     * @return
     */
    @AutoLog(value = "结算申请-添加")
    @ApiOperation(value = "结算申请-添加", notes = "结算申请-添加")
    @RequiresPermissions("settlement:apply_info:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody ApplyInfo applyInfo) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
////        HttpGetAndPost httpGetAndPost = new HttpGetAndPost();
//        String jsonStr = mapper.writeValueAsString(applyInfoPage);
//        System.out.println(jsonStr);
//        //把转好的数据保存到文件中
//        ApplyInfo applyInfo = new ApplyInfo();
//        BeanUtils.copyProperties(applyInfoPage, applyInfo);
        String apply_id = applyInfoService.saveMain(applyInfo);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String username = sysUser.getUsername();
        HashMap<String, Object> map = new HashMap<>();
        map.put("taskUser", username);
        map.put("money", applyInfo.getMoney());
//        HttpGetAndPost httpGetAndPost = new HttpGetAndPost();
        String processInstanceId = httpGetAndPost.httpPost("http://127.0.0.1:8081/workflow/start", map);
        ApplyProcessMapping applyProcessMapping = new ApplyProcessMapping(apply_id, processInstanceId);
        applyProcessService.save(applyProcessMapping);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param applyInfoPage
     * @return
     */
    @AutoLog(value = "结算申请-编辑")
    @ApiOperation(value = "结算申请-编辑", notes = "结算申请-编辑")
    @RequiresPermissions("settlement:apply_info:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody ApplyInfoPage applyInfoPage) {
        ApplyInfo applyInfo = new ApplyInfo();
        BeanUtils.copyProperties(applyInfoPage, applyInfo);
        ApplyInfo applyInfoEntity = applyInfoService.getById(applyInfo.getId());
        if (applyInfoEntity == null) {
            return Result.error("未找到对应数据");
        }
        applyInfoService.updateMain(applyInfo, applyInfoPage.getApplyWorkflowDTOList());
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "结算申请-通过id删除")
    @ApiOperation(value = "结算申请-通过id删除", notes = "结算申请-通过id删除")
    @RequiresPermissions("settlement:apply_info:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        applyInfoService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "结算申请-批量删除")
    @ApiOperation(value = "结算申请-批量删除", notes = "结算申请-批量删除")
    @RequiresPermissions("settlement:apply_info:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.applyInfoService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }


    /**
     * 通过process_id查询
     *
     * @param process_id
     * @return
     */
    //@AutoLog(value = "结算申请-通过id查询")
    @ApiOperation(value = "结算申请-通过id查询", notes = "结算申请-通过id查询")
    @GetMapping(value = "/queryByProcessId")
    public Result<ApplyInfo> queryByProcessId(@RequestParam(name = "process_id", required = true) String process_id) {
        ApplyProcessMapping applyProcessMapping = applyProcessService.queryByProcessId(process_id);
        ApplyInfo applyInfo = applyInfoService.getById(applyProcessMapping.getApply_id());
        if (applyInfo == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(applyInfo);

    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "结算申请-通过id查询")
    @ApiOperation(value = "结算申请-通过id查询", notes = "结算申请-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<ApplyInfo> queryById(@RequestParam(name = "id", required = true) String id) {
        ApplyInfo applyInfo = applyInfoService.getById(id);
        if (applyInfo == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(applyInfo);

    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "结算申请-通过id查询")
    @ApiOperation(value = "结算申请-通过id查询", notes = "结算申请-通过id查询")
    @GetMapping(value = "/queryProcessId")
    public Result<String> queryProcessId(@RequestParam(name = "id", required = true) String id) {
        ApplyProcessMapping applyProcessMapping = applyWorkflowService.getById(id);
        return Result.OK(applyProcessMapping.getProcess_instance_id());

    }

    /**
     * 导出excel
     *
     * @param request
     * @param applyInfo
     */
    @RequiresPermissions("settlement:apply_info:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ApplyInfo applyInfo) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<ApplyInfo> queryWrapper = QueryGenerator.initQueryWrapper(applyInfo, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        //配置选中数据查询条件
        String selections = request.getParameter("selections");
        if (oConvertUtils.isNotEmpty(selections)) {
            List<String> selectionList = Arrays.asList(selections.split(","));
            queryWrapper.in("id", selectionList);
        }
        //Step.2 获取导出数据
        List<ApplyInfo> applyInfoList = applyInfoService.list(queryWrapper);

        // Step.3 组装pageList
//        List<ApplyInfoPage> pageList = new ArrayList<ApplyInfoPage>();
//        for (ApplyInfo main : applyInfoList) {
//            ApplyInfoPage vo = new ApplyInfoPage();
//            BeanUtils.copyProperties(main, vo);
//            List<ApplyWorkflowDTO> applyWorkflowDTOList = applyWorkflowService.selectByMainId(main.getId());
//            vo.setApplyWorkflowDTOList(applyWorkflowDTOList);
//            pageList.add(vo);
//        }

        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "结算申请列表");
        mv.addObject(NormalExcelConstants.CLASS, ApplyInfoPage.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("结算申请数据", "导出人:" + sysUser.getRealname(), "结算申请"));
//        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("settlement:apply_info:importExcel")
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
                List<ApplyInfoPage> list = ExcelImportUtil.importExcel(file.getInputStream(), ApplyInfoPage.class, params);
                for (ApplyInfoPage page : list) {
                    ApplyInfo po = new ApplyInfo();
                    BeanUtils.copyProperties(page, po);
                    applyInfoService.saveMain(po, page.getApplyWorkflowDTOList());
                }
                return Result.OK("文件导入成功！数据行数:" + list.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
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
