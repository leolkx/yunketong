package org.fzu.ybk.mapper;

import org.apache.ibatis.annotations.*;
import org.fzu.ybk.entity.ClassMember;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: Mu.xx
 * @date: 2020/4/9 16:14
 */


@Component
@Mapper
public interface OrgMemberMapper {

    // 班级成员的增删查改
    @Select("SELECT COUNT(user_id) FROM user_org_info WHERE user_id = #{userId} and org_id = #{orgId} ")
    Boolean userInOrgnization(Long userId,Long orgId);

//    @Insert("INSERT INTO user_org_info (user_id, org_id) VALUES (#{userId}, #{orgId} )")
//    void userJoinOrgnization(long userId,long orgId);

    @Insert("INSERT INTO user_org_info (user_id, org_id, user_org_exp, extend_json, creation_date, is_deleted) VALUES (#{userId}, #{orgId}, #{user_org_exp}, #{richTextId}, #{creationDate}, #{isDeleted} )")
    void addUserToOrgnization(Long userId,Long orgId, Long user_org_exp, Long richTextId,  String creationDate, boolean isDeleted);

    /**
     * @description:
     * 成员-组关系的删除最好还是软删除...
     * 成员-组关系最好用联合主键+分开主键，都建一遍，加速查找
     * @author: Mu.xx
     * @date: 2020/4/9 16:37
     * @param: userId
     * @param: orgId
     * @return: void
     */
    @Delete("DELETE FROM user_org_info WHERE user_id = #{userId} and org_id = #{orgId}")
    void deleteUserFromOrgnization(Long userId,Long orgId);


    @Select("SELECT extend_json FROM user_org_info WHERE user_id = #{userId} AND org_id = #{orgId}  ")
    Long getRichTextId(Long userId, Long orgId);

    @Select("SELECT user_org_exp FROM user_org_info WHERE user_id = #{userId} AND org_id = #{orgId} ")
    Long getExperience(Long userId, Long orgId);

    @Update("UPDATE user_org_info SET user_org_exp = #{exp} WHERE user_id = #{userId} AND org_id = #{orgId}")
    void updateExperience(Long userId, Long orgId, Long exp);

//    @Delete("DELETE FROM user_org_info WHERE user_id = #{userId} and org_id = #{orgId}")
//    void deleteOrgMemberInfo(long userId,long orgId);


//    @Select("SELECT r.extend_json FROM user AS u INNER JOIN user_org_info AS r ON r.org_id = #{orgId} and r.user_id = u.user_id ")
//    List<Long> getMembersRichTextIdByOrgId(long orgId);

    @Select("SELECT u.id userId, u.username, r.user_org_exp userClassExp, r.extend_json richTextId FROM user AS u INNER JOIN user_org_info AS r ON r.org_id = #{orgId} and r.user_id = u.id")
    List<ClassMember> getMembersByOrgId(long orgId);

    @Select("SELECT u.id userId, u.username, r.user_org_exp userClassExp, r.extend_json richTextId FROM user AS u INNER JOIN user_org_info AS r ON r.org_id = #{orgId} and r.user_id = u.id LIMIT #{limit} OFFSET #{offset} ")
    List<ClassMember> getMembersPageByOrgId(long orgId, long limit, long offset);


    //删除班级->删除所有对应的班级-成员关系
    @Delete("DELETE FROM user_org_info WHERE org_id = #{orgId}")
    void deleteAllUsersFromOrgnization(Long orgId);

    //获得班课成员数目
    @Select("SELECT COUNT(1) FROM user_org_info WHERE org_id = #{orgId} AND is_deleted = 0")
    Long getClassMemberNumber(Long orgId);

}
