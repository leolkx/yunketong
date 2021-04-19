package org.fzu.ybk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.fzu.ybk.entity.Menu;
import org.fzu.ybk.entity.RoleMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper extends BaseMapper<Menu> {


    @Select("SELECT m.*  FROM menu m LEFT JOIN r_role__menu rm ON m.id = rm.menu_id WHERE rm.role_id = #{roleId} ORDER BY order_num")
    List<Menu> selectMenuByRoleId(Long roleId);

    @Select("SELECT m.*  FROM menu m ORDER BY order_num")
    List<Menu> selectMenuList();

    @Select("select id, menu_name, parent_id, order_num, path, component, is_frame, menu_type, visible, status, ifnull(perms,'') as perms, icon, create_time " +
        "from menu where menu_name=#{menuName} and parent_id = #{parentId}")
    Menu checkMenuNameUnique(@Param("menuName")String menuName, @Param("parentId") Long parentId);

    @Insert("insert into menu( id,parent_id,menu_name,order_num,path,component,is_frame," +
            "menu_type,visible,status,perms,icon,remark,create_by,create_time )values(#{id},#{parentId},#{menuName}," +
            "#{orderNum},#{path},#{component},#{isFrame},#{menuType},#{visible},#{status},#{perms},#{icon},#{remark}," +
            "#{createBy},sysdate())")
    int insertMenu(Menu menu);

    @Update("update menu set menu_name = #{menuName},parent_id = #{parentId},order_num = #{orderNum},path = #{path}," +
            " component = #{component},is_frame = #{isFrame},menu_type = #{menuType},visible = #{visible},status = #{status}," +
            " perms = #{perms},icon = #{icon},remark = #{remark},update_by = #{updateBy},update_time = sysdate() where id = #{id}")
    int updateMenu(Menu menu);

    @Select("select count(1) from menu where parent_id = #{menuId}")
    int hasChildByMenuId(Long menuId);

    @Select("select count(1) from r_role__menu where menu_id = #{menuId}")
    int checkMenuExistRole(Long menuId);

    @Delete("delete from menu where id = #{menuId}")
    int deleteMenuById(Long menuId);

    @Insert("insert into r_role__menu( role_id, menu_id )values(#{roleId},#{menuId})")
    int insertRoleMenu(RoleMenu roleMenu);

    @Delete("delete from r_role__menu where menu_id = #{menuId} and role_id = #{roleId}")
    int deleteRoleMenu(RoleMenu roleMenu);

    @Select("SELECT m.*  FROM menu m WHERE menu_type = 'M' OR menu_type = 'C'ORDER BY order_num")
    List<Menu> selectMenuPageList();

    @Select("SELECT m.*  FROM menu m WHERE menu_type = 'F'ORDER BY order_num")
    List<Menu> selectAuthenList();

    @Select("SELECT m.*  FROM menu m LEFT JOIN r_role__menu rm ON m.id = rm.menu_id " +
            "WHERE rm.role_id = #{roleId} AND (menu_type = 'M' OR menu_type = 'C') ORDER BY order_num")
    List<Menu> selectMenuPageByRoleId(Long roleId);

    @Select("SELECT m.*  FROM menu m LEFT JOIN r_role__menu rm ON m.id = rm.menu_id " +
            "WHERE rm.role_id = #{roleId} AND menu_type = 'F'ORDER BY order_num")
    List<Menu> selectAuthenListByRoleId(Long roleId);

    @Select("SELECT id  FROM menu  WHERE menu_name = #{menuName} LIMIT 1")
    Long selectMenuIdByMenuName(String menuName);
}
