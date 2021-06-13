package org.fzu.ybk.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.fzu.ybk.StatusCode;
import org.fzu.ybk.entity.Role;
import org.fzu.ybk.entity.User;
import org.fzu.ybk.exception.MailVerificationException;
import org.fzu.ybk.exception.PhoneVerficationException;
import org.fzu.ybk.exception.SignUpException;
import org.fzu.ybk.exception.VerificationCodeException;
import org.fzu.ybk.mapper.UserMapper;
import org.fzu.ybk.shiroPackage.util.SHA256Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Component
public class SignUpService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResponseService responseService;

    @Autowired
    private PhoneService phoneService;
    @Autowired
    private MailVerificationService mailVerificationService;
    @Autowired
    private VerificationCodeService verificationCodeService;

    private final Logger logger = LoggerFactory.getLogger(SignUpService.class);

    boolean checkUserName(String userName){
//        ^[a-zA-Z][a-zA-Z0-9_]\\w{5,17}$
//        ^[a-zA-Z]\\w{5,17}$
        String pattern = "^[a-zA-Z][a-zA-Z0-9_]{3,17}$";
        return Pattern.matches(pattern,userName);
    }

//    boolean checkPassword(String password){
//        String pattern = "^[a-zA-Z][a-zA-Z0-9_]\\w{5,17}$";
//        return Pattern.matches(pattern,password);
//    }
    public void createAccount(User user, HttpSession session) throws SignUpException, MailVerificationException, VerificationCodeException, PhoneVerficationException {

        Date date = new Date();

        if(user.getEmail() == null) { throw new SignUpException("email is empty"); }
        if (user.getUsername() == null) { throw new SignUpException("username is empty"); }
        if(user.getPassword() == null) { throw new SignUpException("password is empty"); }
        if (! this.checkUserName(user.getUsername())) { throw new SignUpException("The user name must start with a letter, and can only contain numbers, letters and underscores. The length is between 6-18 digits"); }
        if (userMapper.userExist(user.getUsername())) throw new SignUpException("user is exist");


//        mailVerificationService.verify(date,user.getEmail(),user.getMailVerificationCode(),session);
//        verificationCodeService.verify(date,user.getVerificationCode(),session);


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date);
        try{
            //设置为正常状态
            user.setState("NORMAL");
            // 随机生成盐值
            String salt = RandomStringUtils.randomAlphanumeric(20);
            user.setSalt(salt);
            // 进行加密
            String password =  user.getPassword();
            user.setPassword(SHA256Util.sha256(password, user.getSalt()));
            System.out.println(user.getVerificationCode());
            System.out.println(user.getPhone());
            phoneService.verify(date,user.getPhone(),user.getVerificationCode(),session);
            userMapper.createAccount(Role.ORDINARY_USER,user.getUsername(),user.getPassword(),user.getSalt(),user.getState(),user.getEmail(),user.getPhone(),dateStr,true,false );

        } catch (Exception e) {
//            e.printStackTrace();
            throw new SignUpException(e.toString());
        }
    }

    public String signUp(User user, HttpSession session) throws Exception{
            this.createAccount(user, session);
            return responseService.responseFactory(StatusCode.RESPONSE_OK,"register success");
    }

    public String fastsignUp(User user, HttpSession session) throws Exception{
        this.fastcreateAccount(user, session);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"register success");
    }

    public void fastcreateAccount(User user, HttpSession session) throws SignUpException, MailVerificationException, VerificationCodeException {

        Date date = new Date();

        user.setEmail("492644858@qq.com");
        user.setUsername(user.getPhone());
        user.setPassword("123456");


//        if(user.getEmail() == null) { throw new SignUpException("邮箱为空"); }
//        if (user.getUsername() == null) { throw new SignUpException("用户名为空"); }
//        if(user.getPassword() == null) { throw new SignUpException("密码为空"); }
//        if (! this.checkUserName(user.getUsername())) { throw new SignUpException("用户名必须以字母开头，且只能包含数字、字母以及下划线，长度在6-18位之间"); }
//        if (userMapper.userExist(user.getPhone())) throw new SignUpException("用户名重复");



//        verificationCodeService.verify(date,user.getVerificationCode(),session);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date);
        try{
            //设置为正常状态
            user.setState("NORMAL");
            // 随机生成盐值
            String salt = RandomStringUtils.randomAlphanumeric(20);
            user.setSalt(salt);
            // 进行加密
            String password =  user.getPassword();
            user.setPassword(SHA256Util.sha256(password, user.getSalt()));
//            phoneService.verify(date, user.getPhone(), user.getVerificationCode(), session);
            userMapper.createAccount(Role.ORDINARY_USER,user.getUsername(),user.getPassword(),user.getSalt(),user.getState(),user.getEmail(),user.getPhone(),dateStr,true,false );

        } catch (Exception e) {
//            e.printStackTrace();
            throw new SignUpException(e.toString());
        }
    }


    public String userExist(String userName) throws Exception {
        boolean exist = false;
        exist = userMapper.userExist(userName);
        if (exist) {
            return responseService.responseFactory(StatusCode.RESPONSE_OK,"","found");
        }
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"","not found");
    }



}
