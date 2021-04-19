package org.fzu.ybk.service;


import org.fzu.ybk.StatusCode;

import org.fzu.ybk.entity.Menu;
import org.fzu.ybk.entity.RoleMenu;
import org.fzu.ybk.entity.User;
import org.fzu.ybk.mapper.MenuMapper;
import org.fzu.ybk.mapper.RoleMapper;
import org.fzu.ybk.mapper.UserMapper;
import org.fzu.ybk.utils.DateFormater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResponseService responseService;
    @Autowired
    private DateFormater dateFormater;
    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMapper roleMapper;

//    public String createActivity(PublishedActivity publishedActivity, HttpServletRequest request){
//
//        return responseService.responseFactory(StatusCode.RESPONSE_OK,"创建成功",id);
//    }
    /**
     * 查询系统所有菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */

    public String selectMenuList()
    {
        List<Menu> menuList = null;
//            menu.getParams().put("userId", userId);
            menuList = menuMapper.selectMenuList();
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",menuList);
    }
    //建立所有的菜单项的菜单树
    public String buildTreeMenuAll(){
        List<Menu> menuList = menuMapper.selectMenuList();
        List<Menu> treeMenuAll = null;
        try {
            treeMenuAll = this.buildTreeMenu(menuList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",treeMenuAll);

    }

    //根据角色id建立该角色拥有的菜单项的菜单树
    public String buildTreeMenuByRoleId(Long roleId , HttpServletRequest request){
        List<Menu> menuList = menuMapper.selectMenuByRoleId(roleId);
        List<Menu> treeMenu = null;
        try {
            treeMenu = this.buildTreeMenu(menuList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",treeMenu);

    }

    //根据用户id建立该角色拥有的菜单项的菜单树
    public String buildTreeMenuByUserId(Long userId , HttpServletRequest request){
        User user =userMapper.selectById(userId);
        Long roleId = user.getRoleId();
        List<Menu> menuList = menuMapper.selectMenuByRoleId(roleId);
        List<Menu> treeMenu = null;
        try {
            treeMenu = this.buildTreeMenu(menuList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",treeMenu);

    }

    public List<Menu> buildTreeMenu(List<Menu> menu) throws Exception {
        List<Menu> parentList = this.findParent(menu);
        return this.findChildren(parentList, menu);
    }

    public List<Menu> findParent(List<Menu> menu) throws Exception {
        List<Menu> menuList = new ArrayList<>();
        for (Menu sysMenu: menu) {
            String parentId = sysMenu.getParentId().toString();
            Boolean flag = true;
            for (Menu sysMenu1:menu) {
                if (parentId.equals(sysMenu1.getId().toString())) {
                    flag = false;
                }
            }
            if (flag) {
                menuList.add(sysMenu);
            }
        }
        return menuList;
    }
    /**
     * 递归查找子节点
     *
     */
    public List<Menu> findChildren(List<Menu> parentList, List<Menu> menu) throws Exception {
        for (Menu sysMenu: parentList) {
            List<Menu> childList = new ArrayList<>();
            for (Menu it : menu) {
                if (sysMenu.getId().toString().equals(it.getParentId().toString())) {
                    childList.add(it);
                }
            }
            sysMenu.setChildren(childList);
            if (childList.size() > 0) {
                findChildren(childList, menu);
            }
        }
        return parentList;
    }

    //新增菜单
    public String insertMenu(Menu menu , HttpServletRequest request){

        menuMapper.insertMenu(menu);

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"新增菜单成功");

    }

    //查询菜单是否唯一，也就是菜单是否已存在                           HttpServletRequest request
    public boolean checkMenuNameUnique(Menu menu ){
//        Long menuId;
//        if(menu.getId()==null){
//            menuId= -1L;
//        }else{
//            menuId=menu.getId();
//        }
        //是不是要再加一个id进去查询，保证一下id也不一样？
        Menu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());

        if ( info==null )
        {   //查不到菜单名和父ID一样的，说明是唯一的，可以插入
            return true;
        }
        return false;

    }

    public String updateMenu(Menu menu , HttpServletRequest request){

        menuMapper.updateMenu(menu);

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"修改菜单成功");

    }


    public boolean hasChildByMenuId(Long menuId ) {
        int result = menuMapper.hasChildByMenuId(menuId);
        return result > 0 ? true : false;
    }


    public boolean checkMenuExistRole(Long menuId)
    {
        int result = menuMapper.checkMenuExistRole(menuId);
        return result > 0 ? true : false;
    }

    public String deleteMenuById(Long menuId)
    {

        menuMapper.deleteMenuById(menuId);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"删除菜单成功");
    }



    //获取角色所拥有的菜单信息，不是树结构
    public String selectRoleMenuList(Long roleId)
    {
        List<Menu> menuList = menuMapper.selectMenuByRoleId(roleId);
//            menu.getParams().put("userId", userId);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询角色菜单信息成功",menuList);
    }


    //新增角色菜单菜单，为角色分配菜单
    public String insertRoleMenu(RoleMenu roleMenu, HttpServletRequest request){

        if(roleMenu.getRoleId()==null){
            Long roleId = roleMapper.getRoleIdByRoleName(roleMenu.getRoleName());
            roleMenu.setRoleId(roleId);
        }
        if (roleMenu.getMenuId()==null){
            Long menuId = menuMapper.selectMenuIdByMenuName(roleMenu.getMenuName());
            roleMenu.setMenuId(menuId);
        }

        try {
            menuMapper.insertRoleMenu(roleMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"新增角色菜单成功");

    }

    public String deleteRoleMenu(RoleMenu roleMenu)
    {
        if(roleMenu.getRoleId()==null){
            Long roleId = roleMapper.getRoleIdByRoleName(roleMenu.getRoleName());
            roleMenu.setRoleId(roleId);
        }
        if (roleMenu.getMenuId()==null){
            Long menuId = menuMapper.selectMenuIdByMenuName(roleMenu.getMenuName());
            roleMenu.setMenuId(menuId);
        }

        try {
            menuMapper.deleteRoleMenu(roleMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"删除角色菜单成功");
    }

    //获取菜单目录树
    public String selectMenuPageList()
    {
        List<Menu> menuList = menuMapper.selectMenuPageList();

        List<Menu> result = this.buildTree(menuList);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询菜单目录树成功",result);
    }


    public List<Menu> buildTree(List<Menu> menuList){
        List<Menu> tree = null;
        try {
            tree = this.buildTreeMenu(menuList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tree;

    }

    public String selectAuthenList()
    {
        List<Menu> menuList = menuMapper.selectAuthenList();

        //List<Menu> result = this.buildTree(menuList);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询菜单目录树成功",menuList);
    }


    //根据用户id建立该角色拥有的菜单目录项的菜单树，不含按钮权限
    public String  builMenuPageTreeByUserId(Long userId , HttpServletRequest request){
        User user =userMapper.selectById(userId);
        Long roleId = user.getRoleId();
        List<Menu> menuList = menuMapper.selectMenuPageByRoleId(roleId);
        List<Menu> treeMenu = null;
        try {
            treeMenu = this.buildTreeMenu(menuList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询用户菜单目录树成功",treeMenu);

    }


    //根据角色id建立该角色拥有的菜单项的菜单树
    public String selectAuthenListByRoleId(Long roleId , HttpServletRequest request){
        List<Menu> authenList = menuMapper.selectAuthenListByRoleId(roleId);

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"通过角色查询权限按钮列表成功",authenList);

    }
    //根据用户id建立该角色拥有的菜单目录项的菜单树，不含按钮权限
    public String  builMenuPageTreeByRoleId(Long roleId , HttpServletRequest request){
        List<Menu> menuList = menuMapper.selectMenuPageByRoleId(roleId);
        List<Menu> treeMenu = null;
        try {
            treeMenu = this.buildTreeMenu(menuList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询角色菜单目录树成功",treeMenu);

    }




}
