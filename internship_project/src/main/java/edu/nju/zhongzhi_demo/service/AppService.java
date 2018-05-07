package edu.nju.zhongzhi_demo.service;

import edu.nju.zhongzhi_demo.controller.Config;
import edu.nju.zhongzhi_demo.dao.AppRepo;
import edu.nju.zhongzhi_demo.entity.App;
import edu.nju.zhongzhi_demo.entity.Department;
import edu.nju.zhongzhi_demo.entity.User;
import edu.nju.zhongzhi_demo.model.vo.AppVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppService {

    @Autowired
    AppRepo appRepo;
    @Autowired
    DepartmentService deptService;
    @Autowired
    AccountService accountService;

    public void save(App app){
        this.appRepo.save(app);
    }

    public App getById(int id){
        return this.appRepo.getOne(id);
    }

    public App getByAppName(String appName){
        return this.appRepo.getByName(appName);
    }


    public List<AppVo> getAppListByUserId(int id) {
        List<AppVo> result = new ArrayList<>();
        List<App> appList = this.appRepo.getByOwnerId(id);
        for(App app : appList){
            AppVo vo = this.transform(app);
            result.add(vo);
        }
        return result;
    }

    public AppVo transform(App app){
        AppVo vo = new AppVo();
        vo.setId(app.getId());
        vo.setName(app.getName());
        vo.setDescription(app.getDescription());
        vo.setOwnerId(app.getOwnerId());
        User user = this.accountService.getById(app.getOwnerId());
        if(user == null){
            throw new RuntimeException(Config.USER_NOT_EXISTS);
        }
        Department department = this.deptService.getById(user.getDeptId());

        if(department == null){
            throw new RuntimeException(Config.DEPARTMENT_NOT_EXISTS);
        }
        vo.deptName = department.getName();
        vo.deptCode = department.getOrgCode();
        return vo;
    }
}
