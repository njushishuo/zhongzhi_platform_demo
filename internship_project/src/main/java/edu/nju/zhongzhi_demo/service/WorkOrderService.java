package edu.nju.zhongzhi_demo.service;

import edu.nju.zhongzhi_demo.controller.Config;
import edu.nju.zhongzhi_demo.dao.AppRepo;
import edu.nju.zhongzhi_demo.dao.DepartmentRepo;
import edu.nju.zhongzhi_demo.dao.WorkOrderRepo;
import edu.nju.zhongzhi_demo.dao.WorkOrderRsrcRepo;
import edu.nju.zhongzhi_demo.entity.*;
import edu.nju.zhongzhi_demo.enums.*;
import edu.nju.zhongzhi_demo.model.para.WorkOrderPara;
import edu.nju.zhongzhi_demo.model.vo.*;
import edu.nju.zhongzhi_demo.util.DateHelper;
import edu.nju.zhongzhi_demo.util.EnumTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    @Autowired
    DepartmentService departmentService;

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

    /**
     * 开发人员查看工单详情
     * @param id
     * @return
     */
    public WorkOrderDetailVo getWorkOrderDetail(int id) {
        WorkOrder workOrder = this.workOrderRepo.getOne(id);
        WorkOrderVo workOrderVo = this.transform(workOrder);
        WorkOrderDetailVo vo = new WorkOrderDetailVo(workOrderVo);

        vo.status = EnumTranslator.translateOrderStatus(workOrder.getStatus());
        vo.reviewStatus = EnumTranslator.translateReviewResult(workOrder.getReviewResult());
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

        return this.getWorkOrderVoListByIdList(workOrderIds);

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

        return this.getWorkOrderVoListByIdList(workOrderIds);
    }

    private List<WorkOrderVo> getWorkOrderVoListByIdList(List<Integer> workOrderIds){
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

    /**
     * 审核人员查看工单详情
     * @param auditor
     * @param workOrder
     * @return
     */
    public WorkOrderDetailVo getWorkOrderDetailForAuditor(User auditor , WorkOrder workOrder){

        WorkOrderVo workOrderVo = this.transform(workOrder);
        WorkOrderDetailVo detailVo = new WorkOrderDetailVo(workOrderVo);
        this.setWorkOrderStatusForAuditor(auditor,workOrder,detailVo);

        detailVo.resourceDetail = this.resourceService.
                getResourceDetailByWorkOrderIdAndRole(workOrder.getId(),auditor.getRole());
        return detailVo;
    }

    private void setWorkOrderStatusForAuditor(User auditor , WorkOrder workOrder,WorkOrderDetailVo detailVo){
        List<WorkOrderRsrc> workOrderRsrcList = null;
        if(auditor.getRole()  == Role.cmpt_conductor){
            workOrderRsrcList = this.workOrderRsrcRepo.
                    getCmptRecordByWorkOrderIdAndReviewDeptId(workOrder.getId(),auditor.getDeptId());
        }else if(auditor.getRole()  == Role.data_conductor){
            workOrderRsrcList = this.workOrderRsrcRepo.
                    getDataRecordByWorkOrderIdAndReviewDeptId(workOrder.getId(),auditor.getDeptId());
        }


        if(workOrderRsrcList == null || workOrderRsrcList.isEmpty()){
            throw new RuntimeException(Config.ORDER_ERROR);
        }

        if(workOrderRsrcList.get(0).getResrcStatus() == null){
            detailVo.status = EnumTranslator.translateOrderStatus(WorkOrderStatus.wait_review);
            detailVo.reviewStatus = EnumTranslator.translateReviewResult(null);
            detailVo.reviewTime = EnumTranslator.translateReviewTime(null);
        }else{
            detailVo.status = EnumTranslator.translateOrderStatus(WorkOrderStatus.processed);
            int pass = 0;
            for(WorkOrderRsrc workOrderRsrc : workOrderRsrcList){
                if(workOrderRsrc.getResrcStatus() == ResourceStatus.approved){
                    pass++;
                }
            }

            WorkOrderReviewResult reviewResult;
            if(pass == 0){
                reviewResult = WorkOrderReviewResult.all_deny;
            }else if(pass == workOrderRsrcList.size()){
                reviewResult = WorkOrderReviewResult.all_pass;
            }else{
                reviewResult = WorkOrderReviewResult.part_pass;
            }
            detailVo.reviewStatus = EnumTranslator.translateReviewResult(reviewResult);
            detailVo.reviewTime = EnumTranslator.translateReviewTime(workOrderRsrcList.get(0).getReviewTime());
        }
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
        vo.reviewTime = EnumTranslator.translateReviewTime(workOrder.getReviewTime());
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



    private void  updateResrcStatus (int auditorId, int workOrderId , List<? extends ResourceDetailVo> resrcDetailVoList){
        if(resrcDetailVoList != null){
            List<Integer> approvedResrcIds = new ArrayList<>();
            List<Integer> deniedResrcIds = new ArrayList<>();
            for(ResourceDetailVo vo : resrcDetailVoList){
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
            workOrder.setReviewTime(new Timestamp(System.currentTimeMillis()));
            this.workOrderRepo.saveAndFlush(workOrder);
        }


    }

    public void createOrder(WorkOrderPara workOrderPara) {
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
                int auditDeptId = this.departmentService.getAuditDeptIdForCmptRsrc(resrcCmpt.getDeptId());
                WorkOrderRsrc workOrderRsrc = this.createWorkOrderRsrc
                        (workOrderPara.appId,workOrder.getId(),resrcCmpt.getId(),ResourceType.compute,auditDeptId);
                result.add(workOrderRsrc);
            }
        }

        if(workOrderPara.dataList !=null && !workOrderPara.dataList.isEmpty()){
            for(ResrcData resrcData: workOrderPara.dataList){
                int auditDeptId = this.departmentService.getAuditDeptIdForDataRsrc(resrcData.getDeptId());
                WorkOrderRsrc workOrderRsrc = this.createWorkOrderRsrc
                        (workOrderPara.appId,workOrder.getId(),resrcData.getId(),ResourceType.data,auditDeptId);
                result.add(workOrderRsrc);
            }
        }

        if(workOrderPara.apiList !=null && !workOrderPara.apiList.isEmpty()){
            for(ResrcApi resrcApi: workOrderPara.apiList){
                int auditDeptId = this.departmentService.getAuditDeptIdForDataRsrc(resrcApi.getDeptId());
                WorkOrderRsrc workOrderRsrc = this.createWorkOrderRsrc
                        (workOrderPara.appId,workOrder.getId(),resrcApi.getId(),ResourceType.api,auditDeptId);
                result.add(workOrderRsrc);
            }
        }
        this.workOrderRsrcRepo.saveAll(result);
    }

    private WorkOrderRsrc createWorkOrderRsrc(int appId, int orderId, int resrcId, ResourceType type, int auditDeptId){
        WorkOrderRsrc workOrderRsrc = new WorkOrderRsrc();
        workOrderRsrc.setAppId(appId);
        workOrderRsrc.setWorkOrderId(orderId);
        workOrderRsrc.setResrcId(resrcId);
        workOrderRsrc.setResrcType(type);
        workOrderRsrc.setReviewDeptId(auditDeptId);
        return workOrderRsrc;
    }
}
