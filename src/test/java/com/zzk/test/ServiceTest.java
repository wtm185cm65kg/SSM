package com.zzk.test;

import com.zzk.pojo.User;
import com.zzk.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring_mapper.xml","classpath:spring_service.xml"})
public class ServiceTest {
    @Autowired
    private UserService userService;


    @Test
    public void testGetRowCount(){
        int rowCount1 = userService.getRowCount("三", null);
        System.out.println("姓名含'三'的条目数: "+rowCount1);
        System.out.println();

        int rowCount2 = userService.getRowCount(null, '女');
        System.out.println("性别为'女'的条目数: "+rowCount2);
        System.out.println();

        int rowCount3 = userService.getRowCount(null, null);
        System.out.println("所有条目数: "+rowCount3);
    }

    @Test
    public void testSelectUserPage(){
        List<User> users1 = userService.selectUserPage("三",null,0);
        users1.forEach(user -> System.out.println(user));
        System.out.println("\n");

        List<User> users21 = userService.selectUserPage(null,'女',0);
        users21.forEach(user -> System.out.println(user));
        System.out.println("<分页>");
        List<User> users22 = userService.selectUserPage(null,'女',5);
        users22.forEach(user -> System.out.println(user));
        System.out.println("\n");

        List<User> users31 = userService.selectUserPage(null,null,0);
        users31.forEach(user -> System.out.println(user));
        System.out.println("<分页>");
        List<User> users32 = userService.selectUserPage(null,null,5);
        users32.forEach(user -> System.out.println(user));
        System.out.println("<分页>");
        List<User> users33 = userService.selectUserPage(null,null,10);
        users33.forEach(user -> System.out.println(user));
    }

    @Test
    public void testCreateUser(){
        User user=new User("2020028227","身份证","JS320322200202168238","周中凯",'男',21,"大四学生");
        int num = userService.createUser(user);
        System.out.println("新建条目数: "+num);
    }

    /**根据是否使用<set/>+<if/>有不同的效果*/
    @Test
    public void testUpdateUserById(){
        User user=new User("2020028227",null,null,"zzk",null,22,"未来Java工程师");
        int num = userService.updateUserById(user);
        System.out.println("修改条目数: "+num);
    }

    @Test
    public void testDeleteUserById(){
        int num = userService.deleteUserById("2020028227");
        System.out.println("删除条目数: "+num);
    }
}
