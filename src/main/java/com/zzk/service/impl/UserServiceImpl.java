package com.zzk.service.impl;

import com.zzk.mapper.UserMapper;
import com.zzk.pojo.User;
import com.zzk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectUserPage(String userName, Character userSex, int startRow) {
        return userMapper.selectUserPage(userName,userSex,startRow);
    }

    @Override
    public int createUser(User user) {
        return userMapper.createUser(user);
    }

    @Override
    public int deleteUserById(String userId) {
        return userMapper.deleteUserById(userId);
    }

    @Override
    public int getRowCount(String userName, Character userSex) {
        return userMapper.getRowCount(userName,userSex);
    }

    @Override
    public int updateUserById(User user) {
        return userMapper.updateUserById(user);
    }
}
