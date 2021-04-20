package org.fzu.ybk.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.fzu.ybk.StatusCode;
import org.fzu.ybk.entity.ClassParams;
import org.fzu.ybk.exception.ParamsException;
import org.fzu.ybk.exception.RoleException;

import org.fzu.ybk.mapper.*;
import org.fzu.ybk.utils.DateFormater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description:
 * @author: Mu.xx
 * @date: 2020/5/16 14:57
 */

@Service
public class ClassParamsService {

    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    PublishedActivityMapper publishedActivityMapper;
    @Autowired
    private OrgnizationMapper orgnizationMapper;
    @Autowired
    private OrgMemberMapper orgMemberMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResponseService responseService;
    @Autowired
    private DateFormater dateFormater;

    @Autowired
    private ClassParamsMapper classParamsMapper;

    private final Logger logger = LoggerFactory.getLogger(ClassParamsService.class);

    public String addParam(ClassParams classParams, HttpServletRequest request) throws Exception{

        //auth

        classParams.setId(null);
        Long paramCode,orgId;
        Long orgCode = classParams.getOrgCode();
        paramCode = classParams.getParamCode();
        orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);

        if (paramCode == null)
            throw new ParamsException("错误的参数代码");

        if (orgId == null || orgCode == null){
            throw new ParamsException("错误的班课号");
        }

        classParams.setOrgId(orgId);

        QueryWrapper<ClassParams> wrapper = new QueryWrapper<>();
        wrapper.eq("org_id",orgId)
                .eq("param_code",paramCode);

        ClassParams res = classParamsMapper.selectOne(wrapper);
        if (res != null)
            throw new ParamsException("该班课下已经存在该参数代码");

        if (classParams.getParamName() == null)
            throw new ParamsException("参数名不能为空");

        classParamsMapper.insert(classParams);

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"创建成功");
    }

    public String deleteParam(Long paramId, HttpServletRequest request){
        //auth
        int n = classParamsMapper.deleteById(paramId);
        if (n == 0)
            return responseService.responseFactory(StatusCode.RESPONSE_OK,"没有对应项");
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"删除成功");
    }

    public String getParams(Long orgCode,Long page,Long pageSize, HttpServletRequest request) throws Exception{
        //auth

        if (page == null || pageSize == null){
            page = 1L;
            pageSize = 10L;
        }

        Long orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);
        if (orgId == null )
            throw new ParamsException("错误的班课号");

        List<ClassParams> results;
        Page<ClassParams> pageManager = new Page<>(page,pageSize);
        QueryWrapper<ClassParams> wrapper = new QueryWrapper<>();
        wrapper.eq("org_id",orgId);
        results = classParamsMapper.selectPage(pageManager,wrapper).getRecords();
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",results);
    }

    public String getParamsCount(Long orgCode, HttpServletRequest request) throws Exception{
        //auth

        Long orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);
        if (orgId == null )
            throw new ParamsException("错误的班课号");

        QueryWrapper<ClassParams> wrapper = new QueryWrapper<>();
        wrapper.eq("org_id",orgId);
        Integer count = classParamsMapper.selectCount(wrapper);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",count);
    }

    public String updateParam(ClassParams classParams, HttpServletRequest request) throws Exception{
        //auth
        if (classParams == null || classParams.getId() == null){
            throw new ParamsException("未指定的参数id");
        }

        Long orgId = orgnizationMapper.getOrgIdByOrgCode(classParams.getOrgCode());
        Long paramCode = classParams.getParamCode();
        QueryWrapper<ClassParams> wrapper = new QueryWrapper<>();
        wrapper.eq("org_id",orgId)
                .eq("param_code",paramCode);

        List<ClassParams> res = classParamsMapper.selectList(wrapper);
        if (res != null){
            if (res.size() > 1)
                throw new RoleException("当前班课下该参数代码已存在");
            if (res.size() == 1 && !res.get(0).getId().equals(classParams.getId()))
                throw new RoleException("当前班课下该参数代码已存在");
        }



        classParamsMapper.updateById(classParams);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"更新成功");
    }
}
