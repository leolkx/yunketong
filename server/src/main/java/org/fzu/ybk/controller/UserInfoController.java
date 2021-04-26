package org.fzu.ybk.controller;

import org.fzu.ybk.StatusCode;
import org.fzu.ybk.entity.UserPassword;
import org.fzu.ybk.entity.UserUpdate;
import org.fzu.ybk.service.ResponseService;
import org.fzu.ybk.service.UserInfoService;
import org.fzu.ybk.utils.SystemParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    ResponseService responseService;

    private final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @GetMapping(value = "/user/joinedClass")
    public String getUserJoinedClass(
            HttpServletRequest request){
        try{
            return userInfoService.getUserJoinedClass(request);

        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @GetMapping(value = "/user/createdClass")
    public String getUserCreatedClass(
            HttpServletRequest request){
        try{
            return userInfoService.getUserCreatedClass(request);

        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @PutMapping(value = "/user/info")
    public String updateUserInfo(
            @RequestBody UserUpdate userUpdate,
            HttpServletRequest request){
        try{
            return userInfoService.updateUserInfo(userUpdate,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @GetMapping(value = "/user/info")
    public String getUserInfo(
            @RequestParam(value = "userName" ,required = false) String userName,
            HttpServletRequest request){
        try{
//            String currUserName = request.getSession().getAttribute(GlobalConstant.sessionUser).toString();
            String currUsername = SystemParams.username;
            if (userName == null || userName.equals(currUsername))
                return userInfoService.getAllUserInfo(request);
            else
                return userInfoService.getSimpleUserInfo(userName, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }


    @PutMapping(value = "/user/password")
    public String updateUserPassword(
            @RequestBody UserPassword userPassword,
            HttpServletRequest request){
        try{
            return userInfoService.updateUserPassword(userPassword,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }



}
