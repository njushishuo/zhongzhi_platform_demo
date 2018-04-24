package edu.nju.zhongzhi_demo.service;

import edu.nju.zhongzhi_demo.controller.Config;
import edu.nju.zhongzhi_demo.dao.AppRepo;
import edu.nju.zhongzhi_demo.dao.DepartmentRepo;
import edu.nju.zhongzhi_demo.dao.WorkOrderRepo;
import edu.nju.zhongzhi_demo.dao.WorkOrderRsrcRepo;
import edu.nju.zhongzhi_demo.entity.Resource;
import edu.nju.zhongzhi_demo.entity.User;
import edu.nju.zhongzhi_demo.entity.WorkOrder;
import edu.nju.zhongzhi_demo.enums.ResourceStatus;
import edu.nju.zhongzhi_demo.enums.Role;
import edu.nju.zhongzhi_demo.enums.WorkOrderReviewResult;
import edu.nju.zhongzhi_demo.enums.WorkOrderStatus;
import edu.nju.zhongzhi_demo.model.vo.*;
import edu.nju.zhongzhi_demo.model.wrapper.ResourceDetail;
import edu.nju.zhongzhi_demo.util.DateHelper;
import edu.nju.zhongzhi_demo.util.EnumTranslator;
import org.hibernate.jdbc.Work;
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
    @Autowired
    DepartmentRepo departmentRepo;

    public List<WorkOrderVo> getWorkOrderListByUserId(int userId){
        List<WorkOrder> workOrderList = this.workOrderRepo.getByApplicantId(userId);
        if(workOrderList == null || workOrderList.isEmpty()){
            return null;
        }

        Map<Integer,String> appIdAndNameMap = new HashMap<>();
        List<WorkOrderVo> result = new ArrayList<>();
        User user =  this.accountService.getById(userId);
        if(user == null){
            throw new RuntimeException(Config.USER_NOT_EXISTS);
        }
        String userName =user.getUsername();
        String deptName = this.departmentRepo.getOne(user.getDeptId()).getName();

        for(WorkOrder workOrder : workOrderList){
            WorkOrderVo vo = new WorkOrderVo();
            vo.id = workOrder.getId();
            if(!appIdAndNameMap.containsKey(workOrder.getAppId())){
                appIdAndNameMap.put(workOrder.getAppId(),this.appRepo.getOne(workOrder.getAppId()).getName());
            }

            vo.appName = appIdAndNameMap.get(workOrder.getAppId());
            vo.userName = userName;
            vo.deptName = deptName;
            vo.createTime = DateHelper.TimestampToString(workOrder.getCreatedTime());
            result.add(vo);
        }

        return result;
    }

    public WorkOrderDetailVo getWorkOrderDetail(int id) {
        WorkOrder workOrder = this.workOrderRepo.getOne(id);
        WorkOrderVo workOrderVo = this.transform(workOrder);
        WorkOrderDetailVo vo = new WorkOrderDetailVo(workOrderVo);

        vo.status = EnumTranslator.translate(workOrder.getStatus());
        vo.reviewStatus = EnumTranslator.translate(workOrder.getReviewResult());
        vo.resourceDetail = this.resourceService.getResourceDetailByWorkOrderId(id);
        return vo;
    }

    public List<WorkOrderVo> getUnprocessedWorkOrdersByAuditDeptIdAndRole
            (int deptId , Role role ){

        List<Integer> workOrderIds ;
        if(role == Role.cmpt_conductor){
            workOrderIds = this.workOrderRsrcRepo.getUnprocessedCmptWorkOrdersByAuditDeptId(deptId);
        }else if(role == Role.data_conductor){
            workOrderIds = this.workOrderRsrcRepo.getUnprocessedDataWorkOrdersByAuditDeptId(deptId);
        }else{
            throw new RuntimeException("role error");
        }

        if(workOrderIds != null && !workOrderIds.isEmpty()){
            List<WorkOrder> workOrderList = this.workOrderRepo.getAllByIdIn(workOrderIds);
            List<WorkOrderVo> result = new ArrayList<>();
            for(WorkOrder workOrder : workOrderList){
                WorkOrderVo vo = this.transform(workOrder);
                result.add(vo);
            }
            return result;
        }

        return null;
    }

    public List<WorkOrderVo> getProcessedWorkOrdersByAuditDeptIdAndRole
            (int deptId , Role role ){
        List<Integer> workOrderIds;
        if(role == Role.cmpt_conductor){
            workOrderIds = this.workOrderRsrcRepo.getProcessedCmptWorkOrdersByAuditDeptId(deptId);
        }else if(role == Role.data_conductor){
            workOrderIds = this.workOrderRsrcRepo.getProcessedDataWorkOrdersByAuditDeptId(deptId);
        }else{
            throw new RuntimeException("role error");
        }

        if(workOrderIds != null && !workOrderIds.isEmpty()){
            List<WorkOrder> workOrderList = this.workOrderRepo.getAllByIdIn(workOrderIds);
            List<WorkOrderVo> result = new ArrayList<>();
            for(WorkOrder workOrder : workOrderList){
                WorkOrderVo vo = this.transform(workOrder);
                result.add(vo);
            }
            return result;
        }

        return null;
    }


    public WorkOrderDetailVo getWorkOrderDetailForAuditor(User auditor , WorkOrder workOrder){

        WorkOrderVo workOrderVo = this.transform(workOrder);
        WorkOrderDetailVo detailVo = new WorkOrderDetailVo(workOrderVo);

        detailVo.status = EnumTranslator.translate(workOrder.getStatus());
        detailVo.reviewStatus = EnumTranslator.translate(workOrder.getReviewResult());
        detailVo.resourceDetail = this.resourceService.
                getResourceDetailByWorkOrderIdAndRole(workOrder.getId(),auditor.getRole());
        return detailVo;
    }


    private WorkOrderVo transform(WorkOrder workOrder){
        WorkOrderVo vo = new WorkOrderVo();
        User user = this.accountService.getById(workOrder.getApplicantId());
        if(user == null){
            throw new RuntimeException(Config.USER_NOT_EXISTS);
        }
        String deptName = this.departmentRepo.getOne(user.getDeptId()).getName();
        String userName = user.getUsername();
        vo.id = workOrder.getId();
        vo.appName = this.appRepo.getOne(workOrder.getAppId()).getName();
        vo.userName = userName;
        vo.deptName = deptName;
        vo.createTime = DateHelper.TimestampToString(workOrder.getCreatedTime());
        vo.reviewTime = EnumTranslator.translate(workOrder.getReviewTime());
        return vo;
    }


    public void reviewOrder(int userId, WorkOrderDetailVo detailVo) {
        int workOrderId = detailVo.id;
        List<ResrcCmptDetailVo> cmptDetailVoList =  detailVo.resourceDetail.resrcCmptList;
        List<ResrcDataDetailVo> dataDetailVoList =  detailVo.resourceDetail.resrcDataList;
        List<ResrcApiDetailVo> apiDetailVoList =  detailVo.resourceDetail.resrcApiList;
        if(cmptDetailVoList !=null && !cmptDetailVoList.isEmpty()){
            this.updateResrcStatus(userId,workOrderId,cmptDetailVoList);
        }

        if(dataDetailVoList !=null && !dataDetailVoList.isEmpty()){
            this.updateResrcStatus(userId,workOrderId,dataDetailVoList);
        }

        if(apiDetailVoList !=null && !apiDetailVoList.isEmpty()){
            this.updateResrcStatus(userId,workOrderId,apiDetailVoList);
        }

        this.updateWorkOrderStatus(workOrderId);
    }



    private void  updateResrcStatus (int auditorId, int workOrderId , List<? extends ResourceVo> resrcDetailVoList){
        if(resrcDetailVoList != null){
            List<Integer> approvedResrcIds = new ArrayList<>();
            List<Integer> deniedResrcIds = new ArrayList<>();
            for(ResourceVo vo : resrcDetailVoList){
                if(vo.approved){
                    approvedResrcIds.add(vo.id);
                }else{
                    deniedResrcIds.add(vo.id);
                }
            }
            if(!approvedResrcIds.isEmpty()){
                this.workOrderRsrcRepo.setReviewStatus(auditorId,workOrderId,approvedResrcIds,ResourceStatus.approved);
            }

            if(!deniedResrcIds.isEmpty()){
                this.workOrderRsrcRepo.setReviewStatus(auditorId,workOrderId,deniedResrcIds,ResourceStatus.rejected);
            }
        }
    }
    private void  updateWorkOrderStatus(int workOrderId){
        if(this.workOrderRsrcRepo.getUnprocessResrcNum(workOrderId) == 0){
            WorkOrder workOrder = this.workOrderRepo.getOne(workOrderId);
            int all = this.workOrderRsrcRepo.getResrcNum(workOrderId);
            int pass = this.workOrderRsrcRepo.getPassedResrcNum(workOrderId);
            WorkOrderReviewResult reviewResult;
            if(pass == all){
               reviewResult = WorkOrderReviewResult.all_pass;
            }else if(pass == 0){
                reviewResult = WorkOrderReviewResult.all_deny;
            }else{
                reviewResult = WorkOrderReviewResult.part_pass;
            }
            workOrder.setStatus(WorkOrderStatus.processed);
            workOrder.setReviewResult(reviewResult);
            this.workOrderRepo.saveAndFlush(workOrder);
        }


    }

}
