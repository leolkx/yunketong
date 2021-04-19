package org.fzu.ybk.entity;

/**
 * @description:
 * @author: Mu.xx
 * @date: 2020/4/15 14:46
 */


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 该实体描述了返回一个用户所有允许被查看的信息
 * @author: Mu.xx
 * @date: 2020/4/15 14:46
 * @param: null
 * @return:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllUserInfo {
    private Long id;
    private Long roleId;
    private String username;
    private String nickname;
    private String gender;
    private String studentId;
    private String phone;
    private String email;
    private String school,education,major;
    private String birthDate;
    private String address,city,province,nation;
    private Integer experience,coin;
    private String profilePhotoUrl;
    private String college;

}
