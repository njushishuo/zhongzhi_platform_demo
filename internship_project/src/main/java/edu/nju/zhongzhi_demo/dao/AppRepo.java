package edu.nju.zhongzhi_demo.dao;

import edu.nju.zhongzhi_demo.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepo extends JpaRepository<App,Integer> {

    App getByName(String appName);
}
