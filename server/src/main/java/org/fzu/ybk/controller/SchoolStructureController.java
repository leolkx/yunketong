package org.fzu.ybk.controller;

import org.fzu.ybk.StatusCode;
import org.fzu.ybk.entity.College;
import org.fzu.ybk.entity.Major;
import org.fzu.ybk.entity.School;
import org.fzu.ybk.service.OrgStructureService;
import org.fzu.ybk.service.ResponseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: Mu.xx
 * @date: 2020/5/16 16:29
 */

@RestController
@CrossOrigin
public class SchoolStructureController {

    @Autowired
    private ResponseService responseService;

    @Autowired
    private OrgStructureService orgStructureService ;

    private final Logger logger = LoggerFactory.getLogger(SchoolStructureController.class);

    @PostMapping(value = "/structure/orgs/schools")
    public String addSchool(
            @RequestBody School school,
            HttpServletRequest request){
        try{
            return orgStructureService.addSchool(school,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @DeleteMapping(value = "/structure/orgs/schools")
    public String deleteSchool(
            @RequestParam(value = "schoolId" ,required = true) Long schoolId,
            HttpServletRequest request){
        try{
            return orgStructureService.deleteSchool(schoolId, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @GetMapping(value = "/structure/orgs/schools")
    public String getSchools(
            @RequestParam(value = "page" ,required = false) Long page,
            @RequestParam(value = "pageSize" ,required = false) Long pageSize,
            HttpServletRequest request){
        try{
            return orgStructureService.getSchools(page,pageSize, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @PutMapping(value = "/structure/orgs/schools")
    public String updateSchool(
            @RequestBody School school,
            HttpServletRequest request){
        try{
            return orgStructureService.updateSchool(school, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }



    // ...


    @PostMapping(value = "/structure/orgs/colleges")
    public String addCollege(
            @RequestBody College college,
            HttpServletRequest request){
        try{
            return orgStructureService.addCollege(college,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @DeleteMapping(value = "/structure/orgs/colleges")
    public String deleteCollege(
            @RequestParam(value = "collegeId" ,required = true) Long collegeId,
            HttpServletRequest request){
        try{
            return orgStructureService.deleteCollege(collegeId, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @GetMapping(value = "/structure/orgs/colleges")
    public String getColleges(
            @RequestParam(value = "schoolId" ,required = true) Long schoolId,
            @RequestParam(value = "page" ,required = false) Long page,
            @RequestParam(value = "pageSize" ,required = false) Long pageSize,
            HttpServletRequest request){
        try{
            return orgStructureService.getColleges(schoolId,page,pageSize, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @PutMapping(value = "/structure/orgs/colleges")
    public String updateCollege(
            @RequestBody College college,
            HttpServletRequest request){
        try{
            return orgStructureService.updateCollege(college, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }


    // ......


    @PostMapping(value = "/structure/orgs/majors")
    public String addMajor(
            @RequestBody Major major,
            HttpServletRequest request){
        try{
            return orgStructureService.addMajor(major,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @DeleteMapping(value = "/structure/orgs/majors")
    public String deleteMajor(
            @RequestParam(value = "majorId" ,required = true) Long majorId,
            HttpServletRequest request){
        try{
            return orgStructureService.deleteMajor(majorId, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @GetMapping(value = "/structure/orgs/majors")
    public String getMajors(
            @RequestParam(value = "collegeId" ,required = true) Long collegeId,
            @RequestParam(value = "page" ,required = false) Long page,
            @RequestParam(value = "pageSize" ,required = false) Long pageSize,
            HttpServletRequest request){
        try{
            return orgStructureService.getMajors(collegeId,page,pageSize, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @PutMapping(value = "/structure/orgs/majors")
    public String updateMajor(
            @RequestBody Major major,
            HttpServletRequest request){
        try{
            return orgStructureService.updateMajor(major, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }


    //...
    @GetMapping(value = "/structure/orgs/classes")
    public String getClasses(
            @RequestParam(value = "majorId" ,required = true) Long majorId,
            @RequestParam(value = "page" ,required = false) Long page,
            @RequestParam(value = "pageSize" ,required = false) Long pageSize,
            HttpServletRequest request){
        try{
            return orgStructureService.getClasses(majorId,page,pageSize, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    //eof



}
