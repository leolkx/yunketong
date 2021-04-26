package org.fzu.ybk.controller;

import org.fzu.ybk.StatusCode;
import org.fzu.ybk.entity.ClassMemberUpdate;
import org.fzu.ybk.mapper.UserMapper;
import org.fzu.ybk.service.ClassMemberService;
import org.fzu.ybk.service.CloudClassService;
import org.fzu.ybk.service.ResponseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @description:
 * 该类的加入、退出班课皆无验证机制，并不安全orz
 * 若需要验证，后台做最好是加kafka，当然也可以直接再spring里验证
 *
 *
 * @author: Mu.xx
 * @date: 2020/4/9 17:30
 * @param: null
 * @return:
 */
@RestController
@CrossOrigin
public class ClassMemberController {

    @Autowired
    private CloudClassService cloudClassService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResponseService responseService;
    @Autowired
    private ClassMemberService classMemberService;

    private final Logger logger = LoggerFactory.getLogger(ClassMemberController.class);


    @GetMapping(value = "/cloudClass/members")
    public String getMembers(
            @RequestParam(value = "orgCode" ,required = true) Long orgCode,
            @RequestParam(value = "page" ,required = false) Long page,
            @RequestParam(value = "pageSize" ,required = false) Long pageSize,
            HttpServletRequest request){
        try{
            if (page == null || pageSize == null)
                return classMemberService.getMembersByOrgCode(orgCode, request);
            if (page < 0 || pageSize < 0)
                return responseService.responseFactory(StatusCode.RESPONSE_ERR,"页面索引、页面大小必须为正整数");
            else
                return classMemberService.getMembersByOrgCode(orgCode,page,pageSize,request);

        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @PostMapping(value = "/cloudClass/members")
    public String addMember(
            @RequestParam(value = "orgCode" ,required = true) Long orgCode,
            HttpServletRequest request){
        try{
            return classMemberService.userJoinClass(orgCode,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @DeleteMapping(value = "/cloudClass/members")
    public String removeMember(
            @RequestParam(value = "orgCode" ,required = true) Long orgCode,
            HttpServletRequest request){
        try{
            return classMemberService.userQuitClass(orgCode,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }


    @PutMapping(value = "/cloudClass/members")
    public String updateClassMemberInfo(
            @RequestParam(value = "orgCode" ,required = true) Long orgCode,
            @RequestBody ClassMemberUpdate classMemberUpdate,
            HttpServletRequest request){
        try{
            return classMemberService.updateClassMemberInfo(orgCode,classMemberUpdate,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }



}
