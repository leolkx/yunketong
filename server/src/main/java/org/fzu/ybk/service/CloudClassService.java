package org.fzu.ybk.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.fzu.ybk.GlobalConstant;
import org.fzu.ybk.StatusCode;
import org.fzu.ybk.entity.CloudClass;
import org.fzu.ybk.entity.Orgnization;
import org.fzu.ybk.exception.CloudClassException;
import org.fzu.ybk.exception.OrgMemberException;
import org.fzu.ybk.exception.OrgnizationException;
import org.fzu.ybk.mapper.OrgMemberMapper;
import org.fzu.ybk.mapper.OrgnizationMapper;
import org.fzu.ybk.mapper.RichTextMapper;
import org.fzu.ybk.mapper.UserMapper;
import org.fzu.ybk.utils.SystemParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class CloudClassService {

    @Autowired
    private RichTextMapper richTextMapper;
    @Autowired
    private OrgnizationMapper orgnizationMapper;
    @Autowired
    private ResponseService responseService;
    @Autowired
    private RichTextService richTextService;
    @Autowired
    private OrgMemberMapper orgMemberMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClassMemberService classMemberService;

    private final Logger logger = LoggerFactory.getLogger(CloudClassService.class);


    public String createCloudClass(CloudClass cloudClass, HttpServletRequest request) throws Exception{
        Long orgCode ;
        Long lastOrgCode = orgnizationMapper.getLastOrgCode();

        if (lastOrgCode == null) orgCode = 10000L;
        else orgCode = lastOrgCode + 1;

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date);
//        String creator = SystemParams.username;
        String creator = GlobalConstant.Username;

        if(creator==null) creator = cloudClass.getTeacherName();
        Long userId = userMapper.getUserIdByUserName(creator);
        if(userId == null) userId = userMapper.getUserIdByPhone(creator);
        if (userId == null)
            throw new CloudClassException("创建班级失败，创建者账户异常");
        String orgName = creator + " 创建的班级";
        String cloudClassDetail = JSON.toJSONString(cloudClass);
        Long richTextId = richTextService.createRichText(cloudClassDetail);
        //创建班课
        orgnizationMapper.cerateOrgnization(orgCode,orgName,richTextId,dateStr,creator,false);
        Long orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);
        //创建者与组织的关系
//        String userName = request.getSession().getAttribute(GlobalConstant.sessionUser).toString();
        String username = SystemParams.username;

        classMemberService.addUserToClass(userId,orgId);


        //懒得改了……直接这样加在屁股后面……(为了结构性，增加三个参数school_id,college_id,major_id)
        Long lastId = orgnizationMapper.getLastOrgId();
        Orgnization orgnization = orgnizationMapper.selectById(lastId);
        orgnization.setSchoolId(cloudClass.getSchoolId());
        orgnization.setCollegeId(cloudClass.getCollegeId());
        orgnization.setMajorId(cloudClass.getMajorId());
        orgnizationMapper.updateById(orgnization);

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"创建成功",orgCode);
    }

    public String getOrgInfoByOrgCode(Long orgCode, HttpServletRequest request) throws Exception{
        if ( ! orgnizationMapper.OrgExistByOrgCode(orgCode)){
            throw new OrgnizationException("未找到对应组织");
        }
        Orgnization orgnization = orgnizationMapper.getOrgInfoByOrgCode(orgCode);
        JSONObject jsonObject = richTextService.objectPlusRichText(orgnization,"classCloud");

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"",jsonObject);
    }


    public String updateClassInfoByOrgCode(Long orgCode, CloudClass updateInfo,  HttpServletRequest request) throws Exception{
//        String userName = request.getSession().getAttribute(GlobalConstant.sessionUser).toString();
        String username = SystemParams.username;
        Long userId = SystemParams.userId;

        Long orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);
        if (orgId == null)
            throw new OrgMemberException("班课不存在");
        if (Boolean.FALSE == orgMemberMapper.userInOrgnization(userId,orgId))
            throw new OrgMemberException("用户不在该班课中");

        Long richTextId = orgnizationMapper.geRichTextIdByOrgCode(orgCode);
        richTextService.updateRichText(richTextId,updateInfo);

        Orgnization orgnization = orgnizationMapper.selectById(orgId);
        orgnization.setSchoolId(updateInfo.getSchoolId());
        orgnization.setCollegeId(updateInfo.getCollegeId());
        orgnization.setMajorId(updateInfo.getMajorId());
        orgnizationMapper.updateById(orgnization);


        return responseService.responseFactory(StatusCode.RESPONSE_OK,"修改成功");
    }


    public String deleteCloudClassAndMembers(Long orgCode,  HttpServletRequest request) throws Exception{
//        String userName = request.getSession().getAttribute(GlobalConstant.sessionUser).toString();
        String username = SystemParams.username;
        Long userId = SystemParams.userId;

        Long orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);
        if ( ! orgnizationMapper.OrgExistByOrgId(orgId))
            throw new CloudClassException("班课不存在，删除失败");
        if (Boolean.FALSE == orgMemberMapper.userInOrgnization(userId,orgId))
            throw new OrgMemberException("用户不在该班课中");

        //删除班级+班级-成员联系
        this.deleteCloudClass(orgId);
        classMemberService.removeAllUsersFromClass(orgId);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"删除成功");
    }


    //==== 共享调用 for classMember

    public void deleteCloudClass(Long orgId) throws Exception{
        Long richTextId = orgnizationMapper.geRichTextIdByOrgId(orgId);
        orgnizationMapper.deleteOrgnizationByOrgId(orgId);
        richTextService.deleteRichText(richTextId);
    }

}
