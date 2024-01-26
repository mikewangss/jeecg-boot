package org.jeecg.modules.demo.settlement.controller;

import org.jeecg.common.api.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FlowableController {
    /**
     * 启动流程
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/startProcess", method = RequestMethod.GET)
    public Result<?> startProcess(HttpServletRequest request, HttpServletResponse response) {
        //1.关联流程
        String dataId = request.getParameter("dataId");
//        flowableService.relationAct(dataId);
        //2.启动流程
        return Result.OK();
    }

}
