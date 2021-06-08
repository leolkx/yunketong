package org.fzu.ybk.controller;


import org.fzu.ybk.StatusCode;
import org.fzu.ybk.service.ResponseService;
import org.fzu.ybk.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class PhoneController {
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private ResponseService responseService;

    @PostMapping("/phonecode")
    public String phoneCode(@RequestParam(value = "phone" ,required = true)String phone, HttpServletRequest request){
        try{
            return phoneService.generateVerificationCode(phone, request.getSession());
        }
        catch (Exception e){
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }
    @GetMapping("/testchinese")
    public String testChinese(){
        return "你好";
    }

}
