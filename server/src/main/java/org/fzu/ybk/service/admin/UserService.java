package org.fzu.ybk.service.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.RandomStringUtils;
import org.fzu.ybk.StatusCode;
import org.fzu.ybk.entity.Role;
import org.fzu.ybk.entity.User;
import org.fzu.ybk.exception.UserException;
import org.fzu.ybk.mapper.RoleMapper;
import org.fzu.ybk.mapper.UserMapper;
import org.fzu.ybk.service.ResponseService;
import org.fzu.ybk.shiroPackage.util.SHA256Util;
import org.fzu.ybk.utils.StringVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description:
 * @author: Mu.xx
 * @date: 2020/5/16 14:13
 */

@Service
// 该服务用于对用户的完全控制，仅限超级管理员使用
public class UserService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResponseService responseService;
    @Autowired
    private StringVerifier stringVerifier ;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public String addUser(User user, HttpServletRequest request) throws Exception{
        //auth

        user.setId(null);

        if (user.getUsername() == null)
            throw new UserException("用户名不能为空");

        if (user.getPassword() == null)
            throw new UserException("密码不能为空");

        if (stringVerifier.verifyUserName(user.getUsername()) == Boolean.FALSE)
            throw new UserException("用户名必须满足正则：^[a-zA-Z][a-zA-Z0-9_]{2,17}，看不懂你用什么app");

        user.setIsActive(Boolean.TRUE);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",user.getUsername());
        User res = userMapper.selectOne(wrapper);
        if (res != null)
            throw new UserException("用户名已存在");

        //对密码加密一下
            //设置为正常状态
            user.setState("NORMAL");
            // 随机生成盐值
            String salt = RandomStringUtils.randomAlphanumeric(20);
            user.setSalt(salt);
            // 进行加密
            String password =  user.getPassword();
            user.setPassword(SHA256Util.sha256(password, user.getSalt()));

        userMapper.insert(user);

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"创建成功");
    }

    public String deleteUser(Long userId, HttpServletRequest request){
        //auth
        int n = userMapper.deleteById(userId);
        if (n == 0)
            return responseService.responseFactory(StatusCode.RESPONSE_OK,"没有对应项");
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"删除成功");
    }

    public String getUsers(Long page,Long pageSize, HttpServletRequest request) throws Exception{
        //auth

        if (page == null || pageSize == null){
            page = 1L;
            pageSize = 10L;
        }

        List<User> results;
        Page<User> pageManager = new Page<>(page,pageSize);
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.select(User.class, info -> !info.getColumn().equals("password"));
        results = userMapper.selectPage(pageManager,wrapper).getRecords();

        //代价很大的查询角色名......待优化.....

        for (int i=0 ; i<results.size();++i){
            Long roleId = results.get(i).getRoleId();
            if (roleId != null ){
                QueryWrapper<Role> wrapper2 = new QueryWrapper<>();
                wrapper2.select("role_name");
                wrapper2.eq("id",roleId);
                Role role = roleMapper.selectOne(wrapper2);
                if (role != null)
                    results.get(i).setRoleName(role.getRoleName());
            }
        }

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",results);
    }

    public String updateUser(User user, HttpServletRequest request) throws Exception{
        //auth
        if (user == null || user.getId() == null){
            throw new UserException("未指定的用户id");
        }

        //可以直接更新密码，但不能读取.
        if (user.getUsername() != null){

            if (stringVerifier.verifyUserName(user.getUsername()) == Boolean.FALSE)
                throw new UserException("用户名必须满足正则：^[a-zA-Z][a-zA-Z0-9_]{2,17}，看不懂你用什么app");

            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username",user.getUsername());
            List<User> res = userMapper.selectList(wrapper);

            if (res != null){
                if (res.size() > 1)
                    throw new UserException("用户名已存在1");
                if (res.size() == 1 && ! res.get(0).getId().equals(user.getId()))
                {
                    System.out.println(res.get(0).getId());
                    System.out.println(user.getId());
                    throw new UserException("用户名已存在2");
                }
            }

        }

        userMapper.updateById(user);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"更新成功");
    }


    public String getUserDetailById(Long id, HttpServletRequest request) throws Exception{
        //auth
        if (id == null){
            throw new UserException("未指定的用户id");
        }

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select(User.class, info -> !info.getColumn().equals("password"))
                .eq("id",id);
        User result = userMapper.selectOne(wrapper);

        Long roleId = result.getRoleId();
        if (roleId != null){
            QueryWrapper<Role> wrapper2 = new QueryWrapper<>();
            wrapper2.select("role_name").eq("id",roleId);
            Role role = roleMapper.selectOne(wrapper2);
            if (role != null)
                result.setRoleName(role.getRoleName());
        }

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",result);
    }

    public String getUserDetailByName(String username, HttpServletRequest request) throws Exception{
        //auth
        if (username == null){
            throw new UserException("未指定的用户名");
        }

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select(User.class, info -> !info.getColumn().equals("password"))
                .eq("username",username);
        User result = userMapper.selectOne(wrapper);

        Long roleId = result.getRoleId();
        if (roleId != null){
            QueryWrapper<Role> wrapper2 = new QueryWrapper<>();
            wrapper2.select("role_name").eq("id",roleId);
            Role role = roleMapper.selectOne(wrapper2);
            if (role != null)
                result.setRoleName(role.getRoleName());
        }

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",result);
    }


    public String getUsersCount(HttpServletRequest request) throws Exception{
        //auth
        Integer count = userMapper.selectCount(null);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",count);
    }


    //
    public User getUserEntityByName(String username) throws Exception{
        //auth
        if (username == null){
            throw new UserException("未指定的用户名");
        }

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        User result = userMapper.selectOne(wrapper);

        Long roleId = result.getRoleId();
        if (roleId != null){
            QueryWrapper<Role> wrapper2 = new QueryWrapper<>();
            wrapper2.select("role_name").eq("id",roleId);
            Role role = roleMapper.selectOne(wrapper2);
            if (role != null)
                result.setRoleName(role.getRoleName());
        }

        return result;
    }

}
