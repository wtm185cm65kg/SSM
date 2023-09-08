package com.zzk.controller;

import com.zzk.pojo.User;
import com.zzk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 如果一个类中所有请求都为Ajax,则可将@Controller注解换为@RestController
 * 这样方法上的@ResponseBody就可以省略了
 */
@RestController
/**
 * @CrossOrigin表示在服务器端支持跨域访问 (只有前端支持不行,后端也必须支持)
 */
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static final int PAGE_SIZE = 5;

    /**根据条件分页获取分页数据
     * url: /user/selectUserPage?userName=z&userSex=男&page=null
     * 返回结果:集合*/
    //@ResponseBody
    @RequestMapping("/selectUserPage")
    public List<User> selectUserPage(String userName,Character userSex,Integer page){
        /**
         * 根据页码计算起始行
         * 此时访问/user/selectUserPage?page=3只会显示2条数据(因为一共12条,现选择展示第三页数据)
         * 切记要传的是page=3而不是startRow=10,因为使用了Controller层后就只能识别page而不能识别startRow了
         */
        int startRow=0;
        if (page!=null){
            startRow = (page-1)*PAGE_SIZE;
        }
        return userService.selectUserPage(userName,userSex,startRow);
    }

    /**获取总行数
     * url：/user/getRowCount?userName=z&userSex=男
     * 返回结果: 结果条数*/
    //@ResponseBody
    @RequestMapping("/getRowCount")
    public int getRowCount(String userName,Character userSex){
        return userService.getRowCount(userName,userSex);
    }


    /**增加用户
     * url：/user/createUser(参数见下面)
     * 返回结果: 0/1*/
    //@ResponseBody
    @RequestMapping("/createUser")
    public int createUser(User user){
        /**将时间戳拼接上""变为String,当作userId,这样就达到了类似于'随机生成不重复id'的效果*/
        String userId=System.currentTimeMillis()+"";
        user.setUserId(userId);
        return userService.createUser(user);
    }

    /**根据用户ID删除用户
     * url:/user/deleteUserById?userId=15968162087363060
     * 返回结果: 0/1*/
    //@ResponseBody
    @RequestMapping("/deleteUserById")
    public int deleteUserById(String userId){
        return userService.deleteUserById(userId);
    }

    /**更新用户
     * url:/user/updateUserById(参数见以下)
     * 返回结果: 0/1*/
    //@ResponseBody
    @RequestMapping("/updateUserById")
    public int updateUserById(User user){
        return userService.updateUserById(user);
    }
}
