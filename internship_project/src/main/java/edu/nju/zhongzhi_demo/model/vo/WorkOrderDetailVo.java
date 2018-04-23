package edu.nju.zhongzhi_demo.model.vo;

import edu.nju.zhongzhi_demo.model.wrapper.ResourceDetail;


public class WorkOrderDetailVo extends WorkOrderVo {

    public String status;
    public String reviewStatus;

    public WorkOrderDetailVo (){

    }

    public WorkOrderDetailVo(WorkOrderVo workOrderVo){
        this.id = workOrderVo.id;
        this.appName = workOrderVo.appName;
        this.userName = workOrderVo.userName;
        this.deptName = workOrderVo.deptName;
        this.createTime = workOrderVo.createTime;
        this.reviewTime = workOrderVo.reviewTime;
    }

    public ResourceDetail resourceDetail;

}
