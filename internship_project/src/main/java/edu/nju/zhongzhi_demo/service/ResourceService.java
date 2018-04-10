package edu.nju.zhongzhi_demo.service;

import edu.nju.zhongzhi_demo.dao.*;
import edu.nju.zhongzhi_demo.enums.ResourceStatus;
import edu.nju.zhongzhi_demo.enums.ResourceType;
import edu.nju.zhongzhi_demo.model.wrapper.ResourceInfo;
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



    /**
     *
     * 获取某个应用已经绑定的资源清单
     * @return
     */
    public ResourceInfo getResourceInfoByAppId(int appId){
        List<Object[]> resourceIdAndTypeList = this.workOrderRsrcRepo.findResourceIdAndTypeListByAppIdAndResrcStatus
                (appId, ResourceStatus.approved.toString());

        return this.getResourceInfoByResourceIdAndTypeList(resourceIdAndTypeList);
    }

    public ResourceInfo getResourceInfoByWorkOrderId(int workOrderId){
        List<Object[]> resourceIdAndTypeList = this.workOrderRsrcRepo.findResourceIdAndTypeListByWOId
                (workOrderId);
        return this.getResourceInfoByResourceIdAndTypeList(resourceIdAndTypeList);
    }



    private ResourceInfo getResourceInfoByResourceIdAndTypeList(List<Object []> resourceIdAndTypeList){
        ResourceInfo resourceInfo = new ResourceInfo();

        List<Integer> cmptIds = new ArrayList<>();
        List<Integer> apiIds = new ArrayList<>();
        List<Integer> dataIds = new ArrayList<>();

        for(Object[] temp : resourceIdAndTypeList){

            if(temp[1].equals(ResourceType.compute.toString())){
                cmptIds.add((Integer)temp[0]);
            }

            if(temp[1].equals(ResourceType.data.toString())){
                dataIds.add((Integer)temp[0]);
            }

            if(temp[1].equals(ResourceType.api.toString())){
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


}
