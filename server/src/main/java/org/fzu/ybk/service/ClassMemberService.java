package org.fzu.ybk.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.fzu.ybk.GlobalConstant;
import org.fzu.ybk.StatusCode;
import org.fzu.ybk.entity.AllUserInfo;
import org.fzu.ybk.entity.ClassMember;
import org.fzu.ybk.entity.ClassMemberUpdate;
import org.fzu.ybk.exception.OrgMemberException;
import org.fzu.ybk.mapper.OrgMemberMapper;
import org.fzu.ybk.mapper.OrgnizationMapper;
import org.fzu.ybk.mapper.UserMapper;
import org.fzu.ybk.utils.SystemParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class ClassMemberService {

    @Autowired
    private OrgnizationMapper orgnizationMapper;
    @Autowired
    private OrgMemberMapper orgMemberMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResponseService responseService;
    @Autowired
    private RichTextService richTextService;
    @Autowired
    private CloudClassService cloudClassService;

    private final Logger logger = LoggerFactory.getLogger(ClassMemberService.class);

    public Boolean userInOrgnization(Long userId, Long orgId){
        return orgMemberMapper.userInOrgnization(userId,orgId);
    }

    private String createDefaultClassMemberInfo(Long userId){
        AllUserInfo allUserInfo = userMapper.getAllUserInfoByUserId(userId);
        ClassMemberUpdate classMemberUpdate = new ClassMemberUpdate();
        String classSchool = allUserInfo.getSchool();
        String classMajor = allUserInfo.getMajor();
        String classCollege = allUserInfo.getCollege();
        String userName = allUserInfo.getUsername();
        String nickName = allUserInfo.getNickname();
        String classNumber = allUserInfo.getStudentId();
        if (nickName == null)
            nickName = userName;
        if (classSchool == null)
            classSchool = "南京航空航天大学";
        if (classMajor == null)
            classSchool = "电力系统及其自动化";
        if (classNumber == null)
            classNumber = "学号未设置";
        if (classCollege == null)
            classCollege = "电气学院(默认)";

        classMemberUpdate.setUserClassCollege(classCollege);
        classMemberUpdate.setUserClassMajor(classMajor);
        classMemberUpdate.setUserClassSchool(classSchool);
        classMemberUpdate.setUserClassNickName(nickName);
        classMemberUpdate.setUserClassNumber(classNumber);

        String json = JSON.toJSONString(classMemberUpdate);
        return json;
    }

    public String userJoinClass(Long orgCode,  HttpServletRequest request) throws Exception{
        String username = GlobalConstant.Username;
        Long userId = userMapper.getUserIdByUserName(username);
        System.out.println("username:" + username);
        Long orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);
        System.out.println("orgid" + orgId.toString());
        if (orgId == null)
            throw new OrgMemberException("班课不存在");
        if (Boolean.TRUE == this.userInOrgnization(userId,orgId))
            throw new OrgMemberException("用户已经存在于该班课中");
        this.addUserToClass(userId,orgId);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"加入成功");
    }

    public String userQuitClass(Long orgCode,  HttpServletRequest request) throws Exception{
        String username = GlobalConstant.Username;
        Long userId = userMapper.getUserIdByUserName(username);
        Long orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);
        if (orgId == null)
            throw new OrgMemberException("班课不存在");
        if (Boolean.FALSE == this.userInOrgnization(userId,orgId))
            throw new OrgMemberException("用户不在该班课中");

        this.removeUserFromClass(userId,orgId);
        //若当前用户退出，班级为空，则删除班级
        if (orgMemberMapper.getClassMemberNumber(orgId) == 0L){
            cloudClassService.deleteCloudClass(orgId);
        }

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"退出成功");
    }


    public String updateClassMemberInfo(long orgCode, ClassMemberUpdate classMemberUpdate, HttpServletRequest request) throws Exception{
        String username = GlobalConstant.Username;
        Long userId = userMapper.getUserIdByUserName(username);

        Long orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);
        if (orgId == null)
            throw new OrgMemberException("班课不存在");
        if ( Boolean.FALSE ==  this.userInOrgnization(userId,orgId))
            throw new OrgMemberException("用户不在该班课中");

        Long richId = orgMemberMapper.getRichTextId(userId,orgId);
        richTextService.updateRichText(richId,classMemberUpdate);

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"更新成功");
    }


    // 获取成员信息
    public String getMembersByOrgCode(Long orgCode, HttpServletRequest request) throws Exception{
        String username = GlobalConstant.Username;
        Long userId = userMapper.getUserIdByUserName(username);

        Long orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);
        if (orgId == null)
            throw new OrgMemberException("班课不存在");
        if (Boolean.FALSE ==  this.userInOrgnization(userId,orgId))
            throw new OrgMemberException("用户不在该班课中");

        List<ClassMember> res = orgMemberMapper.getMembersByOrgId(orgId);
        JSONArray jsonArray = richTextService.objectListPlusRichText(res,"memberDetail");

        if(jsonArray.size() == 0)
            throw new OrgMemberException("结果数为0");

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",jsonArray);
    }


    public String getMembersByOrgCode(Long orgCode, Long page, Long pageSize, HttpServletRequest request) throws Exception{

        String username = GlobalConstant.Username;
        Long userId = userMapper.getUserIdByUserName(username);

        Long orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);
        if (orgId == null)
            throw new OrgMemberException("班课不存在");
        if (Boolean.FALSE == this.userInOrgnization(userId,orgId))
            throw new OrgMemberException("用户不在该班课中");

        List<ClassMember> res = orgMemberMapper.getMembersPageByOrgId(orgId,pageSize,(page-1)*pageSize);
        JSONArray jsonArray = richTextService.objectListPlusRichText(res,"memberDetail");

        if(jsonArray.size() == 0)
            throw new OrgMemberException("结果数为0");

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",jsonArray);
    }


    // 以下是为service逻辑控制提供的事务级数据库操作实现，不再检查操作合理性。
    // 因此，在service函数中需要做合法、权限检查。以及控制操作前后数据状态的合理性
    public void addUserToClass(Long userId, Long orgId) throws Exception {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date);

        //创建班级-成员的联系 , 两句最好用事务...
        Long richId = richTextService.createRichText(this.createDefaultClassMemberInfo(userId));
        orgMemberMapper.addUserToOrgnization(userId,orgId,0L,richId,dateStr,false);
    }


    public void removeAllUsersFromClass(Long orgId) throws Exception{
//        if (orgId == null)
//            throw new OrgMemberException("班课不存在");
        //删除所有班级成员
        orgMemberMapper.deleteAllUsersFromOrgnization(orgId);
    }

    public void removeUserFromClass(Long userId, Long orgId) throws Exception {

        //删除班级-成员的联系
        Long richTextId = orgMemberMapper.getRichTextId(userId,orgId);
        orgMemberMapper.deleteUserFromOrgnization(userId,orgId);
        richTextService.deleteRichText(richTextId);
    }

}
