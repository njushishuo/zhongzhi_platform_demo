package edu.nju.zhongzhi_demo.service;

import edu.nju.zhongzhi_demo.dao.DepartmentRepo;
import edu.nju.zhongzhi_demo.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeptService {
    @Autowired
    DepartmentRepo departmentRepo;


    public Department getById(int id){
        return this.departmentRepo.getOne(id);
    }
}
