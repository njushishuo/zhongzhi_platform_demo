package edu.nju.zhongzhi_demo.service;

import edu.nju.zhongzhi_demo.dao.*;
import edu.nju.zhongzhi_demo.entity.ResrcApi;
import edu.nju.zhongzhi_demo.entity.ResrcCmpt;
import edu.nju.zhongzhi_demo.entity.ResrcData;
import edu.nju.zhongzhi_demo.entity.WorkOrderRsrc;
import edu.nju.zhongzhi_demo.enums.ResourceStatus;
import edu.nju.zhongzhi_demo.enums.ResourceType;
import edu.nju.zhongzhi_demo.enums.Role;
import edu.nju.zhongzhi_demo.model.wrapper.ResourceDetail;
import edu.nju.zhongzhi_demo.model.wrapper.ResourceInfo;
import edu.nju.zhongzhi_demo.util.VoTransformHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceService {

    @Autowired
    WorkOrderRsrcRepo workOrderRsrcRepo;
    @Autowired
    ResourceRepo resourceRepo;
    @Autowired
    ResrcCmptRepo resrcCmptRepo;
    @Autowired
    ResrcApiRepo resrcApiRepo;
    @Autowired
    ResrcDataRepo resrcDataRepo;
    @Autowired
    VoTransformHelper voTransformHelper;



    /**
     *
     * 获取某个应用已经绑定的资源清单
     * @return
     */
    public ResourceInfo getResourceInfoByAppId(int appId){
        List<Object[]> resourceIdAndTypeList = this.workOrderRsrcRepo.findResourceIdAndTypeListByAppIdAndResrcStatus
                (appId, ResourceStatus.approved);

        return this.getResourceInfoByResourceIdAndTypeList(resourceIdAndTypeList);
    }


    /**
     *
     * 获取某个应用可以申请的资源清单
     * @return
     */
    public ResourceInfo getResourceInfoAppCanApply(int appId){
        List<Object[]> resourceIdAndTypeList = this.resourceRepo.
                getResourceIdAndTypeListAppCanApply(appId,ResourceStatus.approved);

        System.err.println(resourceIdAndTypeList.size());
        return this.getResourceInfoByResourceIdAndTypeList(resourceIdAndTypeList);
    }


    /**
     * 获取某个工单的资源信息
     * @param workOrderId
     * @return
     */
    public ResourceDetail getResourceDetailByWorkOrderId(int workOrderId){
        List<WorkOrderRsrc> workOrderRsrcList = this.workOrderRsrcRepo.findByWorkOrderId(workOrderId);
        return this.getResourceDetailByWorList(workOrderRsrcList);
    }

    public ResourceDetail getResourceDetailByWorkOrderIdAndRole(int workOrderId , Role role){
        if(role == Role.cmpt_conductor){
            List<WorkOrderRsrc> workOrderRsrcList = this.workOrderRsrcRepo.findCmptRecordByWorkOrderId(workOrderId);
            return this.getResourceDetailByWorList(workOrderRsrcList);
        }else if(role == Role.data_conductor){
            List<WorkOrderRsrc> workOrderRsrcList = this.workOrderRsrcRepo.findDataRecordByWorkOrderId(workOrderId);
            return this.getResourceDetailByWorList(workOrderRsrcList);
        }else{
            throw new RuntimeException("Role Error");
        }
    }


    private ResourceInfo getResourceInfoByResourceIdAndTypeList(List<Object []> resourceIdAndTypeList){
        ResourceInfo resourceInfo = new ResourceInfo();

        List<Integer> cmptIds = new ArrayList<>();
        List<Integer> apiIds = new ArrayList<>();
        List<Integer> dataIds = new ArrayList<>();

        for(Object[] temp : resourceIdAndTypeList){

            if(temp[1] == ResourceType.compute){
                cmptIds.add((Integer)temp[0]);
            }

            if(temp[1] == ResourceType.data){
                dataIds.add((Integer)temp[0]);
            }

            if(temp[1] == ResourceType.api){
                apiIds.add((Integer)temp[0]);
            }
        }

        if(!cmptIds.isEmpty()){
            resourceInfo.resrcCmptList.addAll(this.resrcCmptRepo.getResrcCmptByIdIn(cmptIds));
        }

        if(!dataIds.isEmpty()){
            resourceInfo.resrcDataList.addAll(this.resrcDataRepo.getResrcDataByIdIn(dataIds));
        }

        if(!apiIds.isEmpty()){
            resourceInfo.resrcApiList.addAll(this.resrcApiRepo.getResrcApiByIdIn(apiIds));
        }

        return resourceInfo;
    }

    private ResourceDetail  getResourceDetailByWorList(List<WorkOrderRsrc> workOrderRsrcs){
        ResourceDetail resourceDetail = new ResourceDetail();

        for(WorkOrderRsrc workOrderRsrc : workOrderRsrcs){

            if(workOrderRsrc.getResrcType() == ResourceType.compute){
                ResrcCmpt resrcCmpt = this.resrcCmptRepo.getOne(workOrderRsrc.getResrcId());
                resourceDetail.resrcCmptList.add(voTransformHelper.toCmptDetailVo(resrcCmpt,workOrderRsrc));
            }

            if(workOrderRsrc.getResrcType() == ResourceType.data){
                ResrcData resrcData = this.resrcDataRepo.getOne(workOrderRsrc.getResrcId());
                resourceDetail.resrcDataList.add(voTransformHelper.toDataDetailVo(resrcData,workOrderRsrc));
            }

            if(workOrderRsrc.getResrcType() == ResourceType.api){
                ResrcApi resrcApi = this.resrcApiRepo.getOne(workOrderRsrc.getResrcId());
                resourceDetail.resrcApiList.add(voTransformHelper.toApiDetailVo(resrcApi,workOrderRsrc));
            }
        }

        return  resourceDetail;
    }

}
