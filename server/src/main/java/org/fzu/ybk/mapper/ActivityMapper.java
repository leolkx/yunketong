package org.fzu.ybk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import org.fzu.ybk.entity.ActivityParticipateState;
import org.fzu.ybk.entity.Activity_UserState;
import org.fzu.ybk.entity.AttendActivity;
import org.fzu.ybk.entity.OrgMemberScore;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: Mu.xx
 * @date: 2020/5/12 20:06
 */

@Repository
public interface ActivityMapper extends BaseMapper<AttendActivity> {

    @Select("select pa2.id activityId, pa2.activity_type_id activityTypeId, pa2.activity_name activityName, " +
            "pa2.begin_date beginDate, pa2.end_date endDate, pa2.is_active isActive, pa2.activity_param answer, " +
            "pia.user_id userId, pia.creation_date creationDate " +
            "from (SELECT pa.id , pa.activity_param, pa.activity_name , pa.activity_type_id , pa.begin_date, pa.end_date, pa.is_active " +
            "from published_activity as pa  where pa.org_id = #{orgId} ) as pa2 LEFT JOIN \n" +
            "participate_in_activity as pia on pia.activity_id = pa2.id  and pia.user_id = #{userId} \n" +
            "order by creationDate DESC LIMIT #{size} OFFSET #{offset}")
    List<Activity_UserState> getUserActivitiesState(Long userId, Long orgId, Long size, Long offset);

    List<Activity_UserState> getClassActivitiesState(Long activityId, Long orgId);


//    @Select(" select  p.score score,r.user_id userId , u.role_id roleId ,  u.username username, u.student_id studentId, o.id orgId , o.org_code orgCode  ,'yes' as isPar from user as u , org as o,user_org_info as r ,participate_in_activity as p" +
//            "where u.id = r.user_id and o.id = r.org_id and o.id in (select org_id from published_activity where id = #{activityId} ) and u.id in (select u.id from participate_in_activity as p,user as u where p.activity_id=#{activityId} and u.id = p.user_id)" +
//            " union" +
//            "  select  p.score score , r.user_id userId , u.role_id roleId ,  u.username username, u.student_id studentId, o.id orgId , o.org_code orgCode ,'no' as isPar from user as u , org as o,user_org_info as r ,participate_in_activity as p " +
//            "where u.id = r.user_id and o.id = r.org_id and o.id in (select org_id from published_activity " +
//            "where id = #{activityId}) and u.id not in (select u.id from participate_in_activity as p,user as u where p.activity_id=#{activityId} and u.id = p.user_id)")
//    List<ActivityParticipateState>getActivitiesOrgParStateByActivityId(Long activityId);


    @Select("select  #{activityId} as activityId , r.user_id as userId, u.role_id as roleId, u.username as username, u.student_id as studentId, o.id as orgId, o.org_code as orgCode, score, p.id as parId, 'yes'as isPar from user as u, org as o, user_org_info as r, participate_in_activity as p where p.activity_id = #{activityId} and u.id = p.user_id and u.id = r.user_id and o.id = r.org_id and o.id in(select org_id from published_activity where id = #{activityId})\n" +
            " union\n"+
            " select #{activityId} as activityId , r.user_id as userId , u.role_id as roleId ,  u.username as username, u.student_id  as studentId, o.id  as orgId , o.org_code as  orgCode,0 as score,0 as parId,'no' as isPar from user as u , org as o,user_org_info as r where  u.id = r.user_id and o.id = r.org_id and o.id in (select org_id from published_activity where id = #{activityId}) and u.id not in (select u.id from participate_in_activity as p,user as u where p.activity_id=#{activityId} and u.id = p.user_id)")
    List<ActivityParticipateState>getActivitiesOrgParStateByActivityId(Long activityId);


    @Select("select o.org_code as orgCode ,u.id as userId ,u.username as username ,u.student_id as studentId,sum(score) as sumScore from user as u, participate_in_activity as p,published_activity as a,org as o " +
            "where o.org_code = #{orgCode} and o.id= a.org_id and p.activity_id = a.id  and u.id = p.user_id and o.id = a.org_id GROUP BY u.id \n" +
            "union\n" +
            "select o.org_code as orgCode ,u.id as userId ,u.username as username,u.student_id as studentId,0 as sumScore from user_org_info as uo ,user as u ,org as o " +
            "where o.org_code= #{orgCode}  and o.id=uo.org_id and u.id = uo.user_id and u.id not in (select u.id from user as u, participate_in_activity as p,published_activity as a,org as o " +
            "where o.org_code = #{orgCode}  and o.id= a.org_id and p.activity_id = a.id  and u.id = p.user_id and o.id = a.org_id )")
    List<OrgMemberScore> getOrgMemberScoreByOrgCode(Long orgCode);

    @Select("select o.extend_json as richTextId,o.org_code as orgCode,o.id as orgId,sum(score) as sumScore from  rich_text as r,participate_in_activity as p,published_activity as a,org as o " +
            "where p.user_id = #{userId} and p.activity_id = a.id and o.extend_json = r.id  and o.id = a.org_id GROUP BY o.id\n" +
            "\tunion\n" +
            "\tselect o.extend_json as richTextId,o.org_code as orgCode,uo.org_id as orgId ,0 as sumScore from rich_text as r,user_org_info as uo,org as o " +
            "where uo.user_id = #{userId} and uo.org_id = o.id and o.extend_json = r.id and uo.org_id not in (select o.id from participate_in_activity as p,published_activity as a,org as o where p.user_id = #{userId} and p.activity_id = a.id and o.id = a.org_id  )")
    List<OrgMemberScore> getOrgScoreByUserId(Long userId);

}
