package org.fzu.ybk.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.fzu.ybk.StatusCode;

import org.fzu.ybk.entity.*;
import org.fzu.ybk.exception.ActivityException;

import org.fzu.ybk.mapper.*;
import org.fzu.ybk.utils.DateFormater;
import org.fzu.ybk.utils.DistanceMetric;
import org.fzu.ybk.utils.SystemParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



@Service
public class ActivityService {
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    PublishedActivityMapper publishedActivityMapper;
    @Autowired
    private OrgnizationMapper orgnizationMapper;
    @Autowired
    private OrgMemberMapper orgMemberMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResponseService responseService;
    @Autowired
    private DateFormater dateFormater;

    @Autowired
    private RichTextService richTextService;


    private final Logger logger = LoggerFactory.getLogger(ActivityService.class);


    public String createActivity(PublishedActivity publishedActivity, HttpServletRequest request){

        //需要做权限验证;比如签到活动同一时刻只能有一个激活

//        String userName = request.getSession().getAttribute(GlobalConstant.sessionUser).toString();
        String username = SystemParams.username;
        Long userId = SystemParams.userId;

        Long orgCode = publishedActivity.getOrgCode();
        if (orgCode == null){
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"未指定班课号");
        }

        Long orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);
        if (orgMemberMapper.userInOrgnization(userId,orgId) == Boolean.FALSE)
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"用户不在指定班课中");

        if (publishedActivity.getActivityTypeId() == null)
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"未指定活动类型");

        if (publishedActivity.getMaxDist() != null)
            if (publishedActivity.getLatitude() == null || publishedActivity.getLongitude() == null)
                return responseService.responseFactory(StatusCode.RESPONSE_ERR,"经纬度不可为空");

        // 设置publishedActivity初始值，
        // 其中ActivityTypeId,
        // activity_description(活动描述),
        // activity_param (活动参数) 一般需要带answer字段
        // 一般需要提供 publishedActivity.getSubmitParams();
        JSONObject submitParams = new JSONObject();
        if (submitParams != null){
            submitParams.put("answer",publishedActivity.getAnswer());
            String jsonString = submitParams.toJSONString();
            publishedActivity.setActivityParam(jsonString);
        }
        publishedActivity.setIsActive(Boolean.TRUE);
        publishedActivity.setBeginDate(dateFormater.getDate());
        publishedActivity.setOrgId(orgId);
        publishedActivity.setCreator(username);

        // 检查结束时间是否错误
        if (publishedActivity.getEndDate() != null){
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //这里的时间格式根据自己需求更改（注意：格式区分大小写、格式区分大小写、格式区分大小写）
            try{
                Date date = (Date)formatter.parse(publishedActivity.getEndDate());
            }catch(Exception e){
                return responseService.responseFactory(StatusCode.RESPONSE_ERR,"时间格式错误，必须是 yyyy-MM-dd HH:mm:ss");
            }
        }


        publishedActivityMapper.insert(publishedActivity);

        //返回新创建的activityID
        Long id = publishedActivityMapper.getLastActivityId();
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"创建成功",id);
    }

    public String getActivitiesByOrgCode(Long orgCode, HttpServletRequest request) throws Exception{

        //需要做权限验证

//        String userName = request.getSession().getAttribute(GlobalConstant.sessionUser).toString();
        String username = SystemParams.username;
        Long userId = SystemParams.userId;

        if (orgCode == null)
            throw new ActivityException("错误的班课号");

        Long orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);

        Boolean result =  orgMemberMapper.userInOrgnization(userId,orgId);
        if (result == Boolean.FALSE)
            throw new ActivityException("用户不在班课中或无权限");

        QueryWrapper<PublishedActivity> wrapper = new QueryWrapper<>();
//        wrapper.eq("is_active",1)
//                .eq("org_id",orgId);
        wrapper.eq("org_id",orgId) //还需要返回未进行的活动，所以注释了原来只需要进行活动查询条件
                .orderByDesc("creation_date");//按创建活动时间倒序排，后创建的排前面
        List<PublishedActivity> results;
        results = publishedActivityMapper.selectList(wrapper);

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",results);
    }


    public String modifyActivity(PublishedActivity publishedActivity, HttpServletRequest request){

        //需要做权限验证;比如签到活动同一时刻只能有一个激活


        // 设置publishedActivity初始值，
        // 其中ActivityTypeId,
        // activity_description(活动描述),
        // activity_param (活动参数) 一般需要带answer字段
        // 一般需要提供 publishedActivity.getSubmitParams();

        JSONObject submitParams = new JSONObject();
        if (publishedActivity.getAnswer() != null ){
            submitParams.put("answer",publishedActivity.getAnswer());
            String jsonString = submitParams.toJSONString();
            publishedActivity.setActivityParam(jsonString);
        }

        // 检查结束时间是否错误
        if (publishedActivity.getEndDate() != null){
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //这里的时间格式根据自己需求更改（注意：格式区分大小写、格式区分大小写、格式区分大小写）
            try{
                Date date = (Date)formatter.parse(publishedActivity.getEndDate());
            }catch(Exception e){
                return responseService.responseFactory(StatusCode.RESPONSE_ERR,"时间格式错误，必须是 yyyy-MM-dd HH:mm:ss");
            }
        }

        publishedActivityMapper.updateById(publishedActivity);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"更新活动信息成功");
    }

    public String closeActivityById(Long activityId, HttpServletRequest request){

        //需要做权限验证

        PublishedActivity publishedActivity = new PublishedActivity();
        publishedActivity.setIsActive(Boolean.FALSE);
        publishedActivity.setEndDate(dateFormater.getDate());
        publishedActivity.setId(activityId);
        publishedActivityMapper.updateById(publishedActivity);

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"活动成功被结束");
    }


    // 下面是参与活动部分(包含更新活动答案 等)
    public String attendActivity(AttendActivity attendActivity, HttpServletRequest request) throws Exception{

        //权限验证
//        String userName = request.getSession().getAttribute(GlobalConstant.sessionUser).toString();
        String username = SystemParams.username;
        Long userId = SystemParams.userId;

        Long activityId = attendActivity.getActivityId();
        if (activityId == null)
            throw new ActivityException("请提供活动id");

        PublishedActivity publishedActivity = publishedActivityMapper.selectById(activityId);

        attendActivity.setActivityTypeId(publishedActivity.getActivityTypeId());

        if (publishedActivity == null)
            throw new ActivityException("不存在的活动id");

        if (publishedActivity.getIsActive() == Boolean.FALSE)
            throw new ActivityException("已经结束的活动");

        String endDateStr = publishedActivity.getEndDate();


        if (endDateStr != null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                Date endDate = (Date)simpleDateFormat.parse(endDateStr);
                Date nowDate = new Date();
                if (nowDate.compareTo(endDate) > 0){
                    PublishedActivity publishedActivity2 = new PublishedActivity();
                    publishedActivity2.setIsActive(Boolean.FALSE);
                    publishedActivity2.setId(activityId);
                    publishedActivityMapper.updateById(publishedActivity2);
                    logger.info("now date"+nowDate.toString());
                    logger.info("end date"+endDate.toString());
                    throw new ActivityException("已经结束的活动");
                }

            }catch(Exception e){
                return responseService.responseFactory(StatusCode.RESPONSE_ERR,"数据存储的结束时间格式错误，必须是 yyyy-MM-dd HH:mm:ss");
            }


        }


        String activityParams = publishedActivity.getActivityParam();
        String answer = null;
        if (activityParams != null){
            JSONObject jsonObject = JSON.parseObject(activityParams);
            answer = (String) jsonObject.get("answer");
        }

        String submit = attendActivity.getAnswer(); //提交的答案
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("answer",submit);
        attendActivity.setSubmitParam(jsonObject2.toJSONString()); //为提交参数加入提交的answer

        Boolean consistent = Boolean.TRUE;

        // 任务提交 id=2
        if (attendActivity.getActivityTypeId() == 2){
            if (attendActivity.getSubmitFileUrl() != null && attendActivity.getSubmitFileName()!=null)
            attendActivity.setScore(publishedActivity.getMaxscore());

        }
        // 签到活动提交 id=1
        // 限制签到距离
        //比较答案和提交是否一致
        if (attendActivity.getActivityTypeId() == 1 ){
            if (attendActivity.getLatitude() ==null || attendActivity.getLongitude() == null){
                return responseService.responseFactory(StatusCode.RESPONSE_ERR,"经纬度不能为空");
            }

            if (publishedActivity.getMaxDist()!=null){
                Double maxDist = Double.valueOf(publishedActivity.getMaxDist()) ;
                Double lat = Double.valueOf(publishedActivity.getLatitude());
                Double logt = Double.valueOf(publishedActivity.getLongitude());
                Double userLat = Double.valueOf(attendActivity.getLatitude());
                Double userLogt = Double.valueOf(attendActivity.getLongitude());


                Double dist = DistanceMetric.getDistance(lat,logt,userLat,userLogt);
                logger.info(username + " 签到距离: " + dist.toString());
                System.out.println(username + " 签到距离: " + dist.toString());

                if (dist > maxDist){
                    return responseService.responseFactory(StatusCode.RESPONSE_ERR,"距离活动源距离超出限制");
                }
            }

            if ( (submit == null && answer == null) || submit.equals(answer) )
            {
                attendActivity.setScore(publishedActivity.getMaxscore());
                attendActivity.setValid(Boolean.TRUE);
                consistent = Boolean.TRUE;
            }
            else{
                attendActivity.setScore(0);
                attendActivity.setValid(Boolean.FALSE);
                consistent = Boolean.FALSE;
            }
        }


        attendActivity.setId(null); // 防止id被恶意写入，清空一下
        attendActivity.setGroupId(null);
        attendActivity.setUserId(userId);

        // 判断是否首次参与,一个活动一个账户只能参与一次
        QueryWrapper<AttendActivity> wrapper = new QueryWrapper<>();
        wrapper.eq("activity_id",activityId)
                .eq("user_id",userId);
        AttendActivity result =  activityMapper.selectOne(wrapper);

        //首次参与
        if (result == null){
            attendActivity.setEditTimes(1);
            activityMapper.insert(attendActivity);
        }
        else if (result.getValid() == Boolean.TRUE || attendActivity.getActivityTypeId() != 1){
            //已经成功参与，拒绝再次参与
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"已经参加过该活动");
        }
        else if (attendActivity.getActivityTypeId() == 1){

            attendActivity.setId(result.getId());
            attendActivity.setEditTimes(result.getEditTimes() + 1);
            activityMapper.updateById(attendActivity);
        }

        if (consistent == Boolean.TRUE)
            return responseService.responseFactory(StatusCode.RESPONSE_OK,"参与成功");
        else
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,"答案错误");
    }



    //=-============



    public String updateAttendedActivity(AttendActivity attendActivity,  HttpServletRequest request) throws Exception{

        String username = SystemParams.username;
        Long userId = SystemParams.userId;

        Long p_activityId = attendActivity.getId();
        if (p_activityId == null)
            throw new ActivityException("请提供参与活动的id");

        Long activityId = attendActivity.getActivityId();
        if (activityId == null)
            throw new ActivityException("请提供活动id");

        PublishedActivity publishedActivity = publishedActivityMapper.selectById(activityId);

        if (publishedActivity == null)
            throw new ActivityException("不存在的活动id");

        if (publishedActivity.getIsActive() == Boolean.FALSE)
            throw new ActivityException("已经结束的活动");

        String endDateStr = publishedActivity.getEndDate();


        boolean timeout = false;
        if (endDateStr != null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                Date endDate = (Date)simpleDateFormat.parse(endDateStr);
                Date nowDate = new Date();
                logger.info("See here!! Time!!");
                logger.info(nowDate.toString());
                logger.info(endDate.toString());
                logger.info( String.valueOf(nowDate.compareTo(endDate)));
                if (nowDate.compareTo(endDate) > 0){
//                    PublishedActivity publishedActivity2 = new PublishedActivity();
//                    publishedActivity2.setIsActive(Boolean.FALSE);
//                    publishedActivity2.setId(activityId);
//                    publishedActivityMapper.updateById(publishedActivity2);
//                    throw new ActivityException("已经结束的活动");
                    timeout = true;
                }

            }catch(Exception e){
                return responseService.responseFactory(StatusCode.RESPONSE_ERR,"数据存储的结束时间格式错误，必须是 yyyy-MM-dd HH:mm:ss");
            }
        }

        QueryWrapper<AttendActivity> wrapper = new QueryWrapper<>();
        wrapper.eq("id",p_activityId);
        AttendActivity result =  activityMapper.selectOne(wrapper);

        AttendActivity attendActivity1 = new AttendActivity();

        attendActivity1.setId(p_activityId);

        if (attendActivity.getScore() != null)
            attendActivity1.setScore(attendActivity.getScore());


        activityMapper.updateById(attendActivity1);

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"修改成功");

    }


    public String getActivityAttendingState(Long activityId, Integer page,Integer pageSize,
                                                          HttpServletRequest request) throws Exception{

        //权限验证

        if (page == null || pageSize == null){
            page = 1;
            pageSize = 10;
        }

//        String userName = request.getSession().getAttribute(GlobalConstant.sessionUser).toString();
        String username = SystemParams.username;
        Long userId = SystemParams.userId;

        if (activityId == null)
            throw new ActivityException("请提供活动id");

        PublishedActivity publishedActivity = publishedActivityMapper.selectById(activityId);

        if (publishedActivity == null)
            throw new ActivityException("不存在的活动id");

        List<AttendActivity> results;
        Page<AttendActivity> pageManager = new Page<>(page,pageSize);
        QueryWrapper<AttendActivity> wrapper = new QueryWrapper<>();
        wrapper.eq("activity_id",activityId);
        results = activityMapper.selectPage(pageManager,wrapper).getRecords();

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",results);
    }



    public String getUserActivitiesState(Long orgCode,Long page,Long pageSize){
        if (page == null){
            page = 1L;
        }
        if (pageSize == null){
            pageSize = 1000L;
        }
        Long userId = SystemParams.userId;
        Long orgId = orgnizationMapper.getOrgIdByOrgCode(orgCode);
        Long offset = (page-1)*pageSize;
        List<Activity_UserState> results = activityMapper.getUserActivitiesState(userId,orgId,pageSize,offset);

        // 后处理
        for (Activity_UserState result : results) {

            String activityParams = result.getAnswer();
            String answer = null;
            if (activityParams != null) {
                JSONObject jsonObject = JSON.parseObject(activityParams);
                answer = (String) jsonObject.get("answer");
            }

            if (answer == null)
                result.setAnswerLength(0L);
            else
                result.setAnswerLength((long) answer.length());

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateBegin = null;
            Date dateEnd = null;
            if (result.getEndDate() != null) {
                try {
                    dateEnd = formatter.parse(result.getEndDate());
                } catch (Exception ignored) {

                }
            }

            if (result.getBeginDate() != null) {
                try {
                    dateBegin = formatter.parse(result.getBeginDate());
                } catch (Exception ignored) {

                }
            }

            if (dateEnd == null || dateBegin == null)
                result.setDateCompare(0L);
            else {
                int com = dateEnd.compareTo(dateBegin);
                result.setDateCompare((long) com);
            }

            result.setAnswer(null);

        }
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",results);
    }


    public String getActivitiesOrgParStateByActivityId(Long activityId){

       List<ActivityParticipateState> results = activityMapper.getActivitiesOrgParStateByActivityId(activityId);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",results);
    }
    public String getOrgMemberScoreByOrgCode(Long orgCode){

        List<OrgMemberScore> results = activityMapper.getOrgMemberScoreByOrgCode(orgCode);
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",results);
    }


    public String getActivityByActivityId(Long activityId,
                                            HttpServletRequest request) throws Exception{

        //权限验证


        PublishedActivity publishedActivity = publishedActivityMapper.selectById(activityId);

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",publishedActivity);
    }



    public String getActivityByActivityIdAndUserId(Long activityId, Long userId,
                                            HttpServletRequest request) throws Exception{

        //权限验证


//        String userName = request.getSession().getAttribute(GlobalConstant.sessionUser).toString();

        AttendActivity results;
        QueryWrapper<AttendActivity> wrapper = new QueryWrapper<>();
        wrapper.eq("activity_id",activityId);
        wrapper.eq("user_id",userId);
        results = activityMapper.selectOne(wrapper);

        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",results);
    }


    public String getOrgScoreByUserId(Long userId){

        List<OrgMemberScore> results = activityMapper.getOrgScoreByUserId(userId);
        JSONArray jsonArray = richTextService.objectListPlusRichText(results,"className");
        return responseService.responseFactory(StatusCode.RESPONSE_OK,"查询成功",jsonArray);
    }



}
