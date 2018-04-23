package edu.nju.zhongzhi_demo.dao;

import edu.nju.zhongzhi_demo.entity.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkOrderRepo extends JpaRepository<WorkOrder,Integer> {

    List<WorkOrder> getByApplicantId(int id);

    List<WorkOrder> getAllByIdIn(List<Integer> ids);
}
