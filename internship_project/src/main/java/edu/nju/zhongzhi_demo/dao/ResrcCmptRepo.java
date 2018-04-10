package edu.nju.zhongzhi_demo.dao;

import edu.nju.zhongzhi_demo.entity.ResrcCmpt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResrcCmptRepo extends JpaRepository<ResrcCmpt,Integer> {

    List<ResrcCmpt> getResrcCmptByIdIn(List<Integer> ids);
}
