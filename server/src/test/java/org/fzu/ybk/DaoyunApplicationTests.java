package org.fzu.ybk;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.fzu.ybk.entity.PublishedActivity;
import org.fzu.ybk.mapper.*;
import org.fzu.ybk.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DaoyunApplicationTests {

    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    PublishedActivityMapper publishedActivityMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    MenuService menuService;

    @Test
    void contextLoads() {

    }

    @Test
    void func(){

//        PublishedActivity publishedActivity = new PublishedActivity();
//        publishedActivity.setActivityTypeId(10L);
//        publishedActivity.setOrgId(10L);
//        publishedActivity.setIsActive(Boolean.TRUE);
//        int result = publishedActivityMapper.insert(publishedActivity);
//        QueryWrapper<UserAuthenticationInfo> wrapper = new QueryWrapper<>();
//        wrapper.eq("username","admin");

//        String username = "admin";
//        UserAuthenticationInfo user = userMapper.getUserAuthenticationInfoByUserName(username);
//        System.out.println(user.getUsername());
//        System.out.println(user.getState());
//        System.out.println(user.getId());
//        System.out.println(user.getPassword());

 //      List<Menu> menus = menuService.buildTreeMenuAll();

//        System.out.println(.getUsername());
//        System.out.println(user.getState());
//        System.out.println(user.getId());
//        System.out.println(user.getPassword());
//            (menuMapper.selectMenuByRoleId(1L)).forEach(System.out::println);
//            String rolename =roleMapper.getRolenameByRoleId(1L);
//            System.out.println(rolename);

        QueryWrapper<PublishedActivity> wrapper = new QueryWrapper<>();
//        wrapper.eq("is_active",1)
//                .eq("org_id",orgId);
        wrapper.eq("org_id",10) //还需要返回未进行的活动，
                .orderByAsc("creation_date");
        List<PublishedActivity> results;
        results = publishedActivityMapper.selectList(wrapper);
        results.forEach(System.out::println);


        //测试注册
//        Date date = new Date();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateStr = format.format(date);
//        User user = new User();
//        user.setUsername("hzqhzq");
//        user.setState("NORMAL");
//        user.setEmail("993352270@qq.com");
//        // 随机生成盐值
//        String salt = RandomStringUtils.randomAlphanumeric(20);
//        user.setSalt(salt);
//        // 进行加密
//        //String password =  user.getPassword();
//        String password = "123456";
//        user.setPassword(SHA256Util.sha256(password, user.getSalt()));
//        userMapper.createAccount(Role.ORDINARY_USER,user.getUsername(),user.getPassword(),user.getSalt(),user.getState(),user.getEmail(),dateStr,true,false );
//
//
//        //测试查询菜单唯一
//        Menu menu = menuMapper.checkMenuNameUnique("用户",1L);
//        System.out.println(menu);

        //测试serve层 菜单唯一
//        Menu menu = new Menu();
//        menu.setMenuName("用hu");
//        menu.setId(116L);
//        menu.setParentId(1L);
//        menuMapper.updateMenu(menu);
//        int count = menuMapper.hasChildByMenuId(4L);
//          int count = menuMapper.deleteMenuById(117L);
//          System.out.println(count);
//        boolean unique = menuService.checkMenuNameUnique(menu);
//        System.out.println(unique);



        //UserAuthenticationInfo getUserAuthenticationInfoByUserName
//        publishedActivityMapper.deleteById(1L);
//
//        List<PublishedActivity> pas = publishedActivityMapper.selectList(null);
//        List<AttendActivity> aas = activityMapper.selectList(null);

//        pas.forEach(System.out::println);
//        aas.forEach(System.out::println);
//        System.out.println(result);

    }

}
