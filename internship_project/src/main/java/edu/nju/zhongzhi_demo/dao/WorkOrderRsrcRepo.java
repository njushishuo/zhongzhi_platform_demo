package edu.nju.zhongzhi_demo.dao;

import edu.nju.zhongzhi_demo.entity.WorkOrderRsrc;
import edu.nju.zhongzhi_demo.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkOrderRsrcRepo extends JpaRepository<WorkOrderRsrc,Integer> {

    @Query("select resrcId , resrcType from WorkOrderRsrc wor where appId = ?1 and resrcStatus = ?2 ")
    List<Object []> findResourceIdAndTypeListByAppIdAndResrcStatus(int appId , String resrcStatus);


    @Query("select resrcId , resrcType from WorkOrderRsrc wor where workOrderId = ?1 ")
    List<Object []> findResourceIdAndTypeListByWOId(int workOrderId);


    @Query("select distinct workOrderId from WorkOrderRsrc wor where reviewDeptId = ?1 and resrcType = 'compute' and resrcStatus is null ")
    List<Integer> getUnprocessedCmptWorkOrdersByAuditDeptId(int deptId);

    @Query("select distinct workOrderId from WorkOrderRsrc wor where reviewDeptId = ?1 and (resrcType = 'data' or resrcType = 'api')and resrcStatus is null ")
    List<Integer> getUnprocessedDataWorkOrdersByAuditDeptId(int deptId);

    @Query("select distinct workOrderId from WorkOrderRsrc wor where reviewDeptId = ?1 and resrcType = 'compute' and resrcStatus is not null ")
    List<Integer> getProcessedCmptWorkOrdersByAuditDeptId(int deptId);

    @Query("select distinct workOrderId from WorkOrderRsrc wor where reviewDeptId = ?1 and (resrcType = 'data' or resrcType = 'api')and resrcStatus is not null ")
    List<Integer> getProcessedDataWorkOrdersByAuditDeptId(int deptId);
}
