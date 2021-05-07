package org.fzu.ybk.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.fzu.ybk.StatusCode;

import org.fzu.ybk.entity.College;
import org.fzu.ybk.entity.Major;
import org.fzu.ybk.entity.Orgnization;
import org.fzu.ybk.entity.School;
import org.fzu.ybk.exception.OrgStructureException;

import org.fzu.ybk.mapper.CollegeMapper;
import org.fzu.ybk.mapper.MajorMapper;
import org.fzu.ybk.mapper.OrgnizationMapper;
import org.fzu.ybk.mapper.SchoolMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description:
 * @author: Mu.xx
 * @date: 2020/5/16 16:40
 */

@Service
public class OrgStructureService {

    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private CollegeMapper collegeMapper;
    @Autowired
    private MajorMapper majorMapper;
    @Autowired
    private ResponseService responseService;
    @Autowired
    private OrgnizationMapper orgnizationMapper;
    private final Logger logger = LoggerFactory.getLogger(OrgStructureService.class);

    //学校级组织
    public String addSchool(School school, HttpServletRequest request) throws Exception{
        //auth

        school.setId(null);

        if (school.getSchoolName() == null)
            throw new OrgStructureException("学校名不能为空");

        schoolMapper.insert(school);

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"创建成功");
    }

    public String deleteSchool(Long schoolId, HttpServletRequest request){
        //auth
        int n = schoolMapper.deleteById(schoolId);
        if (n == 0)
            return responseService.responseFactory(StatusCode.RESPONSE_OK,"没有对应项");
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"删除成功");
    }

    public String getSchools(Long page,Long pageSize, HttpServletRequest request) throws Exception{
        //auth

        if (page == null || pageSize == null){
            page = 1L;
            pageSize = 10L;
        }

        List<School> results;
        Page<School> pageManager = new Page<>(page,pageSize);
        results = schoolMapper.selectPage(pageManager,null).getRecords();
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",results);
    }

    public String updateSchool(School school, HttpServletRequest request) throws Exception{
        //auth
        if (school == null || school.getId() == null){
            throw new OrgStructureException("未指定的学校id");
        }
        schoolMapper.updateById(school);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"更新成功");
    }

    // 学院级结构

    public String addCollege(College college, HttpServletRequest request) throws Exception{
        //auth

        college.setId(null);
        if (college.getCollegeName() == null)
            throw new OrgStructureException("学院名不能为空");
        collegeMapper.insert(college);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"创建成功");
    }

    public String deleteCollege(Long collegeId, HttpServletRequest request){
        //auth
        int n = collegeMapper.deleteById(collegeId);
        if (n == 0)
            return responseService.responseFactory(StatusCode.RESPONSE_OK,"没有对应项");

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"删除成功");
    }

    public String getColleges(Long schoolId,Long page,Long pageSize, HttpServletRequest request) throws Exception{
        //auth

        if (page == null || pageSize == null){
            page = 1L;
            pageSize = 10L;
        }


        if (schoolId == null)
            throw new OrgStructureException("未指定的学校id");

        List<College> results;
        Page<College> pageManager = new Page<>(page,pageSize);
        QueryWrapper<College> wrapper = new QueryWrapper<>();
        wrapper.eq("school_id",schoolId);
        results = collegeMapper.selectPage(pageManager,wrapper).getRecords();
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",results);
    }

    public String updateCollege(College college, HttpServletRequest request) throws Exception{
        //auth
        if (college == null || college.getId() == null){
            throw new OrgStructureException("未指定的学院id");
        }
        collegeMapper.updateById(college);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"更新成功");
    }

    //系/专业结构

    public String addMajor(Major major, HttpServletRequest request) throws Exception{
        //auth

        major.setId(null);
        if (major.getMajorName() == null)
            throw new OrgStructureException("专业名不能为空");
        majorMapper.insert(major);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"创建成功");
    }

    public String deleteMajor(Long majorId, HttpServletRequest request){
        //auth
        int n = majorMapper.deleteById(majorId);
        if (n == 0)
            return responseService.responseFactory(StatusCode.RESPONSE_OK,"没有对应项");
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"删除成功");
    }

    public String getMajors(Long collegeId,Long page,Long pageSize, HttpServletRequest request) throws Exception{
        //auth

        if (page == null || pageSize == null){
            page = 1L;
            pageSize = 10L;
        }

        if (collegeId == null)
            throw new OrgStructureException("未指定学院id");

        List<Major> results;
        Page<Major> pageManager = new Page<>(page,pageSize);
        QueryWrapper<Major> wrapper = new QueryWrapper<>();
        wrapper.eq("college_id",collegeId);
        results = majorMapper.selectPage(pageManager,wrapper).getRecords();
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",results);
    }

    public String updateMajor(Major major, HttpServletRequest request) throws Exception {
        //auth
        if (major == null || major.getId() == null){
            throw new OrgStructureException("未指定专业id");
        }
        majorMapper.updateById(major);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"更新成功");
    }

    //查询系下的具体课程

    public String getClasses(Long majorId,Long page,Long pageSize, HttpServletRequest request) throws Exception{
        //auth

        if (page == null || pageSize == null){
            page = 1L;
            pageSize = 10L;
        }

        if (majorId == null)
            throw new OrgStructureException("未指定专业id");

        List<Orgnization> results;
        Page<Orgnization> pageManager = new Page<>(page,pageSize);
        QueryWrapper<Orgnization> wrapper = new QueryWrapper<>();
        wrapper.eq("major_id",majorId);
        results = orgnizationMapper.selectPage(pageManager,wrapper).getRecords();
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",results);
    }


}
