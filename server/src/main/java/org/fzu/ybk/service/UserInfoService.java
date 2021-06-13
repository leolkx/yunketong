package org.fzu.ybk.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.fzu.ybk.GlobalConstant;
import org.fzu.ybk.StatusCode;

import org.fzu.ybk.exception.UserInfoException;
import org.fzu.ybk.mapper.OrgMemberMapper;
import org.fzu.ybk.mapper.OrgnizationMapper;
import org.fzu.ybk.mapper.RichTextMapper;
import org.fzu.ybk.mapper.UserMapper;
import org.fzu.ybk.shiroPackage.util.SHA256Util;
import org.fzu.ybk.utils.SystemParams;
import org.fzu.ybk.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Mu.xx
 * @date: 2020/4/9 16:40
 */

@Service
public class UserInfoService {
    @Autowired
    private OrgnizationMapper orgnizationMapper;
    @Autowired
    private OrgMemberMapper orgMemberMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RichTextMapper richTextMapper;
    @Autowired
    private ResponseService responseService;
    @Autowired
    private RichTextService richTextService;
    @Autowired
    private MailVerificationService mailVerificationService;
    @Autowired
    private PhoneService phoneService;

    private final Logger logger = LoggerFactory.getLogger(UserInfoService.class);

    public String getUserJoinedClass(HttpServletRequest request) throws Exception {
        String username = GlobalConstant.Username;
        Long userId = userMapper.getUserIdByUserName(username);
        List<Orgnization> res = userMapper.getUserJoinedOrgnizationExcludeCreated(userId, username);
        JSONArray jsonArray = richTextService.objectListPlusRichText(res,"cloudClass");
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"",jsonArray);
    }

    public String getUserCreatedClass(HttpServletRequest request) throws Exception {

        String username = GlobalConstant.Username;
        Long userId = userMapper.getUserIdByUserName(username);
        List<Orgnization> res = userMapper.getUserCreatedOrgnization(userId,username);
        System.out.println(res.size());
        JSONArray jsonArray = richTextService.objectListPlusRichText(res,"cloudClass");
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"",jsonArray);
    }

    // 本地测试正常，服务器运行报nullPointer Exception? 2020/4/16
    // 经测试:数据库读入时为空，查询信息就为空报错。
    // 因此要先判断一下如果是空，直接新数据替换
    public String updateUserInfo(UserUpdate userUpdate, HttpServletRequest request) throws Exception{
        if (userUpdate == null){
            throw new UserInfoException("提交的用户信息为空");
        }
        String username = GlobalConstant.Username;
        Long userId = userMapper.getUserIdByUserName(username);



        if (userId == null){
            throw new UserInfoException("未查询到用户");
        }
        UserUpdate oldUserUpdatableInfo = userMapper.getUserUpdatableInfoByUserId(userId);
        UserUpdate newUserInfo = null;

        if (oldUserUpdatableInfo != null){
            //个体用jsonobj,list用jsonarray
            JSONObject oldJsonObject = (JSONObject) JSON.toJSON(oldUserUpdatableInfo);
            JSONObject newJsonObject = (JSONObject) JSON.toJSON(userUpdate);

            if(oldJsonObject == null || newJsonObject == null){
                throw new UserInfoException("实体fastJson转化异常");
            }

            for(Map.Entry<String, Object> entry: newJsonObject.entrySet()){
                if (entry.getValue() != null)
                    oldJsonObject.put(entry.getKey(),entry.getValue());
            }
            newUserInfo = JSON.toJavaObject(oldJsonObject,UserUpdate.class);
            if(newJsonObject == null)
                throw new UserInfoException("fastJson实体转化异常");
        }
        else{
            newUserInfo = userUpdate;
        }

        userMapper.updateUserInfoByUserId(userId,newUserInfo.getNickname(),newUserInfo.getStudentId(),
                newUserInfo.getGender(),newUserInfo.getProfilePhotoUrl(),newUserInfo.getSchool(),
                newUserInfo.getMajor(),newUserInfo.getCollege(),newUserInfo.getEducation(),
                newUserInfo.getBirthDate(),newUserInfo.getAddress(),newUserInfo.getCity(),
                newUserInfo.getProvince(),newUserInfo.getNation());

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"更新用户信息成功");
    }



    public String updateUserPassword(UserPassword userPassword, HttpServletRequest request) throws Exception{
        Date date = new Date();
        String phone = userPassword.getPhone();
//        phoneService.verify(date, userPassword.getPhone(), userPassword.getVerificationCode(), request.getSession());
        if (userPassword == null){
            throw new UserInfoException("错误的参数提交(更新密码)");
        }

        if (userPassword.getPhone() == null){
            throw new UserInfoException("错误的用户手机号");
        }

        if (userPassword.getPassword() == null){
            throw new UserInfoException("新的密码不能为空");
        }


        Long id = userMapper.getUserIdByPhone(phone);
        User currUserInfo = userMapper.selectById(id);


        User tempUser = new User();
        tempUser.setId(id);

        // 随机生成新盐值
        String newSalt = RandomStringUtils.randomAlphanumeric(20);
        tempUser.setSalt(newSalt);
        // 进行加密
        tempUser.setPassword(SHA256Util.sha256(userPassword.getPassword(), tempUser.getSalt()));
        userMapper.updateById(tempUser);

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"修改用户密码成功");
    }



    public String getSimpleUserInfo(String userName, HttpServletRequest request) throws Exception{
        if (!userMapper.userExist(userName))
            throw new UserInfoException("无此用户");
        Long userId = userMapper.getUserIdByUserName(userName);
        SimpleUserInfo simpleUserInfo = userMapper.getSimpleUserInfoByUserId(userId);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",simpleUserInfo);
    }

    public String getAllUserInfo(HttpServletRequest request) throws Exception{
        String username = GlobalConstant.Username;
        Long userId = userMapper.getUserIdByUserName(username);

        AllUserInfo allUserInfo = userMapper.getAllUserInfoByUserId(userId);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",allUserInfo);
    }

}
