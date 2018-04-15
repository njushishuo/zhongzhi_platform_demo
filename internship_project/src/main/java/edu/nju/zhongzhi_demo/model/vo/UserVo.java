package edu.nju.zhongzhi_demo.model.vo;

import edu.nju.zhongzhi_demo.entity.User;
import edu.nju.zhongzhi_demo.enums.Role;

public class UserVo {
    public int id;
    public String username;
    public String email;
    public String role;
    public Integer deptId;

    public UserVo(){

    }

    public UserVo(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole().toString();
        this.deptId = user.getDeptId();
    }
}
