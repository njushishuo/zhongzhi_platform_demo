package edu.nju.zhongzhi_demo.service;

import edu.nju.zhongzhi_demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Service
public class AuthService {
    @Autowired
    HttpSession session;

    private static final String AUTH_KEY = "auth";

    public User getAuthUser(){
        Object result = getSession().getAttribute(AUTH_KEY);
        if(result == null){
            throw  new RuntimeException("please login first");
        }

        return (User)result;
    }

    private static HttpSession getSession() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest().getSession();
    }

    public void setAuth(User user){

        HttpSession session = getSession();
        session.setAttribute(AUTH_KEY,user);

    }

    public void removeAuth(){

        HttpSession session = getSession();
        session.removeAttribute(AUTH_KEY);

    }
}
