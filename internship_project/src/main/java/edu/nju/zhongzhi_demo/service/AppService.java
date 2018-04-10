package edu.nju.zhongzhi_demo.service;

import edu.nju.zhongzhi_demo.dao.AppRepo;
import edu.nju.zhongzhi_demo.entity.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppService {

    @Autowired
    AppRepo appRepo;

    public void save(App app){
        this.appRepo.save(app);
    }

    public App getById(int id){
        return this.appRepo.getOne(id);
    }

    public App getByAppName(String appName){
        return this.appRepo.getByName(appName);
    }


}
