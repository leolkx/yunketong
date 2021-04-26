package org.fzu.ybk.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.fzu.ybk.StatusCode;
import org.fzu.ybk.entity.User;
import org.fzu.ybk.exception.SignInException;
import org.fzu.ybk.mapper.UserMapper;
import org.fzu.ybk.utils.TokenMapUtils;
import org.fzu.ybk.shiroPackage.util.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class SignInService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private TokenMapUtils tokenMapUtils;

    @Autowired
    private PhoneService phoneService;
    @Autowired
    private VerificationCodeService verificationCodeService;

    private final Logger logger = LoggerFactory.getLogger(SignInService.class);

    boolean isPhoneNumber(String userName){
        return Character.isDigit(userName.charAt(0)) ;
    }
    public String signIn(User user, HttpServletRequest request) throws Exception{
        String username = user.getUsername();
        String password = user.getPassword();
        Long userId;


        // 若当前用户登录为手机号，则找到对应用户名
        if (username!= null && isPhoneNumber(username)) {
            username = userMapper.getUserNameByPhone(username);
            if (username == null){
                throw new SignInException("Unable to search the user name corresponding to the mobile phone number");
            }
        }

        if (username == null || password == null) throw new SignInException("username or password is empyt");
        if (! userMapper.userExist(username)) throw new SignInException("user not exist");
        //if (! userMapper.getUserPassword(username).equals(password)) throw new SignInException("密码不正确");


//        // 获取数据库里面存的用户注册时生成的盐值
//        String salt =userMapper.getSaltByUsername(username);
//        // 进行加密
//        String postPasswordEncryed  =  SHA256Util.sha256(password,salt);
//        if (! userMapper.getUserPassword(username).equals(postPasswordEncryed )) throw new SignInException("密码不正确");




        //验证码认证
//        verificationCodeService.verify(new Date(),user.getVerificationCode(),request.getSession());

         userId = userMapper.getUserIdByUserName(username);
//         phoneService.verify(new Date(),user.getPhone(),user.getVerificationCode(),request.getSession());

//        String oldUserName ;
//        Object obj = request.getSession().getAttribute("userName");
//
//        if (obj == null)
//            oldUserName = null;
//        else
//            oldUserName = obj.toString();
//
//
//
//        // 若session已绑定用户，且与当前用户不一致，则删除当前绑定
//        if (oldUserName != null && ! oldUserName.equals(userName) ) {
//            tokenMapUtils.removeSession(oldUserName,request);
//        }
//        Long userId = userMapper.getUserIdByUserName(userName);

//        request.getSession().setAttribute("userName",userName);
//        request.getSession().setAttribute("userId",userId);


        //shiro进行身份验证
        try{
            //验证身份和登陆
            System.out.println("username");
            System.out.println(username);
            System.out.println("password");
            System.out.println(password);
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);

            //进行登录操作
            subject.login(token);
        }catch (IncorrectCredentialsException e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"The user does not exist or the password is wrong");
        } catch (LockedAccountException e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"Login failed. The user has been frozen");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"The user does not exist");
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"Unknown exception");
        }


        //设置shiroSession的过期时间，token是sessionid，也就是过期时间 24 * 60 * 60 * 1000 。好像有异常，先关了
        //SecurityUtils.getSubject().getSession().setTimeout(GlobalConstant.tokenExpiryTime);
        //SecurityUtils.getSubject().getSession().setTimeout(10 * 60 * 1000);









        //String token = request.getSession().getId();
        String token = ShiroUtils.getSession().getId().toString();//shiro的sessionid
        JSONObject loginResult = new JSONObject();
        loginResult.put("id", userId);
        loginResult.put("token", token);
        loginResult.put("username", username);

        tokenMapUtils.updateToken(userId,token, request);

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"登录成功",loginResult);
    }

    public String signInbyphone(User user, HttpServletRequest request) throws Exception{
        String username = user.getUsername();
        String password = user.getPassword();
//        password = "123456";
        Long userId;

        // 若当前用户登录为手机号，则找到对应用户名
        if (username!= null && isPhoneNumber(username)) {
            username = userMapper.getUserNameByPhone(username);
            if (username == null){
                throw new SignInException("Unable to search the user name corresponding to the mobile phone number");
            }
        }

        if (username == null) throw new SignInException("username is empyt");
        if (! userMapper.userExist(username)) throw new SignInException("user not exist");


        userId = userMapper.getUserIdByUserName(username);

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date);
        HttpSession session = request.getSession();
        phoneService.verify(date, user.getPhone(), user.getVerificationCode(), session);




        //shiro进行身份验证
        try{
            //验证身份和登陆
            System.out.println("username");
            System.out.println(username);
            System.out.println("password");
            System.out.println(password);
            Subject subject = SecurityUtils.getSubject();
            if(password==null)password="123456";
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            //进行登录操作
            subject.login(token);
        }catch (IncorrectCredentialsException e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"The user does not exist or the password is wrong");
        } catch (LockedAccountException e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"Login failed. The user has been frozen");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"The user does not exist");
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"Unknown exception");
        }


        //设置shiroSession的过期时间，token是sessionid，也就是过期时间 24 * 60 * 60 * 1000 。好像有异常，先关了
        //SecurityUtils.getSubject().getSession().setTimeout(GlobalConstant.tokenExpiryTime);
        //SecurityUtils.getSubject().getSession().setTimeout(10 * 60 * 1000);









        //String token = request.getSession().getId();
        String token = ShiroUtils.getSession().getId().toString();//shiro的sessionid
        JSONObject loginResult = new JSONObject();
        loginResult.put("id", userId);
        loginResult.put("token", token);
        loginResult.put("username", username);

        tokenMapUtils.updateToken(userId,token, request);

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"登录成功",loginResult);
    }

}
