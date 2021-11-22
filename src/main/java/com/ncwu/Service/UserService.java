package com.ncwu.Service;


import com.ncwu.Entity.User;
import com.ncwu.Repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.Transient;

@Service
public class UserService {
    @Resource
    private UserRepository userRepository;

    /**
     * 根据邮箱和密码查找用户
     */
    public User getUserByEmailAndPwd(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    /**
     * 只根据邮箱查找用户
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transient
    public void save(User user) {
        userRepository.save(user);
    }

}
