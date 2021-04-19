package org.fzu.ybk.controller;


import org.fzu.ybk.StatusCode;
import org.fzu.ybk.entity.Menu;
import org.fzu.ybk.entity.RoleMenu;
import org.fzu.ybk.service.MenuService;
import org.fzu.ybk.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class MenuController {
    @Autowired
    MenuService menuService;
    @Autowired
    ResponseService responseService;
    //获取所有树形菜单
    @GetMapping(value = "/menuTreeAll")
    //@RequiresRoles("USER")
    //@RequiresPermissions("system:user:list")
    public String getMenuTreeAll(
            HttpServletRequest request){
        try{
            return menuService.buildTreeMenuAll();
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }


    //根据角色id获取对于的菜单树
    @GetMapping(value = "/roleMenuTree/{roleId}")
    public String roleMenuTree(@PathVariable("roleId") Long roleId ,HttpServletRequest request)
    {
//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        List<SysMenu> menus = menuService.selectMenuList(loginUser.getUser().getUserId());
        try{
            return menuService.buildTreeMenuByRoleId(roleId,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }

    }
    //根据用户id获取对应的菜单树
    @GetMapping(value = "/userMenuTree/{userId}")
    public String userMenuTree(@PathVariable("userId") Long userId ,HttpServletRequest request)
    {
//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        List<SysMenu> menus = menuService.selectMenuList(loginUser.getUser().getUserId());
        try{
            return menuService.buildTreeMenuByUserId(userId,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }

    }


    @PostMapping (value = "/menuAdd")
    public String menuAdd(@RequestBody Menu menu , HttpServletRequest request)
    {
//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        List<SysMenu> menus = menuService.selectMenuList(loginUser.getUser().getUserId());
        if (!menuService.checkMenuNameUnique(menu))
        {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        //menu.setCreateBy(SecurityUtils.getUsername());
        try{
            return menuService.insertMenu(menu,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }

    }
    //更新菜单信息，主要是菜单id要对
    @PutMapping(value = "/menuEdit")
    public String menuEdit(@RequestBody Menu menu , HttpServletRequest request)
    {
//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        List<SysMenu> menus = menuService.selectMenuList(loginUser.getUser().getUserId());
        //menu.setCreateBy(SecurityUtils.getUsername());
        try{
            return menuService.updateMenu(menu,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }

    }


    @DeleteMapping(value = "/menuDelete/{menuId}")
    public String menuRemove(@PathVariable("menuId") Long menuId, HttpServletRequest request)
    {
        if (menuService.hasChildByMenuId(menuId))
        {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId))
        {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"菜单已分配,不允许删除");
        }
        try{
            return menuService.deleteMenuById(menuId);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }

    }


    //获取所有菜单信息 ，不是树结构
    @GetMapping(value = "/menuAll")
    public String getMenuAll(
            HttpServletRequest request){
        try{
            return menuService.selectMenuList();
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    //获取对应角色的菜单信息，不是菜单树结构，只是列表结构
    @GetMapping(value = "/roleMenuAll/{roleId}")
    public String getRoleMenuAll(@PathVariable("roleId") Long roleId,
            HttpServletRequest request){
        try{
            return menuService.selectRoleMenuList(roleId);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    //是否要插入 isdelete字段
    @PostMapping (value = "/roleMenuAdd")
    public String roleMenuAdd(@RequestBody RoleMenu roleMenu, HttpServletRequest request)
    {
        if (roleMenu.getRoleId()==null && roleMenu.getRoleName()==null)
        {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"请输入角色信息");
        }
        if (roleMenu.getMenuId()==null && roleMenu.getMenuName()==null)
        {
        return responseService.responseFactory(StatusCode.RESPONSE_ERR,"请输入菜单信息");
        }
        try{
            return menuService.insertRoleMenu(roleMenu,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }

    }

    //删除角色菜单表信息
    @DeleteMapping(value = "/roleMenuDelete")
    public String roleMenuRemove(@RequestBody RoleMenu roleMenu, HttpServletRequest request)
    {
        if (roleMenu.getRoleId()==null && roleMenu.getRoleName()==null)
        {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"请输入角色信息");
        }
        if (roleMenu.getMenuId()==null && roleMenu.getMenuName()==null)
        {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"请输入菜单信息");
        }
        try{
            return menuService.deleteRoleMenu(roleMenu);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }

    }

    //获取所有菜单，不包括按钮（权限）信息 ，是菜单树
    @GetMapping(value = "/menuPageTreeAll")
    public String getMenuPageAll(
            HttpServletRequest request){
        try{
            return menuService.selectMenuPageList();
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    //获取按钮（权限）信息，是列表结构，不是树
    @GetMapping(value = "/authenAll")
    public String getAuthenAll(
            HttpServletRequest request){
        try{
            return menuService.selectAuthenList();
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    //根据用户id获取对应的菜单树
    @GetMapping(value = "menuPageTreeAllByUser/{userId}")
    public String userMenuPageTree(@PathVariable("userId") Long userId ,HttpServletRequest request)
    {
        try{
            return menuService.builMenuPageTreeByUserId(userId,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    //根据角色id获取对应的按钮权限列表
    @GetMapping(value = "authenByRole/{roleId}")
    public String getAuthenByRole(@PathVariable("roleId") Long roleId ,HttpServletRequest request)
    {
        try{
            return menuService.selectAuthenListByRoleId(roleId,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }


    @GetMapping(value = "menuPageTreeAllByRole/{roleId}")
    public String roleMenuPageTree(@PathVariable("roleId") Long roleId ,HttpServletRequest request)
    {
        try{
            return menuService.builMenuPageTreeByRoleId(roleId,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }


}
