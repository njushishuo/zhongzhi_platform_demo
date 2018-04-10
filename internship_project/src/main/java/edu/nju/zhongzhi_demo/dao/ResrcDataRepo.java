package edu.nju.zhongzhi_demo.dao;

import edu.nju.zhongzhi_demo.entity.ResrcData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResrcDataRepo extends JpaRepository<ResrcData,Integer> {
    List<ResrcData> getResrcDataByIdIn(List<Integer> ids);
}
