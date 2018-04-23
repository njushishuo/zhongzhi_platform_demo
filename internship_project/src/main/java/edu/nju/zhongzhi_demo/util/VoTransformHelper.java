package edu.nju.zhongzhi_demo.util;

import edu.nju.zhongzhi_demo.dao.AccountRepo;
import edu.nju.zhongzhi_demo.dao.DepartmentRepo;
import edu.nju.zhongzhi_demo.entity.*;
import edu.nju.zhongzhi_demo.model.vo.ResourceVo;
import edu.nju.zhongzhi_demo.model.vo.ResrcApiDetailVo;
import edu.nju.zhongzhi_demo.model.vo.ResrcCmptDetailVo;
import edu.nju.zhongzhi_demo.model.vo.ResrcDataDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class VoTransformHelper {

    @Autowired
    DepartmentRepo departmentRepo;
    @Autowired
    AccountRepo accountRepo;

    public void toDetailVo(Resource resource , ResourceVo resourceVo , WorkOrderRsrc workOrderRsrc){
        resourceVo.id = resource.getId();
        resourceVo.name = resource.getName();
        resourceVo.resourceType = resource.getResourceType();
        resourceVo.deptName = resource.getDepartment().getName();
        resourceVo.auditDeptName =  this.departmentRepo.getOne(workOrderRsrc.getReviewDeptId()).getName();
        if(workOrderRsrc.getReviewUserId() != null){
            resourceVo.auditorName = this.accountRepo.getOne(workOrderRsrc.getReviewUserId()).getUsername();
        }
        resourceVo.reviewStatus = EnumTranslator.translate(workOrderRsrc.getResrcStatus());
        if(workOrderRsrc.getReviewTime()!= null){
            resourceVo.reviewTime = DateHelper.TimestampToString(workOrderRsrc.getReviewTime());
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

    public ResrcDataDetailVo toDataDetailVo(ResrcData resrcData , WorkOrderRsrc workOrderRsrc){
        ResrcDataDetailVo vo = new ResrcDataDetailVo();
        this.toDetailVo(resrcData,vo,workOrderRsrc);
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
}
