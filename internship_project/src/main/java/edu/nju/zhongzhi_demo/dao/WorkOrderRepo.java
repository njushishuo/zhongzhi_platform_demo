package edu.nju.zhongzhi_demo.dao;

import edu.nju.zhongzhi_demo.entity.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
public interface WorkOrderRepo extends JpaRepository<WorkOrder,Integer> {
}
