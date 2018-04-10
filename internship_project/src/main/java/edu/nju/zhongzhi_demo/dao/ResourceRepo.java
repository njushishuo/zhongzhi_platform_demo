package edu.nju.zhongzhi_demo.dao;

import edu.nju.zhongzhi_demo.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepo extends JpaRepository<Resource,Integer> {



}
