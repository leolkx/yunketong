package org.fzu.ybk.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

/**
 * @description:
 * @author: Mu.xx
 * @date: 2020/4/9 16:09
 */

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
            classSchool = "????????????????????????";
        if (classMajor == null)
            classSchool = "???????????????????????????";
        if (classNumber == null)
            classNumber = "???????????????";
        if (classCollege == null)
            classCollege = "????????????(??????)";

        classMemberUpdate.setUserClassCollege(classCollege);
        classMemberUpdate.setUserClassMajor(classMajor);
        classMemberUpdate.setUserClassSchool(classSchool);
        classMemberUpdate.setUserClassNickName(nickName);
        classMemberUpdate.setUserClassNumber(classNumber);

        String json = JSON.toJSONString(classMemberUpdate);
        return json;
    }

    public String userJoinClass(Long orgCode,  HttpServletRequest request) throws Exception{
//        String userName = request.getSession().getAttribute(GlobalConstant.sessionUser).toString();
        String username = SystemParams.username;
        Long userId = SystemParams.userId;

        Long orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);
        if (orgId == null)
            throw new OrgMemberException("???????????????");
        if (Boolean.TRUE == this.userInOrgnization(userId,orgId))
            throw new OrgMemberException("?????????????????????????????????");
        this.addUserToClass(userId,orgId);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"????????????");
    }

    public String userQuitClass(Long orgCode,  HttpServletRequest request) throws Exception{
//        String userName = request.getSession().getAttribute(GlobalConstant.sessionUser).toString();
        String username = SystemParams.username;
        Long userId = SystemParams.userId;
        Long orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);
        if (orgId == null)
            throw new OrgMemberException("???????????????");
        if (Boolean.FALSE == this.userInOrgnization(userId,orgId))
            throw new OrgMemberException("????????????????????????");

        this.removeUserFromClass(userId,orgId);
        //??????????????????????????????????????????????????????
        if (orgMemberMapper.getClassMemberNumber(orgId) == 0L){
            cloudClassService.deleteCloudClass(orgId);
        }

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"????????????");
    }


    public String updateClassMemberInfo(long orgCode, ClassMemberUpdate classMemberUpdate, HttpServletRequest request) throws Exception{
//        String userName = request.getSession().getAttribute(GlobalConstant.sessionUser).toString();
        String username = SystemParams.username;
        Long userId = SystemParams.userId;

        Long orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);
        if (orgId == null)
            throw new OrgMemberException("???????????????");
        if ( Boolean.FALSE ==  this.userInOrgnization(userId,orgId))
            throw new OrgMemberException("????????????????????????");

        Long richId = orgMemberMapper.getRichTextId(userId,orgId);
        richTextService.updateRichText(richId,classMemberUpdate);

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"????????????");
    }


    // ??????????????????
    public String getMembersByOrgCode(Long orgCode, HttpServletRequest request) throws Exception{
//        String userName = request.getSession().getAttribute(GlobalConstant.sessionUser).toString();
        String username = SystemParams.username;
        Long userId = SystemParams.userId;

        Long orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);
        if (orgId == null)
            throw new OrgMemberException("???????????????");
        if (Boolean.FALSE ==  this.userInOrgnization(userId,orgId))
            throw new OrgMemberException("????????????????????????");

        List<ClassMember> res = orgMemberMapper.getMembersByOrgId(orgId);
        JSONArray jsonArray = richTextService.objectListPlusRichText(res,"memberDetail");

        if(jsonArray.size() == 0)
            throw new OrgMemberException("????????????0");

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"????????????",jsonArray);
    }


    public String getMembersByOrgCode(Long orgCode, Long page, Long pageSize, HttpServletRequest request) throws Exception{

//        String userName = request.getSession().getAttribute(GlobalConstant.sessionUser).toString();
        String username = SystemParams.username;
        Long userId = SystemParams.userId;

        Long orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);
        if (orgId == null)
            throw new OrgMemberException("???????????????");
        if (Boolean.FALSE == this.userInOrgnization(userId,orgId))
            throw new OrgMemberException("????????????????????????");

        List<ClassMember> res = orgMemberMapper.getMembersPageByOrgId(orgId,pageSize,(page-1)*pageSize);
        JSONArray jsonArray = richTextService.objectListPlusRichText(res,"memberDetail");

        if(jsonArray.size() == 0)
            throw new OrgMemberException("????????????0");

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"????????????",jsonArray);
    }


    // ????????????service????????????????????????????????????????????????????????????????????????????????????
    // ????????????service??????????????????????????????????????????????????????????????????????????????????????????
    public void addUserToClass(Long userId, Long orgId) throws Exception {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date);

        //????????????-??????????????? , ?????????????????????...
        Long richId = richTextService.createRichText(this.createDefaultClassMemberInfo(userId));
        orgMemberMapper.addUserToOrgnization(userId,orgId,0L,richId,dateStr,false);
    }


    public void removeAllUsersFromClass(Long orgId) throws Exception{
//        if (orgId == null)
//            throw new OrgMemberException("???????????????");
        //????????????????????????
        orgMemberMapper.deleteAllUsersFromOrgnization(orgId);
    }

    public void removeUserFromClass(Long userId, Long orgId) throws Exception {

        //????????????-???????????????
        Long richTextId = orgMemberMapper.getRichTextId(userId,orgId);
        orgMemberMapper.deleteUserFromOrgnization(userId,orgId);
        richTextService.deleteRichText(richTextId);
    }

}
