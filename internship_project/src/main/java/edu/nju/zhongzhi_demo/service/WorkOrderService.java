package edu.nju.zhongzhi_demo.service;

import edu.nju.zhongzhi_demo.dao.AppRepo;
import edu.nju.zhongzhi_demo.dao.WorkOrderRepo;
import edu.nju.zhongzhi_demo.dao.WorkOrderRsrcRepo;
import edu.nju.zhongzhi_demo.entity.User;
import edu.nju.zhongzhi_demo.entity.WorkOrder;
import edu.nju.zhongzhi_demo.enums.Role;
import edu.nju.zhongzhi_demo.model.vo.WorkOrderDetailVo;
import edu.nju.zhongzhi_demo.model.vo.WorkOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkOrderService {

    @Autowired
    WorkOrderRepo workOrderRepo;
    @Autowired
    WorkOrderRsrcRepo workOrderRsrcRepo;
    @Autowired
    AppRepo appRepo;
    @Autowired
    AccountService accountService;
    @Autowired
    ResourceService resourceService;

    public List<WorkOrderVo> getWorkOrderListByUserId(int userId){
        List<WorkOrder> workOrderList = this.workOrderRepo.getByApplicantId(userId);
        if(workOrderList == null || workOrderList.isEmpty()){
            return null;
        }

        Map<Integer,String> appIdAndNameMap = new HashMap<>();
        List<WorkOrderVo> result = new ArrayList<>();
        String userName = this.accountService.getById(userId).getUsername();

        for(WorkOrder workOrder : workOrderList){
            WorkOrderVo vo = new WorkOrderVo();
            vo.id = workOrder.getId();
            if(!appIdAndNameMap.containsKey(workOrder.getAppId())){
                appIdAndNameMap.put(workOrder.getAppId(),this.appRepo.getOne(workOrder.getAppId()).getName());
            }

            vo.appName = appIdAndNameMap.get(workOrder.getAppId());
            vo.userName = userName;
            vo.status = workOrder.getStatus().toString();
            vo.reviewStatus = workOrder.getStatus().toString();
            result.add(vo);
        }

        return result;
    }

    public List<WorkOrderVo> getUnprocessedWorkOrdersByAuditDeptIdAndRole
            (int deptId , Role role ){

        List<Integer> workOrderIds = new ArrayList<>();
        if(role == Role.cmpt_conductor){
            workOrderIds = this.workOrderRsrcRepo.getUnprocessedCmptWorkOrdersByAuditDeptId(deptId);
        }else if(role == Role.data_conductor){
            workOrderIds = this.workOrderRsrcRepo.getUnprocessedDataWorkOrdersByAuditDeptId(deptId);
        }else{
            throw new RuntimeException("role error");
        }

        if(workOrderIds != null && !workOrderIds.isEmpty()){
            List<WorkOrderVo> result = new ArrayList<>();
            for(Integer workOrderId : workOrderIds){
                WorkOrderVo vo = this.transform(this.workOrderRepo.getOne(workOrderId));
                result.add(vo);
            }
            return result;
        }

        return null;
    }

    public List<WorkOrderVo> getProcessedWorkOrdersByAuditDeptIdAndRole
            (int deptId , Role role ){
        List<Integer> workOrderIds = new ArrayList<>();
        if(role == Role.cmpt_conductor){
            workOrderIds = this.workOrderRsrcRepo.getProcessedCmptWorkOrdersByAuditDeptId(deptId);
        }else if(role == Role.data_conductor){
            workOrderIds = this.workOrderRsrcRepo.getProcessedDataWorkOrdersByAuditDeptId(deptId);
        }else{
            throw new RuntimeException("role error");
        }

        if(workOrderIds != null && !workOrderIds.isEmpty()){
            List<WorkOrderVo> result = new ArrayList<>();
            for(Integer workOrderId : workOrderIds){
                WorkOrderVo vo = this.transform(this.workOrderRepo.getOne(workOrderId));
                result.add(vo);
            }
            return result;
        }

        return null;
    }


    public WorkOrderDetailVo getWorkOrderDetailForAuditor(User auditor , WorkOrder workOrder){
        WorkOrderVo workOrderVo = this.transform(workOrder);
        WorkOrderDetailVo detailVo = new WorkOrderDetailVo(workOrderVo);
        detailVo.resourceInfo = this.resourceService.getResourceInfoByWorkOrderIdAndRole(workOrder.getId(),auditor.getRole());
        return detailVo;
    }


    private WorkOrderVo transform(WorkOrder workOrder){

        WorkOrderVo vo = new WorkOrderVo();
        String userName = this.accountService.getById(workOrder.getApplicantId()).getUsername();
        vo.id = workOrder.getId();
        vo.appName = this.appRepo.getOne(workOrder.getAppId()).getName();
        vo.userName = userName;
        vo.status = workOrder.getStatus().toString();
        vo.reviewStatus = workOrder.getStatus().toString();
        return vo;
    }

}
