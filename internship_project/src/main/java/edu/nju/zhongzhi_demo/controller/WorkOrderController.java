package edu.nju.zhongzhi_demo.controller;

import edu.nju.zhongzhi_demo.dao.AppRepo;
import edu.nju.zhongzhi_demo.dao.WorkOrderRepo;
import edu.nju.zhongzhi_demo.entity.User;
import edu.nju.zhongzhi_demo.entity.WorkOrder;
import edu.nju.zhongzhi_demo.enums.WorkOrderStatus;
import edu.nju.zhongzhi_demo.model.para.WorkOrderPara;
import edu.nju.zhongzhi_demo.model.vo.WorkOrderDetailVo;
import edu.nju.zhongzhi_demo.model.vo.WorkOrderVo;
import edu.nju.zhongzhi_demo.service.AccountService;
import edu.nju.zhongzhi_demo.service.ResourceService;
import edu.nju.zhongzhi_demo.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class WorkOrderController {

    @Autowired
    WorkOrderRepo workOrderRepo;
    @Autowired
    AppRepo appRepo;
    @Autowired
    WorkOrderService workOrderService;
    @Autowired
    AccountService accountService;
    @Autowired
    ResourceService resourceService;

    @PostMapping("/workOrder")
    @Transactional
    public void createWorkOrder(@RequestBody WorkOrderPara workOrderPara){
        this.workOrderService.createOrder(workOrderPara);
    }

    @GetMapping("/isv/{userId}/workOrderList")
    public List<WorkOrderVo> getWorkOrderList(@PathVariable int userId){
        return  this.workOrderService.getWorkOrderListByUserId(userId);
    }

    @GetMapping("/isv/workOrder/{id}")
    public WorkOrderDetailVo getWorkOrderDetail(@PathVariable int id){

        return this.workOrderService.getWorkOrderDetail(id);
    }


    @GetMapping("/auditor/{userId}/workOrderList")
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


    @GetMapping("/auditor/{userId}/workOrder/{woId}")
    public WorkOrderDetailVo getWorkOrderDetailForAuditor(@PathVariable int userId,@PathVariable int woId){
        User user = this.accountService.getById(userId);
        WorkOrder workOrder = this.workOrderRepo.getOne(woId);
        return this.workOrderService.getWorkOrderDetailForAuditor(user,workOrder);
    }

    @PostMapping("/auditor/{userId}/review")
    public void reviewOrder(@PathVariable int userId , @RequestBody WorkOrderDetailVo detailVo){

        this.workOrderService.reviewOrder(userId , detailVo);
    }


}
