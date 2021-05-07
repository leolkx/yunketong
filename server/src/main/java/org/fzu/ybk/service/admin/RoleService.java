package org.fzu.ybk.service.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.fzu.ybk.StatusCode;
import org.fzu.ybk.entity.Role;
import org.fzu.ybk.exception.RoleException;
import org.fzu.ybk.mapper.RoleMapper;
import org.fzu.ybk.service.ResponseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private ResponseService responseService;

    private final Logger logger = LoggerFactory.getLogger(RoleService.class);

    public String addRole(Role role, HttpServletRequest request) throws Exception{
        //auth

        role.setId(null);
        role.setIsTemplate(Boolean.TRUE);

        if (role.getRoleCode() == null)
            throw new RoleException("角色代码不能为空");

        if (role.getRoleName() == null)
            throw new RoleException("角色名不能为空");


        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_code",role.getRoleCode());
        Role res = roleMapper.selectOne(wrapper);
        if (res != null)
            throw new RoleException("角色代码已存在");

        roleMapper.insert(role);

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"创建成功");
    }

    public String deleteRole(Long roleId, HttpServletRequest request){
        //auth

        int n = roleMapper.deleteById(roleId);
        if (n == 0)
            return responseService.responseFactory(StatusCode.RESPONSE_OK,"没有对应项");
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"删除成功");
    }

    public String getRoleById(Long id, HttpServletRequest request) throws Exception{
        //auth
        if (id == null)
            throw new RoleException("角色ID为空");

        Role result = roleMapper.selectById(id);

        if (result == null )
            throw new RoleException("不存在该角色");

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",result);
    }

    public String getRoles(Long page,Long pageSize, HttpServletRequest request) throws Exception{
        //auth
        if (page == null || pageSize == null){
            page = 1L;
            pageSize = 10L;
        }

        List<Role> results;
        Page<Role> pageManager = new Page<>(page,pageSize);
        results = roleMapper.selectPage(pageManager,null).getRecords();
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",results);
    }

    public String getRolesCount(HttpServletRequest request) throws Exception{
        //auth
        Integer count = roleMapper.selectCount(null);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",count);
    }



    public String updateRole(Role role, HttpServletRequest request) throws Exception{
        //auth
        if (role == null || role.getId() == null){
            throw new RoleException("未指定的参数id");
        }

        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_code",role.getRoleCode());
        List<Role> res = roleMapper.selectList(wrapper);
        if (res != null){
            if (res.size() > 1)
                throw new RoleException("角色代码已存在");
            if (res.size() == 1 && !res.get(0).getId().equals(role.getId()) )
                throw new RoleException("角色代码已存在");
        }


        roleMapper.updateById(role);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"更新成功");
    }
}
