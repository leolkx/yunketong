package org.fzu.ybk.controller;

import org.fzu.ybk.StatusCode;
import org.fzu.ybk.entity.AttendActivity;
import org.fzu.ybk.entity.PublishedActivity;
import org.fzu.ybk.mapper.UserMapper;
import org.fzu.ybk.service.ActivityService;
import org.fzu.ybk.service.ResponseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin
public class ActivityController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResponseService responseService;
    @Autowired
    private ActivityService activityService;

    private final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @PostMapping(value = "/activities")
    public String createActivity(
            @RequestBody PublishedActivity publishedActivity,
                                   HttpServletRequest request){
        try{
            return activityService.createActivity(publishedActivity, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }


    @PutMapping(value = "/activities/modify")
    public String modifyActivity(
            @RequestBody PublishedActivity publishedActivity,
            HttpServletRequest request){
        try{
            return activityService.modifyActivity(publishedActivity, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @PutMapping(value = "/activities")
    public String closeActivity(
            @RequestParam(value = "activityId" ,required = true) Long activityId,
            HttpServletRequest request){
        try{
            return activityService.closeActivityById(activityId, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }


    @PutMapping(value = "/activities/records/modify")
    public String updateAttendedActivity(
            @RequestBody AttendActivity attendActivity,
            HttpServletRequest request){
        try{
            return activityService.updateAttendedActivity(attendActivity, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }



    @PostMapping(value = "/activities/records")
    public String participateInActivity(
            @RequestBody AttendActivity attendActivity,
            HttpServletRequest request){
        try{
            return activityService.attendActivity(attendActivity, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }


    @GetMapping(value = "/activities/class")
    public String getActivities(
            @RequestParam(value = "orgCode" ,required = true) Long orgCode,
            HttpServletRequest request) {

        try{
            return activityService.getActivitiesByOrgCode(orgCode, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    @GetMapping(value = "/activities/class/self")
    public String getUserActivitiesState(
            @RequestParam(value = "orgCode" ,required = true) Long orgCode,
            @RequestParam(value = "page" ,required = false) Long page,
            @RequestParam(value = "pageSize" ,required = false) Long pageSize,
            HttpServletRequest request){
        try{
            return activityService.getUserActivitiesState(orgCode,page,pageSize);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

//
//    @GetMapping(value = "/activities/class/records")



    // 根据 activity id
    @GetMapping(value = "/activities/records")
    public String getActivityAttendingState(
            @RequestParam(value = "activityId" ,required = true) Long activityId,
            @RequestParam(value = "page" ,required = false) Integer page,
            @RequestParam(value = "pageSize" ,required = false) Integer pageSize,
            HttpServletRequest request){
        try{
            return activityService.getActivityAttendingState(activityId,page,pageSize, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    //根据活动id获取该活动对应班课下所有成员参与该活动的状态  ，就是是否参与
    @GetMapping(value = "/activities/orgParState")
    public String getActivitiesOrgParState(
            @RequestParam(value = "activityId" ,required = true) Long activityId,
            HttpServletRequest request) {

        try{
            return activityService.getActivitiesOrgParStateByActivityId(activityId);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }
    //根据班课码获取该班课下所有成员及对应成绩分数
    @GetMapping(value = "/activities/orgMemberScore")
    public String getOrgMemberScore(
            @RequestParam(value = "orgCode" ,required = true) Long orgCode,
            HttpServletRequest request) {

        try{
            return activityService.getOrgMemberScoreByOrgCode(orgCode);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    // 根据 activity id 获取活动所以信息
    @GetMapping(value = "/activities/recordsByActivityId")
    public String getActivityByActivityId(
            @RequestParam(value = "activityId" ,required = true) Long activityId,
            HttpServletRequest request){
        try{
            return activityService.getActivityByActivityId(activityId, request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    // 根据 activity id  和用户id 获取该用户参与的那个参与活动的信息
    @GetMapping(value = "/activities/recordsByActivityIdAndUserId")
    public String getActivityByUserId(
            @RequestParam(value = "activityId" ,required = true) Long activityId,
            @RequestParam(value = "userId" ,required = true) Long userId,
            HttpServletRequest request){
        try{
            return activityService.getActivityByActivityIdAndUserId(activityId,userId ,request);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

    // 根据 用户id 获取该用户参与所有的课程信息及课程的成绩分数
    @GetMapping(value = "/activities/getOrgScoreByUserId")
    public String getOrgScoreByUserId(
            @RequestParam(value = "userId" ,required = true) Long userId,
            HttpServletRequest request){
        try{
            return activityService.getOrgScoreByUserId(userId);
        } catch (Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }

}
