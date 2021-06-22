package org.fzu.ybk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.fzu.ybk.entity.Orgnization;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface OrgnizationMapper extends BaseMapper<Orgnization> {

    @Insert("INSERT INTO org (org_code, org_name, extend_json, creation_date, creator, is_deleted, lesson_enddate) VALUES (#{ordCode}, #{orgName}, #{richTextId},  #{createDate}, #{creator}, #{isDeleted}, #{lessonEndDate} )")
    void cerateOrgnization(Long ordCode, String orgName,  Long richTextId, String createDate, String creator, boolean isDeleted, Date lessonEndDate);

    @Select("SELECT count(id) FROM org WHERE org_code = #{orgCode} limit 1")
    boolean OrgExistByOrgCode(Long orgCode);

    @Select("SELECT lesson_enddate FROM org WHERE org_code = #{orgCode}")
    Date lessonEndDatebyOrgCode(Long orgCode);

    @Select("SELECT count(id) FROM org WHERE id = #{orgId} limit 1")
    boolean OrgExistByOrgId(Long orgId);

    @Select("SELECT extend_json FROM org WHERE org_code = #{orgCode} ")
    Long geRichTextIdByOrgCode(Long orgCode);

    @Select("SELECT extend_json FROM org WHERE id = #{orgId} ")
    Long geRichTextIdByOrgId(Long orgId);

    @Select("SELECT org_code, org_name, extend_json richTextId, creation_date, creator FROM org WHERE org_code = #{orgCode}")
    Orgnization getOrgInfoByOrgCode(Long orgCode);

    @Delete("DELETE FROM org WHERE org_code = #{orgCode}")
    Long deleteOrgnizationByOrgCode(Long orgCode);

    @Delete("DELETE FROM org WHERE id = #{orgId}")
    Long deleteOrgnizationByOrgId(Long orgId);

    @Select( "SELECT id FROM org WHERE org_code = #{orgCode} limit 1 ")
    Long getOrgIdByOrgCode(Long orgCode);

    @Select( "SELECT org_code FROM org order by id DESC limit 1" )
    Long getLastOrgCode();

    @Select( "SELECT id FROM org order by id DESC limit 1" )
    Long getLastOrgId();



    //获得创建者用户名
    @Select("SELECT creator FROM org WHERE id = #{orgId}")
    String getCreatorByOrgId(Long orgId);




}
