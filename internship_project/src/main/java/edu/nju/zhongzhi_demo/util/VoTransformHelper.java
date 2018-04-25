package edu.nju.zhongzhi_demo.util;

import edu.nju.zhongzhi_demo.dao.AccountRepo;
import edu.nju.zhongzhi_demo.dao.DepartmentRepo;
import edu.nju.zhongzhi_demo.entity.*;
import edu.nju.zhongzhi_demo.enums.ResourceStatus;
import edu.nju.zhongzhi_demo.model.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class VoTransformHelper {

    @Autowired
    DepartmentRepo departmentRepo;
    @Autowired
    AccountRepo accountRepo;

    public void toResourceVo(Resource resource , ResourceVo resourceVo){
        resourceVo.id = resource.getId();
        resourceVo.name = resource.getName();
        resourceVo.resourceType = resource.getResourceType();
        resourceVo.deptName = this.departmentRepo.getOne(resource.getDeptId()).getName();
    }

    public void toDetailVo(Resource resource , ResourceDetailVo resourceDetailVo, WorkOrderRsrc workOrderRsrc){
        resourceDetailVo.id = resource.getId();
        resourceDetailVo.name = resource.getName();
        resourceDetailVo.resourceType = resource.getResourceType();
        resourceDetailVo.deptName = this.departmentRepo.getOne(resource.getDeptId()).getName();
        resourceDetailVo.auditDeptName =  this.departmentRepo.getOne(workOrderRsrc.getReviewDeptId()).getName();
        if(workOrderRsrc.getReviewUserId() != null){
            resourceDetailVo.auditorName = this.accountRepo.getOne(workOrderRsrc.getReviewUserId()).getUsername();
        }
        resourceDetailVo.resrcStatus = EnumTranslator.translateResrcStatus(workOrderRsrc.getResrcStatus());
        if(workOrderRsrc.getReviewTime()!= null){
            resourceDetailVo.reviewTime = DateHelper.TimestampToString(workOrderRsrc.getReviewTime());
        }

        if(workOrderRsrc.getResrcStatus() != null && workOrderRsrc.getResrcStatus()==ResourceStatus.approved){
            resourceDetailVo.approved = true;
        }
    }

    public ResrcCmptDetailVo toCmptDetailVo(ResrcCmpt resrcCmpt , WorkOrderRsrc workOrderRsrc){
        ResrcCmptDetailVo vo = new ResrcCmptDetailVo();
        this.toDetailVo(resrcCmpt,vo,workOrderRsrc);
        vo.cmptType = resrcCmpt.getCmptType();
        vo.config = resrcCmpt.getConfig();
        vo.count = resrcCmpt.getCount();
        return vo;
    }

    public ResrcCmptVo toCmptVo(ResrcCmpt resrcCmpt ){
        ResrcCmptVo vo = new ResrcCmptVo();
        this.toResourceVo(resrcCmpt,vo);
        vo.cmptType = resrcCmpt.getCmptType();
        vo.config = resrcCmpt.getConfig();
        vo.count = resrcCmpt.getCount();
        return vo;
    }

    public ResrcDataDetailVo toDataDetailVo(ResrcData resrcData , WorkOrderRsrc workOrderRsrc){
        ResrcDataDetailVo vo = new ResrcDataDetailVo();
        this.toDetailVo(resrcData,vo,workOrderRsrc);
        vo.dataType = EnumTranslator.translate(resrcData.getDataType());
        vo.sqdwName = resrcData.getSqdwName();
        vo.updateCycle = resrcData.getUpdateCycle();
        return vo;
    }

    public ResrcDataVo toDataVo(ResrcData resrcData){
        ResrcDataVo vo = new ResrcDataVo();
        this.toResourceVo(resrcData,vo);
        vo.dataType = EnumTranslator.translate(resrcData.getDataType());
        vo.sqdwName = resrcData.getSqdwName();
        vo.updateCycle = resrcData.getUpdateCycle();
        return vo;
    }


    public ResrcApiDetailVo toApiDetailVo(ResrcApi resrcApi, WorkOrderRsrc workOrderRsrc){
        ResrcApiDetailVo vo = new ResrcApiDetailVo();
        this.toDetailVo(resrcApi,vo,workOrderRsrc);
        vo.apiLevel = EnumTranslator.translate(resrcApi.getApiLevel());
        return vo;
    }

    public ResrcApiVo toApiVo(ResrcApi resrcApi){
        ResrcApiVo vo = new ResrcApiVo();
        this.toResourceVo(resrcApi,vo);
        vo.apiLevel = EnumTranslator.translate(resrcApi.getApiLevel());
        return vo;
    }

}
