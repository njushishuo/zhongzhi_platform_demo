package edu.nju.zhongzhi_demo.dao;

import edu.nju.zhongzhi_demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepo extends JpaRepository<User,Integer> {

    User getByUsername(String username);

    User getByEmail(String email);
}
