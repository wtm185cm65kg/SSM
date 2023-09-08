package com.zzk.service;

import com.zzk.pojo.User;

import java.util.List;

public interface UserService {
    /**根据条件分页获取分页数据
     * url: /user/selectUserPage?userName=z&userSex=男&page=null*/
    List<User> selectUserPage(String userName,Character userSex,int startRow);

    /**增加用户
     * url：/user/createUser(参数见下面)*/
    int createUser(User user);

    /**根据用户ID删除用户
     * url:/user/deleteUserById?userId=15968162087363060*/
    int deleteUserById(String userId);

    /**获取总行数
     * url：/user/getRowCount?userName=z&userSex=男*/
    int getRowCount(String userName,Character userSex);

    /**更新用户
     * url:/user/updateUserById(参数见以下)*/
    int updateUserById(User user);
}
