package org.fzu.ybk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.fzu.ybk.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {

//    @Select("select * from student order by id asc")
//    List<Student> getStudentList();
    @Insert("INSERT INTO user (role_id, username, password, salt, state,email, phone,creation_date, is_active, is_deleted) VALUES (#{roleId},#{userName}, #{password},#{salt},#{state} ,#{email},#{phone}, #{createDate}, #{isActive}, #{isDeleted} )")
    void createAccount(Long roleId,String userName, String password, String salt, String state,String email,String phone, String createDate, boolean isActive, boolean isDeleted);

    @Select("SELECT * FROM user ORDER BY id")
    List<User> getUserList();

    @Select("SELECT * FROM user WHERE id= #{id} ")
    List<User> getUser(Long id);

    @Select("SELECT count(id) FROM user WHERE (username = #{userName} or phone = #{userName}) and (is_deleted = 0 or id = 1) limit 1")
    boolean userExist(String userName);

    @Select("SELECT password FROM user WHERE username = #{userName} or phone = #{userName}")
    String getUserPassword(String userName);

    @Select("SELECT username FROM user WHERE email = #{email}")
    List<User> getAccountByEmail(String email);

    @Select("SELECT username FROM user WHERE phone = #{phone}")
    String getUserNameByPhone(String phone);

    @Select("SELECT nickname FROM user WHERE id = #{userId} limit 1")
    String getUserNickNameByUserId(Long userId);

    @Select("SELECT nickname FROM user WHERE username = #{userName} limit 1")
    String getUserNickNameByUserName(String userName);

    @Select("SELECT id FROM user WHERE username = #{userName} limit 1")
    Long getUserIdByUserName(String userName);

    @Select("SELECT username FROM user WHERE id = #{id} limit 1")
    String getUsernameById(Long id);



    @Select("SELECT o.org_code, o.org_name, o.extend_json richTextId, o.creation_date, o.creator FROM org AS o INNER JOIN user_org_info AS r ON r.user_id = #{userId} and r.org_id = o.id")
    List<Orgnization> getUserJoinedOrgnization(Long userId);

    //查询用户加入的组织，不包括自己创建的，或者应该说排除【自己作为群主的群】
    // 目前没有加入群角色控制，简单先用创建人字段应付一下getUserJoinedOrgnizationExcludeCreated
    @Select("SELECT o.org_code, o.org_name, o.extend_json richTextId, o.creation_date, o.creator FROM org AS o INNER JOIN user_org_info AS r ON r.user_id = #{userId} and r.org_id = o.id WHERE o.creator <> #{userName}")
    List<Orgnization> getUserJoinedOrgnizationExcludeCreated(Long userId, String userName);

    //更新用户信息
    @Update("UPDATE user SET nickname = #{nickName}, student_id = #{studentId}, gender = #{gender}, " +
            "profile_photo_url = #{profilePhotoUrl}, school = #{school}, major = #{major}, college = #{college}, " +
            "education=#{education}, birth_date = #{birthDate}, address=#{address},city=#{city}, " +
            "province=#{province}, nation=#{nation} WHERE id = #{userId}")
    void updateUserInfoByUserId(Long userId,String nickName, String studentId, String gender,
                                String profilePhotoUrl, String school, String major, String college,
                                String education, String birthDate, String address, String city,
                                String province, String nation);

    //获取用户可更新信息
    @Select("SELECT nickname, student_id, gender, profile_photo_url, school, major, college," +
            "education, birth_date, address, city, province, nation" +
            " FROM user WHERE id = #{userId}")
    UserUpdate getUserUpdatableInfoByUserId(Long userId);

    //获取SimpleUserInfo
    @Select("SELECT id, username, nickname, student_id, gender, school, college, education, major, birth_date, address, city, province, nation, profile_photo_url FROM user WHERE id = #{userId}")
    SimpleUserInfo getSimpleUserInfoByUserId(Long userId);

    //获取UserAuthenticationInfo，用于shiro用户认证
    @Select("SELECT id, role_id ,username, password, salt , state FROM user WHERE username = #{username}")
    UserAuthenticationInfo getUserAuthenticationInfoByUserName(String username);

    //获取AllUserInfo
    @Select("SELECT id, role_id, username, nickname, student_id, gender, phone, email, school, college, education, major, birth_date, address, city, province, nation, experience, coin, profile_photo_url FROM user WHERE id = #{userId}")
    AllUserInfo getAllUserInfoByUserId(Long userId);


// 带子查询，另一种也可以写成 on + where 哪种快?
//    SELECT org_code, org_name, extend_json richTextId, creation_date, creator FROM (SELECT org_id FROM r_user__org WHERE user_id = #{userId}) LIMIT #{limit} OFFSET #{offset}

    @Select("SELECT o.org_code, o.org_name, o.extend_json richTextId, o.creation_date, o.creator FROM org AS o INNER JOIN user_org_info AS r ON r.user_id = #{userId} and r.org_id = o.id LIMIT #{limit} OFFSET #{offset}")
    List<Orgnization> getUserJoinedOrgnizationPage(Long userId, Long limit, Long offset);

    @Select("SELECT o.org_code, o.org_name, o.extend_json richTextId, o.creation_date, o.creator FROM org AS o INNER JOIN user_org_info AS r ON r.user_id = #{userId} and r.org_id = o.id WHERE o.creator = #{creator}")
    List<Orgnization> getUserCreatedOrgnization(Long userId, String creator);


    @Select("SELECT o.org_code, o.org_name, o.extend_json richTextId, o.creation_date, o.creator FROM org AS o INNER JOIN user_org_info AS r ON r.user_id = #{userId} and r.org_id = o.id WHERE o.creator = #{creator} LIMIT #{limit} OFFSET #{offset}")
    List<Orgnization> getUserCreatedOrgnizationPage(Long userId, String creator, Long limit, Long offset);


    @Select("SELECT salt FROM user WHERE username = #{username} limit 1")
    String getSaltByUsername(String username);
}
