package edu.nju.zhongzhi_demo.controller;

import edu.nju.zhongzhi_demo.model.wrapper.ResourceInfo;
import edu.nju.zhongzhi_demo.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Resourcecontroller {

    @Autowired
    ResourceService resourceService;
    @GetMapping("/resource/app/{id}")
    public ResourceInfo getAllResourcesAppCanApply(@PathVariable int id){
        return this.resourceService.getResourceInfoAppCanApply(id);
    }


}
