package edu.nju.zhongzhi_demo.service;

import edu.nju.zhongzhi_demo.dao.AccountRepo;
import edu.nju.zhongzhi_demo.dao.DepartmentRepo;
import edu.nju.zhongzhi_demo.dao.DeptRelationRepo;
import edu.nju.zhongzhi_demo.entity.Department;
import edu.nju.zhongzhi_demo.entity.User;
import edu.nju.zhongzhi_demo.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentService {

    @Value("${constants.root_dept_id}")
    private int rootDeptId;

    @Autowired
    AccountRepo accountRepo;
    @Autowired
    DeptRelationRepo deptRelationRepo;
    @Autowired
    DepartmentRepo departmentRepo;


    public Department getById(int id){
        return this.departmentRepo.getOne(id);
    }

    /**
     * 根据资源发布部门，确定计算资源的审核部门
     * 目前指定为省公安厅
     * @param rsrcDeptId
     * @return
     */
    public int getAuditDeptIdForCmptRsrc(int rsrcDeptId){
        return this.rootDeptId;
    }

    /**
     * 根据资源发布部门，确定数据资源的审核部门
     * 数据资源指的是 data,api
     * @param rsrcDeptId
     * @return
     */
    public int getAuditDeptIdForDataRsrc(int rsrcDeptId){
        Integer curDeptId = rsrcDeptId;
        while(curDeptId != null){
            if(curDeptHasAuditor(Role.data_conductor,curDeptId)){
                return curDeptId;
            }
            curDeptId = this.getParentDept(curDeptId);
        }
        return this.rootDeptId;
    }

    private boolean curDeptHasAuditor(Role role, int deptId){
        List<User> auditorList =  this.accountRepo.getAllByRoleAndDeptId(role ,deptId);
        if(auditorList != null && !auditorList.isEmpty()){
            return true;
        }
        return false;
    }

    private Integer getParentDept(int deptId){
        return this.deptRelationRepo.getParentDeptId(deptId);
    }


}
