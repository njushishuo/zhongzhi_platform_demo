package edu.nju.zhongzhi_demo.dao;

import edu.nju.zhongzhi_demo.entity.User;
import edu.nju.zhongzhi_demo.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AccountRepo extends JpaRepository<User,Integer> {

    User getByUsername(String username);

    User getByEmail(String email);

    List<User> getAllByRoleAndDeptId(Role role, int deptId);
}
