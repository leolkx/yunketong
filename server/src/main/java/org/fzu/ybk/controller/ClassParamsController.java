package org.fzu.ybk.controller;

import org.fzu.ybk.StatusCode;
import org.fzu.ybk.entity.ClassParams;
import org.fzu.ybk.service.ClassParamsService;
import org.fzu.ybk.service.ResponseService;
import org.fzu.ybk.utils.DateFormater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class ClassParamsController {
    @Autowired
    private ResponseService responseService;
    @Autowired
    private DateFormater dateFormater;

    @Autowired
    private ClassParamsService classParamsService ;

    private final Logger logger = LoggerFactory.getLogger(ClassParamsController.class);

    @PostMapping(value = "/params/class")
    public String addParam(
            @RequestBody ClassParams classParams,
            HttpServletRequest request){
        try{
            return classParamsService.addParam(classParams,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @DeleteMapping(value = "/params/class")
    public String deleteParam(
            @RequestParam(value = "paramId" ,required = true) Long paramId,
            HttpServletRequest request){
        try{
            return classParamsService.deleteParam(paramId, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @GetMapping(value = "/params/class")
    public String getParams(
            @RequestParam(value = "orgCode" ,required = true) Long orgCode,
            @RequestParam(value = "page" ,required = false) Long page,
            @RequestParam(value = "pageSize" ,required = false) Long pageSize,
            HttpServletRequest request){
        try{
            return classParamsService.getParams(orgCode,page,pageSize, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @GetMapping(value = "/params/class/total")
    public String getParamsCount(
            @RequestParam(value = "orgCode" ,required = true) Long orgCode,
            HttpServletRequest request){
        try{
            return classParamsService.getParamsCount(orgCode, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }


    @PutMapping(value = "/params/class")
    public String updateParam(
            @RequestBody ClassParams classParams,
            HttpServletRequest request){
        try{
            return classParamsService.updateParam(classParams, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }


}
