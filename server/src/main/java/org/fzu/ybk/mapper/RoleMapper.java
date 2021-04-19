package org.fzu.ybk.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.fzu.ybk.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper extends BaseMapper<Role> {

    @Select("SELECT role_name FROM role WHERE id = #{id}")
    String getRolenameByRoleId(Long id);

    @Select("SELECT id FROM role WHERE  role_name= #{roleName}")
    Long getRoleIdByRoleName(String roleName);
}
