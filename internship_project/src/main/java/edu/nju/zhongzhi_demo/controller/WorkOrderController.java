package edu.nju.zhongzhi_demo.controller;

import edu.nju.zhongzhi_demo.dao.AppRepo;
import edu.nju.zhongzhi_demo.dao.WorkOrderRepo;
import edu.nju.zhongzhi_demo.dao.WorkOrderRsrcRepo;
import edu.nju.zhongzhi_demo.entity.WorkOrder;
import edu.nju.zhongzhi_demo.entity.WorkOrderRsrc;
import edu.nju.zhongzhi_demo.enums.ResourceStatus;
import edu.nju.zhongzhi_demo.enums.ResourceType;
import edu.nju.zhongzhi_demo.enums.WorkOrderStatus;
import edu.nju.zhongzhi_demo.model.para.WorkOrderPara;
import edu.nju.zhongzhi_demo.model.vo.WorkOrderDetailVo;
import edu.nju.zhongzhi_demo.model.vo.WorkOrderVo;
import edu.nju.zhongzhi_demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/workorder")
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
    AuthService authService;
    @Autowired
    ResourceService resourceService;

    @PostMapping
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
        workOrder = workOrderRepo.save(workOrder);

        if(workOrderPara.cmptIds != null && !workOrderPara.cmptIds.isEmpty()){
            for(Integer resourceId: workOrderPara.cmptIds){
                int auditDeptId = this.departmentService.getAuditDeptIdForCmptRsrc(resourceId);
                WorkOrderRsrc workOrderRsrc = new WorkOrderRsrc();
                workOrderRsrc.setAppId(workOrderPara.appId);
                workOrderRsrc.setWorkOrderId(workOrder.getId());
                workOrderRsrc.setResrcId(resourceId);
                workOrderRsrc.setResrcType(ResourceType.compute);
                workOrderRsrc.setReviewDeptId(auditDeptId);
                workOrderRsrc.setResrcStatus(ResourceStatus.wait_review);
                this.workOrderRsrcRepo.save(workOrderRsrc);
            }
        }

        if(workOrderPara.dataIds !=null && !workOrderPara.dataIds.isEmpty()){
            for(Integer resourceId: workOrderPara.dataIds){
                int auditDeptId = this.departmentService.getAuditDeptIdForDataRsrc(resourceId);
                WorkOrderRsrc workOrderRsrc = new WorkOrderRsrc();
                workOrderRsrc.setAppId(workOrderPara.appId);
                workOrderRsrc.setWorkOrderId(workOrder.getId());
                workOrderRsrc.setResrcId(resourceId);
                workOrderRsrc.setResrcType(ResourceType.data);
                workOrderRsrc.setReviewDeptId(auditDeptId);
                workOrderRsrc.setResrcStatus(ResourceStatus.wait_review);
                this.workOrderRsrcRepo.save(workOrderRsrc);
            }
        }

        if(workOrderPara.apiIds !=null && !workOrderPara.apiIds.isEmpty()){
            for(Integer resourceId: workOrderPara.apiIds){
                int auditDeptId = this.departmentService.getAuditDeptIdForDataRsrc(resourceId);
                WorkOrderRsrc workOrderRsrc = new WorkOrderRsrc();
                workOrderRsrc.setAppId(workOrderPara.appId);
                workOrderRsrc.setWorkOrderId(workOrder.getId());
                workOrderRsrc.setResrcId(resourceId);
                workOrderRsrc.setResrcType(ResourceType.api);
                workOrderRsrc.setReviewDeptId(auditDeptId);
                workOrderRsrc.setResrcStatus(ResourceStatus.wait_review);
                this.workOrderRsrcRepo.save(workOrderRsrc);
            }
        }



    }

    @GetMapping("/user/{id}")
    public List<WorkOrderVo> getWorkOrderByUserId(@PathVariable int id){
        return  this.workOrderService.getWorkOrderListByUserId(id);
    }

    @GetMapping("/{id}")
    public WorkOrderDetailVo getWorkOrderDetail(@PathVariable int id){

        WorkOrder workOrder = this.workOrderRepo.getOne(id);
        WorkOrderDetailVo vo = new WorkOrderDetailVo();
        String userName = this.authService.getAuthUser().getUsername();
        vo.id = id;
        vo.appName = this.appRepo.getOne(workOrder.getAppId()).getName();
        vo.userName = userName;
        vo.status = workOrder.getStatus().toString();
        vo.reviewStatus = workOrder.getStatus().toString();
        vo.resourceInfo = this.resourceService.getResourceInfoByWorkOrderId(id);
        return vo;
    }
}
