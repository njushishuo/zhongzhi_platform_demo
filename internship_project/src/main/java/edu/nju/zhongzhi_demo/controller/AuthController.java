package edu.nju.zhongzhi_demo.controller;

import edu.nju.zhongzhi_demo.entity.User;
import edu.nju.zhongzhi_demo.model.para.LoginPara;
import edu.nju.zhongzhi_demo.model.vo.UserVo;
import edu.nju.zhongzhi_demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AccountService accountService;

    @PostMapping("/login")
    public UserVo login(@RequestBody LoginPara loginPara){

        User user = this.accountService.getByEmail(loginPara.email);
        if(user == null){
            throw new RuntimeException(Config.UNKNOWN_USER);
        }else{

            if (!loginPara.password.equals(user.getPassword()) ){
                throw new RuntimeException(Config.ERROR_PASSWORD);
            }else{
                UserVo userVo = new UserVo(user);
                return userVo;
            }
        }

    }

}
