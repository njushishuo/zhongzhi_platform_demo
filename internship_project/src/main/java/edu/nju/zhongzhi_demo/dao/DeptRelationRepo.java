package edu.nju.zhongzhi_demo.dao;

import edu.nju.zhongzhi_demo.entity.DeptRelation;
import edu.nju.zhongzhi_demo.entity.DeptRelationPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeptRelationRepo extends JpaRepository<DeptRelation,DeptRelationPK> {
    @Query("select dr.parentId from DeptRelation  dr where dr.deptId = ?1")
    Integer getParentDeptId(int deptId);
}
