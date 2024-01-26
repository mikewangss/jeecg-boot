package org.jeecg.modules.demo.settlement.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.demo.settlement.entity.ApplyContract;
import org.jeecg.modules.demo.settlement.service.IApplyContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**合同
* @Author: jeecg-boot
* @Date:   2023-12-21
* @Version: V1.0
*/
@Api(tags="合同")
@RestController
@RequestMapping("/settlement/applyContract")
@Slf4j
public class ApplyContractController extends JeecgController<ApplyContract, IApplyContractService> {
   @Autowired
   private IApplyContractService applyContractService;
   
   
   
   
   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   //@AutoLog(value = "合同-通过id查询")
   @ApiOperation(value="合同-通过id查询", notes="合同-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<ApplyContract> queryById(@RequestParam(name="id",required=true) String id) {
       ApplyContract applyContract = applyContractService.getById(id);
       if(applyContract==null) {
           return Result.error("未找到对应数据");
       }
       return Result.OK(applyContract);
   }

  

}
