package edu.nju.zhongzhi_demo.controller;

import edu.nju.zhongzhi_demo.entity.User;
import edu.nju.zhongzhi_demo.model.para.LoginPara;
import edu.nju.zhongzhi_demo.service.AccountService;
import edu.nju.zhongzhi_demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AccountService accountService;
    @Autowired
    AuthService authService;
    @PostMapping("/login")
    public String login(@RequestBody LoginPara loginPara){

        System.out.println(loginPara.username);
        User user = this.accountService.getByUsername(loginPara.username);
        if(user == null){
            return Config.UNKNOWN_USER;
        }else{

            if (!loginPara.password.equals(user.getPassword()) ){
                return Config.ERROR_PASSWORD;
            }else{
                this.authService.setAuth(user);
                return "success";
            }
        }

    }

}
