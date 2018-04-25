package edu.nju.zhongzhi_demo.dao;

import edu.nju.zhongzhi_demo.entity.Resource;
import edu.nju.zhongzhi_demo.enums.ResourceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResourceRepo extends JpaRepository<Resource,Integer> {
    @Query("select r.id , r.resourceType from Resource r where r.id not in (select wor.resrcId from  WorkOrderRsrc wor where wor.appId = ?1 and wor.resrcStatus = ?2)" )
    List<Object[]> getResourceIdAndTypeListAppCanApply(int appId , ResourceStatus resourceStatus);
}
