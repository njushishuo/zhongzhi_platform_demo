package edu.nju.zhongzhi_demo.dao;

import edu.nju.zhongzhi_demo.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department,Integer> {
}
