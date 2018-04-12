package edu.nju.zhongzhi_demo.model.vo;

import edu.nju.zhongzhi_demo.model.wrapper.ResourceInfo;


public class WorkOrderDetailVo extends WorkOrderVo {


    public WorkOrderDetailVo (){

    }

    public WorkOrderDetailVo(WorkOrderVo workOrderVo){
        this.id = workOrderVo.id;
        this.appName = workOrderVo.appName;
        this.status = workOrderVo.status;
        this.reviewStatus = workOrderVo.reviewStatus;
        this.userName = workOrderVo.userName;
    }

    public ResourceInfo resourceInfo;

}
