package org.fzu.ybk.controller;

import org.fzu.ybk.StatusCode;
import org.fzu.ybk.entity.User;
import org.fzu.ybk.service.ResponseService;
import org.fzu.ybk.service.SignUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class SignUpController {

    @Autowired
    SignUpService signUpService;

    @Autowired
    ResponseService responseService;

    private final Logger logger = LoggerFactory.getLogger(SignUpController.class);


    // 用户是否存在
    @GetMapping(value = "/signup/users")
    public String userExist(@RequestParam(value = "userName" ,required = true) String userName, HttpServletRequest request){
        try{
//            String info = "用户: " + request.getSession().getId() + "， 欲访问: "+request.getRequestURI();
            return signUpService.userExist(userName);
        } catch (Exception e) {
//            e.printStackTrace();
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @PostMapping(value = "/signup")
    public String createAccount(@RequestBody User user, HttpServletRequest request){
        try{
//            System.out.println("用户: " + request.getSession().getId() + "， 欲访问: "+request.getRequestURI());
            return signUpService.signUp(user,request.getSession());
        } catch (Exception e) {
//            e.printStackTrace();
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }

    }
    @PostMapping(value = "/fastsignup")
    public String fastcreateAccount(@RequestBody User user, HttpServletRequest request){
        try{
//            System.out.println("用户: " + request.getSession().getId() + "， 欲访问: "+request.getRequestURI());
            return signUpService.fastsignUp(user,request.getSession());
        } catch (Exception e) {
//            e.printStackTrace();
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }
//    @PostMapping(value = "/mytest")
//    public String mytest(@RequestBody User user, HttpServletRequest request){
//        return request.getSession().getId();
//    }
//    @PostMapping(value = "/histest")
//    public String histest(@RequestBody User user, HttpServletRequest request){
//        return request.getSession().getId();
//    }


}
