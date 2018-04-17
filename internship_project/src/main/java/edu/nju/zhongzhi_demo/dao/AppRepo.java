package edu.nju.zhongzhi_demo.dao;

import edu.nju.zhongzhi_demo.entity.App;
import edu.nju.zhongzhi_demo.model.vo.AppVo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppRepo extends JpaRepository<App,Integer> {

    App getByName(String appName);

    List<App> getByOwnerId(int id);
}
