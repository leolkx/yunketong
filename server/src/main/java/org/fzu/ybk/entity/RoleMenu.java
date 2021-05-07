package org.fzu.ybk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenu {
    //角色id
    private Long roleId;
    //菜单id
    private Long menuId;
    //角色名
    private String roleName;
    //菜单名
    private String menuName;



}
