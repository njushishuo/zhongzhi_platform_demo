package edu.nju.zhongzhi_demo.dao;

import edu.nju.zhongzhi_demo.entity.ResrcApi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResrcApiRepo extends JpaRepository<ResrcApi,Integer> {
    List<ResrcApi> getResrcApiByIdIn(List<Integer> ids);
}
