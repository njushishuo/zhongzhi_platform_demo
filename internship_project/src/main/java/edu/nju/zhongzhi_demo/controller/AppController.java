package edu.nju.zhongzhi_demo.controller;

import edu.nju.zhongzhi_demo.entity.App;
import edu.nju.zhongzhi_demo.entity.Department;
import edu.nju.zhongzhi_demo.entity.User;
import edu.nju.zhongzhi_demo.model.para.AppPara;
import edu.nju.zhongzhi_demo.model.vo.AppVo;
import edu.nju.zhongzhi_demo.service.AccountService;
import edu.nju.zhongzhi_demo.service.AppService;
import edu.nju.zhongzhi_demo.service.DeptService;
import edu.nju.zhongzhi_demo.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    AppService appService;
    @Autowired
    DeptService deptService;
    @Autowired
    AccountService accountService;
    @Autowired
    ResourceService resourceService;


    @PostMapping
    public AppVo createApp(@RequestBody AppPara appPara){

        App app = new App();
        app.setName(appPara.name);
        app.setDescription(appPara.desc);
        app.setOwnerId(appPara.ownerId);
        this.appService.save(app);
        app = this.appService.getByAppName(appPara.name);
        return this.transform(app);
    }


    @GetMapping("/{id}")
    public AppVo getAppInfo(@PathVariable int id){
        App app = this.appService.getById(id);
        AppVo appVo = this.transform(app);
        appVo.resourceInfo = this.resourceService.getResourceInfoByAppId(id);
        return appVo;
    }


    private AppVo transform(App app){
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
