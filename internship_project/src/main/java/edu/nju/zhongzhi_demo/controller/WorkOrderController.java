package edu.nju.zhongzhi_demo.controller;

import edu.nju.zhongzhi_demo.dao.AppRepo;
import edu.nju.zhongzhi_demo.dao.WorkOrderRepo;
import edu.nju.zhongzhi_demo.dao.WorkOrderRsrcRepo;
import edu.nju.zhongzhi_demo.entity.*;
import edu.nju.zhongzhi_demo.enums.ResourceType;
import edu.nju.zhongzhi_demo.enums.WorkOrderStatus;
import edu.nju.zhongzhi_demo.model.para.WorkOrderPara;
import edu.nju.zhongzhi_demo.model.vo.WorkOrderDetailVo;
import edu.nju.zhongzhi_demo.model.vo.WorkOrderVo;
import edu.nju.zhongzhi_demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WorkOrderController {

    @Autowired
    WorkOrderRepo workOrderRepo;
    @Autowired
    WorkOrderRsrcRepo workOrderRsrcRepo;
    @Autowired
    AppRepo appRepo;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    WorkOrderService workOrderService;
    @Autowired
    AccountService accountService;
    @Autowired
    ResourceService resourceService;

    @PostMapping("/workOrder")
    @Transactional
    public void createWorkOrder(@RequestBody WorkOrderPara workOrderPara){

        if(workOrderPara.appId == null){
            throw new RuntimeException(Config.PARA_ERROR_APPID_IS_NULL);
        }

        if(workOrderPara.userId == null){
            throw new RuntimeException(Config.PARA_ERROR_USERID_IS_NULL);
        }

        WorkOrder workOrder = new WorkOrder();
        workOrder.setAppId(workOrderPara.appId);
        workOrder.setApplicantId(workOrderPara.userId);
        workOrder.setStatus(WorkOrderStatus.wait_review);
        workOrder = workOrderRepo.saveAndFlush(workOrder);

        List<WorkOrderRsrc> result = new ArrayList<>();

        if(workOrderPara.cmptList != null && !workOrderPara.cmptList.isEmpty()){
            for(ResrcCmpt resrcCmpt: workOrderPara.cmptList){
                int auditDeptId = this.departmentService.getAuditDeptIdForCmptRsrc(resrcCmpt.getDepartment().getId());
                WorkOrderRsrc workOrderRsrc = new WorkOrderRsrc();
                workOrderRsrc.setAppId(workOrderPara.appId);
                workOrderRsrc.setWorkOrderId(workOrder.getId());
                workOrderRsrc.setResrcId(resrcCmpt.getId());
                workOrderRsrc.setResrcType(ResourceType.compute);
                workOrderRsrc.setReviewDeptId(auditDeptId);
                result.add(workOrderRsrc);
            }
        }

        if(workOrderPara.dataList !=null && !workOrderPara.dataList.isEmpty()){
            for(ResrcData resrcData: workOrderPara.dataList){
                int auditDeptId = this.departmentService.getAuditDeptIdForDataRsrc(resrcData.getDepartment().getId());
                WorkOrderRsrc workOrderRsrc = new WorkOrderRsrc();
                workOrderRsrc.setAppId(workOrderPara.appId);
                workOrderRsrc.setWorkOrderId(workOrder.getId());
                workOrderRsrc.setResrcId(resrcData.getId());
                workOrderRsrc.setResrcType(ResourceType.data);
                workOrderRsrc.setReviewDeptId(auditDeptId);
                result.add(workOrderRsrc);
            }
        }

        if(workOrderPara.apiList !=null && !workOrderPara.apiList.isEmpty()){
            for(ResrcApi resrcApi: workOrderPara.apiList){
                int auditDeptId = this.departmentService.getAuditDeptIdForDataRsrc(resrcApi.getDepartment().getId());
                WorkOrderRsrc workOrderRsrc = new WorkOrderRsrc();
                workOrderRsrc.setAppId(workOrderPara.appId);
                workOrderRsrc.setWorkOrderId(workOrder.getId());
                workOrderRsrc.setResrcId(resrcApi.getId());
                workOrderRsrc.setResrcType(ResourceType.api);
                workOrderRsrc.setReviewDeptId(auditDeptId);
                result.add(workOrderRsrc);
            }
        }

        this.workOrderRsrcRepo.saveAll(result);

    }

    @GetMapping("/isv/{userId}/workOrderList")
    public List<WorkOrderVo> getWorkOrderByUserId(@PathVariable int userId){
        return  this.workOrderService.getWorkOrderListByUserId(userId);
    }

    @GetMapping("isv/workOrder/{id}")
    public WorkOrderDetailVo getWorkOrderDetail(@PathVariable int id){

        WorkOrder workOrder = this.workOrderRepo.getOne(id);
        WorkOrderDetailVo vo = new WorkOrderDetailVo();
        String userName = this.accountService.getById(workOrder.getApplicantId()).getUsername();
        vo.id = id;
        vo.appName = this.appRepo.getOne(workOrder.getAppId()).getName();
        vo.userName = userName;
        vo.status = workOrder.getStatus().toString();
        vo.reviewStatus = workOrder.getStatus().toString();
        vo.resourceInfo = this.resourceService.getResourceInfoByWorkOrderId(id);
        return vo;
    }


    @GetMapping("/audit/{userId}/workOrder")
    public List<WorkOrderVo> getWorkOrderListForAudit(@PathVariable int userId, @RequestParam String status){
        User user = this.accountService.getById(userId);
        if(status.equals(WorkOrderStatus.wait_review.toString())){
            return this.workOrderService.getUnprocessedWorkOrdersByAuditDeptIdAndRole(user.getDeptId(),user.getRole());
        }

        if(status.equals(WorkOrderStatus.processed.toString())){
            return this.workOrderService.getProcessedWorkOrdersByAuditDeptIdAndRole(user.getDeptId(),user.getRole());
        }

        return null;
    }


    @GetMapping("audit/{userId}/workOrder/{id}/")
    public WorkOrderDetailVo getWorkOrderDetailForAuditor(@PathVariable int userId,@RequestBody int id){
        User user = this.accountService.getById(userId);
        WorkOrder workOrder = this.workOrderRepo.getOne(id);
        return this.workOrderService.getWorkOrderDetailForAuditor(user,workOrder);
    }

    public String reviewOrder(){
        return "";
    }


}
