package edu.nju.zhongzhi_demo.controller;

import edu.nju.zhongzhi_demo.entity.App;
import edu.nju.zhongzhi_demo.model.para.AppPara;
import edu.nju.zhongzhi_demo.model.vo.AppVo;
import edu.nju.zhongzhi_demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppController {

    @Autowired
    AppService appService;

    @Autowired
    ResourceService resourceService;

    @GetMapping("/isv/{userId}/appList")
    public List<AppVo> getAppList(@PathVariable int userId){
        return  this.appService.getAppListByUserId(userId);
    }


    @PostMapping("/isv/app")
    public AppVo createApp(@RequestBody AppPara appPara){

        App app = new App();
        app.setName(appPara.name);
        app.setDescription(appPara.desc);
        app.setOwnerId(appPara.ownerId);
        this.appService.save(app);
        app = this.appService.getByAppName(appPara.name);
        return this.appService.transform(app);
    }


    @GetMapping("/isv/{id}")
    public AppVo getAppInfo(@PathVariable int id){
        App app = this.appService.getById(id);
        AppVo appVo = this.appService.transform(app);
        appVo.resourceInfo = this.resourceService.getResourceInfoByAppId(id);
        return appVo;
    }






}
