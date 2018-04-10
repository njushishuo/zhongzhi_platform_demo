package edu.nju.zhongzhi_demo.service;

import edu.nju.zhongzhi_demo.dao.AccountRepo;
import edu.nju.zhongzhi_demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Service
public class AccountService {

    @Autowired
    AccountRepo accountRepo;

    public User getById(int id){

        return this.accountRepo.getOne(id);

    }

    public User getByUsername(String username){

        return this.accountRepo.getByUsername(username);

    }

}
