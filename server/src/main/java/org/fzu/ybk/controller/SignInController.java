package org.fzu.ybk.controller;

import org.fzu.ybk.StatusCode;
import org.fzu.ybk.entity.User;
import org.fzu.ybk.service.ResponseService;
import org.fzu.ybk.service.SignInService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class SignInController {

    @Autowired
    SignInService signInService;
    @Autowired
    ResponseService responseService;

    private final Logger logger = LoggerFactory.getLogger(SignInController.class);

    @PostMapping(value = "/signin")
    public String singnIn(@RequestBody User user, HttpServletRequest request){
        try{
            return signInService.signIn(user,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }
    @PostMapping(value = "/signinbyphone")
    public String signinByphone(@RequestBody User user, HttpServletRequest request){
        try{
            return signInService.signInbyphone(user,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }
}
