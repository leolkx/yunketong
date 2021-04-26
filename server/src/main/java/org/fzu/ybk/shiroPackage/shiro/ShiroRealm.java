package org.fzu.ybk.shiroPackage.shiro;

import org.fzu.ybk.entity.Menu;
import org.fzu.ybk.entity.UserAuthenticationInfo;
import org.fzu.ybk.mapper.MenuMapper;
import org.fzu.ybk.mapper.RoleMapper;
import org.fzu.ybk.mapper.UserMapper;
import org.fzu.ybk.service.admin.UserService;
//import com.sh.demo.core.entity.SysMenuEntity;
//import com.sh.demo.core.entity.SysRoleEntity;
//import com.sh.demo.core.entity.SysUserEntity;
//import com.sh.demo.core.service.SysMenuService;
//import com.sh.demo.core.service.SysRoleService;
//import com.sh.demo.core.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
//import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashSet;
//import java.util.List;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description Shiro权限匹配和账号密码匹配

 */
public class ShiroRealm extends AuthorizingRealm {

//    @Autowired
//    private SysUserService sysUserService;
//    @Autowired
//    private SysRoleService sysRoleService;
//    @Autowired
//    private SysMenuService sysMenuService;
      @Autowired
      private UserService userService;
      @Autowired
      private UserMapper userMapper;
      @Autowired
      private RoleMapper roleMapper;
      @Autowired
      private MenuMapper menuMapper;
    /**
     * 授权权限
     * 用户进行权限验证时候Shiro会去缓存中找,如果查不到数据,会执行这个方法去查权限,并放入缓存中
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户ID
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserAuthenticationInfo userAuthenticationInfo = (UserAuthenticationInfo) principalCollection.getPrimaryPrincipal();
        Long id =userAuthenticationInfo.getId(); //获取用户id
        //这里可以进行授权和处理
        Set<String> rolesSet = new HashSet<>();
        Set<String> permsSet = new HashSet<>();
        //查询角色和权限(这里根据业务自行查询)
        //List<SysRoleEntity> sysRoleEntityList = sysRoleService.selectSysRoleByUserId(userId);
        Long roleId = userAuthenticationInfo.getRoleId();
        String roleName=roleMapper.getRolenameByRoleId(roleId);
        rolesSet.add(roleName);
        System.out.println(roleName);
        List<Menu> menuList = menuMapper.selectMenuByRoleId(roleId);
        for (Menu menu :menuList) {
            String perms =menu.getPerms();
            System.out.println("获取到"+perms);
            if(perms==null||perms.length()<= 0){//如果权限标识符是空就跳过，不添加到权限集合中
                continue;
                }
            permsSet.add(perms);
            System.out.println("插入"+perms);
            }


//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        //这里可以进行授权和处理
//        Set<String> rolesSet = new HashSet<>();
//        Set<String> permsSet = new HashSet<>();
//        rolesSet.add("ADMIN");
//        permsSet.add("sys:user:info");


        //将查到的权限和角色分别传入authorizationInfo中
        authorizationInfo.setStringPermissions(permsSet);
        authorizationInfo.setRoles(rolesSet);
        return authorizationInfo;
    }

    /**
     * 身份认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String) authenticationToken.getPrincipal();
        //通过username从数据库中查找 User对象，如果找到进行验证
        //实际项目中,这里可以根据实际情况做缓存,如果不做,Shiro自己也是有时间间隔机制,2分钟内不会重复执行该方法
        //SysUserEntity user = sysUserService.selectUserByName(username);
//        User user = null;
//        try {
//            user = userService.getUserEntityByName(username);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().eq(User::getUsername,username);
//        QueryWrapper wrapper = new QueryWrapper();
//        wrapper.eq("username","admin");
//        User user = userMapper.selectById(1);
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("username",username);
//        User user = userMapper.selectOne(wrapper);
        //System.out.println(user.getId());

        UserAuthenticationInfo user = userMapper.getUserAuthenticationInfoByUserName(username);
//        System.out.println(user.getUsername());
//        UserAuthenticationInfo user = new  UserAuthenticationInfo();
//        user.setUsername("admin");
//        user.setPassword("a1bb09ad5dea12e0f94762cb116c447e80c784d8aa2c6625263f7f3436cdd583");
//        user.setSalt("RvP3UID2n30Q2sycZYvH");
//        user.setState("NORMAL");
//        user.setId(1L);
        //判断账号是否存在
        if (user == null) {
            throw new AuthenticationException();
        }
        //判断账号是否被冻结
        if (user.getState()==null||user.getState().equals("PROHIBIT")){
            throw new LockedAccountException();
        }
        //进行验证
        //如果验证成功，最终这里返回的信息authenticationInfo 的值与传入的第一个字段的值相同（这里传的是user对象）。
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,                                  //用户名
                user.getPassword(),                    //密码
                ByteSource.Util.bytes(user.getSalt()), //设置盐值
                getName()
        );


//        获取用户的输入的账号.
//        通过username从数据库中查找 User对象，如果找到进行验证
//        实际项目中,这里可以根据实际情况做缓存,如果不做,Shiro自己也是有时间间隔机制,2分钟内不会重复执行该方法
//        SysUserEntity user = sysUserService.selectUserByName(username);
//        //判断账号是否存在
//        if (user == null) {
//            throw new AuthenticationException();
//        }
//        //判断账号是否被冻结
//        if (user.getState()==null||user.getState().equals("PROHIBIT")){
//            throw new LockedAccountException();
//        }
//        sysUserEntity user = new sysUserEntity();
//        user.setUsername("admin");
//        user.setPassword("a1bb09ad5dea12e0f94762cb116c447e80c784d8aa2c6625263f7f3436cdd583");
//        user.setSalt("RvP3UID2n30Q2sycZYvH");
//        user.setState("NORMAL");
//
//        user.setUserId(1L);
//        String username = user.getUsername();
//        //进行验证
//        //如果验证成功，最终这里返回的信息authenticationInfo 的值与传入的第一个字段的值相同（这里传的是user对象）。
//        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
//                user,                                  //用户名
//                user.getPassword(),                    //密码
//                ByteSource.Util.bytes(user.getSalt()), //设置盐值
//                getName()
//        );
        //验证成功开始踢人(清除缓存和Session)
        // 原demo有清理缓存踢人操作，这里注释了，因为sessionmanager  里面如果没带token设置cookie方式获取
        //sessionid的话，会把原来的session删了，二次登录的时候就直接删了。

        //ShiroUtils.deleteCache(username,true);
        return authenticationInfo;
    }
}
