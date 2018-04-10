package edu.nju.zhongzhi_demo.service;

import edu.nju.zhongzhi_demo.dao.AppRepo;
import edu.nju.zhongzhi_demo.dao.WorkOrderRepo;
import edu.nju.zhongzhi_demo.entity.WorkOrder;
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
    AppRepo appRepo;
    @Autowired
    AccountService accountService;
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

}
